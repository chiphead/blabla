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
import chiphead.model.Operation;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.RootProjectPhase;
import chiphead.utils.DateUtil;
import chiphead.utils.PersonUtil;

public class GetOperationServ extends HttpServlet {

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
		String operNoString = request.getParameter("oper_no");
		operNoString = operNoString.replaceAll("\r|\n", "");
		Integer operNo;
		if(operNoString != null && !operNoString.isEmpty()){
			operNo = Integer.parseInt(operNoString);
		}
		else{
			operNo = null;
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
		//根据编号在operations中查询一条
		Operation op = session.selectOne("OperationMapper.selectOperationByOperNo", operNo);
		//人员不存在，或运维记录不存在，报错返回
		if(per == null || op ==null){
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
		} else if (!AutCheck.HaveChangeOperAut(operNo,empYstId,session)) {
			//判断是否有修改权限
			out.print("noauth");
			session.close();
			out.flush();
			out.close();
			return;
		}
		
		  
		Map cellMap = new LinkedHashMap();
		
		cellMap.put("oper_no", op.getOperNo().toString()); 
		cellMap.put("oper_type", op.getOperType().toString());
		String startDateTimeStr[] = DateUtil.getDateTimeStringArray(op.getOperStartTime());
		cellMap.put("oper_start_date", startDateTimeStr[0]);
		cellMap.put("oper_start_time", startDateTimeStr[1]);
		
		String endDateTimeStr[] = DateUtil.getDateTimeStringArray(op.getOperEndTime());
		cellMap.put("oper_end_date", endDateTimeStr[0]);
		cellMap.put("oper_end_time", endDateTimeStr[1]);
		
		cellMap.put("operation", op.getOperation()==null?"":op.getOperation());
		cellMap.put("level", op.getLevel().toString());
		
		String operators = PersonUtil.getJsonPersonsStr(op.getOperators(),session);//nomal->Json
		
		cellMap.put("operators", operators);
		
		/*
		cellMap.put("system", op.getSystem().toString());
		cellMap.put("module", op.getModule().toString());
		
		String belongPersons = PersonUtil.getJsonPersonsStr(op.getBelongPersons(),session);//nomal->Json
		*/
		cellMap.put("system", op.getSystem()==null?"":op.getSystem());
		cellMap.put("belong_persons",op.getBelongPersons()==null?"":op.getBelongPersons());
		cellMap.put("affected_business", op.getAffectedBusiness()==null?"":op.getAffectedBusiness());
		cellMap.put("reason", op.getReason()==null?"":op.getReason());
		cellMap.put("suggestion", op.getSuggestion()==null?"":op.getSuggestion());
		cellMap.put("remark", op.getRemark()==null?"":op.getRemark());
		
	    String str = gson.toJson(cellMap);			
		out.print(str);//输出到前台
		session.close();
		out.flush();
		out.close();
	}

}
