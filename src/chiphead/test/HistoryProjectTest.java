package chiphead.test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;

import chiphead.config.Constants;
import chiphead.model.ComputeWeekNum;
import chiphead.model.CurrentStatus;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.ProjectQueryParm;
import chiphead.model.RootProjectPhase;
import chiphead.utils.DateUtil;
//未更新
public class HistoryProjectTest {//以下所有案例均copy的ProjectTest中的，把ProjectMapper换成HistoryProjectMapper即可

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();

		try {
			// 插入一条
			
 			Project p = new Project();

 			String projNo = "T1354531";		
 			String projName = "（紧急维护补充立项）申请在生产机CMB03上临时修改自扣程序RPCDTBHERP";		
 			Integer projType = new Integer(1);//1:流量 2：重点
 			String projCharge = "尹刚";
 			String projManager = "不知道";
 			Integer curState = new Integer(1);//1:正常 2:延期 3:暂缓	
 			String riskQuestion = "";
 			//java.sql.Date
 			Date startDate = Date.valueOf("2013-8-12");
 			Date planEndDate = Date.valueOf("2013-9-20");
 			Date actualEndDate = Date.valueOf("2013-9-25");
 			Integer actualDays = (int) Math.abs((actualEndDate.getTime() - startDate.getTime())  / 
 					(24 * 60 * 60 * 1000)) + 1; 
 			Double planWorkAmount = new Double(1234567.18);
 			Double actualWorkAmount = new Double(1234567.34);
 			Double functionAmount = new Double(1234567.05);
 			Double productivity = new Double(12.12);
 			Integer smokeNum = new Integer(4);
 			//stType:1: A类：测试全负责
			//2: B类：测试牵头开发执行
			//3: C类：开发全负责
			//4: D类：免测
 			Integer stType = new Integer(1);
 			Double stBugDensity = new Double(1.234);
 			String nc = "无";
 			String designAudit = "木有";
 			String codeAudit = "木有";
 			String installAudit = "木有";
 			String experience = "木有";
 			String problemAnalysis = "木有";
 			String remark = "木有";
 			
 			p.setEvaluateState(1);
 			p.setUpdateTime(DateUtil.getCurDateTime());
 			p.setReqNo("R123456");
 			p.setProjNo(projNo);
 			p.setProjName(projName);
 			p.setProjType(projType);
 			p.setProjCharge(projCharge);
 			p.setProjManager(projManager);
 			p.setCurState(curState );
 			p.setRiskQuestion(riskQuestion);
 			p.setStartDate(startDate);
 			p.setActualInstallDate("2013/11/11 13:11");
 			p.setPlanEndDate(planEndDate);	
 			p.setActualEndDate(actualEndDate); 
 			p.setActualDays(actualDays);
 			p.setOverdueReason("超期原因");
 			p.setPlanWorkAmount(planWorkAmount);
 			p.setActualWorkAmount(actualWorkAmount);
 			p.setFunctionAmount(functionAmount);
 			p.setProductivity(productivity);
 			p.setSmokeNum(smokeNum);
 			p.setStType(stType);	
 			p.setStBugDensity(stBugDensity);
 			p.setNc(nc);
 			p.setDesignAudit(designAudit);
 			p.setCodeAudit(codeAudit);
 			p.setInstallAudit(installAudit);
 			p.setExperience(experience);	
 			p.setProblemAnalysis(problemAnalysis);		
 			p.setRemark(remark);
 			
			int ret = session.insert("HistoryProjectMapper.insertProject", p);
			session.commit();
			System.out.println(ret + " " + p);
			
			
			/*
			// 查询一条
			
			Project p1 =
			session.selectOne("ProjectMapper.selectProject", 2);
			System.out.println(p1);
			
			
			//根据项目编号查询一条
			
			String projNo = "T222333";
			Project p2 =
				session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
			System.out.println(p2);
			
			//根据项目编号模糊查询
			String projNo2 = "T";
			List<Project> list1 = session.selectList("ProjectMapper.selectProjectByProjNoPart",projNo2);
 			System.out.println(list1);
			
			
			//根据项目名称查询
 			
			String projName2 = "hahaha";
			List<Project> list2 = session.selectList("ProjectMapper.selectProjectByProjName",projName2);
 			System.out.println(list2);
		
			//根据项目名称模糊查询
			String projName3 = "ha";
			List<Project> list3 = session.selectList("ProjectMapper.selectProjectByProjNamePart",projName3);
 			System.out.println(list3);
 			
			
			// 查询多条
			
 			List<Project> list = session.selectList("ProjectMapper.selectAllProjects");
 			System.out.println(list);
 			
			
			
 			//根据参数查询并排序、分页
			ProjectQueryParm p = new ProjectQueryParm();
			String queryType = "proj_manager";
			String queryValue = "邬";
			String orderType = "proj_id";
			String orderMethod = "asc";
			Integer startIndex = 0;
			Integer fetchNum = 2;
			
			p.setQueryType(queryType);
			p.setQueryValue(queryValue);
			p.setOrderType(orderType);
			p.setOrderMethod(orderMethod);
			p.setStartIndex(startIndex);
			p.setFetchNum(fetchNum);
			
 			List<Project> list2 = session.selectList("ProjectMapper.selectProjectsByParms",p);
 			for(int i=0;i<list2.size();i++)
 				System.out.println(list2.get(i));
 			
 			Integer num = session.selectOne("ProjectMapper.selectProjectsNumByParms",p);
 			System.out.println(num);
 			
 			
 			
			// 更新一条
			
			Project p = new Project();
			
			BigInteger projId = new BigInteger("1");
			String projNo = "T1111111";		
 			String projName = "（紧急维护补充立项）申请在生产机CMB03上临时修改自扣程序RPCDTBHERP";		
 			Integer projType = new Integer(1);//1:流量 2：重点
 			String projCharge = "尹刚";
 			String projManager = "不知道";
 			String interAuditor = "哈哈";
 			Integer curState = new Integer(1);//1:正常 2:延期 3:暂缓	
 			String riskQuestion = "";
 			//java.sql.Date
 			Date startDate = Date.valueOf("2013-8-10");
 			Date planEndDate = Date.valueOf("2013-8-10");
 			Date actualEndDate = Date.valueOf("2013-8-10");
 			Integer actualDays = (int) Math.abs((actualEndDate.getTime() - startDate.getTime())  / 
 					(24 * 60 * 60 * 1000)) + 1; 
 			Double planWorkAmount = new Double(1234567.99);
 			Double actualWorkAmount = new Double(1234567.99);
 			Double functionAmount = new Double(1234567.99);
 			Double productivity = new Double(12.99);
 			Integer smokeNum = new Integer(4);
 			//stType:1: A类：测试全负责
			//2: B类：测试牵头开发执行
			//3: C类：开发全负责
			//4: D类：免测
 			Integer stType = new Integer(2);
 			Double stBugDensity = new Double(1.999);
 			String nc = "无";
 			String designAudit = "木有";
 			String codeAudit = "木有";
 			String installAudit = "木有";
 			String experience = "木有";
 			String problemAnalysis = "木有";
 			String remark = "木有";
 			
 			p.setProjId(projId);
 			p.setProjNo(projNo);
 			p.setProjName(projName);
 			p.setProjType(projType);
 			p.setProjCharge(projCharge);
 			p.setProjManager(projManager);
 			p.setInterAuditor(interAuditor);
 			p.setCurState(curState );
 			p.setRiskQuestion(riskQuestion);
 			p.setStartDate(startDate);
 			p.setPlanEndDate(planEndDate);	
 			p.setActualEndDate(actualEndDate); 
 			p.setActualDays(actualDays);
 			p.setPlanWorkAmount(planWorkAmount);
 			p.setActualWorkAmount(actualWorkAmount);
 			p.setFunctionAmount(functionAmount);
 			p.setProductivity(productivity);
 			p.setSmokeNum(smokeNum);
 			p.setStType(stType);	
 			p.setStBugDensity(stBugDensity);
 			p.setNc(nc);
 			p.setDesignAudit(designAudit);
 			p.setCodeAudit(codeAudit);
 			p.setInstallAudit(installAudit);
 			p.setExperience(experience);	
 			p.setProblemAnalysis(problemAnalysis);		
 			p.setRemark(remark);
 			
 			session.update("ProjectMapper.updateProject", p);
 			session.commit();
 			System.out.println(p);
 			
			
			Project p = new Project();
			
			String projNo = "T222";		
 			String projName = "（紧急维护补充立项）申请在生产机CMB03上临时修改自扣程序RPCDTBHERP";		
 			Integer projType = new Integer(1);//1:流量 2：重点
 			String projCharge = "尹刚";
 			String projManager = "不知道";
 			String interAuditor = "哈哈";
 			Integer curState = new Integer(1);//1:正常 2:延期 3:暂缓	
 			String riskQuestion = "";
 			//java.sql.Date
 			Date startDate = Date.valueOf("2013-8-10");
 			Date planEndDate = Date.valueOf("2013-8-10");
 			Date actualEndDate = Date.valueOf("2013-8-10");
 			Integer actualDays = (int) Math.abs((actualEndDate.getTime() - startDate.getTime())  / 
 					(24 * 60 * 60 * 1000)) + 1; 
 			Double planWorkAmount = new Double(1234567.99);
 			Double actualWorkAmount = new Double(1234567.99);
 			Double functionAmount = new Double(1234567.99);
 			Double productivity = new Double(12.99);
 			Integer smokeNum = null;
 			//stType:1: A类：测试全负责
			//2: B类：测试牵头开发执行
			//3: C类：开发全负责
			//4: D类：免测
 			Integer stType = new Integer(2);
 			Double stBugDensity = new Double(1.999);
 			String nc = null;
 			String designAudit = "木有";
 			String codeAudit = "木有";
 			String installAudit = "木有";
 			String experience = "木有";
 			String problemAnalysis = "木有";
 			String remark = "木有";
 			
 			p.setProjNo(projNo);
 			p.setProjName(projName);
 			p.setProjType(projType);
 			p.setProjCharge(projCharge);
 			p.setProjManager(projManager);
 			p.setInterAuditor(interAuditor);
 			p.setCurState(curState );
 			p.setRiskQuestion(riskQuestion);
 			p.setStartDate(startDate);
 			p.setPlanEndDate(planEndDate);	
 			p.setActualEndDate(actualEndDate); 
 			p.setActualDays(actualDays);
 			p.setPlanWorkAmount(planWorkAmount);
 			p.setActualWorkAmount(actualWorkAmount);
 			p.setFunctionAmount(functionAmount);
 			p.setProductivity(productivity);
 			p.setSmokeNum(smokeNum);
 			p.setStType(stType);	
 			p.setStBugDensity(stBugDensity);
 			p.setNc(nc);
 			p.setDesignAudit(designAudit);
 			p.setCodeAudit(codeAudit);
 			p.setInstallAudit(installAudit);
 			p.setExperience(experience);	
 			p.setProblemAnalysis(problemAnalysis);		
 			p.setRemark(remark);
 			
 			session.update("ProjectMapper.updateProjectByProjNo", p);
 			session.commit();
 			System.out.println(p);
			

			// 删除一条
 			
 			session.delete("ProjectMapper.deleteProject", 2);
 			session.commit();
 			
			
			
			//根据项目编号查询项目类型名
			String projNo = "T0";
			String p1 =
				session.selectOne("ProjectMapper.getProjectTypeNameByProjNo", projNo);
			System.out.println(p1);
			
			
			//根据项目编号查询当前状态名
			String p2 =
				session.selectOne("ProjectMapper.getProjectCurStateNameByProjNo", projNo);
			System.out.println(p2);

			//根据项目编号查询ST测试类型名
			String p3 =
				session.selectOne("ProjectMapper.getProjectStTypeNameByProjNo", projNo);
			System.out.println(p3);
			
 			
 			//查询记录条数
 			BigInteger num;
 			num = session.selectOne("ProjectMapper.selectProjectsNum");
 			System.out.println(num);
			
			
			
			//修改项目前用于显示的信息以json字符串返回测试
			String projNo = "TEST11";
			Gson gson = new Gson();
			
			//从projects中取该条项目数据
			Project project = session.selectOne(
					"ProjectMapper.selectProjectByProjNo", projNo);
			
			//从person_project中取出设计、开发、评审人员列表
			PersonProject perpro = new PersonProject();
			perpro.setProjNo(projNo);
			perpro.setRoleId(1);//设计
			List<Person> designerList = session.selectList("PersonProjectMapper.selectPersonByProjNoRoleId",perpro);
			perpro.setRoleId(2);//开发
			List<Person> developerList = session.selectList("PersonProjectMapper.selectPersonByProjNoRoleId",perpro);
			//System.out.println(developerList);
			perpro.setRoleId(3);//评审
			List<Person> auditorList = session.selectList("PersonProjectMapper.selectPersonByProjNoRoleId",perpro);
			String designer = gson.toJson(designerList);
			String developer = gson.toJson(developerList);
			//System.out.println(developer);
			String auditor = gson.toJson(auditorList);
			//从root_project_phase中取出项目8个阶段的起止年份、月份、周序号,以"-"分隔一个阶段的6个数据
			String[] params = ComputeWeekNum.getProjectStartEndYearMonthWeekNo(projNo,session);
			  
			Map cellMap = new LinkedHashMap(); 
			
			cellMap.put("proj_no", projNo);  
			cellMap.put("proj_name", project.getProjName());
			
			String projType = session.selectOne("ProjectMapper.getProjectTypeNameByProjNo", projNo);
			cellMap.put("proj_type", projType);
			
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
			cellMap.put("cur_phase",curPhase);
			
			Integer curState = project.getCurState();
			if(curState != null){
				CurrentStatus state = session.selectOne(
						"CurrentStatusMapper.selectCurrentStatus", curState);
				cellMap.put("cur_state",state.getStateName());
			}
			else
				cellMap.put("cur_state","");
			
			cellMap.put("risk_question", project.getRiskQuestion()!=null?project.getRiskQuestion():"");
			cellMap.put("proj_charge", project.getProjCharge()!=null?project.getProjCharge():"");
			cellMap.put("inter_auditor", project.getInterAuditor()!=null?project.getInterAuditor():"");
			cellMap.put("designer", designer);
			cellMap.put("developer", developer);
			cellMap.put("auditor", auditor);
			cellMap.put("start_date", project.getStartDate()!=null?project.getStartDate().toString():"");
			cellMap.put("plan_end_date", project.getPlanEndDate()!=null?project.getPlanEndDate().toString():"");
			cellMap.put("actual_end_date", project.getActualEndDate()!=null?project.getActualEndDate().toString():"");
			cellMap.put("actual_days", project.getActualDays()!=null?project.getActualDays().toString():"");
			
			for(int i=1;i<=8;i++){
				cellMap.put("phase_" + i, params[i-1]);
			}
			
			cellMap.put("plan_work_amount", project.getPlanWorkAmount()!=null?project.getPlanWorkAmount().toString():"");
			cellMap.put("actual_work_amount", project.getActualWorkAmount()!=null?project.getActualWorkAmount().toString():"");
			cellMap.put("function_amount", project.getFunctionAmount()!=null?project.getFunctionAmount().toString():"");
			cellMap.put("productivity", project.getProductivity()!=null?project.getProductivity().toString():"");
			cellMap.put("smoke_num", project.getSmokeNum()!=null?project.getSmokeNum().toString():"");
			
			String stType = session.selectOne("ProjectMapper.getProjectStTypeNameByProjNo", projNo);
			cellMap.put("st_type",stType!=null?stType:"");
			
			cellMap.put("st_bug_density", project.getStBugDensity()!=null?project.getStBugDensity().toString():"");
			cellMap.put("nc", project.getNc()!=null?project.getNc():"");
			cellMap.put("design_audit", project.getDesignAudit()!=null?project.getDesignAudit():"");
			cellMap.put("code_audit", project.getCodeAudit()!=null?project.getCodeAudit():"");
			cellMap.put("install_audit", project.getInstallAudit()!=null?project.getInstallAudit():"");
			cellMap.put("experience", project.getExperience()!=null?project.getExperience():"");
			cellMap.put("problem_analysis", project.getProblemAnalysis()!=null?project.getProblemAnalysis():"");
			cellMap.put("remark", project.getRemark()!=null?project.getRemark():"");
			
		    String str = gson.toJson(cellMap);			
			System.out.print(str);//输出到前台
			*/
			
		} finally {
			session.close();
		}
	}

}