package chiphead.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;
import chiphead.io.export.ExportEmerTable;
import chiphead.io.export.ExportExcel;
import chiphead.io.export.ExportInnoTable;
import chiphead.io.export.ExportOperTable;
import chiphead.io.export.ExportProjSchdTable;
import chiphead.io.export.ExportProjTable;
import chiphead.io.export.ExportSchdTable;
import chiphead.model.ComputeWeekNum;
import chiphead.model.DisplayInnovation;
import chiphead.model.DisplayOperation;
import chiphead.model.DisplayParse;
import chiphead.model.DisplayProject;
import chiphead.model.Emergency;
import chiphead.model.ExportInnovation;
import chiphead.model.ExportOperation;
import chiphead.model.Innovation;
import chiphead.model.Operation;
import chiphead.model.PersonProject;
import chiphead.model.Project;
import chiphead.model.ProjectQueryParm;

public class ExportExcelServ extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("octets/stream");

		InputStream inputStream = Resources
				.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		SqlSession session = sqlSessionFactory.openSession();

		//String queryType = request.getParameter("qtype");
		//String queryValue = request.getParameter("query");
		//String orderType = request.getParameter("sortname");
		//String orderMethod = request.getParameter("sortorder");
		String type = request.getParameter("type");

		//ProjectQueryParm projectQueryParm = new ProjectQueryParm();
		//projectQueryParm.setQueryType(queryType);
		//projectQueryParm.setQueryValue(queryValue);
		//projectQueryParm.setOrderType(orderType);
		//projectQueryParm.setOrderMethod(orderMethod);
		//projectQueryParm.setStartIndex(0);
		//projectQueryParm.setFetchNum(1);

		String excelName = "file";

		Calendar cal = Calendar.getInstance();

		String version = "V2.0";
		int year = cal.get(Calendar.YEAR);// 获得当前年份
		String cat = version + "(" + year + ")" + ".xls";
		if (type.equals("projtable")) {
			excelName = Constants.PROJTABLENAME;
		} else if (type.equals("projschdtable")) {
			excelName = Constants.PROJSCHDTABLENAME;
		} else if (type.equals("schdtable")) {
			excelName = Constants.SCHDTABLENAME;
		} else if(type.equals("opertable")){
			excelName = Constants.OPERTABLENAME;
		} else if(type.equals("innotable")){
			excelName = Constants.INNOTABLENAME;
		} else if (type.equals("emertable")) {
			excelName = Constants.EMERTABLENAME;
		}
		else{
			return;
		}

		String fileName = encodeFilename(excelName, request.getHeader("User-Agent")) + cat;
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

		OutputStream out = response.getOutputStream();
		try {
			
			if (type.equals("projtable")) {//项目情况表
				//int fetchNum = session.selectOne("ProjectMapper.selectProjectsNumByParms", projectQueryParm);
				//projectQueryParm.setFetchNum(fetchNum);
				DisplayParse displayParse = new DisplayParse();
				
				List<Project> jichuList = session.selectList("ProjectMapper.selectProjectsByManagerTeamOrdered",Constants.jichuTeam);
				List<Project> yewuList = session.selectList("ProjectMapper.selectProjectsByManagerTeamOrdered",Constants.yewuTeam);
				List<Project> yingyongList = session.selectList("ProjectMapper.selectProjectsByManagerTeamOrdered",Constants.yingyongTeam);
				//List<Project> otherList = session.selectList("ProjectMapper.selectProjectsExcluedingManagerTeamOrdered");
				
				List<DisplayProject> jichuDList = displayParse.parseToDisplayProjectsList(jichuList);
				List<DisplayProject> yewuDList = displayParse.parseToDisplayProjectsList(yewuList);
				List<DisplayProject> yingyongDList = displayParse.parseToDisplayProjectsList(yingyongList);
				//List<DisplayProject> otherDList = displayParse.parseToDisplayProjectsList(otherList);
				
				ExportProjTable exProjTable = new ExportProjTable();
				exProjTable.exportExcel(jichuDList, yewuDList, yingyongDList ,out);
			} 
			else if (type.equals("projschdtable")) {//项目排期表
				DisplayParse displayParse = new DisplayParse();
				
				List<Project> jichuList = session.selectList("ProjectMapper.selectProjectsByManagerTeamOrdered",Constants.jichuTeam);
				List<Project> yewuList = session.selectList("ProjectMapper.selectProjectsByManagerTeamOrdered",Constants.yewuTeam);
				List<Project> yingyongList = session.selectList("ProjectMapper.selectProjectsByManagerTeamOrdered",Constants.yingyongTeam);
				//List<Project> otherList = session.selectList("ProjectMapper.selectProjectsExcluedingManagerTeamOrdered");
				
				List<DisplayProject> jichuDList = displayParse.parseToDisplayProjectsList(jichuList);
				List<DisplayProject> yewuDList = displayParse.parseToDisplayProjectsList(yewuList);
				List<DisplayProject> yingyongDList = displayParse.parseToDisplayProjectsList(yingyongList);
				//List<DisplayProject> otherDList = displayParse.parseToDisplayProjectsList(otherList);
				
				List<List<String>> jichuDataList = ComputeWeekNum.getProjSchdDataList(jichuList,jichuDList,session);
				List<List<String>> yewuDataList = ComputeWeekNum.getProjSchdDataList(yewuList,yewuDList,session);
				List<List<String>> yingyongDataList = ComputeWeekNum.getProjSchdDataList(yingyongList,yingyongDList,session);
				//List<List<String>> otherDataList = ComputeWeekNum.getProjSchdDataList(otherList,otherDList,session);
				
				ExportProjSchdTable eProjSchdTable = new ExportProjSchdTable();
				eProjSchdTable.exportExcel(jichuDataList, yewuDataList, yingyongDataList, out);
			} 
			else if (type.equals("schdtable")) {//工作排期表
				List<PersonProject> jichuList = session.selectList("PersonProjectMapper.selectDistinctPersonProjectByTeamOrdered",Constants.jichuTeam);
				List<PersonProject> yewuList = session.selectList("PersonProjectMapper.selectDistinctPersonProjectByTeamOrdered",Constants.yewuTeam);
				List<PersonProject> yingyongList = session.selectList("PersonProjectMapper.selectDistinctPersonProjectByTeamOrdered",Constants.yingyongTeam);
				//List<PersonProject> otherList = session.selectList("PersonProjectMapper.selectDistinctPersonProjectByTeamOrdered",Constants.otherTeam);
				
				List<List<String>> jichuDataList = ComputeWeekNum.getSchdDataList(jichuList,session);
				List<List<String>> yewuDataList = ComputeWeekNum.getSchdDataList(yewuList,session);
				List<List<String>> yingyongDataList = ComputeWeekNum.getSchdDataList(yingyongList,session);
				//List<List<String>> otherDataList = ComputeWeekNum.getSchdDataList(otherList,session);

				ExportSchdTable eSchdTable = new ExportSchdTable();
				eSchdTable.exportExcel(jichuDataList, yewuDataList, yingyongDataList, out);
			}
			else if(type.equals("opertable")) {//运维登记表
				List<Operation> list = session.selectList("OperationMapper.selectAllOperationsOrderByRegDateASC");

				DisplayParse displayParse = new DisplayParse();
				List<DisplayOperation> dList = displayParse.parseToDisplayOperationsList(list);
				List<ExportOperation> eList = displayParse.parseToExportOperationsList(dList);

				ExportOperTable exOperTable = new ExportOperTable();
				exOperTable.exportExcel(excelName, eList, out);
			}
			else if(type.equals("innotable")) {//创新登记表
				List<Innovation> list = session.selectList("InnovationMapper.selectAllInnovationsOrderByRegDateASC");

				DisplayParse displayParse = new DisplayParse();
				List<DisplayInnovation> dList = displayParse.parseToDisplayInnovationsList(list);
				List<ExportInnovation> eList = displayParse.parseToExportInnovationsList(dList);

				ExportInnoTable exInnoTable = new ExportInnoTable();
				exInnoTable.exportExcel(excelName, eList, out);
			}
			else if(type.equals("emertable")) {//紧急登记表
				List<Emergency> list = session.selectList("EmergencyMapper.selectAllEmergencyOrderByRegDateASC");

				ExportEmerTable exportEmerTable = new ExportEmerTable();
				exportEmerTable.exportExcel(excelName, list, out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			session.close();
		}
	}

	public String encodeFilename(String fileName, String agent) {
		try {
			// Firefox
			if (null != agent && -1 != agent.toLowerCase().indexOf("firefox")) {
				fileName = MimeUtility.encodeText(fileName, "UTF8", "B");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF8");
			}
		} catch (UnsupportedEncodingException e) {
			try {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return fileName;
	}
}
