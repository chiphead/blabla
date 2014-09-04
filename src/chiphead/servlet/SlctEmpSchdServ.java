package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.model.ComputeWeekNum;
import chiphead.model.DisplayParse;
import chiphead.model.DisplayProject;
import chiphead.model.Person;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.ProjectPhase;
import chiphead.model.ProjectQueryParm;

import com.google.gson.Gson;

public class SlctEmpSchdServ extends HttpServlet {

	@SuppressWarnings("unchecked")
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
		
		String queryType = request.getParameter("qtype");
        String queryValue = request.getParameter("query");
        String orderType = request.getParameter("sortname");
        String orderMethod = request.getParameter("sortorder");
        String page = request.getParameter("page");
        String rp = request.getParameter("rp");
        String teamName = request.getParameter("team_name");
        int fetchNum = Integer.parseInt(rp);
        int startIndex = (Integer.parseInt(page) - 1) * fetchNum;

        Map pageInfo = new HashMap();
        ProjectQueryParm projectQueryParm = new ProjectQueryParm();
        projectQueryParm.setQueryType(queryType);
        projectQueryParm.setQueryValue(queryValue);
        projectQueryParm.setOrderType(orderType);
        projectQueryParm.setOrderMethod(orderMethod);
		projectQueryParm.setStartIndex(startIndex);
		projectQueryParm.setFetchNum(fetchNum);		
		//显示本组
		if(teamName != null &&!teamName.isEmpty() && !teamName.equals("all")){
			Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", Integer.parseInt(teamName));
			Integer teamId = per.getTeam_id();
			if(teamId == 5){//统筹收单三组
				//System.out.println("统筹收单三组");
				projectQueryParm.setTeamName("all");
			}
			else{
				projectQueryParm.setTeamName(teamId.toString());
			}
			//System.out.println(teamId);
		}
		else{//显示全部,null或者"all"
			//System.out.println(teamName);
			projectQueryParm.setTeamName(teamName);
		}
        
		try {
			//根据参数查询不同的一事通id，项目编号
			List<PersonProject> list = session.selectList("PersonProjectMapper.selectDistinctPersonProjectByParms",projectQueryParm);
			//根据参数查询不同的一事通id，项目编号个数，即员工排期表记录条数
			int total = session.selectOne("PersonProjectMapper.selectDistinctPersonProjectNumByParms",projectQueryParm);
			
			if (total < fetchNum) {
				pageInfo.put("page", 1);
			} else {
				pageInfo.put("page", Integer.parseInt(page));
			}
		    pageInfo.put("total", total);
		       
		    List mapList = new ArrayList();
		    int count = 0;
		    for(int i = 0; i < list.size(); i++) {
		    	Map cellMap = new HashMap();
	        	PersonProject temp = list.get(i);//存了proj_phase_id、一事通id、项目编号
	        	Integer empYstId = temp.getEmpYstId();
	        	String projNo = temp.getProjNo();
	        	
	        	
	        	cellMap.put("id", ++count);
		        List<String> cells = new ArrayList<String>();
		        
		        //如果是员工排期表，显示员工姓名，如果是个人排期表，不显示
		        if(!queryType.equals("self")){
		        	//根据一事通id从persons中取员工名字
		        	String empName = session.selectOne("chiphead.mapper.PersonMapper.selectEmpNameByEmpYstId", empYstId);
		        	cells.add(empName);
		        }
		        
		        //根据项目编号从projects中取记录
	        	Project projTemp = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
	        	String curState = session.selectOne("ProjectMapper.getProjectCurStateNameByProjNo", projNo);
	        	String type = projNo.substring(0, 1);//T/P
	        	String projName = projTemp.getProjName();
		        cells.add(curState);
		        cells.add(type);
		        cells.add(projNo);
		        cells.add(projName);
		        String[] weekCells = ComputeWeekNum.getDspEmpSchd(empYstId,projNo,session);
		        for (int j = 0; j < weekCells.length; j++) {
					cells.add(weekCells[j]);
				}
				cellMap.put("cell", cells);
		        mapList.add(cellMap);
		    }   
	        pageInfo.put("rows", mapList);
	        Gson gson = new Gson();

			out.print(gson.toJson(pageInfo));
		} catch (Exception e) {
			e.printStackTrace();
			out.print(e);
		} finally {
			session.close();
		}
		out.flush();
		out.close();
	}

}