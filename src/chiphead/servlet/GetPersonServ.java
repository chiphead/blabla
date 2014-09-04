package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;

import chiphead.config.Constants;
import chiphead.model.Person;
import chiphead.model.Project;
import chiphead.utils.DateUtil;

public class GetPersonServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		PrintWriter out = response.getWriter();
		
		SqlSession session = sqlSessionFactory.openSession();
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
		out.print(str);//输出到前台
		session.close();
		out.flush();
		out.close();
	}

}
