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
import chiphead.model.Project;
import chiphead.model.ProjectQueryParm;

import com.google.gson.Gson;

public class SlctProjSchdServ extends HttpServlet {

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
        
		try {
			List<Project> list = session.selectList("ProjectMapper.selectProjectsByParms", projectQueryParm);
			DisplayParse displayParse = new DisplayParse();
			List<DisplayProject> dList = displayParse.parseToDisplayProjectsList(list);

			int total = session.selectOne("ProjectMapper.selectProjectsNumByParms", projectQueryParm);

			if (total < fetchNum) {
				pageInfo.put("page", 1);
			} else {
				pageInfo.put("page", Integer.parseInt(page));
			}
	        pageInfo.put("total", total);
			
			List mapList = new ArrayList();
			int count = 0;
	        for(int i = 0; i < dList.size(); i++) {
	            Map cellMap = new HashMap();
        		DisplayProject dProject = ((DisplayProject)dList.get(i));
        		
	            cellMap.put("id", ++count);
	            List<String> cells = new ArrayList<String>();
	            cells.add(dProject.getProjNo());
	            cells.add(dProject.getProjName());
	            cells.add(((Project)list.get(i)).getProjCharge());
	            cells.add(dProject.getProjManager());
	            cells.add(dProject.getCurPhase());
	            String[] weekCells = ComputeWeekNum.getProjSchd(dProject.getProjNo(), session);
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