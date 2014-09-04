package chiphead.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.model.ProjectPhase;

public class HistoryProjectPhaseTest {

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
 			
			int ret = session.insert("HistoryProjectPhaseMapper.insertProjectPhase", p);
			session.commit();
			System.out.println(ret + " " + p);
			
			/*
			// 查询一条
			
			BigInteger b = new BigInteger("1");
			ProjectPhase p1 =
			session.selectOne("ProjectPhaseMapper.selectProjectPhase", b);
			System.out.println(p1);
			
			
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
 			String projNo = "T1111222";
 			Integer year = 2013;
 			
 			p.setEmpYstId(empYstId);
 			p.setProjNo(projNo);
 			p.setYear(year);
 			
 			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectProjectPhaseByYPY", p);
 			System.out.println(list);
			
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
 			
 			
			// 删除一条
 			
 			session.delete("ProjectPhaseMapper.deleteProjectPhase", 1);
 			session.commit();
 			*/
		} finally {
			session.close();
		}
	}
}