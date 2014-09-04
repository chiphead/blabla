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
import chiphead.model.Operation;
import chiphead.model.Person;
import chiphead.model.ProjectQueryParm;
import chiphead.utils.DateUtil;


public class OperationTest {

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
 			Operation op = new Operation();
 			
 			Date regDate = Date.valueOf("2013-11-11");
 			Integer operType = 1;
 			Timestamp operStartTime = DateUtil.getDateTime("2013/11/10 10:11");
 			Timestamp operEndTime = DateUtil.getDateTime("2013/11/10 23:11") ;
 			String operation = "啥也没干";
 			String operators = "常晶晶、周悦";
 			String system = "交换平台";
 			String module = "VISA日终";
 			String reason = "呵呵";
 			String suggestion = "无";
 			Integer checkState = 1;
 			
 			op.setRegDate(regDate);
 			op.setOperType(operType);
 			op.setOperStartTime(operStartTime);
 			op.setOperEndTime(operEndTime);
 			op.setOperation(operation);
 			op.setOperators(operators);
 			op.setSystem(system);
 			op.setModule(module);
 			op.setReason(reason);
 			op.setSuggestion(suggestion);
 			op.setCheckState(checkState);
 			
			int ret = session.insert("OperationMapper.insertOperation", op);
			session.commit();
			System.out.println(ret + " " + op);
			
			
			
 			//根据参数查询并排序、分页
			ProjectQueryParm p = new ProjectQueryParm();
			String queryType = "oper_type";
			String queryValue = "电话";
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
			
 			List<Operation> list2 = session.selectList("OperationMapper.selectOperationsByParms",p);
 			for(int i=0;i<list2.size();i++)
 				System.out.println(list2.get(i));
 			
 			Integer num = session.selectOne("OperationMapper.selectOperationsNumByParms",p);
 			System.out.println(num);
 			*/
			
			//查询所有，根据登记日期降序排列
			List<Operation> list2 = session.selectList("OperationMapper.selectAllOperationsOrderByRegDateDesc");
 			for(int i=0;i<list2.size();i++)
 				System.out.println(list2.get(i));
			
 			/*
			//根据编号更新一条
			Operation op = new Operation();
 			
			Integer operNo = 1;
 			Date regDate = Date.valueOf("2013-11-22");
 			Integer operType = 1;
 			Timestamp operStartTime = DateUtil.getDateTime("2013/11/10 10:11:11");
 			Timestamp operEndTime = DateUtil.getDateTime("2013/11/10 23:11:11") ;
 			String operation = "啥也没干";
 			String operators = "常晶晶、周悦、邬丹";
 			String system = "交换平台";
 			String module = "VISA日终";
 			String reason = "呵呵";
 			String suggestion = "无";
 			Integer checkState = 1;
 			
 			op.setOperNo(operNo);
 			op.setRegDate(regDate);
 			op.setOperType(operType);
 			op.setOperStartTime(operStartTime);
 			op.setOperEndTime(operEndTime);
 			op.setOperation(operation);
 			op.setOperators(operators);
 			op.setSystem(system);
 			op.setModule(module);
 			op.setReason(reason);
 			op.setSuggestion(suggestion);
 			op.setCheckState(checkState);
 			
 			session.update("OperationMapper.updateOperationByOperNo", op);
 			session.commit();
 			System.out.println(op);
			
			
			//根据编号更新审批状态
			Operation op = new Operation();
			op.setOperNo(1);
			op.setCheckState(3);
			session.update("OperationMapper.updateCheckStateByOperNo", op);
 			session.commit();
 			System.out.println(op);
			
			
			//根据编号删除一条	
 			session.delete("OperationMapper.deleteOperationByOperNo", 2);
 			session.commit();
			*/
			
			
		} finally {
			session.close();
		}
	}

}