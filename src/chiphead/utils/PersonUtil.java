package chiphead.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.model.Person;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PersonUtil {
	//将Person的json string转成以逗号分隔的string,可能返回""
	public static String getNormalPersonsStr(String jsonPersonsStr) {
		if(jsonPersonsStr == null || jsonPersonsStr.isEmpty()){
			return "";
		}
		
		Gson gson = new Gson();
		List<Person> personList = gson.fromJson(jsonPersonsStr, new TypeToken<List<Person>>(){}.getType());
		int operatorsNum = personList.size();
		String persons = "";
		for(int i=0;i<operatorsNum;i++){
			if(i == operatorsNum-1){
				persons += personList.get(i).getEmp_name();
			}
			else{
				persons += personList.get(i).getEmp_name() + ",";
			}
		}
		return persons;
	}
	
	
	//将以逗号分隔的Person的string转成json string,包含一事通id和姓名
	public static String getJsonPersonsStr(String normalPersonsStr,SqlSession session) {
		String persons = "[";
		
		if(normalPersonsStr == null || normalPersonsStr.isEmpty()){
			return "[]";
		}
		
		String personsArray[] = normalPersonsStr.split(",");//得到姓名数组
		List<Person> personList = new ArrayList<Person>();
		Person p;
		for(int i=0;i<personsArray.length;i++){//根据姓名数组，查询Persons表，得到Person类型的List
			p = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpName",personsArray[i]);
			personList.add(p);
		}
		
		for (int i = 0; i < personsArray.length; i++) {
			persons += "{\"emp_yst_id\":" + personList.get(i).getEmp_yst_id() + 
				",\"emp_name\":\"" + personList.get(i).getEmp_name() + "\"},";
		}
		persons = persons.substring(0, persons.length() - 1);
		persons += "]";
		return persons;
	}
	
	public static void main(String[] args) throws IOException{	
	
		String resource = "chiphead/config/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		/*
		try{
			System.out.println(getJsonPersonsStr("",session));
		}finally {
			session.close();
		}
		*/
		Gson gson = new Gson();
		List<Person> auditorList = new ArrayList<Person>();
		System.out.println(gson.toJson(auditorList));
	}
	
}
