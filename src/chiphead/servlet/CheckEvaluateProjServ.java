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

public class CheckEvaluateProjServ extends HttpServlet {

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
		
		//根据得到的项目编号和评估状态 修改项目
		String projNo = request.getParameter("proj_no");
		projNo = projNo.replaceAll("\r|\n", "");
		String evaluateStateString = request.getParameter("evaluate_state");
		
		int evaluateState = Integer.parseInt(evaluateStateString);
		
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
					|| !AutCheck.HaveCheckEvaluateProjAut(empYstId,session)){
				out.print("noauth");
				return;
			}
			
			//根据项目编号查询
			Project p = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
			if(p == null){//项目不存在
				out.print(false);
				return;
			}
			
			//根据项目编号更新审核状态
			Project pTemp = new Project();
 			pTemp.setProjNo(projNo);
			pTemp.setEvaluateState(evaluateState);		
 			
			session.update("ProjectMapper.updateEvaluateStateByProjNo", pTemp);
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