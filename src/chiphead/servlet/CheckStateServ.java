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
import chiphead.model.AutCheck;
import chiphead.model.ComputeWeekNum;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.ProjectPhase;
import chiphead.model.RootProjectPhase;
import chiphead.utils.EncUtil;

public class CheckStateServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		
		PrintWriter out = response.getWriter();		
		
		//取出用于登录检查及修改权限检查的信息
		String empYstIdString = request.getParameter("emp_yst_id");//登录用户一事通id
		String empPwd = request.getParameter("emp_pwd");//登录用户密码
		
		//根据得到的姓名、项目编号、列id添加
		String empName = request.getParameter("emp_name");
		empName = empName.replaceAll("\r|\n", "");
		String projNo = request.getParameter("proj_no");
		projNo = projNo.replaceAll("\r|\n", "");
		String colIdString = request.getParameter("col_id");
		String checkStateString = request.getParameter("check_state");
		
		int colId = Integer.parseInt(colIdString);
		int checkState = Integer.parseInt(checkStateString);
		
		if (empYstIdString == null || empYstIdString.equals("")) {
			session.close();
			out.print(false);
			out.flush();
			out.close();
			return;
		}
		
		try {
			Integer empYstId = Integer.parseInt(empYstIdString);
			//判断登录信息是否正确，是否有审核权限
			if(!AutCheck.YstPwdCorrect(empYstId,empPwd,session) 
					|| !AutCheck.HaveCheckStateAut(empYstId,empName,session)){
				out.print("noauth");
				return;
			}
			
			int[] ymw = ComputeWeekNum.getYearMonthWeekNoByColId(colId);
			int year = ymw[0];
			int month = ymw[1];
			int weekNo = ymw[2];
			
			//得到的年月周序号均为0，数据不合法
			if(year == 0){
				out.print(false);
				return;
			}	
			//for(int i=0;i<3;i++)
				//System.out.println(ymw[i]);
			//根据一事通id、项目编号、年份、月份、周序号更新审核状态
			ProjectPhase pTemp = new ProjectPhase();
 			
			Person p = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpName",empName);
			if(p==null){
				out.print(false);
				return;
			}	
				
			pTemp.setEmpYstId(p.getEmp_yst_id());
			pTemp.setProjNo(projNo);
			pTemp.setYear(year);
			pTemp.setMonth(month);
			pTemp.setWeekNo(weekNo);
 			pTemp.setCheckState(checkState);
 			
 			
 			session.update("ProjectPhaseMapper.updateProjectPhaseCheckStateByEPYMW", pTemp);
 			session.commit();
 			
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