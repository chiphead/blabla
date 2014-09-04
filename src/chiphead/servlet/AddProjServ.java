package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import chiphead.model.ComputeWeekNum;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.RootProjectPhase;
import chiphead.utils.DateUtil;
import chiphead.utils.EncUtil;

public class AddProjServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		//用于开发负责人及权限检查
		String empYstIdString = request.getParameter("emp_yst_id");
		Integer empYstId = Integer.parseInt(empYstIdString);
		//updateTime：Timestamp 更新时间为系统时间
		String reqNo = request.getParameter("req_no");
		String projNo = request.getParameter("proj_no");
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
		//overdueMark:String 计算得出
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
		
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		PrintWriter out = response.getWriter();

		SqlSession session = sqlSessionFactory.openSession();
		
		Project project = new Project();
		project.setUpdateTime(DateUtil.getCurDateTime());//更新时间为系统时间
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
		Date actualEndDate = DateUtil.getDate(actualEndDateStr);
		project.setActualEndDate(actualEndDate);
		if (!actualDays.equals(""))
			project.setActualDays(Integer.parseInt(actualDays));
		else{
			project.setActualDays(null);
		}
		//project.setOverdueReason(overdueReason);
		project.setOverdueReason("");
		if(!planWorkAmount.equals("")){
			project.setPlanWorkAmount(Double.parseDouble(planWorkAmount));
		}
		if (!actualWorkAmount.equals(""))
			project.setActualWorkAmount(Double.parseDouble(actualWorkAmount));
		if (!functionAmount.equals(""))
			project.setFunctionAmount(Double.parseDouble(functionAmount));
		if (!productivity.equals(""))
			project.setProductivity(Double.parseDouble(productivity));
		if (!smokeNum.equals(""))
			project.setSmokeNum(Integer.parseInt(smokeNum));
		if (!stType.equals(""))
			project.setStType(Integer.parseInt(stType));
		if (!stBugDensity.equals(""))
			project.setStBugDensity(Double.parseDouble(stBugDensity));
		project.setNc(nc);
		project.setDesignAudit(designAudit);
		project.setCodeAudit(codeAudit);
		project.setInstallAudit(installAudit);
		project.setExperience(experience);
		project.setProblemAnalysis(problemAnalysis);
		project.setRemark(remark);
		project.setEvaluateState(evaluateState);
		
		List<Person> designerList = gson.fromJson(designer, new TypeToken<List<Person>>(){}.getType());
		List<Person> developerList = gson.fromJson(developer, new TypeToken<List<Person>>(){}.getType());
		List<Person> auditorList = gson.fromJson(auditor, new TypeToken<List<Person>>(){}.getType());
		
		//增加的部分，取出要存入项目排期表的信息
		int numOfRootPhases = 8;
		String[] params = new String[numOfRootPhases];
		String[][] phases = new String[numOfRootPhases][6];
		 
		for (int i = 1; i <= numOfRootPhases; i++) {
			params[i-1] = request.getParameter("phase_" + i);
			phases[i-1] = params[i-1].split("-");
		}
		//System.out.println(phases);
		

		try {
			Project pTemp = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
			if(pTemp != null){
				out.print("duplicateProjNo");
				return;
			}
			
			//根据一事通id从persons取数据
			Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
			String empName = per.getEmp_name();
			int aut = per.getEmp_aut();
			//既不是开发负责人也不是管理员
			if(!project.getProjManager().equals(empName) && aut != 1){
				out.print("notProjManagerOrAdmin");
				return;
			}
			
			int num = session.insert("ProjectMapper.insertProject",project);
			session.commit();
			boolean result = (num > 0) ? true : false;
			if (!result) {
				out.print(result);
				return;
			}
			
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
			//存入项目排期表信息到root_project_phase中
			for(int i=0;i<numOfRootPhases;i++){
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
			
			out.print(result);
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
