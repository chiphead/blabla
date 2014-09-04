package chiphead.test;

import java.io.IOException;
import java.io.InputStream;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.model.PersonProject;

public class HistoryPersonProjectTest {

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
 			PersonProject perpro = new PersonProject();
 			//empProjId自增，无需设置
 			//BigInteger empProjId = new BigInteger("18446744073709551615");
 			//perpro.setEmpProjId(empProjId);
 			Integer empYstId = new Integer(274899);			
 			String projNo = "T1111111";	
 			Integer roleId = new Integer(2);//1：设计 2:开发 3:评审
 
 			perpro.setEmpYstId(empYstId);
 			perpro.setProjNo(projNo);
 			perpro.setRoleId(roleId);
 			
			int ret = session.insert("HistoryPersonProjectMapper.insertPersonProject", perpro);
			session.commit();
			System.out.println(ret + " " + perpro);
			
			/*
			// 查询一条
			
			//BigInteger b2 = new BigInteger("18446744073709551615");
			BigInteger b2 = new BigInteger("1");
			PersonProject perpro1 =
			session.selectOne("PersonProjectMapper.selectPersonProject", b2);
			System.out.println(perpro1);
			
			
			//根据一事通ID和项目编号查询一条
			PersonProject pTemp = new PersonProject();
			Integer empYstId2 = new Integer("274888");
			String projNo2 = "T1111122";
			pTemp.setEmpYstId(empYstId2);
			pTemp.setProjNo(projNo2);
			
			PersonProject perpro2 =
			session.selectOne("PersonProjectMapper.selectPersonProjectByEmpYstIdProjNo", pTemp);
			System.out.println(perpro2);
			
			// 查询多条
			
 			List<PersonProject> list = session.selectList("PersonProjectMapper.selectAllPersonProjects");
 			System.out.println(list);
 			
			
			// 更新一条
			
			PersonProject perpro1 = new PersonProject();
			BigInteger empProjId = new BigInteger("1");
			Integer empYstId = new Integer(274888);
			String projNo = "T2222222";
			Integer roleId = new Integer(1);//1：设计 2:开发 3:评审
			
			perpro1.setEmpProjId(empProjId);
			perpro1.setEmpYstId(empYstId);
			perpro1.setProjNo(projNo);
			perpro1.setRoleId(roleId);
			
 			session.update("PersonProjectMapper.updatePersonProject", perpro1);
 			session.commit();
 			System.out.println(perpro1);
 			
 			
			// 删除一条
 			session.delete("PersonProjectMapper.deletePersonProject", 1);
 			session.commit();
 			
			Gson gson = new Gson();
			String str = "[{\"id\":1,\"name\":\"aaa\"}," +
					"{\"id\":2,\"name\":\"bbb\"}] ";
			
			List<Per> ps = gson.fromJson(str, new TypeToken<List<Per>>(){}.getType());
			for(int i = 0; i < ps.size() ; i++)
			{
			Per p = ps.get(i);
			System.out.println(p.getId());
			System.out.println(p.getName());
			}
			
			
			//根据项目编号和角色查询用户信息
			PersonProject perpro = new PersonProject();
 			//empProjId自增，无需设置
 			//BigInteger empProjId = new BigInteger("18446744073709551615");
 			//perpro.setEmpProjId(empProjId);
			String projNo = "T1111113";				
 			Integer roleId = new Integer(3);//1：设计 2:开发 3:评审
 
 			perpro.setProjNo(projNo);
 			perpro.setRoleId(roleId);
			
			List<Person> list = session.selectList("PersonProjectMapper.selectPersonByProjNoRoleId",perpro);
 			System.out.println(list);
 			Gson gson = new Gson();
 			String jsonList = gson.toJson(list);
 			System.out.println(jsonList);
 			List<Person> auditorList = gson.fromJson(jsonList, new TypeToken<List<Person>>(){}.getType());
 			for(int i=0;i<auditorList.size();i++)
 				System.out.println(auditorList.get(i));
 			
			//根据项目编号删除
			session.delete("PersonProjectMapper.deletePersonProjectByProjNo","11");
			session.commit();
			*/
		} finally {
			session.close();
		}
	}

}