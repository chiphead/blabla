package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.model.AutCheck;
import chiphead.model.Project;

public class DelProjServ extends HttpServlet {

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		PrintWriter out = response.getWriter();
		SqlSession session = sqlSessionFactory.openSession();

		String projNo = request.getParameter("proj_no");
		projNo = projNo.replaceAll("\r|\n", "");
		String empName = request.getParameter("emp_name");
		String empYstIdString = request.getParameter("emp_yst_id");
		String empPwd = request.getParameter("emp_pwd");
		Integer empYstId = Integer.parseInt(empYstIdString);
		try {
			//判断登录信息是否正确，是否有删除权限
			if (AutCheck.YstPwdCorrect(empYstId,empPwd,session) && AutCheck.HaveDeleteProjAut(projNo,empName,session)) {
				//删除projects中该项目信息
				session.delete("ProjectMapper.deleteProjectByProjNo", projNo);
				session.commit();
				//删除person_project中该项目相关人员
				session.delete("PersonProjectMapper.deletePersonProjectByProjNo",projNo);
				session.commit();
				//删除root_project_phase中该项目的所有排期信息
				session.delete("RootProjectPhaseMapper.deleteRootProjectPhaseByProjNo", projNo);
				session.commit();
				//删除project_phase中该项目的所有员工排期信息
				session.delete("ProjectPhaseMapper.deleteProjectPhaseByProjNo", projNo);
	 			session.commit();
				out.print(true);
			} 
			else{
				out.print("noauth");
			}
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
