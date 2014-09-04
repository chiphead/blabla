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

public class LoginServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		String emp_yst_id = request.getParameter("emp_yst_id");
		String emp_pwd = request.getParameter("emp_pwd");
		String remember = request.getParameter("remember");

		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		PrintWriter out = response.getWriter();

		SqlSession session = sqlSessionFactory.openSession();
		Person person = new Person();
		person.setEmp_yst_id(Integer.parseInt(emp_yst_id));
		if (remember.equals("cookie")) {					// cookies login
			person.setEmp_pwd(URLDecoder.decode(emp_pwd, "UTF-8"));
		} else {
			person.setEmp_pwd(EncUtil.MD5(URLDecoder.decode(emp_pwd, "UTF-8")));
		}
		try {
			person = session.selectOne(
					"chiphead.mapper.PersonMapper.selectPerson", person);
			boolean result = (person != null) ? true : false;
			if (result && remember.equals("true")) {
				Cookie ystidcookie = new Cookie("emp_yst_id", URLEncoder.encode(
						person.getEmp_yst_id() + "", "UTF-8"));
				Cookie namecookie = new Cookie("emp_name", URLEncoder.encode(
						person.getEmp_name(), "UTF-8"));
				Cookie pwdcookie = new Cookie("emp_pwd", URLEncoder.encode(
						person.getEmp_pwd(), "UTF-8"));
				ystidcookie.setMaxAge(30 * 24 * 60 * 60);
				namecookie.setMaxAge(30 * 24 * 60 * 60);
				pwdcookie.setMaxAge(30 * 24 * 60 * 60);
				response.addCookie(ystidcookie);
				response.addCookie(namecookie);
				response.addCookie(pwdcookie);
			}
			if(!result)
				out.print(result);
			else
				out.print(person.getEmp_aut());
		} catch (Exception e) {
			response.setContentType("text/html;charset=utf-8");
			out.print(e.toString());
			return;
		} finally {
			session.close();
		}
		out.flush();
		out.close();
	}
}
