package chiphead.test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;

import chiphead.config.Constants;
import chiphead.model.ProjectPhase;
import chiphead.model.ProjectQueryParm;

public class ProjectPhaseTest {

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
			// 插入一条
			/*
			//MySql中bigint最大值18446744073709551615
 			ProjectPhase p = new ProjectPhase();
 			
 			Integer empYstId = new Integer(274811);
 			String projNo = "T111111";
 			Integer phaseId = new Integer(2);//1-5,参照phase表
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(3);//1-12
 			Integer weekNo = new Integer(4);//1-5
 			Double devoteHours = new Double(12345678.9);
 			Integer checkState = new Integer(1);//默认值：1，1:未审核 2:审核通过3:审核不通过
 			
 			p.setEmpYstId(empYstId);
 			p.setProjNo(projNo);
 			p.setPhaseId(phaseId);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			p.setDevoteHours(devoteHours);
 			p.setCheckState(checkState);
 			
			int ret = session.insert("ProjectPhaseMapper.insertProjectPhase", p);
			session.commit();
			System.out.println(ret + " " + p);
			
			
			// 查询一条
			
			BigInteger b = new BigInteger("1");
			ProjectPhase p1 =
			session.selectOne("ProjectPhaseMapper.selectProjectPhase", b);
			System.out.println(p1);
			
			
			//根据项目编号查询
			String projNo = "T1111222";
			List<ProjectPhase> ppList = session.selectList("ProjectPhaseMapper.selectProjectPhaseByProjNo",projNo);
			for(int i=0;i<ppList.size();i++)
				System.out.println(ppList.get(i));
			
			
			
			//根据一事通ID、项目编号、阶段、年份、月份、周序号查询一条
			ProjectPhase p = new ProjectPhase();
 			Integer empYstId = new Integer(274888);
 			String projNo = "T2222222";
 			Integer phaseId = new Integer(5);//1-5,参照phase表
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(5);//1-12
 			Integer weekNo = new Integer(5);//1-5
 			
 			p.setEmpYstId(empYstId);
 			p.setProjNo(projNo);
 			p.setPhaseId(phaseId);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			
 			p =	session.selectOne("ProjectPhaseMapper.selectProjectPhaseByYPYMWP", p);
 			System.out.println(p);
			
 			
 			//根据一事通ID、项目编号、年份、月份、周序号查询记录
			ProjectPhase p = new ProjectPhase();
 			Integer empYstId = new Integer(274899);
 			String projNo = "T1111222";
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(10);//1-12
 			Integer weekNo = new Integer(1);//1-5
 			
 			p.setEmpYstId(empYstId);
 			p.setProjNo(projNo);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			
 			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectProjectPhaseByYPYMW", p);
 			System.out.println(list);
 			
			
			//根据一事通ID、项目编号、年份查询记录
			ProjectPhase p = new ProjectPhase();
 			Integer empYstId = new Integer(274899);
 			String projNo = "T1";
 			Integer year = 2013;
 			
 			p.setEmpYstId(empYstId);
 			p.setProjNo(projNo);
 			p.setYear(year);
 			
 			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectProjectPhaseByYPY", p);
 			for(int i=0;i<list.size();i++)
 				System.out.println(list.get(i));
		
 			
			//查询不同的一事通id，项目编号，指定一事通id
			Integer empYstId = 1;
			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectDistinctProjectPhasesByEmpYstId",empYstId);
			for(int i=0;i<list.size();i++)
				System.out.println(list.get(i));
			int num = session.selectOne("ProjectPhaseMapper.selectDistinctProjectPhaseNumByEmpYstId",empYstId);
			System.out.println(num);
			
			
			
			//查询不同的一事通id，项目编号
			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectDistinctProjectPhases");
			for(int i=0;i<list.size();i++)
				System.out.println(list.get(i));
			
			//查询不同的一事通id，项目编号个数，即员工排期表记录条数
			int num = session.selectOne("ProjectPhaseMapper.selectDistinctProjectPhaseNum");
			System.out.println(num);
			
			String projNo = "T11";
			String type = projNo.substring(0, 1);
			System.out.println(type);
			
			String queryType = "emp_name";
	        String queryValue = "晶晶";
	        String orderType = "proj_no";
	        String orderMethod = "asc";
			
			ProjectQueryParm projectQueryParm = new ProjectQueryParm();
	        projectQueryParm.setQueryType(queryType);
	        projectQueryParm.setQueryValue(queryValue);
	        projectQueryParm.setOrderType(orderType);
	        projectQueryParm.setOrderMethod(orderMethod);
			projectQueryParm.setStartIndex(0);
			projectQueryParm.setFetchNum(5);
			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectDistinctProjectPhasesByParms",projectQueryParm);
			for(int i=0;i<list.size();i++)
				System.out.println(list.get(i));
			int num = session.selectOne("ProjectPhaseMapper.selectDistinctProjectPhaseNumByParms",projectQueryParm);
			System.out.println(num);
			
			
			
			
			
			// 查询多条
			
 			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectAllProjectPhases");
 			System.out.println(list);
 			
 			
			// 更新一条
 			
			ProjectPhase p = new ProjectPhase();
			BigInteger projPhaseId = new BigInteger("1");
 			Integer empYstId = new Integer(274888);
 			String projNo = "T2222222";
 			Integer phaseId = new Integer(5);//1-8,参照阶段表
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(5);//1-12
 			Integer weekNo = new Integer(5);//1-5
 			Double devoteHours = new Double(12345678.9);
 			Integer checkState = new Integer(5);//默认值：1，1:未审核 2:审核通过3:审核不通过
 			
 			p.setProjPhaseId(projPhaseId);
 			p.setEmpYstId(empYstId);
 			p.setProjNo(projNo);
 			p.setPhaseId(phaseId);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			p.setDevoteHours(devoteHours);
 			p.setCheckState(checkState);
 			
 			session.update("ProjectPhaseMapper.updateProjectPhase", p);
 			session.commit();
 			System.out.println(p);
 			
			
			//根据一事通id、项目编号、年份、月份、周序号更新审核状态
			ProjectPhase p = new ProjectPhase();
 			Integer empYstId = new Integer(1235);
 			String projNo = "T1234";
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(11);//1-12
 			Integer weekNo = new Integer(3);//1-5
 			Integer checkState = new Integer(3);//默认值：1，1:未审核 2:审核通过3:审核不通过
 			
 			p.setEmpYstId(empYstId);
 			p.setProjNo(projNo);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			p.setCheckState(checkState);
 			
 			session.update("ProjectPhaseMapper.updateProjectPhaseCheckStateByEPYMW", p);
 			session.commit();
 			System.out.println(p);
			
 			
			// 删除一条
 			
 			session.delete("ProjectPhaseMapper.deleteProjectPhase", 1);
 			session.commit();
 			
			//根据项目编号删除
			String projNo = "T1";
			session.delete("ProjectPhaseMapper.deleteProjectPhaseByProjNo", projNo);
 			session.commit();
			
			
			//根据一事通id、项目编号、年份、月份、周序号删除
			ProjectPhase p = new ProjectPhase();
 			Integer empYstId = new Integer(274899);
 			String projNo = "T1";
 			Integer phaseId = new Integer(5);//1-8,参照阶段表
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(3);//1-12
 			Integer weekNo = new Integer(1);//1-5
 			
 			p.setEmpYstId(empYstId);
 			p.setProjNo(projNo);
 			p.setPhaseId(phaseId);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			
			session.delete("ProjectPhaseMapper.deleteProjectPhaseByEPYMW", p);
 			session.commit();
			
			
			//根据一事通id、项目编号、年份、月份、周序号查找
			ProjectPhase pTemp = new ProjectPhase();
 			
			pTemp.setEmpYstId(274899);
			pTemp.setProjNo("T1356071");
			pTemp.setYear(2013);
			pTemp.setMonth(10);
			pTemp.setWeekNo(2);
 			
			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectProjectPhaseByYPYMW", pTemp);
			
			Map phaseJson = new HashMap();
			List mapList = new ArrayList();
		    for(int i = 0; i < list.size(); i++) {
		    	Map cellMap = new HashMap();
		    	//从数据库中phase表取
				String phaseName =  session.selectOne("PhaseMapper.selectPhaseNameByPhaseId", list.get(i).getPhaseId());
				cellMap.put("phaseName", phaseName);
				cellMap.put("devoteHours", list.get(i).getDevoteHours());
		        mapList.add(cellMap);
		    }   
	        phaseJson.put("rows", mapList);
	        Gson gson = new Gson();

			System.out.print(gson.toJson(phaseJson));
			*/
		} finally {
			session.close();
		}
	}
}