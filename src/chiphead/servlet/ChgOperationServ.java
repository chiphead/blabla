package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import chiphead.model.AutCheck;
import chiphead.model.ComputeWeekNum;
import chiphead.model.Operation;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.RootProjectPhase;
import chiphead.utils.DateUtil;
import chiphead.utils.EncUtil;
import chiphead.utils.PersonUtil;

public class ChgOperationServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		String resource = "chiphead/config/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		PrintWriter out = response.getWriter();

		SqlSession session = sqlSessionFactory.openSession();
		
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
		
		
		try {
				//获得要更新的信息
				String operTypeStr = request.getParameter("oper_type");
				String operStartDateStr = request.getParameter("oper_start_date");
				String operStartTimeStr = request.getParameter("oper_start_time");
				String operEndDateStr = request.getParameter("oper_end_date");
				String operEndTimeStr = request.getParameter("oper_end_time");
				
				String operStartDateTimeStr = operStartDateStr + " " + operStartTimeStr;
				String operEndDateTimeStr = operEndDateStr + " " + operEndTimeStr;
				
				Date regDate = DateUtil.getCurDate();//登记时间为当前系统时间
				Integer operType = Integer.parseInt(operTypeStr);
				
				Timestamp operStartTime = null;
				Timestamp operEndTime = null;
				
				if(!operStartDateStr.isEmpty() && !operStartTimeStr.isEmpty()){
					operStartTime = DateUtil.getDateTime(operStartDateTimeStr);
				}
				if(! operEndDateStr.isEmpty() && !operEndTimeStr.isEmpty()){
					operEndTime = DateUtil.getDateTime(operEndDateTimeStr);
				}
				
				String operation = request.getParameter("operation");
				String levelStr = request.getParameter("level");
				Integer level = Integer.parseInt(levelStr);
				
				String operatorsJsonStr = request.getParameter("operators");
				String operators = PersonUtil.getNormalPersonsStr(operatorsJsonStr);
				
				/*
				String systemStr = request.getParameter("system");
				Integer system = Integer.parseInt(systemStr);
				String moduleStr = request.getParameter("module");
				Integer module = Integer.parseInt(moduleStr);
				
				String belongPersonsJsonStr = request.getParameter("belong_persons");
				String belongPersons = PersonUtil.getNormalPersonsStr(belongPersonsJsonStr);
				*/
				
				String system = request.getParameter("system");
				String belongPersons = request.getParameter("belong_persons");
				String affectedBusiness = request.getParameter("affected_business");
				
				String reason = request.getParameter("reason");
				String suggestion = request.getParameter("suggestion");
				
				String remark = request.getParameter("remark");
				
				Integer checkState = 1;//修改之后默认未审批
				
				op.setRegDate(regDate);
				op.setOperType(operType);
				op.setOperStartTime(operStartTime);
				op.setOperEndTime(operEndTime);
				op.setOperation(operation);
				op.setLevel(level);
				op.setOperators(operators);
				op.setSystem(system);
				//op.setModule(module);
				op.setBelongPersons(belongPersons);
				op.setAffectedBusiness(affectedBusiness);
				op.setReason(reason);
				op.setSuggestion(suggestion);
				op.setRemark(remark);
				op.setCheckState(checkState);
				
				session.update("OperationMapper.updateOperationByOperNo", op);
	 			session.commit();
	 			
				out.print(true);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(false);
		} finally {
			session.close();
		}
		out.flush();
		out.close();
	}
}
