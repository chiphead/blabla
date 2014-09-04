package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import chiphead.model.ComputeWeekNum;
import chiphead.model.Person;
import chiphead.model.Project;
import chiphead.utils.DateUtil;

public class GetProjSchdServ extends HttpServlet {

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
		String str = "";
		
		//String startYearString = request.getParameter("startYear");
		//String startMonthString = request.getParameter("startMonth");
		//String startWeekNoString = request.getParameter("startWeekNo");
		//String endYearString = request.getParameter("endYear");
		//String endMonthString = request.getParameter("endMonth");
		//String endWeekNoString = request.getParameter("endWeekNo");
		
		//if(startYearString != null && startMonthString != null && startWeekNoString != null &&
		//   endYearString != null && endMonthString != null && endWeekNoString != null){
		//返回显示周数
		//此处为java.util.Date，大部分地方都是java.sql.Date
		Date date = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		cal.setTime(date);
		int curYear = cal.get(Calendar.YEAR);//获得当前年份
		int curMonth = cal.get(Calendar.MONTH);//获得当前月份,0-11
		int startYear = curYear;
		int startMonth = curMonth+1;//0~11 -> 1~12
		int startWeekNo = 1;
		int endYear = curYear;
		int endMonth = 12;
		int endWeekNo = ComputeWeekNum.getWeekNumByYearMonth(endYear,endMonth);
		if(startMonth > 1){//如果当前月份大于1，从上个月开始
			startMonth -= 1;
		}
		else{//如果当前月份等于1，从上个月开始，即从去年最后一个月开始
			startYear = curYear-1;
			startMonth = 12;
		}
		if(Constants.debugMode){
			startYear = 2013;
			startMonth = 12;
			startWeekNo = 1;
			endYear = 2014;
			endMonth = 12;
			endWeekNo = 3;
		}
		str += ComputeWeekNum.getTableHead(startYear,startMonth,startWeekNo,endYear,endMonth,endWeekNo, "");	
		
		out.print(str);//输出到前台
		session.close();
		out.flush();
		out.close();
	}

}
