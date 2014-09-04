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
import chiphead.model.AutCheck;
import chiphead.model.ComputeWeekNum;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.RootProjectPhase;
import chiphead.utils.EncUtil;

public class ChgProjAutCheckServ extends HttpServlet {

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
		
		//System.out.print("222");//输出到后台	
		
		//取出用于登录检查及修改权限检查的信息
		String projNo = request.getParameter("proj_no");
		projNo = projNo.replaceAll("\r|\n", "");
		String emp_name = request.getParameter("emp_name");
		String empYstIdString = request.getParameter("emp_yst_id");
		String empPwd = request.getParameter("emp_pwd");
		
		//根据项目编号在projects中查询一条
		Project project = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
		
		if (empYstIdString == null || empYstIdString.equals("") || project==null) {
			session.close();
			out.print(false);
			System.out.print(false);
			out.flush();
			out.close();
			return;
		}
		
		Integer empYstId = Integer.parseInt(empYstIdString);

		if (!AutCheck.YstPwdCorrect(empYstId,empPwd,session)) {
			out.print("notlogged");
			//System.out.print("notlogged");
			
		} else if (!AutCheck.HaveChangeProjAut(projNo,emp_name,session)) {
			out.print("noauth");
			//System.out.print("notlogged");
		}
		else{
			out.print(true);
			//System.out.print(true);
		}
			
		session.close();
		out.flush();
		out.close();
	}
}
