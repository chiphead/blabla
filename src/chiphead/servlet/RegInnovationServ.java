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
import java.text.ParseException;
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

public class RegInnovationServ extends HttpServlet {

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
		
		//用于登录信息检查
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
		if(per == null || !AutCheck.YstPwdCorrect(empYstId,empPwd,session)){
			session.close();
			out.print(false);
			out.flush();
			out.close();
			return;
		}
		
		//String innoNo = request.getParameter("inno_no");
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
		
		
		
		if(!AutCheck.HaveRegInnoAut(conclusion,empYstId,session)){
			session.close();
			out.print("noauth");
			out.flush();
			out.close();
			return;
		}
		
		Date regDate = null;
		if(!regDateStr.isEmpty()){
			regDate = DateUtil.getDate(regDateStr);
		}
		
		Integer innoType = Integer.parseInt(innoTypeStr);
		
		String proposers = PersonUtil.getNormalPersonsStr(proposersJsonStr);
		
		Integer system = Integer.parseInt(systemStr);
		
		String executors = PersonUtil.getNormalPersonsStr(executorsJsonStr);
		
		Innovation in = new Innovation();

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
			

		try {
			//插入创新表
			int num = session.insert("InnovationMapper.insertInnovation", in);
			session.commit();
			
			boolean result = (num > 0) ? true : false;
			if (!result) {
				out.print(result);
				return;
			}	
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
