package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import chiphead.config.Constants;
import chiphead.model.ComputeWeekNum;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.ProjectPhase;
import chiphead.model.RootProjectPhase;
import chiphead.utils.EncUtil;

public class ShowChgEmpSchdServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		//根据得到的一事通id、项目编号、列id返回阶段和时间
		String projNo = request.getParameter("proj_no");
		String colIdString = request.getParameter("col_id");
		String empYstIdString = request.getParameter("emp_yst_id");//Integer
		
		int colId = Integer.parseInt(colIdString);
		Integer empYstId = Integer.parseInt(empYstIdString);
		
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		
		PrintWriter out = response.getWriter();		

		try {
			int[] ymw = ComputeWeekNum.getYearMonthWeekNoByColId(colId);
			int year = ymw[0];
			int month = ymw[1];
			int weekNo = ymw[2];
			
			//得到的年月周序号均为0，数据不合法
			if(year == 0){
				out.print(false);
				return;
			}	
			
			//根据一事通id、项目编号、年份、月份、周序号查找
			ProjectPhase pTemp = new ProjectPhase();
 			
			pTemp.setEmpYstId(empYstId);
			pTemp.setProjNo(projNo);
			pTemp.setYear(year);
			pTemp.setMonth(month);
			pTemp.setWeekNo(weekNo);
 			
			List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectProjectPhaseByYPYMW", pTemp);
			
			Map phaseJson = new HashMap();
			List mapList = new ArrayList();
		    for(int i = 0; i < list.size(); i++) {
		    	Map cellMap = new HashMap();
		    	//从数据库中phase表取
				String phaseName =  session.selectOne("PhaseMapper.selectPhaseNameByPhaseId", list.get(i).getPhaseId());
				cellMap.put("devoteHours", list.get(i).getDevoteHours());
				cellMap.put("phaseName", phaseName);
		        mapList.add(cellMap);
		    }   
	        phaseJson.put("rows", mapList);
	        Gson gson = new Gson();

			out.print(gson.toJson(phaseJson));
		} catch (Exception e) {
			e.printStackTrace();
			out.print(false);
		} finally {
			session.close();
		}
		out.flush();
		out.close();
	}
}