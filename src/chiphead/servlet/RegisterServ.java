package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import chiphead.config.Constants;
import chiphead.model.Person;
import chiphead.utils.EncUtil;

public class RegisterServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		String emp_yst_id = request.getParameter("emp_yst_id");
		String emp_name = request.getParameter("emp_name");
		String emp_pwd = request.getParameter("emp_pwd");

		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		PrintWriter out = response.getWriter();

		SqlSession session = sqlSessionFactory.openSession();
		Person person = new Person();
		person.setEmp_yst_id(Integer.parseInt(emp_yst_id));
		person.setEmp_name(emp_name);
		person.setEmp_pwd(EncUtil.MD5(URLDecoder.decode(emp_pwd, "UTF-8")));
		try {
			int num = session.insert("chiphead.mapper.PersonMapper.insertPerson",
					person);
			session.commit();
			boolean result = (num > 0) ? true : false;
			if (result) {
				Cookie ystidcookie = new Cookie("emp_yst_id", URLEncoder.encode(
						emp_yst_id, "UTF-8"));
				Cookie namecookie = new Cookie("emp_name", URLEncoder.encode(
						emp_name, "UTF-8"));
				Cookie pwdcookie = new Cookie("emp_pwd", URLEncoder.encode(
						person.getEmp_pwd(), "UTF-8"));
				ystidcookie.setMaxAge(30 * 24 * 60 * 60);
				namecookie.setMaxAge(30 * 24 * 60 * 60);
				pwdcookie.setMaxAge(30 * 24 * 60 * 60);
				response.addCookie(ystidcookie);
				response.addCookie(namecookie);
				response.addCookie(pwdcookie);
			}
			out.print(result);
		} catch (Exception e) {
			out.print(false);
		} finally {
			session.close();
		}
		out.flush();
		out.close();
	}
}
