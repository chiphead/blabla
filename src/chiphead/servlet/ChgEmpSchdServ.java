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

public class ChgEmpSchdServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		//根据得到的一事通id、项目编号、列id、阶段编号、投入时间添加
		String projNo = request.getParameter("proj_no");
		String colIdString = request.getParameter("col_id");
		String empYstIdString = request.getParameter("emp_yst_id");//Integer
		String phaseJson = request.getParameter("phase_json");
		
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
			
			//根据一事通id、项目编号、年份、月份、周序号删除所有阶段
			ProjectPhase pTemp = new ProjectPhase();
 			
			pTemp.setEmpYstId(empYstId);
			pTemp.setProjNo(projNo);
			pTemp.setYear(year);
			pTemp.setMonth(month);
			pTemp.setWeekNo(weekNo);
 			
			session.delete("ProjectPhaseMapper.deleteProjectPhaseByEPYMW", pTemp);
 			session.commit();
 			
 			//根据一事通id查询
			Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
			
			//获得要插入的阶段id和投入小时数，并逐条插入
			Gson gson = new Gson();
			List<PhaseTemp> pt = gson.fromJson(phaseJson, new TypeToken<List<PhaseTemp>>(){}.getType());
			for(int i = 0; i < pt.size() ; i++)
			{
				PhaseTemp p = pt.get(i);
				pTemp.setPhaseId(p.getPhase_id());
				pTemp.setDevoteHours(p.getDevote_hours());
				if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3){
					//如果修改排期的是管理员或者超级管理员，审核状态置为审核通过
					pTemp.setCheckState(2);
				}
				else{
					//如果修改排期的是普通用户，置为未审核
					pTemp.setCheckState(1);
				}
				session.insert("ProjectPhaseMapper.insertProjectPhase", pTemp);
				session.commit();
			}
			out.print(true);
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

class PhaseTemp{
	Integer phase_id;
	Double devote_hours;
	public Integer getPhase_id() {
		return phase_id;
	}
	public void setPhase_id(Integer phaseId) {
		phase_id = phaseId;
	}
	public Double getDevote_hours() {
		return devote_hours;
	}
	public void setDevote_hours(Double devoteHours) {
		devote_hours = devoteHours;
	}
	
}
