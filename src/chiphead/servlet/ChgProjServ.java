package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
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

import chiphead.config.Constants;
import chiphead.model.AutCheck;
import chiphead.model.ComputeWeekNum;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.RootProjectPhase;
import chiphead.utils.DateUtil;
import chiphead.utils.EncUtil;

public class ChgProjServ extends HttpServlet {

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
		
		//取出用于登录检查及修改权限检查的信息
		String projNo = request.getParameter("proj_no");
		projNo = projNo.replaceAll("\r|\n", "");
		String emp_name = request.getParameter("emp_name");
		String empYstIdString = request.getParameter("emp_yst_id");
		String empPwd = request.getParameter("emp_pwd");
		
		//根据项目编号在projects中查询一条
		Project pro = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
		
		if (empYstIdString == null || empYstIdString.equals("") || pro==null) {
			session.close();
			out.print(false);
			out.flush();
			out.close();
			return;
		}
		
		Integer empYstId = Integer.parseInt(empYstIdString);

		if (!AutCheck.YstPwdCorrect(empYstId,empPwd,session)) {
			out.print("notlogged");
			session.close();
			out.flush();
			out.close();
			return;
		} else if (!AutCheck.HaveChangeProjAut(projNo,emp_name,session)) {
			out.print("noauth");
			session.close();
			out.flush();
			out.close();
			return;
		}
		try {
			//取出要存入projects表的信息
			//更新时间为系统时间
			String reqNo = request.getParameter("req_no");
			String projName = request.getParameter("proj_name");
			String projType = request.getParameter("proj_type");//Integer
			String masterSlave = request.getParameter("master_slave");
			String projCharge = request.getParameter("proj_charge");
			String projManager = request.getParameter("proj_manager");
			String curState = request.getParameter("cur_state");//Integer
			String riskQuestion = request.getParameter("risk_question");
			String designer = request.getParameter("designer");	// 三个人员以json传入，作处理*
			String developer = request.getParameter("developer");
			String auditor = request.getParameter("auditor");
			String startDateStr = request.getParameter("start_date");//Date
			String actualInstallDateJsonStr = request.getParameter("actual_install_date");//String
			String planEndDateStr = request.getParameter("plan_end_date");//Date
			String actualEndDateStr = request.getParameter("actual_end_date");//Date
			String actualDays = request.getParameter("actual_days");//Integer
			//String overdueReason = request.getParameter("overdue_reason");
			String planWorkAmount = request.getParameter("plan_work_amount");//Double
			String actualWorkAmount = request.getParameter("actual_work_amount");//Double
			String functionAmount = request.getParameter("function_amount");//Double
			String productivity = request.getParameter("productivity");//Double
			String smokeNum = request.getParameter("smoke_num");//Integer
			String stType = request.getParameter("st_type");//Integer
			String stBugDensity = request.getParameter("st_bug_density");//Double
			String nc = request.getParameter("nc");
			String designAudit = request.getParameter("design_audit");
			String codeAudit = request.getParameter("code_audit");
			String installAudit = request.getParameter("install_audit");
			String experience = request.getParameter("experience");
			String problemAnalysis = request.getParameter("problem_analysis");
			String remark = request.getParameter("remark");
			Integer evaluateState = 1;//默认未审批
			
			Project project = new Project();
			project.setUpdateTime(DateUtil.getCurDateTime());
			project.setReqNo(reqNo);
			project.setProjNo(projNo);
			project.setProjName(projName);
			project.setProjType(Integer.parseInt(projType));
			project.setMasterSlave(Integer.parseInt(masterSlave));
			project.setProjManager(projManager);
			project.setCurState(Integer.parseInt(curState));
			project.setRiskQuestion(riskQuestion);
			project.setProjCharge(projCharge);
			Date startDate = DateUtil.getDate(startDateStr);
			project.setStartDate(startDate);
			
			//获得要插入的多个日期，json字符串转成普通字符串后插入
			Gson gson = new Gson();
			String actualInstallDate = "";
			List<DateTemp> pt = gson.fromJson(actualInstallDateJsonStr, new TypeToken<List<DateTemp>>(){}.getType());
			for(int i = 0; i < pt.size() ; i++)
			{
				if(i == pt.size()-1){
					actualInstallDate += pt.get(i).getDate();
				}
				else{
					actualInstallDate += pt.get(i).getDate() + ",";
				}	
			}
			project.setActualInstallDate(actualInstallDate);
			
			project.setPlanEndDate(DateUtil.getDate(planEndDateStr));
			if (actualEndDateStr.trim().isEmpty()) {
				project.setActualEndDate(null);
			} else {
				Date actualEndDate = DateUtil.getDate(actualEndDateStr);
				project.setActualEndDate(actualEndDate);	
			}
			
			if (!actualDays.equals(""))
				project.setActualDays(Integer.parseInt(actualDays));
			else
				project.setActualDays(null);
			//project.setOverdueReason(overdueReason);
			project.setOverdueReason("");
			if (!planWorkAmount.equals(""))
				project.setPlanWorkAmount(Double.parseDouble(planWorkAmount));
			else
				project.setPlanWorkAmount(null);
			
			if (!actualWorkAmount.equals(""))
				project.setActualWorkAmount(Double.parseDouble(actualWorkAmount));
			else
				project.setActualWorkAmount(null);
			
			if (!functionAmount.equals(""))
				project.setFunctionAmount(Double.parseDouble(functionAmount));
			else
				project.setFunctionAmount(null);
			
			if (!productivity.equals(""))
				project.setProductivity(Double.parseDouble(productivity));
			else
				project.setProductivity(null);
			
			if (!smokeNum.equals(""))
				project.setSmokeNum(Integer.parseInt(smokeNum));
			else
				project.setSmokeNum(null);
			
			if (!stType.equals(""))
				project.setStType(Integer.parseInt(stType));
			else
				project.setStType(null);
			
			if (!stBugDensity.equals(""))
				project.setStBugDensity(Double.parseDouble(stBugDensity));
			else
				project.setStBugDensity(null);
			
			project.setNc(nc);
			project.setDesignAudit(designAudit);
			project.setCodeAudit(codeAudit);
			project.setInstallAudit(installAudit);
			project.setExperience(experience);
			project.setProblemAnalysis(problemAnalysis);
			project.setRemark(remark);
			
			//若项目后评估内容未修改 则评估状态不变，否则变为未审批
			if(pro.getExperience().equals(project.getExperience()) &&
					pro.getProblemAnalysis().equals(project.getProblemAnalysis()) &&
					pro.getRemark().equals(project.getRemark())){
				project.setEvaluateState(pro.getEvaluateState());//设为原状态
			}
			else{
				project.setEvaluateState(evaluateState);//未审批
			}
			
			//取出要存入person_project表的信息
			List<Person> designerList = gson.fromJson(designer, new TypeToken<List<Person>>(){}.getType());
			List<Person> developerList = gson.fromJson(developer, new TypeToken<List<Person>>(){}.getType());
			List<Person> auditorList = gson.fromJson(auditor, new TypeToken<List<Person>>(){}.getType());
			
			//取出要存入root_project_phase表的信息
			int numOfRootPhases = 8;
			String[] params = new String[numOfRootPhases];
			String[][] phases = new String[numOfRootPhases][6];
			 
			for (int i = 1; i <= numOfRootPhases; i++) {
				params[i-1] = request.getParameter("phase_" + i);
				phases[i-1] = params[i-1].split("-");
			}
			//System.out.println(phases);
			
			//更新projects中字段
			session.update("ProjectMapper.updateProjectByProjNo", project);
			session.commit();
			//删除person_project中该项目相关人员
			session.delete("PersonProjectMapper.deletePersonProjectByProjNo",projNo);
			session.commit();
			//往person_project中添加该项目相关人员
			for(int i = 0; i < designerList.size(); i++)
			{
				Person p = designerList.get(i);
				PersonProject personProject = new PersonProject();
				personProject.setEmpYstId(p.getEmp_yst_id());
				personProject.setProjNo(projNo);
				personProject.setRoleId(1);
				session.insert("PersonProjectMapper.insertPersonProject", personProject);
				session.commit();
			}
			for(int i = 0; i < developerList.size(); i++)
			{
				Person p = developerList.get(i);
				PersonProject personProject = new PersonProject();
				personProject.setEmpYstId(p.getEmp_yst_id());
				personProject.setProjNo(projNo);
				personProject.setRoleId(2);
				session.insert("PersonProjectMapper.insertPersonProject", personProject);
				session.commit();
			}
			for(int i = 0; i < auditorList.size(); i++)
			{
				Person p = auditorList.get(i);
				PersonProject personProject = new PersonProject();
				personProject.setEmpYstId(p.getEmp_yst_id());
				personProject.setProjNo(projNo);
				personProject.setRoleId(3);
				session.insert("PersonProjectMapper.insertPersonProject", personProject);
				session.commit();
			}
			//删除root_project_phase中该项目的所有排期信息
			session.delete("RootProjectPhaseMapper.deleteRootProjectPhaseByProjNo", projNo);
 			session.commit();
			//存入该项目排期信息到root_project_phase中
			for(int i=0;i<numOfRootPhases;i++)
			{
				int startYear = Integer.parseInt(phases[i][0]);
				int startMonth = Integer.parseInt(phases[i][1]);
				int startWeekNo = Integer.parseInt(phases[i][2]);
				int endYear = Integer.parseInt(phases[i][3]);
				int endMonth = Integer.parseInt(phases[i][4]);
				int endWeekNo = Integer.parseInt(phases[i][5]);
				ComputeWeekNum.fillRootProjectPhase(startYear,startMonth,startWeekNo,
						   							endYear,endMonth,endWeekNo,
						   							session,i+1,projNo);
			}
			out.print(true);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			out.write(sw.toString());
		} finally {
			session.close();
		}
		out.flush();
		out.close();
	}
}

class DateTemp{
	String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}