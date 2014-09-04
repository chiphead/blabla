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
import chiphead.model.Innovation;
import chiphead.model.Operation;
import chiphead.model.Person;
import chiphead.model.Project;

public class DelInnovationServ extends HttpServlet {

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

		//取出用于登录检查及修改权限检查的信息
		String innoNoString = request.getParameter("inno_no");
		innoNoString = innoNoString.replaceAll("\r|\n", "");
		Integer innoNo;
		if(innoNoString != null && !innoNoString.isEmpty()){
			innoNo = Integer.parseInt(innoNoString);
		}
		else{
			innoNo = null;
		}
		
		String empYstIdString = request.getParameter("emp_yst_id");
		Integer empYstId;
		if(empYstIdString != null && !empYstIdString.isEmpty()){
			empYstId = Integer.parseInt(empYstIdString);
		}
		else{
			empYstId = null;
		}
		
		String empPwd = request.getParameter("emp_pwd");		

		//根据一事通id从persons取数据
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		//根据编号在innovations中查询一条
		Innovation in = session.selectOne("InnovationMapper.selectInnovationByInnoNo",innoNo);
		//人员不存在，或创新记录不存在，报错返回
		if(per == null || in ==null){
			session.close();
			out.print(false);
			out.flush();
			out.close();
			return;
		}
		
		try {
			//判断登录信息是否正确，是否有删除权限
			if (AutCheck.YstPwdCorrect(empYstId,empPwd,session) &&
					AutCheck.HaveDeleteInnoAut(innoNo,empYstId,session)) {
				//删除innovations中该条目信息
				session.delete("InnovationMapper.deleteInnovationByInnoNo", innoNo);
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
