package chiphead.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import chiphead.model.ComputeWeekNum;

public class GetWeekNumServ extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String yearString = request.getParameter("year");
		String monthString = request.getParameter("month");
		if (yearString == null || monthString == null) {
			
		}else if (yearString != null && monthString.equals("")) {
			int year = Integer.parseInt(yearString);
			Map<Integer, Integer> weekNums = new HashMap<Integer, Integer>();
			for (int j = 0; j < 12; j++) {
				weekNums.put(j + 1, ComputeWeekNum.getWeekNumByYearMonth(year, j + 1));
			}
			Gson gson = new Gson();
			out.print(gson.toJson(weekNums));
		} else {
			int year = Integer.parseInt(yearString);
			int month = Integer.parseInt(monthString);
			Integer weekNum = ComputeWeekNum.getWeekNumByYearMonth(year,month);
			String str = weekNum.toString();
			out.print(str);//输出到前台
		}
		
		out.flush();
		out.close();
	}


}
