package chiphead.test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.model.ComputeWeekNum;
import chiphead.model.RootProjectPhase;

public class HistoryRootProjectPhaseTest {

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
 			RootProjectPhase p = new RootProjectPhase();
 			
 			String projNo = "T111112";
 			Integer rootPhaseId = new Integer(1);//1-8,参照root_phase表
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(3);//1-12
 			Integer weekNo = new Integer(4);//1-5
 	
 			
 			p.setProjNo(projNo);
 			p.setRootPhaseId(rootPhaseId);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			
			int ret = session.insert("HistoryRootProjectPhaseMapper.insertRootProjectPhase", p);
			session.commit();
			System.out.println(ret + " " + p);
			
			/*
			// 查询一条
			
			BigInteger b = new BigInteger("1");
			RootProjectPhase p1 =
			session.selectOne("RootProjectPhaseMapper.selectRootProjectPhase", b);
			System.out.println(p1);
			
			
			//根据项目编号、年份、月份、周序号查询
			RootProjectPhase p = new RootProjectPhase();
 			String projNo = "T111111";
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(3);//1-12
 			Integer weekNo = new Integer(4);//1-5
 			
 			p.setProjNo(projNo);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			
 			List<RootProjectPhase> list =	session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseByPYMW", p);
 			System.out.println(list);
			
			
			// 查询多条
 			
 			List<RootProjectPhase> list = session.selectList("RootProjectPhaseMapper.selectAllRootProjectPhases");
 			System.out.println(list);
 			
 			
			// 更新一条
 			
			RootProjectPhase p = new RootProjectPhase();
			BigInteger projPhaseId = new BigInteger("1");
 			String projNo = "T222222";
 			Integer rootPhaseId = new Integer(5);//1-8,参照阶段表
 			Integer year = new Integer(2012);//年份，四位整数
 			Integer month = new Integer(5);//1-12
 			Integer weekNo = new Integer(5);//1-5
 			
 			
 			p.setRootProjPhaseId(projPhaseId);
 			p.setProjNo(projNo);
 			p.setRootPhaseId(rootPhaseId);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			
 			session.update("RootProjectPhaseMapper.updateRootProjectPhase", p);
 			session.commit();
 			System.out.println(p);
 			
 			
			// 删除一条
 			
 			session.delete("RootProjectPhaseMapper.deleteRootProjectPhase", 4);
 			session.commit();
 			
			
			//根据项目编号、年份、月份、周序号、阶段ID定位到唯一一条并删除
			
			RootProjectPhase p = new RootProjectPhase();
 			String projNo = "T111111";
 			Integer rootPhaseId = new Integer(1);
 			Integer year = new Integer(2013);//年份，四位整数
 			Integer month = new Integer(4);//1-12
 			Integer weekNo = new Integer(3);//1-5
 			
 			p.setProjNo(projNo);
 			p.setRootPhaseId(rootPhaseId);
 			p.setYear(year);
 			p.setMonth(month);
 			p.setWeekNo(weekNo);
 			
 			p = session.selectOne("RootProjectPhaseMapper.selectRootProjectPhaseByPYMWR",p);	
 			session.delete("RootProjectPhaseMapper.deleteRootProjectPhase", p.getRootProjPhaseId());
 			session.commit();
 			System.out.println(p);
 			
			
			//根据项目编号、年份、月份、周序号取当前阶段
			RootProjectPhase p = new RootProjectPhase();
 			String projNo = "T111112";//项目编号
 			p.setProjNo(projNo);
 			java.util.Date d = new java.util.Date();//当前日期
 			Date date = new Date(d.getTime());
 			System.out.println(date);
 		    ComputeWeekNum.getRootProjectPhaseByDate(p,date);
 		    System.out.println(p);
 		    List<String> list = session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseNameByPYMW",p);
			System.out.println(list);
			
			
			//根据项目编号查询
			String projNo = "T1";
			List<RootProjectPhase> list = session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseByProjNo",projNo);
			for(int i=0;i<list.size();i++)
				System.out.println(list.get(i));
			
			
			//根据项目编号和年份查询
			RootProjectPhase p = new RootProjectPhase();
 			String projNo = "T1111222";//项目编号
 			p.setProjNo(projNo);
 			p.setYear(2013);
 			List<RootProjectPhase> list = session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseByProjNoYear",p);
			System.out.println(list);
			
			
			//根据项目编号和阶段id查询
			RootProjectPhase p = new RootProjectPhase();
 			String projNo = "T1";//项目编号
 			p.setProjNo(projNo);
 			p.setRootPhaseId(4);
 			List<RootProjectPhase> list = session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseByProjNoRootPhaseId",p);
 			for(int i=0;i<list.size();i++)
				System.out.println(list.get(i));
			
			
			
			//根据项目编号删除
			session.delete("RootProjectPhaseMapper.deleteRootProjectPhaseByProjNo", "T1111222");
 			session.commit();
 			*/
 			
		} finally {
			session.close();
		}
	}
}