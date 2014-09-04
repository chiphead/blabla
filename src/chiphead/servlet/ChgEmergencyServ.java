package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.model.AutCheck;
import chiphead.model.Emergency;
import chiphead.model.Person;
import chiphead.utils.DateUtil;
import chiphead.utils.PersonUtil;

public class ChgEmergencyServ extends HttpServlet {

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
		
		String emerNo = request.getParameter("emer_no");
//		String regDateStr = request.getParameter("reg_date");
		String emerDateStr = request.getParameter("emer_date");
		String emerContentStr = request.getParameter("emer_content");
		String influenceStr = request.getParameter("influence");
		String analyticsStr = request.getParameter("analytics");
		String codeStr = request.getParameter("code");
		String levelStr = request.getParameter("level");
		String operatorsStr = request.getParameter("operators");
		String projNoStr = request.getParameter("proj_no");
		String projNameStr = request.getParameter("proj_name");
		String projManagerStr = request.getParameter("proj_manager");
		String responsibleStr = request.getParameter("responsible");
		String systemStr = request.getParameter("system");
		String remarkStr = request.getParameter("remark");
		
		Date emerDate = null;
		if(!emerDateStr.isEmpty()){
			emerDate = DateUtil.getDate(emerDateStr);
		}
		
		String operators = PersonUtil.getNormalPersonsStr(operatorsStr);
		String responsible = PersonUtil.getNormalPersonsStr(responsibleStr);
		
		Integer emerNoInteger = null;
		if (emerNo != null && !emerNo.isEmpty()) {
			emerNoInteger = Integer.parseInt(emerNo);
		}
		
		Emergency emer = new Emergency();

		emer.setEmerNo(emerNoInteger);
		emer.setRegDate(DateUtil.getCurDate());
		emer.setEmerDate(emerDate);
		emer.setEmerContent(emerContentStr);
		emer.setInfluence(influenceStr);
		emer.setAnalytics(analyticsStr);
		emer.setCode(codeStr);
		emer.setLevel(levelStr);
		emer.setOperators(operators);
		emer.setProjNo(projNoStr);
		emer.setProjName(projNameStr);
		emer.setProjManager(projManagerStr);
		emer.setResponsible(responsible);
		emer.setSystem(systemStr);
		emer.setRemark(remarkStr);

		try {
			//插入紧急登记表
			int num = session.update("EmergencyMapper.updateEmergencyByEmerNo", emer);
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
