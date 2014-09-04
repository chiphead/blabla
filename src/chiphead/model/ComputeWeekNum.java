package chiphead.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chiphead.config.Constants;

import com.google.gson.Gson;




public class ComputeWeekNum {	
/********************************项目和员工排期共用方法开始****************************************************/
	//计算相差几个自然月
	public static int[] getNaturalMonthDayGap(Date startDate,Date endDate) { 
		Calendar calendarBirth = Calendar.getInstance();
		calendarBirth.setTime(startDate);
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.setTime(endDate);
		int diffMonths=0, diffDays=0;
		int dayOfBirth = calendarBirth.get(Calendar.DAY_OF_MONTH); 
		int dayOfNow = calendarNow.get(Calendar.DAY_OF_MONTH); 
		if (dayOfBirth <= dayOfNow) { //若开始日期的号数 <= 结束日期
			diffMonths = getMonthGap(calendarBirth, calendarNow); //自然月份差初始化为实际月份的差
			diffDays = dayOfNow - dayOfBirth+1; //额外天数差为号数之差+1
		} 
		else { //若开始日期的号数 > 结束日期
			if (isEndOfMonth(calendarBirth)) {//若开始日期是月尾 
				if (isEndOfMonth(calendarNow)) {//若结束日期是月尾
					diffMonths = getMonthGap(calendarBirth, calendarNow); //自然月份差就是实际月份的差
					diffDays = 0; //额外天数差为0
				} 
				else {//若结束日期不是月尾                 
					calendarNow.add(Calendar.MONTH, -1);//结束日期往前推一个自然月
					diffMonths = getMonthGap(calendarBirth, calendarNow);//自然月份差减一
					//获取上个月最大的一天 
					int maxDayOfLastMonth = calendarNow.getActualMaximum(Calendar.DAY_OF_MONTH); 
					if(maxDayOfLastMonth < dayOfBirth){
						diffDays = dayOfNow;//额外天数差为结束号数
					}
					else{
						diffDays = maxDayOfLastMonth - dayOfBirth + dayOfNow + 1;//额外天数差为结束号数+1
					}
				} 
			}
			else {//若开始日期不是月尾 
				if (isEndOfMonth(calendarNow)) {//若结束日期是月尾
					diffMonths = getMonthGap(calendarBirth, calendarNow); //自然月份差就是实际月份的差
					diffDays = 0; 
				} 
				else {//若结束日期不是月尾   
					calendarNow.add(Calendar.MONTH, -1);//上个月
					diffMonths = getMonthGap(calendarBirth, calendarNow);
					//获取上个月最大的一天 
					int maxDayOfLastMonth = calendarNow.getActualMaximum(Calendar.DAY_OF_MONTH); 
					if(maxDayOfLastMonth < dayOfBirth){
						diffDays = dayOfNow;//额外天数差为结束号数
					}
					else{
						diffDays = maxDayOfLastMonth - dayOfBirth + dayOfNow + 1;//额外天数差为结束号数+1
					}
				} 
			} 
		} 
		return new int[] {diffMonths, diffDays }; 
	} 
		 
		//获取两个日历的月份之差  
		public static int getMonthGap(Calendar calendarStart,  Calendar calendarEnd) { 
			return (calendarEnd.get(Calendar.YEAR) - calendarStart  .get(Calendar.YEAR))* 12+ 
			calendarEnd.get(Calendar.MONTH)  - calendarStart.get(Calendar.MONTH);      
		}
		
		//判断这一天是否月底
		public static boolean isEndOfMonth(Calendar calendar) {  
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH))  
				return true;  
			else
				return false;      
		} 
	
	
	//判断某个项目/任务是否超期
	public static String isOverdue(Project p){//Y/N/""
		String isOverdue = "N";
		Date startDate = p.getStartDate();
		Date actualEndDate = p.getActualEndDate();
		String projNo = p.getProjNo();
		if(startDate != null && actualEndDate != null){
			int gap[] = getNaturalMonthDayGap(startDate,actualEndDate);
			int monthGap = gap[0];
			int dayGap = gap[1];
			if(projNo.startsWith("P")){//项目，超过6个月算超期
				if(monthGap>6 || (monthGap == 6 && dayGap > 0))
					isOverdue = "Y";
			}
			else if(projNo.startsWith("T")){//任务，超过3个月算超期
				if(monthGap>3 || (monthGap == 3 && dayGap > 0))
					isOverdue = "Y";
			}
			return isOverdue;
		}
		else{
			return "";
		}
		
	}
	
	
	//判断某个项目/任务是否超期
	public static String isOverdue(String projNo,Date startDate,Date actualEndDate){
		String isOverdue = "N";
		if(startDate != null && actualEndDate != null){
			int gap[] = getNaturalMonthDayGap(startDate,actualEndDate);
			int monthGap = gap[0];
			int dayGap = gap[1];
			if(projNo.startsWith("P")){//项目，超过6个月算超期
				if(monthGap>6 || (monthGap == 6 && dayGap > 0))
					isOverdue = "Y";
			}
			else if(projNo.startsWith("T")){//任务，超过3个月算超期
				if(monthGap>3 || (monthGap == 3 && dayGap > 0))
					isOverdue = "Y";
			}
			return isOverdue;
		}
		else{
			return "";
		}
		
	}
	
	
	/*根据年份和月份获得周数*/
	public static int getWeekNumByYearMonth(int year,int month){
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		cal.set(Calendar.YEAR,year); 
		cal.set(Calendar.MONTH, month-1);//Java月份从0开始算 
		cal.set(Calendar.DATE, 1);//日期设为1号
		//System.out.println(cal.getTime());
		int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //获得这个月有几天
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//得到1号是星期几，Sunday:1,Monday:2...
		int temp = daysOfMonth;
		
		int weeksOfMonth = 0;//这个月有几个星期
		if(month == 1){
			weeksOfMonth += 1;
			temp -= 8-dayOfWeek;
			weeksOfMonth += (int)(temp/7);//沾满7天的周数
			if(temp%7 >= 4){
				weeksOfMonth += 1;	
			}
		}		
		else if(month == 12){
			if(dayOfWeek<=4){
				weeksOfMonth += 1;
			}
			temp -= 8-dayOfWeek;
			weeksOfMonth += (int)(temp/7);//占满7天的周数
			if(temp%7 > 0){
				weeksOfMonth += 1;	
			}
		}
		else{
			if(dayOfWeek<=4){
				weeksOfMonth += 1;
			}
			temp -= 8-dayOfWeek;
			weeksOfMonth += (int)(temp/7);//占满7天的周数
			if(temp%7 >= 4){
				weeksOfMonth += 1;	
			}
		}		
		return weeksOfMonth;
	}
	
			
	//根据年份取各个月周数
	public static void getWeekNumsByYear(int year,int weekNums[]){
		for(int i=0;i<12;i++)
			weekNums[i] = getWeekNumByYearMonth(year,i+1);
	}
	
	//根据年份取各个月周数
	public static int getWeekNumOfCurYear(){
		int weekNum = 0;
		Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获得当前年份
		for(int i=0;i<12;i++){
			weekNum += getWeekNumByYearMonth(year,i+1);
		}
		return weekNum;
	}
	
	
	//根据开始和结束年份、月份、周序号取横跨周数
	public static int getGapWeekNum(int startYear,int startMonth,int startWeekNo,
				 int endYear,int endMonth,int endWeekNo){
		int gapWeekNum = 0;
		int weekNums[] = new int[12];
		
		if(startYear == endYear && startMonth == endMonth){
			//开始、结束周在同一个月内
			gapWeekNum = endWeekNo - startWeekNo + 1;
		}
		else if(startYear == endYear && startMonth < endMonth)
		{//开始、结束周在同一年的几个月内	
			getWeekNumsByYear(startYear,weekNums);//得到该年12个月的周数，存入weekNums
			//把每个月的周数累加
			for(int j=startMonth;j<=endMonth;j++){
				if(j==startMonth){
				//开始月份的周数
					gapWeekNum += weekNums[j-1] - startWeekNo + 1;
				}
				else if(j==endMonth){
				//结束月份的周数
					gapWeekNum += endWeekNo;
				}
				else{
				//中间月份的周数
					gapWeekNum += weekNums[j-1];
				}
			}
		}
		else{
		//开始、结束周在几年内
			for(int k=startYear;k<=endYear;k++){
			//累加每年的周数
				getWeekNumsByYear(k,weekNums);//得到该年12个月的周数，存入weekNums
				if(k==startYear){
				//开始年份,累加从开始月份到12月的周数
					for(int j=startMonth;j<=12;j++){
						if(j==startMonth){//如果是开始月份，加上从开始周至最后一周的周数
							gapWeekNum += weekNums[j-1] - startWeekNo + 1;
						}
						else{//中间月份，加上所有周数
							gapWeekNum += weekNums[j-1];
						}
					}				
				}
				else if(k==endYear){
				//结束年份,累加从1月到结束月份的周数
					for(int j=1;j<=endMonth;j++){
						if(j==endMonth){//如果是结束月份，加上从第1周至结束周的周数
							gapWeekNum += endWeekNo;
						}
						else{//中间月份，加上所有周数
							gapWeekNum += weekNums[j-1];
						}
					}			
				}
				else{
				//中间年份，累加从1月到12月的周数
					for(int j=1;j<=12;j++){
						gapWeekNum += weekNums[j-1];
					}
				}
			}
		}
		return gapWeekNum;	
	}
	
	//根据开始和结束年份、月份、周序号返回表头字符串
	public static String getTableHead(int startYear,int startMonth,int startWeekNo,
				 int endYear,int endMonth,int endWeekNo,String prefix){
		int weekNums[] = new int[12];
		String tableHead = prefix;
		
		if(startYear == endYear && startMonth == endMonth){
			//开始、结束周在同一个月内
			for(int i =startWeekNo;i<endWeekNo;i++){
				tableHead += startMonth + "月第" + startWeekNo + "周|";
			}
			tableHead += startMonth + "月第" + endWeekNo + "周";
		}
		else if(startYear == endYear && startMonth < endMonth)
		{//开始、结束周在同一年的几个月内	
			getWeekNumsByYear(startYear,weekNums);//得到该年12个月的周数，存入weekNums
			//依次得到每个月的表头
			for(int j=startMonth;j<=endMonth;j++){
				if(j==startMonth){
				//开始月份
					for(int i =startWeekNo;i<=weekNums[j-1];i++){
						tableHead += j + "月第" + i + "周|";
					}
				}
				else if(j==endMonth){
				//结束月份
					for(int i =1;i<endWeekNo;i++){
						tableHead += j + "月第" + i + "周|";
					}
					tableHead += j + "月第" + endWeekNo + "周";
				}
				else{
				//中间月份
					for(int i =1;i<=weekNums[j-1];i++){
						tableHead += j + "月第" + i + "周|";
					}
				}
			}
		}
		else{
		//开始、结束周在几年内
			for(int k=startYear;k<=endYear;k++){
			//累加每年的表头
				getWeekNumsByYear(k,weekNums);//得到该年12个月的周数，存入weekNums
				if(k==startYear){
				//开始年份,累加从开始月份到12月的表头
					for(int j=startMonth;j<=12;j++){
						if(j==startMonth){//如果是开始月份，加上从开始周至最后一周的表头
							for(int i =startWeekNo;i<=weekNums[j-1];i++){
								tableHead += j + "月第" + i + "周|";
							}
						}
						else{//中间月份，加上所有周表头
							for(int i =1;i<=weekNums[j-1];i++){
								tableHead += j + "月第" + i + "周|";
							}
						}
					}				
				}
				else if(k==endYear){
				//结束年份,累加从1月到结束月份的表头
					for(int j=1;j<=endMonth;j++){
						if(j==endMonth){//如果是结束月份，加上从第1周至结束周的表头
							for(int i =1;i<endWeekNo;i++){
								tableHead += j + "月第" + i + "周|";
							}
							tableHead += j + "月第" + endWeekNo + "周";
						}
						else{//中间月份，加上所有周表头
							for(int i =1;i<=weekNums[j-1];i++){
								tableHead += j + "月第" + i + "周|";
							}
						}
					}			
				}
				else{
				//中间年份，累加从1月到12月的表头
					for(int j=1;j<=12;j++){
						for(int i =1;i<=weekNums[j-1];i++){
							tableHead += j + "月第" + i + "周|";
						}
					}
				}
			}
		}
		return tableHead;	
	}
	
	//返回当年12月的表头字符串，若当前月是1月，则返回去年和今年的表头字符串
	public static String getWholeYearTableHead(String prefix){
		//此处为java.util.Date，大部分地方都是java.sql.Date
		java.util.Date curDate = new java.util.Date();
		Date date = new Date(curDate.getTime());
		Calendar cal=Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		cal.setTime(date);
		int curYear = cal.get(Calendar.YEAR);//获得当前年份
		int curMonth = cal.get(Calendar.MONTH)+1;//获得当前月份 0-11 -> 1-12
		int endWeekNo = getWeekNumByYearMonth(curYear,12);//当年12月的周数
		if(Constants.debugMode){
			curYear = 2014;
			curMonth = 1;
			endWeekNo = 4;
		}
		if(curMonth > 1){
			return getTableHead(curYear,1,1,curYear,12,endWeekNo,prefix);
		}
		else{
			return getTableHead(curYear-1,1,1,curYear,12,endWeekNo,prefix);
		}
	}
		
	
/********************************项目和员工排期共用方法结束********************************************/
/********************************项目排期使用方法开始************************************************/	
	//获得项目排期数据行
	public static List<List<String>> getProjSchdDataList(List<Project> list,List<DisplayProject> dList,SqlSession session){
		List<List<String>> dataList = new ArrayList<List<String>>();
		for (int i = 0; i < dList.size(); i++) {
			List<String> dspItem = new ArrayList<String>();
			dspItem.add(dList.get(i).getProjNo());
			dspItem.add(dList.get(i).getProjName());
			dspItem.add(list.get(i).getProjCharge());
			dspItem.add(dList.get(i).getProjManager());
			dspItem.add(dList.get(i).getCurPhase());
			String[] weekCells = ComputeWeekNum.getWholeYearProjSchd(
					list.get(i).getProjNo(), session);
	
			for (int j = 0; j < weekCells.length; j++) {
				dspItem.add(weekCells[j]);
			}
			dataList.add(dspItem);
		}
		return dataList;
	}
	
	
	/*根据日期得到RootProjectPhase中年份、月份、周序号，据此，结合项目编号可取得数据库中相应记录*/	
	public static void getRootProjectPhaseByDate(RootProjectPhase rp,Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);//获得当前年份
		int curMonth = cal.get(Calendar.MONTH);//获得当前月份,0-11
		int curDate = cal.get(Calendar.DATE);//获得当前日期，1-31
		int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //获得当前月份有几天		
		
		Calendar calTemp=Calendar.getInstance();
		calTemp.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		calTemp.set(Calendar.YEAR,year); 
		calTemp.set(Calendar.MONTH, curMonth);
		calTemp.set(Calendar.DATE, 1);//日期设为1号
		int dayOfWeek = calTemp.get(Calendar.DAY_OF_WEEK);//得到当前月的1号是星期几，Sunday:1,Monday:2...
		
		int month = 0;
		int weekNo = 0;
	
		int daysToLastMonth = 0;
		int daysToNextMonth = 0;
		int temp = daysOfMonth;
		int weekNumTemp = 0;
		int dayNumTemp = 0;
		
		if(curMonth != 0 && dayOfWeek > 4){//当前月不为1月，且1号为周四(5)、周五(6)或周六(7)
		/*计算计入上个月的天数*/
			daysToLastMonth = 8-dayOfWeek;
		}
		/*计算计入下个月的天数*/
		temp -= 8-dayOfWeek;//减去1号所在周 占的当前月天数
		if(curMonth != 11 && temp%7 < 4){//当前月不为12月，且最后一周小于4天
			daysToNextMonth = temp%7;
		}
			
		if(daysToLastMonth >0){//若本月前几天计入上个月	
			if(curDate <= daysToLastMonth){//若当前日期落在1号所在星期内
				//System.out.println("conducting...");
				month = curMonth - 1;//计入上个月
				//System.out.println(month);
				weekNo = getWeekNumByYearMonth(year,month+1);//计入上个月最后一周
				month += 1;//0~11 -> 1-12
			}
			else{
			
				if(daysToNextMonth == 0){//若当前日期不落在1号所在星期内，且本月最后几天不计入下个月
					month = curMonth;//当前日期计入当前月
					month += 1;
					weekNumTemp = (int)((curDate-(8-dayOfWeek))/7);//除去第一周的天数后，占几个7天
					dayNumTemp = (int)((curDate-(8-dayOfWeek))%7);//除去第一周的天数后，占了几个7天后，剩几天
					if(weekNumTemp == 0){
						weekNo = 1;
					}
					else if(dayNumTemp == 0)
					{
						weekNo = weekNumTemp;
					}
					else{
						weekNo = weekNumTemp + 1;
					}
				}
				else{//若当前日期不落在1号所在星期内，且本月最后几天计入下个月
					weekNumTemp = (int)((curDate-(8-dayOfWeek))/7);//除去第一周的天数后，占几个7天
					dayNumTemp = (int)((curDate-(8-dayOfWeek))%7);//除去第一周的天数后，占了几个7天后，剩几天
					if(weekNumTemp == 0){//占0个7天，即不到7天
						month = curMonth;//计入当前月
						month += 1;//0~11 -> 1~12
						weekNo = 1;
					}
					else if(dayNumTemp == 0)//占整数个7天
					{
						month = curMonth;//计入当前月
						month += 1;//0~11 -> 1~12
						weekNo = weekNumTemp;
					}
					else{//占几个7天加个零头
						if(daysOfMonth - curDate < daysToNextMonth){//若当前日期落入计入下个月的几天
							month = curMonth + 1;//计入下个月第一周
							month += 1;//0~11 -> 1~12
							weekNo = 1;
						}
						else{//若当前日期落入未计入下个月的几天
							month = curMonth;//计入当前月最后一周
							month += 1;//0~11 -> 1~12
							weekNo = weekNumTemp + 1;
						}
					}
				}	
			}
			
		}
		else if(daysToNextMonth >0){//若本月最后几天计入下个月	
			if(daysOfMonth - curDate  < daysToNextMonth){//若当前日期落入计入下个月的几天
				month = curMonth + 1;//计入下个月第一周
				month += 1;
				weekNo = 1;
	
			}
			else{		
				if(daysToLastMonth == 0){//若当前日期不落在下个月的第一周，且本月前几天不计入上个月
					month = curMonth;//当前日期计入当前月
					month += 1;
					if(curDate <= (8-dayOfWeek)){//若当前日期落在1号所在星期内
						weekNo = 1;
					}
					else{//若当前日期不落在1号所在星期内
						weekNumTemp = (int)((curDate-(8-dayOfWeek))/7);//除去第一周的天数后，占几个7天
						dayNumTemp = (int)((curDate-(8-dayOfWeek))%7);//除去第一周的天数后，占了几个7天后，剩几天
						if(weekNumTemp == 0){
							weekNo = 1;
						}
						else if(dayNumTemp == 0)
						{
							weekNo = weekNumTemp + 1;
						}
						else{
							weekNo = weekNumTemp + 2;
						}
					}
				}
				else{//若当前日期不落在下个月的第一周，且本月前几天计入上个月
					if(curDate <= daysToLastMonth ){//若当前日期落入计入上个月的几天
						month = curMonth - 1;//计入上个月最后一周
						weekNo = getWeekNumByYearMonth(year,month+1);
						month += 1;//0~11 -> 1-12
					}
					else{//若当前日期不落入计入上个月的几天
						month = curMonth;//计入当前月
						month += 1;//0~11 -> 1~12
						weekNumTemp = (int)((curDate-(8-dayOfWeek))/7);//除去第一周的天数后，占几个7天
						dayNumTemp = (int)((curDate-(8-dayOfWeek))%7);//除去第一周的天数后，占了几个7天后，剩几天
						if(weekNumTemp == 0){//占0个7天，即不到7天	
							weekNo = 1;
						}
						else if(dayNumTemp == 0)//占整数个7天
						{
							weekNo = weekNumTemp;
						}
						else{
							weekNo = weekNumTemp + 1;
						}
					}
				}	
			
			}
			
		}
		else{
			//System.out.println("有头有尾");
		//否则当前日期计入本月
			month = curMonth;
			month += 1;
			if(curDate <= (8-dayOfWeek)){//落在1号所在星期内
				weekNo = 1;
			}
			else{//否则
				//System.out.println(8-dayOfWeek);
				//System.out.println(curDate);
				weekNumTemp = (int)((curDate-(8-dayOfWeek))/7);//除去第一周的天数后，占几个7天
				//System.out.println("除去第一周占" + weekNumTemp + "个7天");
				dayNumTemp = (int)((curDate-(8-dayOfWeek))%7);//除去第一周的天数后，占了几个7天后，剩几天
				//System.out.println("剩" + dayNumTemp + "天");
				if(weekNumTemp == 0){
					weekNo = 2;
				}
				else if(dayNumTemp == 0)
				{
					weekNo = weekNumTemp + 1;
				}
				else{
					weekNo = weekNumTemp + 2;
				}
			}
			
		}
		rp.setYear(year);
		rp.setMonth(month);
		rp.setWeekNo(weekNo);
		return;		
	}
	
	//根据起止年份、月份、周序号、阶段id、项目编号存入数据库N条记录
	public static void fillRootProjectPhase(int startYear,int startMonth,int startWeekNo,
										   int endYear,int endMonth,int endWeekNo,
										   SqlSession session,int rootPhaseId,
										   String projNo){
		int weekNums[] = new int[12];
		RootProjectPhase temp = new RootProjectPhase();
		
		if(startYear == endYear && startMonth == endMonth){
		//该项目的该阶段所占周在同一个月内
			temp.setProjNo(projNo);
 			temp.setRootPhaseId(rootPhaseId);
 			temp.setYear(startYear);
 			temp.setMonth(startMonth);
			for(int i=startWeekNo;i<=endWeekNo;i++){		
	 			temp.setWeekNo(i);
	 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
				session.commit();
			}
		}
		else if(startYear == endYear && startMonth < endMonth)
		{//该项目的该阶段所占周在几个月内
			temp.setProjNo(projNo);
 			temp.setRootPhaseId(rootPhaseId);
 			temp.setYear(startYear);
 			getWeekNumsByYear(startYear,weekNums);//得到该年12个月的周数，存入weekNums
 			//for(int i=0;i<12;i++)
 			//	System.out.print(weekNums[i]+" ");
 			//System.out.println();
 			//把每个月每周的数据存入
			for(int j=startMonth;j<=endMonth;j++){
				temp.setMonth(j);
				if(j==startMonth){
				//开始月份，存入从开始周至最后一周的数据
					for(int i=startWeekNo;i<=weekNums[j-1];i++){		
			 			temp.setWeekNo(i);
			 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
			 			session.commit();
					}
				}
				else if(j==endMonth){
				//结束月份，存入从第一周到结束周的数据
					for(int i=1;i<=endWeekNo;i++){		
			 			temp.setWeekNo(i);
			 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
			 			session.commit();
					}
				}
				else{
				//中间的某个月，存入所有周的数据
					for(int i=1;i<=weekNums[j-1];i++){		
			 			temp.setWeekNo(i);
			 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
			 			session.commit();
					}
				}
			}
		}
		else{
		//该项目的该阶段所占周在几年内
			for(int k=startYear;k<=endYear;k++){
			//存入每年的数据
				temp.setProjNo(projNo);
	 			temp.setRootPhaseId(rootPhaseId);
	 			temp.setYear(k);
				getWeekNumsByYear(k,weekNums);//得到该年12个月的周数，存入weekNums
				if(k==startYear){
				//开始年份,存入从开始月份到12月的数据
					for(int j=startMonth;j<=12;j++){
						temp.setMonth(j);
						if(j==startMonth){//如果是开始月份，存入从开始周至最后一周的数据
							for(int i=startWeekNo;i<=weekNums[j-1];i++){		
					 			temp.setWeekNo(i);
					 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
					 			session.commit();
							}
						}
						else{//中间月份，存入所有周的数据
							//System.out.println(j);
							//System.out.println(weekNums[j-1]);
							for(int i=1;i<=weekNums[j-1];i++){	
					 			temp.setWeekNo(i);
					 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
					 			session.commit();
							}
							
						}
					}				
				}
				else if(k==endYear){
				//结束年份,存入从1月到结束月份的数据
					for(int j=1;j<=endMonth;j++){
						temp.setMonth(j);
						if(j==endMonth){//如果是结束月份，存入从第1周至结束周的数据
							for(int i=1;i<=endWeekNo;i++){		
					 			temp.setWeekNo(i);
					 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
					 			session.commit();
							}
						}
						else{//中间月份，存入所有周的数据
							for(int i=1;i<=weekNums[j-1];i++){		
					 			temp.setWeekNo(i);
					 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
					 			session.commit();
							}
							
						}
					}			
				}
				else{
				//中间年份，存入所有月份信息
					for(int j=1;j<=12;j++){//存入12个月信息
						temp.setMonth(j);
						for(int i=1;i<=weekNums[j-1];i++){//存入每周信息
							temp.setWeekNo(i);
				 			session.insert("RootProjectPhaseMapper.insertRootProjectPhase", temp);
				 			session.commit();
						}
					}
				}
			}
		}
		return;
	}
	
	
	//根据项目编号，返回该项目从当年1月到12月 每周的阶段，若当前是1月份，则返回去年和今年的
	public static String[] getWholeYearProjSchd(String projNo,SqlSession session){
		Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获得当前年份
		int curMonth = cal.get(Calendar.MONTH)+1;//获得当前月份 0-11 -> 1-12
		if(Constants.debugMode){
			year = 2014;//获得当前年份
			curMonth = 1;
		}
		if(curMonth > 1){
			return getProjSchdByYearStartEndMonth(projNo,session,year,1,12);
		}
		else{
			String[] s1 = getProjSchdByYearStartEndMonth(projNo,session,year-1,1,12);//去年的
			String[] s2 = getProjSchdByYearStartEndMonth(projNo,session,year,1,12);//今年的
			String[] s3 = new String[s1.length + s2.length];
		    System.arraycopy(s1, 0, s3, 0, s1.length);
		    System.arraycopy(s2, 0, s3, s1.length, s2.length);
		    return s3;
		}
	}
	
	//根据项目编号，返回该项目从当年当前月的前一个月（月份大于1时）到12月 每周的阶段，若当前月是1月，则返回从去年12月到今年12月每周的阶段
	public static String[] getProjSchd(String projNo,SqlSession session){
		Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获得当前年份
		int curMonth = cal.get(Calendar.MONTH)+1;//获得当前月份,0-11 -> 1-12
		if(Constants.debugMode){
			year = 2014;//获得当前年份
			curMonth = 1;
		}
		if(curMonth > 1)
			return getProjSchdByYearStartEndMonth(projNo,session,year,curMonth-1,12);	
		else{//1月份
			String[] s1 = getProjSchdByYearStartEndMonth(projNo,session,year-1,12,12);//去年12月的
			String[] s2 = getProjSchdByYearStartEndMonth(projNo,session,year,1,12);//今年全年的
			String[] s3 = new String[s1.length + s2.length];
		    System.arraycopy(s1, 0, s3, 0, s1.length);
		    System.arraycopy(s2, 0, s3, s1.length, s2.length);
		    return s3;
		}
	}
	
	//根据项目编号，返回该项目在某一年中，从开始月到结束月， 每周的阶段
	public static String[] getProjSchdByYearStartEndMonth(String projNo,SqlSession session,
												int year,int startMonth,int endMonth){
		int endWeekNo = getWeekNumByYearMonth(year,endMonth);
		int gapWeekNum = getGapWeekNum(year,startMonth,1,year,endMonth,endWeekNo);//获得当年从开始月到结束月的总周数
		
		int weekNums[] = new int[12];
		getWeekNumsByYear(year,weekNums);//得到该年12个月的周数，存入weekNums
		
		String phases[] = new String[gapWeekNum];
		for(int i=0;i<gapWeekNum;i++)
			phases[i] = "";
		
		
		RootProjectPhase p = new RootProjectPhase();
		p.setProjNo(projNo);
		p.setYear(year);
		
		String phasesTemp[][] = new String[12][5];
		for(int i=0;i<12;i++){
			for(int j=0;j<5;j++)
				phasesTemp[i][j] = "";
		}
		//从数据库中取出该项目当年所有的记录并写入phasesTemp
		List<RootProjectPhase> rppList = session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseByProjNoYear",p);
		int index1=0;
		int index2=0;
		String phaseNameTemp = "";
		int phaseIdTemp;
		for(int i=0;i<rppList.size();i++){
			index1 = rppList.get(i).getMonth()-1;
			index2 = rppList.get(i).getWeekNo()-1;
			phaseIdTemp = rppList.get(i).getRootPhaseId();
			
			if(index1>=0 && index2>=0){
				//从数据库中root_phase表取
				phaseNameTemp =  session.selectOne("RootPhaseMapper.selectRootPhaseNameByRootPhaseId", phaseIdTemp);
				if(phasesTemp[index1][index2] == ""){
					phasesTemp[index1][index2] += phaseNameTemp;
				}
				else{
					phasesTemp[index1][index2] += "/" + phaseNameTemp;
				}
			}
		}
		
		int index = 0;
		for(int i=startMonth;i<=endMonth;i++){//将每个月每周的阶段存入phases中
			//long begin3=System.currentTimeMillis();
			for(int j=1;j<=weekNums[i-1];j++){
	 		    index = 0;
	 		    if(phasesTemp[i-1][j-1] != ""){//如果该月该周有对应记录
	 		    	//计算phases中对应下标
		 		    for(int k=startMonth;k<i;k++){
		 		    	index += weekNums[k-1];
		 		    }
		 		    index += j-1;
	 		    	phases[index] = phasesTemp[i-1][j-1];
	 		    }
			}
		}
		return phases;
	}
	
	
	//根据项目编号，返回8个阶段的起止年份、月份、周序号，并以长度为8的字符串数组返回，一个阶段的6个数据用"-"分隔
	public static String[] getProjectStartEndYearMonthWeekNo(String projNo,SqlSession session){
		int numOfRootPhases = 8;
		String[] params = new String[numOfRootPhases];//以"-"分隔一个阶段的6个数据
		RootProjectPhase p = new RootProjectPhase();
		p.setProjNo(projNo);
		RootProjectPhase startTemp;
		RootProjectPhase endTemp;
		List<RootProjectPhase> list;
		for(int i=0;i<numOfRootPhases;i++){//取每个阶段的起止年、月、周序号
			p.setRootPhaseId(i+1);
			//取出该项目该阶段对应的所有记录，已按年、月、周序号升序排列
			list = session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseByProjNoRootPhaseId",p);
			if(!list.isEmpty()){
				//list.get(0)对应开始年月周
				startTemp = list.get(0);
				//list.get(list.size()-1)对应结束年月周
				endTemp = list.get(list.size()-1);
				params[i] = startTemp.getYear() + "-" + startTemp.getMonth() + "-" + startTemp.getWeekNo() + "-"
							+ endTemp.getYear() + "-" + endTemp.getMonth() + "-" + endTemp.getWeekNo();
			}
			else{
				params[i] = "";
			}
		}
		return params;
	}
	
	//根据项目编号，返回项目的结束年份、月份、周序号
	public static int[] getProjectEndYearMonthWeekNo(String projNo,SqlSession session){
		int[] ymw = new int[3];
		RootProjectPhase p = new RootProjectPhase();
		p.setProjNo(projNo);
		//取结项阶段的结束年、月、周序号
		p.setRootPhaseId(8);
		//取出该项目该阶段对应的所有记录，已按年、月、周序号升序排列
		List<RootProjectPhase> list = session.selectList("RootProjectPhaseMapper.selectRootProjectPhaseByProjNoRootPhaseId",p);
		if(!list.isEmpty()){
			//list.get(list.size()-1)对应结束年月周
			RootProjectPhase endTemp = list.get(list.size()-1);
			ymw[0] = endTemp.getYear();
			ymw[1] = endTemp.getMonth();
			ymw[2] = endTemp.getWeekNo();
		}
		else{
			ymw[0] = 0;
			ymw[1] = 0;
			ymw[2] = 0;
		}	
		return ymw;
	}
	/********************************项目排期使用方法结束************************************************/
	/********************************员工排期使用方法开始************************************************/
	//获得员工排期数据行
	public static List<List<String>> getSchdDataList(List<PersonProject> perproList,SqlSession session){
		List<List<String>> dataList = new ArrayList<List<String>>();

		Integer empYstId = 0;
		String projNo = "";
		String empName = "";
		String curState = "";
		String typeOfProj = "";
		String projName = "";
		for (int i = 0; i < perproList.size(); i++) {
			PersonProject temp = perproList.get(i);// 存了emp_proj_id、一事通id、项目编号
			empYstId = temp.getEmpYstId();
			projNo = temp.getProjNo();

			// 根据一事通id从persons中取员工名字
			empName = session
					.selectOne(
							"chiphead.mapper.PersonMapper.selectEmpNameByEmpYstId",
							empYstId);
			// 根据项目编号从projects中取记录
			Project projTemp = session.selectOne(
					"ProjectMapper.selectProjectByProjNo", projNo);
			curState = session.selectOne(
					"ProjectMapper.getProjectCurStateNameByProjNo",
					projNo);
			typeOfProj = projNo.substring(0, 1);// T/P
			projName = projTemp.getProjName();

			List<String> dspItem = new ArrayList<String>();

			dspItem.add(empName);
			dspItem.add(curState);
			dspItem.add(typeOfProj);
			dspItem.add(projNo);
			dspItem.add(projName);
			String[] weekCells = ComputeWeekNum.getWholeYearEmpSchd(
					empYstId, projNo, session);
			for (int j = 0; j < weekCells.length; j++) {
				dspItem.add(weekCells[j]);
			}
			dataList.add(dspItem);
		}
		return dataList;
	}
	
	
	
	
	//根据员工一事通id、项目编号，返回该项目从当年1月到12月 每周的阶段和投入时间,用于导出,带颜色码，若当前是1月，则返回两年的数据
	public static String[] getWholeYearEmpSchd(Integer empYstId,String projNo,SqlSession session){
		Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获得当前年份
		int curMonth = cal.get(Calendar.MONTH)+1;//获得当前月份，0-11 -> 1-12
		if(Constants.debugMode){
			year = 2014;
			curMonth = 1;
		}
		if(curMonth > 1){
			return getDspEmpSchd(empYstId,projNo,session,year,1,12);
		}
		else{//1月份
			String[] s1 = getDspEmpSchd(empYstId,projNo,session,year-1,1,12);//去年的
			String[] s2 = getDspEmpSchd(empYstId,projNo,session,year,1,12);//今年的
			String[] s3 = new String[s1.length + s2.length];
		    System.arraycopy(s1, 0, s3, 0, s1.length);
		    System.arraycopy(s2, 0, s3, s1.length, s2.length);
		    return s3;
		}
	}
	
	
	//根据员工一事通id、项目编号，返回该项目在当年，当前月的前一个月(月份大于1时)到12月，每周的阶段,用于页面显示，带颜色码，若当前月份是1月，则返回从去年12月到今年12月的
	public static String[] getDspEmpSchd(Integer empYstId,String projNo,SqlSession session){
		Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获得当前年份
		int curMonth = cal.get(Calendar.MONTH)+1;//获得当前月份,0-11 -> 1-12
		if(Constants.debugMode){
			year = 2014;
			curMonth = 1;
		}
		if(curMonth > 1)
			return getDspEmpSchd(empYstId,projNo,session,year,curMonth-1,12);
		else{//若当前月份是1月，则返回从去年12月到今年12月的
			String[] s1 = getDspEmpSchd(empYstId,projNo,session,year-1,12,12);//去年12月的
			String[] s2 = getDspEmpSchd(empYstId,projNo,session,year,curMonth,12);//今年全年的
			String[] s3 = new String[s1.length + s2.length];
		    System.arraycopy(s1, 0, s3, 0, s1.length);
		    System.arraycopy(s2, 0, s3, s1.length, s2.length);
		    return s3;
		}
	}
	
	
	//根据员工一事通id、项目编号、年份，返回该员工在该项目中，在该年，从开始月到结束月，每周的阶段和时间，不加颜色
	public static String[] getEmpSchd(Integer empYstId,String projNo,SqlSession session,
										int year,int startMonth,int endMonth){	
		int endWeekNo = getWeekNumByYearMonth(year,endMonth);
		int gapWeekNum = getGapWeekNum(year,startMonth,1,year,endMonth,endWeekNo);//获得当年从开始月到结束月的总周数
		
		int weekNums[] = new int[12];
		getWeekNumsByYear(year,weekNums);//得到该年12个月的周数，存入weekNums
		
		//阶段和时间
		String phasesAndHours[] = new String[gapWeekNum];
		for(int i=0;i<gapWeekNum;i++)
			 phasesAndHours[i] = "";
		
		
		ProjectPhase p = new ProjectPhase();
		
		p.setEmpYstId(empYstId);
		p.setProjNo(projNo);
		p.setYear(year);
		
		//阶段和时间，先存入阶段
		String  phasesTemp[][] = new String[12][5];
		for(int i=0;i<12;i++){
			for(int j=0;j<5;j++)
				 phasesTemp[i][j] = "";
		}
		
		//时间
		double hoursTemp[][] = new double[12][5];
		for(int i=0;i<12;i++){
			for(int j=0;j<5;j++)
				 hoursTemp[i][j] = 0;
		}
		
		//从数据库中取出该员工，该项目，该年，所有的记录并写入phasesTemp
		List<ProjectPhase> pplist = session.selectList("ProjectPhaseMapper.selectProjectPhaseByYPY", p);
		int index1=0;
		int index2=0;
		String phaseNameTemp = "";
		int phaseIdTemp;
		Double devoteHoursTemp;
		for(int i=0;i<pplist.size();i++){
			index1 = pplist.get(i).getMonth()-1;
			index2 = pplist.get(i).getWeekNo()-1;
			phaseIdTemp = pplist.get(i).getPhaseId();
			devoteHoursTemp = pplist.get(i).getDevoteHours();
			if(index1 >=0 && index2>=0){
				//获得单元格中时间
				hoursTemp[index1][index2] += devoteHoursTemp;
				//从数据库中phase表取
				phaseNameTemp =  session.selectOne("PhaseMapper.selectPhaseNameByPhaseId", phaseIdTemp);
				if( phasesTemp[index1][index2] == ""){
					phasesTemp[index1][index2] += phaseNameTemp;
				}
				else{
					phasesTemp[index1][index2] += "/" + phaseNameTemp;
				}
			}
		}
		
		int index = 0;
		for(int i=startMonth;i<=endMonth;i++){//
			p.setMonth(i);
			//long begin3=System.currentTimeMillis();
			for(int j=1;j<=weekNums[i-1];j++){
	 		    index = 0;
	 		    if(phasesTemp[i-1][j-1] != ""){//如果该月该周有对应记录
	 		    	//计算phases中对应下标
		 		    for(int k=startMonth;k<i;k++){
		 		    	index += weekNums[k-1];
		 		    }
		 		    index += j-1;
		 		    phasesAndHours[index] = phasesTemp[i-1][j-1] + "/" + hoursTemp[i-1][j-1];
	 		    }
			}
		}
		return phasesAndHours;
	}
	
	
	//根据员工一事通id、项目编号、年份，返回该员工在该项目中，在该年，从开始月到结束月，每周的阶段和时间，用于页面显示，加颜色
	public static String[] getDspEmpSchd(Integer empYstId,String projNo,SqlSession session,
										int year,int startMonth,int endMonth){	
		int endWeekNo = getWeekNumByYearMonth(year,endMonth);
		int gapWeekNum = getGapWeekNum(year,startMonth,1,year,endMonth,endWeekNo);//获得当年从开始月到结束月的总周数
		
		int weekNums[] = new int[12];
		getWeekNumsByYear(year,weekNums);//得到该年12个月的周数，存入weekNums
		
		//返回的颜色码+阶段+时间
		String phasesAndHours[] = new String[gapWeekNum];
		for(int i=0;i<gapWeekNum;i++)
			 phasesAndHours[i] = "";
		
		
		ProjectPhase p = new ProjectPhase();
		
		p.setEmpYstId(empYstId);
		p.setProjNo(projNo);
		p.setYear(year);
		
		//颜色码+阶段+时间，先存阶段，再加上颜色码和时间
		String  phasesTemp[][] = new String[12][5];
		for(int i=0;i<12;i++){
			for(int j=0;j<5;j++)
				 phasesTemp[i][j] = "";
		}
		
		//表示审核状态，优先级3>1>2，即不通过>未审核>通过
		int  checkStateTemp[][] = new int[12][5];
		for(int i=0;i<12;i++){
			for(int j=0;j<5;j++)
				checkStateTemp[i][j] = 0;
		}
		
		//时间
		Double hoursTemp[][] = new Double[12][5];
		for(int i=0;i<12;i++){
			for(int j=0;j<5;j++)
				 hoursTemp[i][j] = 0.0;
		}
		
		//从数据库中取出该员工，该项目，该年，所有的记录并写入phasesAndHoursTemp
		List<ProjectPhase> pplist = session.selectList("ProjectPhaseMapper.selectProjectPhaseByYPY", p);
		int index1=0;
		int index2=0;
		String phaseNameTemp = "";
		int phaseIdTemp;
		Double devoteHoursTemp;
		//获得阶段和时间
		for(int i=0;i<pplist.size();i++){
			index1 = pplist.get(i).getMonth()-1;
			index2 = pplist.get(i).getWeekNo()-1;
			phaseIdTemp = pplist.get(i).getPhaseId();
			devoteHoursTemp = pplist.get(i).getDevoteHours();
			
			if(index1 >=0 && index2>=0){
				//获得单元格中审批状态
				checkStateTemp[index1][index2] = getCheckState(checkStateTemp[index1][index2],pplist.get(i).getCheckState());
				//获得单元格中时间
				hoursTemp[index1][index2] += devoteHoursTemp;
				//从数据库中phase表取
				phaseNameTemp =  session.selectOne("PhaseMapper.selectPhaseNameByPhaseId", phaseIdTemp);
				if( phasesTemp[index1][index2] == ""){
					phasesTemp[index1][index2] += phaseNameTemp;
				}
				else{
					phasesTemp[index1][index2] += "/" + phaseNameTemp;
				}
			}
		}
		
		int index = 0;
		int checkState = 0;
		Double hours = 0.0;
		String colorTemp = "";//用于存放颜色码
		RootProjectPhase rpp = new RootProjectPhase();//用于存放当前年、月、周序号
		java.util.Date d = new java.util.Date();// 当前日期
		java.sql.Date date = new java.sql.Date(d.getTime());	
		//rpp中存放了当前日期的年月周序号
		ComputeWeekNum.getRootProjectPhaseByDate(rpp, date);
		int curYear = rpp.getYear();
		int curMonth = rpp.getMonth();
		int curWeekNo = rpp.getWeekNo();
		boolean changeColorFlag = false;
		for(int i=startMonth;i<=endMonth;i++){//
			p.setMonth(i);
			//long begin3=System.currentTimeMillis();
			for(int j=1;j<=weekNums[i-1];j++){
	 		    index = 0;
	 		    if(phasesTemp[i-1][j-1] != ""){//如果该月该周有对应记录
	 		    	//计算phases中对应下标
		 		    for(int k=startMonth;k<i;k++){
		 		    	index += weekNums[k-1];
		 		    }
		 		    index += j-1;
		 		    //加上颜色字符串和时间
		 		    checkState = checkStateTemp[i-1][j-1];
		 		    hours = hoursTemp[i-1][j-1];
		 		    colorTemp = getCheckStateColor(checkState,hours);
		 		    if(colorTemp.equals("[BGCOLOR=#FFC500]")){//黄色
		 		    	//如果该周在当前周之后，改为绿色
		 		    	changeColorFlag = false;
  		 				if(year>curYear){//年份大于当前
  		 					changeColorFlag = true;
  		 				}
  		 				else if(year == curYear){//年份等于当前
  		 					if(i>curMonth){//月份大于当前
  		 						changeColorFlag = true;
  		 					}
  		 					else{
  		 						if(i==curMonth && j>curWeekNo){//月份等于当前,周序号大于当前
	  		 						changeColorFlag = true;
  		 						}
  		 					}
  		 				}
  		 				if(changeColorFlag){//绿色
  		 					colorTemp = "[BGCOLOR=#22B14C]";
  		 				}
		 		    }
		 
		 		    phasesAndHours[index] = colorTemp + phasesTemp[i-1][j-1] + "/" + hours;
	 		    }
			}
		}
		return phasesAndHours;
	}
	//根据列id返回年份、月份、周序号，colId从0开始计数，即上个月第一周对应colId为0,若当前为1月，则特殊处理
	public static int[] getYearMonthWeekNoByColId(int colId){
		int ymw[] = new int[3];//表示年份、月份、周序号
		for(int i=0;i<ymw.length;i++){//初始化为全0
			ymw[i] = 0;
		}
		
		Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获得当前年份
		int curMonthIndex = cal.get(Calendar.MONTH);//获得当前月份,0-11
		int preMonthIndex = curMonthIndex>0?curMonthIndex-1:11;
		
		int[] weekNums = new int[12];
		getWeekNumsByYear(year,weekNums);//获得当年每个月的周数
		int weekNum = colId+1;//当前周是显示的第几周
		int sum = 0;
		int weekNumTemp = 0;
		//从上个月开始累加周数
		if(preMonthIndex==11){//上个月是去年12月
			weekNumTemp = getWeekNumByYearMonth(year-1,12);//该月周数
			sum += weekNumTemp;//加上该月周数
			if(sum >= weekNum){//选中的周落在该月内
				ymw[0] = year-1;
				ymw[1] = 12;
				ymw[2] = weekNum - (sum-weekNumTemp);//减去上个月到该月的前一个月的周数累计之和，即为在当月的周序号
			}	
			if(sum<weekNum){//若累加周数之和仍<当前列数对应周数
				for(int i=0;i<12;i++){
					weekNumTemp = weekNums[i];//该月周数
					sum += weekNumTemp;//加上该月周数
					if(sum >= weekNum){//选中的周落在该月内
						ymw[0] = year;
						ymw[1] = i+1;
						ymw[2] = weekNum - (sum-weekNumTemp);//减去上个月到该月的前一个月的周数累计之和，即为在当月的周序号
						break;
					}	
				}
			}
		}
		else{
			for(int i=preMonthIndex;i<12;i++){
				weekNumTemp = weekNums[i];//该月周数
				sum += weekNumTemp;//加上该月周数
				if(sum >= weekNum){//选中的周落在该月内
					ymw[0] = year;
					ymw[1] = i+1;
					ymw[2] = weekNum - (sum-weekNumTemp);//减去上个月到该月的前一个月的周数累计之和，即为在当月的周序号
					break;
				}	
			}
		}
		
		return ymw;
	}
	
	//根据一事通id、项目编号、年份、月份、周序号、阶段id、投入小时数、审核状态插入一条记录，若插入不成功，返回false，否则返回true
	public static boolean fillProjectPhase(Integer empYstId,String projNo,int year,int month,int weekNo,
											Integer phaseId,Double devoteHours,int checkState,SqlSession session){
		ProjectPhase p = new ProjectPhase();
		p.setEmpYstId(empYstId);
		p.setProjNo(projNo);
		p.setPhaseId(phaseId);
		p.setYear(year);
		p.setMonth(month);
		p.setWeekNo(weekNo);
		p.setDevoteHours(devoteHours);
		p.setCheckState(checkState);
			
		int ret = session.insert("ProjectPhaseMapper.insertProjectPhase", p);
		session.commit();
		if(ret > 0){
			return true;
		}
		else{
			return false;
		}	
	}
	
	
	/*得到当前日期的上一周的RootProjectPhase中年份、月份、周序号，据此，结合项目编号可取得数据库中相应记录*/	
	@SuppressWarnings("deprecation")
	public static void getPreWeekRootProjectPhase(RootProjectPhase rp){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		int year = cal.get(Calendar.YEAR);//获得当前年份
		int month = cal.get(Calendar.MONTH);//获得月份,0-11
		int date = cal.get(Calendar.DATE);//获得日期，1-31
		Date sqlDate = new Date(year-1900,month,date);
		getRootProjectPhaseByDate(rp,sqlDate);
	}
	
	//返回当前周的上一周的表头字符串
	public static String getPreWeekTableHead(){
		RootProjectPhase rp = new RootProjectPhase();
		getPreWeekRootProjectPhase(rp);//得到上一周的年、月、周序号
		String s = rp.getMonth() + "月第" + rp.getWeekNo() + "周";
		return s;
	}
	
	//根据员工一事通id、项目编号，返回当前周的上一周的阶段和时间
	public static String getPreWeekEmpSchd(Integer empYstId,String projNo,SqlSession session){
		String s = "";
		RootProjectPhase rp = new RootProjectPhase();
		getPreWeekRootProjectPhase(rp);//获得上一周的年、月、周序号
		ProjectPhase p = new ProjectPhase();
		p.setEmpYstId(empYstId);
		p.setProjNo(projNo);
		p.setYear(rp.getYear());
		p.setMonth(rp.getMonth());
		p.setWeekNo(rp.getWeekNo());
		//根据一事通ID、项目编号、年份、月份、周序号查询记录，按阶段号升序排列
		List<ProjectPhase> list = session.selectList("ProjectPhaseMapper.selectProjectPhaseByYPYMW", p);
		String phaseNameTemp;
		for(int i=0;i<list.size();i++){
			//从数据库中phase表取
			p = list.get(i);
			phaseNameTemp = session.selectOne("PhaseMapper.selectPhaseNameByPhaseId", p.getPhaseId());
			if(i==0){
				s += phaseNameTemp + "/" + p.getDevoteHours();
			}
			else{
				s += "," + phaseNameTemp + "/" + p.getDevoteHours();
			}
		}
		return s;
	}
	
	//返回不同审核状态的颜色码对应字符串,结合投入时间
	public static String getCheckStateColor(int checkState, Double hours){
		String colorString = "";
		if(checkState == 1){//未审批
			colorString = "[BGCOLOR=#999999]";//灰色
		}
		else if(checkState == 3){//审批不通过
			colorString = "[BGCOLOR=#b94a48]";//红色
		}
		else if(checkState == 2){//审批通过
			//若投入时间>=7，黄色，否则无色
			if(hours>=7)
				colorString = "[BGCOLOR=#FFC500]";//黄色
		}
		return colorString;
			
	}
	
	public static int getCheckState(int oldCheckState,int newCheckState){
		int state = oldCheckState;
		if(oldCheckState == 0 || oldCheckState == newCheckState)
			state = newCheckState;
		else if(oldCheckState == 1 && newCheckState ==3){
				state = newCheckState;
		}
		else if(oldCheckState == 2){
				state = newCheckState;
		}
		return state;
	}
	/********************************员工排期使用方法结束************************************************/
	/*  Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")) */
	public static void main(String[] args) throws IOException{
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		//两个方法中时区均设置为GMT-8
		
		//根据年份、月份获取周数
		/*
		int i = getWeekNumByYearMonth(2013,12);
		System.out.println(i);
		
		//根据日期取项目排期表中相应记录
		//即若该日期计入X年X月X周，则rp中年份、月份、周序号赋成相应值
		//结合项目编号、（阶段）可获得相应记录
		Date date = Date.valueOf("2013-10-20");	
		RootProjectPhase rp = new RootProjectPhase();
		getRootProjectPhaseByDate(rp,date);
		System.out.println(rp);
		*/
		
		//根据年份取各个月周数
		/*
		int weekNums[] = new int[12];
		for(int i=0;i<12;i++)
			System.out.print(weekNums[i]+" ");
		getWeekNumsByYear(2013,weekNums);
		for(int i=0;i<12;i++)
			System.out.print(weekNums[i]+" ");
		*/
		
		//写入root_project_phase
		/*
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		
		fillRootProjectPhase(2012,10,2,2014,3,3,session,4,"T44444");
		*/
		
		//System.out.println(getGapWeekNum(2013,10,1,2014,1,1));
		/*
		String s = "";
		s += getTableHead(2013,10,1,2013,12,5);
		System.out.println(s);
		*/
		
		//String s[] = new String[3];
		//for(int i=0;i<s.length;i++)
		//	System.out.print(s[i] + " ");
		
		/*
		String projNo = "T1111222";
		Integer empYstId = 274899;
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		String s[] = getWholeYearEmpSchd(empYstId,projNo,session);
		System.out.println(s.length);
		for(int i=0;i<s.length;i++)
			System.out.print(s[i] + " ");
		
		Gson gson = new Gson();
		List list = null;
		String s = gson.toJson(list);
		System.out.println(s);
		
		int colId = 5;
		int[] ymw = getYearMonthWeekNoByColId(colId);
		for(int i=0;i<ymw.length;i++){
			System.out.println(ymw[i]);
		}
		
		Integer empYstId = 1233;
		String projNo = "T1234";
		int year = 2013;
		int month = 10;
		int weekNo = 3;
		Integer phaseId = 1;
		Double devoteHours = 13.5;
		int checkState = 1;
		boolean result = fillProjectPhase(empYstId,projNo,year,month,weekNo,phaseId,devoteHours,checkState,session);
		System.out.println(result);
		
		//System.out.println(getWeekNumOfCurYear());
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		int year = cal.get(Calendar.YEAR);//获得当前年份
		int month = cal.get(Calendar.MONTH);//获得月份,0-11
		int caldate = cal.get(Calendar.DATE);//获得日期，1-31
		System.out.println(year);
		System.out.println(month);
		System.out.println(caldate);
		Date date = new Date(year-1900,month,caldate);
		System.out.println(date);
		*/
		//System.out.println(getPreWeekEmpSchd(274899,"T2",session));
		ProjectPhase pTemp = new ProjectPhase();
			
		pTemp.setEmpYstId(274899);
		pTemp.setProjNo("T1356071");
		pTemp.setYear(2013);
		pTemp.setMonth(10);
		pTemp.setWeekNo(1);
		pTemp.setCheckState(2);
			
		session.update("ProjectPhaseMapper.updateProjectPhaseCheckStateByEPYMW", pTemp);
		session.commit();
	/*
	Calendar cal = Calendar.getInstance(); 
	cal.set(Calendar.YEAR,2013); 
	cal.set(Calendar.MONTH, 11 - 1);//Java月份才0开始算 
	
	int dayOfMonth = 
	cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
	
	cal.set(Calendar.DATE, 15);
	java.util.Date date = cal.getTime();
	System.out.println(date);
	System.out.println(cal.get(Calendar.DAY_OF_WEEK));
	System.out.println(dayOfMonth);
	
	int dayOfMonth1 = 
		cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
	cal.set(Calendar.DATE, 30);
	java.util.Date date1 = cal.getTime();
	System.out.println(date1);
	System.out.println(cal.get(Calendar.DAY_OF_WEEK));
	System.out.println(dayOfMonth1);
	
	System.out.println((int)(18/7));
	System.out.println(18%7);
	*/
	}
}
