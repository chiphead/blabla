package chiphead.test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;

import chiphead.model.ComputeWeekNum;
import chiphead.model.DisplayOperation;
import chiphead.model.DisplayParse;
import chiphead.model.Innovation;
import chiphead.model.Operation;
import chiphead.model.Person;
import chiphead.model.ProjectQueryParm;
import chiphead.utils.DateUtil;


public class InnovationTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		String resource = "chiphead/config/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();

		try {
			// 插入一条
			/*
 			Innovation in = new Innovation();
 			
 			Date regDate = Date.valueOf("2013-11-11");
 			Integer innoType = 1;
 			String proposers = "梁万山";
 			Integer system = 1;
 			String module = "呵呵";
 			String suggestion = "哈哈";
 			String reason = "拍脑袋大法";
 			String addSuggestion = "啦啦";
 			String conclusion = "无";
 			String executors = "倪文才,周国荣";
 			String effect = "无";

 			in.setRegDate(regDate);
 			in.setInnoType(innoType);
 			in.setProposers(proposers);
 			in.setSystem(system);
 			in.setModule(module);
 			in.setSuggestion(suggestion);
 			in.setReason(reason);
 			in.setAddSuggestion(addSuggestion);
 			in.setConclusion(conclusion);
 			in.setExecutors(executors);
 			in.setEffect(effect);
 			
 			System.out.println(in.getAddSuggestion());
			int ret = session.insert("InnovationMapper.insertInnovation", in);
			session.commit();
			System.out.println(ret + " " + in);
			
			
			
			//根据编号查询一条
			Innovation in = session.selectOne("InnovationMapper.selectInnovationByInnoNo",1);
 			System.out.println(in);
			
			
 			//根据参数查询并排序、分页
			ProjectQueryParm p = new ProjectQueryParm();
			String queryType = "inno_type";
			String queryValue = "";
			String orderType = "reg_date";
			String orderMethod = "desc";
			Integer startIndex = 0;
			Integer fetchNum = 5;
			
			p.setQueryType(queryType);
			p.setQueryValue(queryValue);
			p.setOrderType(orderType);
			p.setOrderMethod(orderMethod);
			p.setStartIndex(startIndex);
			p.setFetchNum(fetchNum);
			
 			List<Innovation> list2 = session.selectList("InnovationMapper.selectInnovationsByParms",p);
 			for(int i=0;i<list2.size();i++)
 				System.out.println(list2.get(i));
 			
 			Integer num = session.selectOne("InnovationMapper.selectInnovationsNumByParms",p);
 			System.out.println(num);
 			
			
			
			
			//查询所有，根据提出日期降序排列
			List<Innovation> list2 = session.selectList("InnovationMapper.selectAllInnovationsOrderByRegDateDesc");
 			for(int i=0;i<list2.size();i++)
 				System.out.println(list2.get(i));
			
 			
			
			//根据编号更新一条
			Innovation in = new Innovation();
 			
			Integer innoNo = 1;
 			Date regDate = Date.valueOf("2013-11-11");
 			Integer innoType = 1;
 			String proposers = "啦啦啦";
 			Integer system = 1;
 			String module = "呵呵";
 			String suggestion = "哈哈";
 			String reason = "拍脑袋大法";
 			String addSuggestion = "啦啦";
 			String conclusion = "无";
 			String executors = "倪文才,周国荣";
 			String effect = "无";

 			in.setInnoNo(innoNo);
 			in.setRegDate(regDate);
 			in.setInnoType(innoType);
 			in.setProposers(proposers);
 			in.setSystem(system);
 			in.setModule(module);
 			in.setSuggestion(suggestion);
 			in.setReason(reason);
 			in.setAddSuggestion(addSuggestion);
 			in.setConclusion(conclusion);
 			in.setExecutors(executors);
 			in.setEffect(effect);
 			
 			session.update("InnovationMapper.updateInnovationByInnoNo", in);
 			session.commit();
 			System.out.println(in);
			
			
			//根据编号更新结论
			Innovation in = new Innovation();
			in.setInnoNo(1);
			in.setConclusion("stay hungry,stay foolish");
			session.update("InnovationMapper.updateConclusionByInnoNo", in);
 			session.commit();
 			System.out.println(in);
			
			
			
			//根据编号删除一条	
 			session.delete("InnovationMapper.deleteInnovationByInnoNo", 2);
 			session.commit();
			*/
			
			
		} finally {
			session.close();
		}
	}

}