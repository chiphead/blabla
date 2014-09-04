package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.model.AutCheck;
import chiphead.model.Emergency;
import chiphead.model.Person;
import chiphead.utils.DateUtil;
import chiphead.utils.PersonUtil;

import com.google.gson.Gson;

public class GetEmergencyServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		PrintWriter out = response.getWriter();
		
		SqlSession session = sqlSessionFactory.openSession();
		
		Gson gson = new Gson();
		
		//取出用于登录检查及修改权限检查的信息
		String emerNoString = request.getParameter("emer_no");
		emerNoString = emerNoString.replaceAll("\r|\n", "");
		Integer emerNo;
		if(emerNoString != null && !emerNoString.isEmpty()){
			emerNo = Integer.parseInt(emerNoString);
		}
		else{
			emerNo = null;
		}
		
		String empYstIdString = request.getParameter("emp_yst_id");
		Integer empYstId;
		if(empYstIdString != null && !empYstIdString.isEmpty()){
			empYstId = Integer.parseInt(empYstIdString);
		}
		else{
			empYstId = null;
		}
		
		String empPwd = request.getParameter("emp_pwd");		

		//根据一事通id从persons取数据
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		//根据编号在emergency中查询一条
		Emergency em = session.selectOne("EmergencyMapper.selectEmergencyByEmerNo", emerNo);
		//人员不存在，或记录不存在，报错返回
		if(per == null || em ==null){
			session.close();
			out.print(false);
			out.flush();
			out.close();
			return;
		}
		
		//判断登录信息是否正确
		if (!AutCheck.YstPwdCorrect(empYstId,empPwd,session) ) {
			out.print("notlogged");
			session.close();
			out.flush();
			out.close();
			return;
//		} else if (!AutCheck.HaveChangeInnoAut(innoNo,in.getConclusion(),empYstId,session)) {
//			//判断是否有修改权限(结论字段只能由管理员或超级管理员修改，其他字段不设权限限制)
//			out.print("noauth");
//			session.close();
//			out.flush();
//			out.close();
//			return;
		}
		
		Map cellMap = new LinkedHashMap();
		
		String operators = PersonUtil.getJsonPersonsStr(em.getOperators(), session);
		String responsible = PersonUtil.getJsonPersonsStr(em.getResponsible(), session);
		String regDateString = DateUtil.getChgDateStr(em.getRegDate());
		String emerDateString = DateUtil.getChgDateStr(em.getEmerDate());
		
		cellMap.put("emer_no", em.getEmerNo());
		cellMap.put("reg_date", regDateString);
		cellMap.put("emer_date", emerDateString);
		cellMap.put("emer_content", em.getEmerContent());
		cellMap.put("influence", em.getInfluence());
		cellMap.put("analytics", em.getAnalytics());
		cellMap.put("code", em.getCode());
		cellMap.put("level", em.getLevel());
		cellMap.put("operators", operators);
		cellMap.put("proj_no", em.getProjNo());
		cellMap.put("proj_name", em.getProjName());
		cellMap.put("proj_manager", em.getProjManager());
		cellMap.put("responsible", responsible);
		cellMap.put("system", em.getSystem());
		cellMap.put("remark", em.getRemark());
		
	    String str = gson.toJson(cellMap);			
		out.print(str);//输出到前台
		session.close();
		out.flush();
		out.close();
	}
}
