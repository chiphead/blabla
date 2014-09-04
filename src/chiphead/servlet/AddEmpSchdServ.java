package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;

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

public class AddEmpSchdServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		//根据得到的一事通id、项目编号、列id、阶段编号、投入时间添加
		String projNo = request.getParameter("proj_no");
		String colIdString = request.getParameter("col_id");
		String phaseIdString = request.getParameter("phase_id");//Integer
		String devoteHoursString = request.getParameter("devote_hours");//Double
		String empYstIdString = request.getParameter("emp_yst_id");//Integer
		
		int colId = Integer.parseInt(colIdString);
		Integer phaseId = Integer.parseInt(phaseIdString);
		Double devoteHours = Double.parseDouble(devoteHoursString);
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
				
			//根据一事通id、项目编号、年份、月份、周序号、阶段id查询，看是否在该周已存在该阶段
			ProjectPhase pTemp = new ProjectPhase();		
			pTemp.setEmpYstId(empYstId);
			pTemp.setProjNo(projNo);
			pTemp.setPhaseId(phaseId);
			pTemp.setYear(year);
			pTemp.setMonth(month);
			pTemp.setWeekNo(weekNo);
			pTemp =	session.selectOne("ProjectPhaseMapper.selectProjectPhaseByYPYMWP", pTemp);
			//若找到，即该人该项目该周已存在该阶段数据
			if(pTemp != null){
				out.print("duplicateProjPhase");
				return;
			}	

			int checkState = 1;//初始置为未审核状态
			//根据一事通id查询
			Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
			if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3){
				//如果添加排期的是管理员或者超级管理员，审核状态置为审核通过
				checkState = 2;
			}
			
			//根据一事通id、项目编号、年份、月份、周序号、阶段id、投入小时数、审核状态插入一条记录，若插入不成功，返回false，否则返回true
			boolean result = false;
			result = ComputeWeekNum.fillProjectPhase(empYstId,projNo,year,month,weekNo,phaseId,devoteHours,checkState,session);
			out.print(result);
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
