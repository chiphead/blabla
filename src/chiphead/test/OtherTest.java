package chiphead.test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.model.ComputeWeekNum;
import chiphead.model.CurrentStatus;
import chiphead.model.InnovationType;
import chiphead.model.Level;
import chiphead.model.ModuleType;
import chiphead.model.OperationType;
import chiphead.model.Phase;
import chiphead.model.Project;
import chiphead.model.ProjectRole;
import chiphead.model.ProjectType;
import chiphead.model.RootPhase;
import chiphead.model.StType;
import chiphead.model.SystemType;
import chiphead.model.WeekNumSet;

public class OtherTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();

		try {
			/*
			//current_status表查询
			// 查询一条
			CurrentStatus state =
			session.selectOne("CurrentStatusMapper.selectCurrentStatus", 1);
			System.out.println(state);
			
			//根据状态名查询状态id
			String i = "延期";
			CurrentStatus state2 =
				session.selectOne("CurrentStatusMapper.selectCurrentStatusByStateName", i);
				System.out.println(state2.getStateId());
			
			
			// 查询多条	
 			List<CurrentStatus> stateList = session.selectList("CurrentStatusMapper.selectAllStatus");
 			System.out.println(stateList);
 			
			
			//root_phase表查询,对应项目排期及开发表中项目阶段！！！
			// 查询一条
			RootPhase rootPhase =
			session.selectOne("RootPhaseMapper.selectRootPhase", 1);
			System.out.println(rootPhase);
			
			
			// 根据阶段名查询阶段id
			String k = "ST";
			RootPhase rootPhase2 =
			session.selectOne("RootPhaseMapper.selectRootPhaseByRootPhaseName", k);
			System.out.println(rootPhase2.getRootPhaseId());
			
			//根据阶段id查询阶段名
			String rootPhaseName = session.selectOne("RootPhaseMapper.selectRootPhaseNameByRootPhaseId", 5);
			System.out.println(rootPhaseName);
			
			
			
			// 查询多条	
 			List<RootPhase> rootPhaseList = session.selectList("RootPhaseMapper.selectAllRootPhases");
 			System.out.println(rootPhaseList);
			
			
 			//phase表查询,对应员工排期表中项目阶段！！！
			// 查询一条
			Phase phase =
			session.selectOne("PhaseMapper.selectPhase", 1);
			System.out.println(phase);
			
			
			// 根据阶段名查询阶段id
			String k = "需求";
			Phase phase2 =
			session.selectOne("PhaseMapper.selectPhaseByPhaseName", k);
			System.out.println(phase2.getPhaseId());
			
			//根据阶段id查询阶段名
			String phaseName =
			session.selectOne("PhaseMapper.selectPhaseNameByPhaseId", 3);
			System.out.println(phaseName);
			
			
			// 查询多条	
 			List<Phase> phaseList = session.selectList("PhaseMapper.selectAllPhases");
 			System.out.println(phaseList);
 			
 			//project_role表查询
			// 查询一条
			ProjectRole role =
			session.selectOne("ProjectRoleMapper.selectProjectRole", 1);
			System.out.println(role);
			
			
			// 根据角色名查询角色id
			String l = "开发";
			ProjectRole role2 =
			session.selectOne("ProjectRoleMapper.selectProjectRoleByRoleName", l);
			System.out.println(role2.getRoleId());
			
			
			// 查询多条	
 			List<ProjectRole> roleList = session.selectList("ProjectRoleMapper.selectAllProjectRoles");
 			System.out.println(roleList);
 			
 			//project_type表查询
			// 查询一条
			ProjectType type =
			session.selectOne("ProjectTypeMapper.selectProjectType", 1);
			System.out.println(type);
			
			//根据类型名查询类型id
			ProjectType type2 =
				session.selectOne("ProjectTypeMapper.selectProjectTypeByTypeName", "流量");
			System.out.println(type2.getTypeId());
			
				  
			// 查询多条	
 			List<ProjectRole> typeList = session.selectList("ProjectTypeMapper.selectAllProjectTypes");
 			System.out.println(typeList);
 			
 			//st_type表查询
			// 查询一条
			StType st =
			session.selectOne("StTypeMapper.selectStType", 1);
			System.out.println(st);
			
			// 根据st测试类型名查询st测试类型id
			String j = "A类：测试全负责";
			StType st2 =
			session.selectOne("StTypeMapper.selectStTypeByStName", j);
			System.out.println(st2.getStId());
			
			
			//根据st测试类型id查询st测试类型名
			Integer stId = 2;
			String stName = session.selectOne("StTypeMapper.selectStNameByStId",stId);
			System.out.println(stName);
			*/
			
			//level表查询
			// 根据level id查询一条
			Level lev1 = session.selectOne("LevelMapper.selectLevel", 1);
			System.out.println(lev1);
			
			// 根据level名查询一条
			String j = "P3";
			Level lev2 = session.selectOne("LevelMapper.selectLevelByLevelName", j);
			System.out.println(lev2.getLevelId());
			
			//根据level ID查询level名
			String lev3 = session.selectOne("LevelMapper.selectLevelNameByLevelId", 5);
			System.out.println(lev3);
			
			
			//根据level id查询level名
			Integer levelId = 2;
			String levelName = session.selectOne("LevelMapper.selectLevelNameByLevelId",levelId);
			System.out.println(levelName);
			
			
			/*
			//oper_type表查询
			//根据运维类型id查询运维类型名
			Integer typeId = 2;
			String typeName = session.selectOne("OperationTypeMapper.selectTypeNameByTypeId",typeId);
			System.out.println(typeName);
			
			String typeName2 = "项目上线";
			OperationType type = session.selectOne("OperationTypeMapper.selectOperationTypeByTypeName",typeName2);
			System.out.println(type);
			
			
			//inno_type表查询
			//根据创新类型id查询创新类型名
			Integer typeId = 2;
			String typeName = session.selectOne("InnovationTypeMapper.selectTypeNameByTypeId",typeId);
			System.out.println(typeName);
			
			String typeName2 = "系统设计";
			InnovationType type = session.selectOne("InnovationTypeMapper.selectInnovationTypeByTypeName",typeName2);
			System.out.println(type);
			
			
			
			//evaluate_state表查询
			//根据评估状态id查询评估状态名
			Integer stateId = 1;
			String stateName = session.selectOne("EvaluateStateMapper.selectStateNameByStateId",stateId);
			System.out.println(stateName);
			
			
			// 查询多条	
 			List<StType> stList = session.selectList("StTypeMapper.selectAllStTypes");
 			System.out.println(stList);
 			
 			//week_num_set表查询
			// 查询一条
			WeekNumSet w1 =
			session.selectOne("WeekNumSetMapper.selectWeekNum", 1);
			System.out.println(w1);
				
			// 查询多条	
 			List<WeekNumSet> w1List = session.selectList("WeekNumSetMapper.selectAllWeekNums");
 			System.out.println(w1List);
 			
 			//根据年份月份查询一条记录,进而获得周数
 			WeekNumSet wTemp = new WeekNumSet();
 			wTemp.setYear(2013);
 			wTemp.setMonth(10);
 			WeekNumSet w2 =
 			session.selectOne("WeekNumSetMapper.selectWeekNumByYearMonth", wTemp);
 			wTemp.setWeekNum(w2.getWeekNum());
 			System.out.println(wTemp);
 			
			
			
			Project pro = session.selectOne("ProjectMapper.selectProjectByProjNo", "T1356071");
			System.out.println(ComputeWeekNum.isOverdue(pro));
			
			
			//根据系统id查询系统名
			Integer typeId = 1;
			String typeName = session.selectOne("SystemTypeMapper.selectTypeNameByTypeId",typeId);
			System.out.println(typeName);
			
			String typeName2 = "LG50－收单平台";
			SystemType type = session.selectOne("SystemTypeMapper.selectSystemTypeByTypeName",typeName2);
			System.out.println(type);
			
			
			
			//根据模块id查询模块名
			Integer typeId = 1;
			String typeName = session.selectOne("ModuleTypeMapper.selectTypeNameByTypeId",typeId);
			System.out.println(typeName);
			
			String typeName2 = "调账";
			ModuleType type = session.selectOne("ModuleTypeMapper.selectModuleTypeByTypeName",typeName2);
			System.out.println(type);
			
			String s = "";
			String dateList[] = s.split(",");
			System.out.println(dateList.length);
			for(int i=0;i<dateList.length;i++){
				System.out.println(dateList[i]);
			}
			
			List mapList = new ArrayList();
			System.out.println(mapList);
			*/
			
			
			
		} finally {
			session.close();
		}
	}

}