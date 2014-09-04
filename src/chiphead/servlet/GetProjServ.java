package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.ProjectPhase;
import chiphead.model.RootProjectPhase;
import chiphead.utils.DateUtil;

public class GetProjServ extends HttpServlet {

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
		
		
		//System.out.print("111");//输出到后台
		
		
		//取出用于登录检查及修改权限检查的信息
		String projNo = request.getParameter("proj_no");
		projNo = projNo.replaceAll("\r|\n", "");
		String emp_name = request.getParameter("emp_name");
		String empYstIdString = request.getParameter("emp_yst_id");
		String empPwd = request.getParameter("emp_pwd");
		
		//根据项目编号在projects中查询一条
		Project project = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
		
		if (empYstIdString == null || empYstIdString.equals("") || project==null) {
			session.close();
			out.print(false);
			out.flush();
			out.close();
			return;
		}
		
		Integer empYstId = Integer.parseInt(empYstIdString);

		if (!AutCheck.YstPwdCorrect(empYstId,empPwd,session)) {
			//System.out.println(empYstId);
			//System.out.println(empPwd);
			out.print("notlogged");
			//System.out.print("notlogged");
			session.close();
			out.flush();
			out.close();
			return;
		} /* else if (!AutCheck.HaveChangeProjAut(projNo,emp_name,session)) {
			out.print("noauth");
			//System.out.print("noauth");
			session.close();
			out.flush();
			out.close();
			return;
		}*/
		
		//从person_project中取出设计、开发、评审人员列表
		PersonProject perpro = new PersonProject();
		perpro.setProjNo(projNo);
		perpro.setRoleId(1);//设计
		List<Person> designerList = session.selectList("PersonProjectMapper.selectPersonByProjNoRoleId",perpro);
		perpro.setRoleId(2);//开发
		List<Person> developerList = session.selectList("PersonProjectMapper.selectPersonByProjNoRoleId",perpro);
		perpro.setRoleId(3);//评审
		List<Person> auditorList = session.selectList("PersonProjectMapper.selectPersonByProjNoRoleId",perpro);
		String designer = gson.toJson(designerList);
		String developer = gson.toJson(developerList);
		String auditor = gson.toJson(auditorList);
		
		//从root_project_phase中取出项目8个阶段的起止年份、月份、周序号,以"-"分隔一个阶段的6个数据
		String[] params = ComputeWeekNum.getProjectStartEndYearMonthWeekNo(projNo,session);
		  
		Map cellMap = new LinkedHashMap(); 
		
		//update_time不人为修改，是系统时间
		String reqNo = project.getReqNo();
		cellMap.put("req_no", (reqNo == null || reqNo.isEmpty())?"":reqNo);
		cellMap.put("proj_no", projNo);  
		cellMap.put("proj_name", project.getProjName());
		
		cellMap.put("proj_type", project.getProjType());
		cellMap.put("master_slave", project.getMasterSlave());
		
		cellMap.put("proj_manager", project.getProjManager());
		
		RootProjectPhase rpp = new RootProjectPhase();
		rpp.setProjNo(projNo);
		java.util.Date d = new java.util.Date();// 当前日期
		java.sql.Date date = new java.sql.Date(d.getTime());
		ComputeWeekNum.getRootProjectPhaseByDate(rpp, date);
		List<String> list = session.selectList(
				"RootProjectPhaseMapper.selectRootProjectPhaseNameByPYMW", rpp);
		String curPhase = "";
		for (int i = 0; i < list.size(); i++) {
			curPhase += list.get(i);
			if (i != list.size() - 1) {
				curPhase += "/";
			}
		}
		// cellMap.put("cur_phase",curPhase);
		
		Integer curState = project.getCurState();
//		if(curState != null){
//			CurrentStatus state = session.selectOne(
//					"CurrentStatusMapper.selectCurrentStatus", curState);
//			cellMap.put("cur_state",state.getStateName());
//		}
//		else
//			cellMap.put("cur_state","");
		cellMap.put("cur_state", curState);
		
		cellMap.put("risk_question", project.getRiskQuestion()!=null?project.getRiskQuestion():"");
		cellMap.put("proj_charge", project.getProjCharge()!=null?project.getProjCharge():"");
		cellMap.put("designer", designer);
		cellMap.put("developer", developer);
		cellMap.put("auditor", auditor);
		cellMap.put("start_date", project.getStartDate()!=null?DateUtil.getChgDateStr(project.getStartDate()):"");
		
		String actualInstallDate = project.getActualInstallDate();
		List mapList = new ArrayList();
		if(actualInstallDate != null && !actualInstallDate.isEmpty()){
			String dateList[] = project.getActualInstallDate().split(",");
		    for(int i = 0; i < dateList.length; i++) {
		    	Map cellMap2 = new HashMap();
				cellMap2.put("date", dateList[i]);
		        mapList.add(cellMap2);
		    }   
		}
	    cellMap.put("actual_install_date", mapList);
		
		cellMap.put("plan_end_date", project.getPlanEndDate()!=null?DateUtil.getChgDateStr(project.getPlanEndDate()):"");
		cellMap.put("actual_end_date", project.getActualEndDate()!=null?DateUtil.getChgDateStr(project.getActualEndDate()):"");
		cellMap.put("actual_days", project.getActualDays()!=null?project.getActualDays().toString():"");
		//overdue_mark不显示，是否超期由页面动态计算
		cellMap.put("overdue_reason",project.getOverdueReason());
		
		int numOfRootPhases = 8;
		for(int i=1;i<=numOfRootPhases;i++){
			cellMap.put("phase_" + i, params[i-1]);
		}
		
		cellMap.put("plan_work_amount", project.getPlanWorkAmount()!=null?project.getPlanWorkAmount().toString():"");
		cellMap.put("actual_work_amount", project.getActualWorkAmount()!=null?project.getActualWorkAmount().toString():"");
		cellMap.put("function_amount", project.getFunctionAmount()!=null?project.getFunctionAmount().toString():"");
		cellMap.put("productivity", project.getProductivity()!=null?project.getProductivity().toString():"");
		cellMap.put("smoke_num", project.getSmokeNum()!=null?project.getSmokeNum().toString():"");
		
//		String stType = session.selectOne("ProjectMapper.getProjectStTypeNameByProjNo", projNo);
//		cellMap.put("st_type",stType!=null?stType:"");
		cellMap.put("st_type", project.getStType());
		
		cellMap.put("st_bug_density", project.getStBugDensity()!=null?project.getStBugDensity().toString():"");
		cellMap.put("nc", project.getNc()!=null?project.getNc():"");
		cellMap.put("design_audit", project.getDesignAudit()!=null?project.getDesignAudit():"");
		cellMap.put("code_audit", project.getCodeAudit()!=null?project.getCodeAudit():"");
		cellMap.put("install_audit", project.getInstallAudit()!=null?project.getInstallAudit():"");
		cellMap.put("experience", project.getExperience()!=null?project.getExperience():"");
		cellMap.put("problem_analysis", project.getProblemAnalysis()!=null?project.getProblemAnalysis():"");
		cellMap.put("remark", project.getRemark()!=null?project.getRemark():"");
		
	    String str = gson.toJson(cellMap);			
		out.print(str);//输出到前台
		
		//System.out.print(str);
		
		session.close();
		out.flush();
		out.close();
	}

}
