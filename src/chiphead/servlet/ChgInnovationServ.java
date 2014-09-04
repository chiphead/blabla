package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
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

import chiphead.model.AutCheck;
import chiphead.model.ComputeWeekNum;
import chiphead.model.Innovation;
import chiphead.model.Operation;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.RootProjectPhase;
import chiphead.utils.DateUtil;
import chiphead.utils.EncUtil;
import chiphead.utils.PersonUtil;

public class ChgInnovationServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		String resource = "chiphead/config/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
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
		
		String regDateStr = request.getParameter("reg_date");//to be processed
		String innoTypeStr = request.getParameter("inno_type");//to be processed
		String proposersJsonStr = request.getParameter("proposers");//to be processed
		String systemStr = request.getParameter("system");//to be processed
		String module = request.getParameter("module");
		String suggestion = request.getParameter("suggestion");
		String reason = request.getParameter("reason");
		String addSuggestion = request.getParameter("add_suggestion");
		String conclusion = request.getParameter("conclusion");
		String executorsJsonStr = request.getParameter("executors");//to be processed
		String effect = request.getParameter("effect");	
		
		
		//判断登录信息是否正确
		if (!AutCheck.YstPwdCorrect(empYstId,empPwd,session) ) {
			out.print("notlogged");
			session.close();
			out.flush();
			out.close();
			return;
		} else if (!AutCheck.HaveChangeInnoAut(innoNo,conclusion,empYstId,session)) {
			//判断是否有修改权限(结论字段只能由管理员或超级管理员修改，其他字段不设权限限制)
			out.print("noauth");
			session.close();
			out.flush();
			out.close();
			return;
		}
		
		
		try {
				//转换要更新的信息
				Date regDate = null;
				if(!regDateStr.isEmpty()){
					regDate = DateUtil.getDate(regDateStr);
				}
				
				Integer innoType = Integer.parseInt(innoTypeStr);
				
				String proposers = PersonUtil.getNormalPersonsStr(proposersJsonStr);
				
				Integer system = Integer.parseInt(systemStr);
				
				String executors = PersonUtil.getNormalPersonsStr(executorsJsonStr);

				in.setRegDate(regDate);
				in.setInnoType(innoType);
				in.setProposers(proposers);
				in.setSystem(system);
				in.setModule(module);
				in.setSuggestion(suggestion);
				in.setReason(reason);
				in.setAddSuggestion(addSuggestion);
				in.setConclusion(conclusion);
				in.setExecutors(executors);
				in.setEffect(effect);
				
				session.update("InnovationMapper.updateInnovationByInnoNo", in);
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
