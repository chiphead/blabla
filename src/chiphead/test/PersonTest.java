package chiphead.test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
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

import chiphead.config.Constants;
import chiphead.model.Person;
import chiphead.utils.EncUtil;

public class PersonTest {

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
 			Person p = new Person();
 			Integer empYstId = new Integer(274813);			
 			String empName = "晶";
 			String empPwd = "111111";
 			Integer empAut = new Integer(1);//1:管理员 2:用户
 			Integer teamId = 1;
 			
 			p.setEmp_yst_id(empYstId);
 			p.setEmp_name(empName);
 			p.setEmp_pwd(empPwd);
 			p.setEmp_aut(empAut);
 			p.setTeam_id(teamId);
 			
			int ret = session.insert("chiphead.mapper.PersonMapper.insertPerson", p);
			session.commit();
			System.out.println(ret + " " + p);
			
			
			// 查询一条
			
			//BigInteger b2 = new BigInteger("18446744073709551615");
			BigInteger b2 = new BigInteger("12");
			Person per1 =
			session.selectOne("PersonMapper.selectPerson", b2);
			System.out.println(per1);
			
			
			// 根据一事通ID查询一条
			Integer empYstId2 = new Integer(274899);
			Person per2 =
			session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId2);
			System.out.println(per2);
			
			
			// 根据一事通ID查姓名
			Integer empYstId3 = new Integer(274899);
			String name =
			session.selectOne("chiphead.mapper.PersonMapper.selectEmpNameByEmpYstId", empYstId3);
			System.out.println(name);
			
			//根据一事通ID和密码查询条数
			Person p = new Person();
			p.setEmp_yst_id(274888);
			p.setEmp_pwd("11");
			int num =
			session.selectOne("chiphead.mapper.PersonMapper.selectPersonNumByEmpYstIdPwd", p);
			System.out.println(num);
			
			
			// 根据姓名查询
			String empName2 = "常晶晶";
			Person p = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpName",empName2);
 			System.out.println(p);
			
			
			
			//根据姓名模糊查询
			String empName2 = "哈";
			List<Person> list = session.selectList("chiphead.mapper.PersonMapper.selectPersonByEmpNamePart",empName2);
 			for(int i=0;i<list.size();i++)
 				System.out.println(list.get(i));
			
			// 查询多条
 			*/
			
 			List<Person> list = session.selectList("chiphead.mapper.PersonMapper.selectAllPersons");
 			for(int i=0;i<list.size();i++)
 				System.out.println(list.get(i));
 			
 			/*
			// 更新一条
			Person p = new Person();
			BigInteger empId = new BigInteger("12");
 			Integer empYstId = new Integer(274899);			
 			String empName = "常晶晶achang";
 			String empPwd = "222222";
 			Integer empAut = new Integer(1);//1:管理员 2:用户
 			
 			p.setEmp_id(empId);
 			p.setEmp_yst_id(empYstId);
 			p.setEmp_name(empName);
 			p.setEmp_pwd(empPwd);
 			p.setEmp_aut(empAut);
 			
 			session.update("PersonMapper.updatePerson", p);
 			session.commit();
 			System.out.println(p);
 			
 			
			// 删除一条
 			session.delete("PersonMapper.deletePerson", 12);
 			session.commit();
 			
 			Gson gson = new Gson();
 			List<Person> list = session.selectList("chiphead.mapper.PersonMapper.selectAllPersons");//从数据库里拿出个List
 			
 			Map pageInfo = new HashMap();
 			List mapList = new ArrayList();
 			
 			for(int i = 0; i < list.size(); i++) {   
 	            Map cellMap = new LinkedHashMap();  
 	     
 	            cellMap.put("emp_yst_id", (list.get(i)).getEmp_yst_id());   
 	            cellMap.put("emp_name", (list.get(i)).getEmp_name()); 
 	            
 	            mapList.add(cellMap);   
 	        }   
 	        pageInfo.put("rows", mapList);
 	      
 			String str = gson.toJson(pageInfo);			
 			System.out.print(str);//输出到前台
			*/
			
			
		} finally {
			session.close();
		}
	}

}