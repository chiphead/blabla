package chiphead.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.utils.DateUtil;

public class DisplayParse {

	private SqlSession session;
	SqlSessionFactory sqlSessionFactory;

	public DisplayParse() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	//Project类型转换成DisplayProject
	public void parseToDisplayProject(DisplayProject dis, Project p)
			throws IOException {
		session = sqlSessionFactory.openSession();
		try {
			String stateName = session.selectOne("EvaluateStateMapper.selectStateNameByStateId",p.getEvaluateState());
			dis.setEvaluateState(stateName);// projects中Integer
			dis.setUpdateTime(DateUtil.getDateTimeString(p.getUpdateTime()));
			dis.setReqNo(p.getReqNo());
			dis.setProjNo(p.getProjNo());
			dis.setProjName(p.getProjName());

			ProjectType type = session.selectOne(
					"ProjectTypeMapper.selectProjectType", p.getProjType());
			if (type == null) {
				dis.setProjType("");
			} else {
				dis.setProjType(type.getTypeName());// projects中Integer
			}
			
			MasterSlave masterSlave = session.selectOne(
					"MasterSlaveMapper.selectMasterSlave", p.getMasterSlave());
			if (masterSlave == null) {
				dis.setMasterSlave("");
			} else {
				dis.setMasterSlave(masterSlave.getTypeName());
			}

			dis.setProjManager(p.getProjManager());

			RootProjectPhase rpp = new RootProjectPhase();
			rpp.setProjNo(p.getProjNo());
			
			java.sql.Date date = DateUtil.getCurDate();
			String curPhase = "";
			
			
			//rpp中存放了当前日期的年月周序号
			ComputeWeekNum.getRootProjectPhaseByDate(rpp, date);	
			//从数据库中查找对应阶段
			List<String> list = session.selectList(
					"RootProjectPhaseMapper.selectRootProjectPhaseNameByPYMW", rpp);				
			for (int i = 0; i < list.size(); i++) {
				curPhase += list.get(i);
				if (i != list.size() - 1) {
					curPhase += "/";
				}
			}
			//若找不到对应阶段
			//实际结项日期不为空，且当前日期在实际结项日期之后
			if(curPhase.equals("") && p.getStartDate() !=null && date.after(p.getStartDate()) && 
									p.getActualEndDate()!=null && date.after(p.getActualEndDate())){
				curPhase += "结项";
			}

			dis.setCurPhase(curPhase);

			CurrentStatus state = session.selectOne(
					"CurrentStatusMapper.selectCurrentStatus", p.getCurState());
			dis.setCurState(state!=null?state.getStateName():"");// projects中Integer
			dis.setRiskQuestion(p.getRiskQuestion());

			PersonProject personProject = new PersonProject();
			personProject.setProjNo(p.getProjNo());
			personProject.setRoleId(1);
			List<Person> designersList = session.selectList(
					"PersonProjectMapper.selectPersonByProjNoRoleId",
					personProject);
			String designersString = "";
			int j;
			for (j = 0; j < designersList.size(); j++) {
				if (j == designersList.size() - 1) {
					designersString += designersList.get(j).getEmp_name();
					break;
				}
				designersString += designersList.get(j).getEmp_name() + ", ";
			}
			dis.setDesigner(designersString);// 设计人员，逗号分隔

			personProject.setRoleId(2);
			List<Person> developersList = session.selectList(
					"PersonProjectMapper.selectPersonByProjNoRoleId",
					personProject);
			String developersString = "";
			for (j = 0; j < developersList.size(); j++) {
				if (j == developersList.size() - 1) {
					developersString += developersList.get(j).getEmp_name();
					break;
				}
				developersString += developersList.get(j).getEmp_name() + ", ";
			}
			dis.setDeveloper(developersString);// 开发人员，逗号分隔

			personProject.setRoleId(3);
			List<Person> auditorsList = session.selectList(
					"PersonProjectMapper.selectPersonByProjNoRoleId",
					personProject);
			String auditorsString = "";
			for (j = 0; j < auditorsList.size(); j++) {
				if (j == auditorsList.size() - 1) {
					auditorsString += auditorsList.get(j).getEmp_name();
					break;
				}
				auditorsString += auditorsList.get(j).getEmp_name() + ", ";
			}
			dis.setAuditor(auditorsString);// 评审人员，逗号分隔

			dis.setStartDate(DateUtil.getDateStr(p.getStartDate()));// projects中Date
			dis.setActualInstallDate(p.getActualInstallDate());// projects中String
			dis.setPlanEndDate(DateUtil.getDateStr(p.getPlanEndDate()));// projects中Date
			dis.setActualEndDate(DateUtil.getDateStr(p.getActualEndDate()));// projects中Date
			dis.setActualDays(p.getActualDays() != null ? p.getActualDays()
					.toString() : "");// projects中Integer
			
			dis.setOverdueMark(ComputeWeekNum.isOverdue(p));//计算是否超期，Y/N/""
			
			dis.setOverdueReason(p.getOverdueReason());
			dis.setPlanWorkAmount(p.getPlanWorkAmount() != null ? p
					.getPlanWorkAmount().toString() : "");// projects中Double
			dis.setActualWorkAmount(p.getActualWorkAmount() != null ? p
					.getActualWorkAmount().toString() : "");// projects中Double
			dis.setFunctionAmount(p.getFunctionAmount() != null ? p
					.getFunctionAmount().toString() : "");// projects中Double
			dis.setProductivity(p.getProductivity() != null ? p
					.getProductivity().toString() : "");// projects中Double
			dis.setSmokeNum(p.getSmokeNum() != null ? p.getSmokeNum()
					.toString() : "");// projects中Integer
			
			String stName = session.selectOne("StTypeMapper.selectStNameByStId",p.getStType());
			dis.setStType(stName);// projects中Integer
			dis.setStBugDensity(p.getStBugDensity() != null ? p
					.getStBugDensity().toString() : "");// projects中Double
			dis.setNc(p.getNc());
			dis.setDesignAudit(p.getDesignAudit());
			dis.setCodeAudit(p.getCodeAudit());
			dis.setInstallAudit(p.getInstallAudit());
			dis.setExperience(p.getExperience());
			dis.setProblemAnalysis(p.getProblemAnalysis());
			dis.setRemark(p.getRemark());
		} finally {
			session.close();
		}
	}

	//Project类型转换成DisplayProject list
	public List<DisplayProject> parseToDisplayProjectsList(List<Project> list) {
		List<DisplayProject> dList = new ArrayList<DisplayProject>();
		DisplayProject displayProject;
		for (int i = 0; i < list.size(); i++) {
			try {
				displayProject = new DisplayProject();
				parseToDisplayProject(displayProject, list.get(i));
				dList.add(displayProject);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dList;
	}
	
	//Operation类型转换成DisplayOperation
	public void parseToDisplayOperation(DisplayOperation dis, Operation op)
	throws IOException {
		session = sqlSessionFactory.openSession();
		try {
			dis.setOperNo(op.getOperNo().toString());	
			dis.setRegDate(DateUtil.getChgDateStr(op.getRegDate()));
			
			//根据运维类型id查询运维类型名
			String operType = session.selectOne("OperationTypeMapper.selectTypeNameByTypeId",op.getOperType());
			dis.setOperType(operType);
			
			dis.setOperTime(DateUtil.getDateTimeSectionString(op.getOperStartTime(),op.getOperEndTime()));
			
			dis.setOperation(op.getOperation());
			
			//根据level ID查询level名
			String level = session.selectOne("LevelMapper.selectLevelNameByLevelId", op.getLevel());
			dis.setLevel(level);
			
			dis.setOperators(op.getOperators());
			
			/*
			String system = session.selectOne("SystemTypeMapper.selectTypeNameByTypeId",op.getSystem());
			dis.setSystem(system);
			String module = session.selectOne("ModuleTypeMapper.selectTypeNameByTypeId",op.getModule());
			dis.setModule(module);
			*/
			
			dis.setSystem(op.getSystem());
			dis.setBelongPersons(op.getBelongPersons());
			dis.setAffectedBusiness(op.getAffectedBusiness());
			dis.setReason(op.getReason());
			dis.setSuggestion(op.getSuggestion());
			dis.setRemark(op.getRemark());
			
			String stateName = session.selectOne("EvaluateStateMapper.selectStateNameByStateId",op.getCheckState());
			dis.setCheckState(stateName);// projects中Integer						
		} finally {
			session.close();
		}
	}
	
	//Operation类型转换成DisplayOperation list
	public List<DisplayOperation> parseToDisplayOperationsList(List<Operation> list) {
		List<DisplayOperation> dList = new ArrayList<DisplayOperation>();
		DisplayOperation displayOperation;
		for (int i = 0; i < list.size(); i++) {
			try {
				displayOperation = new DisplayOperation();
				parseToDisplayOperation(displayOperation, list.get(i));
				dList.add(displayOperation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dList;
	}
	
	
	//DisplayOperation转换成ExportOperation
	public void parseToExportOperation(ExportOperation exp, DisplayOperation dis){
		exp.setCheckState(dis.getCheckState());
		exp.setRegDate(dis.getRegDate());
		exp.setOperType(dis.getOperType());
		exp.setOperTime(dis.getOperTime());
		exp.setOperation(dis.getOperation());
		exp.setLevel(dis.getLevel());
		exp.setOperators(dis.getOperators());
		exp.setSystem(dis.getSystem());
		//exp.setModule(dis.getModule());
		exp.setBelongPersons(dis.getBelongPersons());
		exp.setAffectedBusiness(dis.getAffectedBusiness());
		exp.setReason(dis.getReason());
		exp.setSuggestion(dis.getSuggestion());
		exp.setRemark(dis.getRemark());
	}
	
	//DisplayOperation转换成ExportOperation list
	public List<ExportOperation> parseToExportOperationsList(List<DisplayOperation> list) {
		List<ExportOperation> eList = new ArrayList<ExportOperation>();
		ExportOperation exportOperation;
		for (int i = 0; i < list.size(); i++) {
			exportOperation = new ExportOperation();
			parseToExportOperation(exportOperation, list.get(i));
			eList.add(exportOperation);
		}
		return eList;
	}

	
	//Innovation类型转换成DisplayInnovation
	public void parseToDisplayInnovation(DisplayInnovation dis, Innovation in)
	throws IOException {
		session = sqlSessionFactory.openSession();
		try {
			dis.setInnoNo(in.getInnoNo().toString());
			dis.setRegDate(DateUtil.getChgDateStr(in.getRegDate()));
			
			//根据创新类型id查询创新类型名
			String innoType = session.selectOne("InnovationTypeMapper.selectTypeNameByTypeId",in.getInnoType());
			dis.setInnoType(innoType);
			
			dis.setProposers(in.getProposers());
			
			//根据系统id查询系统名
			String system = session.selectOne("SystemTypeMapper.selectTypeNameByTypeId",in.getSystem());
			dis.setSystem(system);
			
			dis.setModule(in.getModule());
			dis.setSuggestion(in.getSuggestion());
			dis.setReason(in.getReason());
			dis.setAddSuggestion(in.getAddSuggestion());
			dis.setConclusion(in.getConclusion());
			dis.setExecutors(in.getExecutors());
			dis.setEffect(in.getEffect());
		} finally {
			session.close();
		}
	}
	
	//Innovation类型转换成DisplayInnovation list
	public List<DisplayInnovation> parseToDisplayInnovationsList(List<Innovation> list) {
		List<DisplayInnovation> dList = new ArrayList<DisplayInnovation>();
		DisplayInnovation displayInnovation;
		for (int i = 0; i < list.size(); i++) {
			try {
				displayInnovation = new DisplayInnovation();
				parseToDisplayInnovation(displayInnovation, list.get(i));
				dList.add(displayInnovation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dList;
	}
	
	//DisplayInnovation转换成ExportInnovation
	public void parseToExportInnovation(ExportInnovation exp, DisplayInnovation dis){
		exp.setRegDate(dis.getRegDate());
		exp.setInnoType(dis.getInnoType());
		exp.setProposers(dis.getProposers());
		exp.setSystem(dis.getSystem());
		exp.setModule(dis.getModule());
		exp.setSuggestion(dis.getSuggestion());
		exp.setReason(dis.getReason());
		exp.setAddSuggestion(dis.getAddSuggestion());
		exp.setConclusion(dis.getConclusion());
		exp.setExecutors(dis.getExecutors());
		exp.setEffect(dis.getEffect());
	}
	
	//DisplayInnovation转换成ExportInnovation list
	public List<ExportInnovation> parseToExportInnovationsList(List<DisplayInnovation> list) {
		List<ExportInnovation> eList = new ArrayList<ExportInnovation>();
		ExportInnovation exportInnovation;
		for (int i = 0; i < list.size(); i++) {
			exportInnovation = new ExportInnovation();
			parseToExportInnovation(exportInnovation, list.get(i));
			eList.add(exportInnovation);
		}
		return eList;
	}
	
}
