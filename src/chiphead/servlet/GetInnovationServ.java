package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import chiphead.config.Constants;
import chiphead.model.AutCheck;
import chiphead.model.ComputeWeekNum;
import chiphead.model.CurrentStatus;
import chiphead.model.Innovation;
import chiphead.model.Operation;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.RootProjectPhase;
import chiphead.utils.DateUtil;
import chiphead.utils.PersonUtil;

public class GetInnovationServ extends HttpServlet {

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
		String innoNoString = request.getParameter("inno_no");
		innoNoString = innoNoString.replaceAll("\r|\n", "");
		Integer innoNo;
		if(innoNoString != null && !innoNoString.isEmpty()){
			innoNo = Integer.parseInt(innoNoString);
		}
		else{
			innoNo = null;
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
		//根据编号在innovations中查询一条
		Innovation in = session.selectOne("InnovationMapper.selectInnovationByInnoNo",innoNo);
		//人员不存在，或创新记录不存在，报错返回
		if(per == null || in ==null){
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
		} else if (!AutCheck.HaveChangeInnoAut(innoNo,in.getConclusion(),empYstId,session)) {
			//判断是否有修改权限(结论字段只能由管理员或超级管理员修改，其他字段不设权限限制)
			out.print("noauth");
			session.close();
			out.flush();
			out.close();
			return;
		}
		
			Map cellMap = new LinkedHashMap();
			String regDate = DateUtil.getChgDateStr(in.getRegDate());//2013/11/11格式字符串
			String innoType = in.getInnoType().toString();//Integer
			String proposers = PersonUtil.getJsonPersonsStr(in.getProposers(),session);//nomal->Json
			String system = in.getSystem().toString();//Integer
			String module = in.getModule();
			String suggestion = in.getSuggestion();
			String reason = in.getReason();
			String addSuggestion = in.getAddSuggestion();
			String conclusion = in.getConclusion();
			String executors = PersonUtil.getJsonPersonsStr(in.getExecutors(),session);//nomal->Json
			String effect = in.getEffect();
			
			cellMap.put("inno_no", innoNo); 
			cellMap.put("reg_date", regDate);
			cellMap.put("inno_type", innoType); 
			cellMap.put("proposers", proposers);
			cellMap.put("system", system);
			cellMap.put("module", module);
			cellMap.put("suggestion", suggestion);
			cellMap.put("reason", reason);
			cellMap.put("add_suggestion", addSuggestion);
			cellMap.put("conclusion", conclusion);
			cellMap.put("executors", executors);
			cellMap.put("effect", effect);
			
		    String str = gson.toJson(cellMap);			
			out.print(str);//输出到前台
			session.close();
			out.flush();
			out.close();
	}
}
