package chiphead.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.model.DisplayParse;
import chiphead.model.DisplayProject;
import chiphead.model.Project;

public class DisplayProjectTest {

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
		Project p = session.selectOne("ProjectMapper.selectProject",2);
		System.out.println(p);
		DisplayProject dis = new DisplayProject();
		System.out.println(dis);
		// DisplayParse.parseToDisplayProject(dis, p);
		DisplayParse dParse = new DisplayParse();
		dParse.parseToDisplayProject(dis, p);
		System.out.println(dis);
	}

}
