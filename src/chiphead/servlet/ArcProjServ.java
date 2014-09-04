package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

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
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.ProjectPhase;
import chiphead.model.RootProjectPhase;

public class ArcProjServ extends HttpServlet {

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
		String curPhase = request.getParameter("cur_phase");
		curPhase = curPhase.replaceAll("\r|\n", "");
		try {
			//判断登录信息是否正确，是否有归档权限
			if (!AutCheck.YstPwdCorrect(empYstId,empPwd,session) || !AutCheck.HaveArchiveProjAut(projNo,empName,session)) {
				out.print("noauth");	
			} 
			else if(!curPhase.equals("结项")){
				out.print("notended");
			}
			else{
				//第1步
				//根据项目编号在projects中查询一条
				Project p = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
				
				if(p==null){
					out.print(false);
					return;
				}
//				else if(p.getEvaluateState()!=2){//未审批通过
//					out.print("notpassed");
//					return;
//					
//				}
				//插入history_projects中
				p.setProjId(null);
				session.insert("HistoryProjectMapper.insertProject", p);
				session.commit();
				//删除projects中该项目信息
				session.delete("ProjectMapper.deleteProjectByProjNo", projNo);
				session.commit();
				
				//第2步
				//根据项目编号在person_project中查询
				List<PersonProject> ppList = session.selectList("PersonProjectMapper.selectPersonProjectByProjNo",projNo);
				PersonProject pp;
				//插入history_person_project中
				for(int i=0;i<ppList.size();i++){
					pp = ppList.get(i);
					pp.setEmpProjId(null);
					session.insert("HistoryPersonProjectMapper.insertPersonProject", pp);
					session.commit();
				}
				//删除person_project中该项目相关人员
				session.delete("PersonProjectMapper.deletePersonProjectByProjNo",projNo);
				session.commit();
				
				//第3步
				//根据项目编号查询root_project_phase中该项目信息
				List<RootProjectPhase> rppList = session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseByProjNo",projNo);
				RootProjectPhase rpp;
				//插入history_root_project_phase中
				for(int i=0;i<rppList.size();i++){
					rpp = rppList.get(i);
					rpp.setRootProjPhaseId(null);
					session.insert("HistoryRootProjectPhaseMapper.insertRootProjectPhase", rpp);
					session.commit();
				}
				//删除root_project_phase中该项目的所有排期信息
				session.delete("RootProjectPhaseMapper.deleteRootProjectPhaseByProjNo", projNo);
				session.commit();
				
				//第4步
				//根据项目编号查询project_phase中该项目信息
				List<ProjectPhase> prophaList = session.selectList("ProjectPhaseMapper.selectProjectPhaseByProjNo",projNo);
				ProjectPhase propha;
				//插入history_project_phase中
				for(int i=0;i<prophaList.size();i++){
					propha = prophaList.get(i);
					propha.setProjPhaseId(null);
					session.insert("HistoryProjectPhaseMapper.insertProjectPhase", propha);
					session.commit();
				}
				//删除project_phase中该项目的所有员工排期信息
				session.delete("ProjectPhaseMapper.deleteProjectPhaseByProjNo", projNo);
	 			session.commit();
				out.print(true);
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
