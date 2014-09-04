package chiphead.utils;

import java.util.TimeZone;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static String getDateStr(java.util.Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(date); 
	}
	
	public static String getDateStr(java.sql.Date date) {
		if (date == null) {
			return "";
		}
		java.util.Date utilDate = new java.util.Date (date.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(utilDate); 
	}
	
	public static String getChgDateStr(java.sql.Date date) {
		if (date == null) {
			return "";
		}
		java.util.Date utilDate = new java.util.Date (date.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(utilDate); 
	}
	
	
	public static String getDateTimeString(Timestamp dateTime) {
		if (dateTime == null) {
			return "";
		}
		java.util.Date utilDateTime = new java.util.Date (dateTime.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		
		return sdf.format(utilDateTime); 
	}
	
	public static String[] getDateTimeStringArray(Timestamp dateTime) {
		String str[] = new String[2];
		str[0] = "";
		str[1] = "";
		if (dateTime == null) {
			return str;
		}
		java.util.Date utilDateTime = new java.util.Date (dateTime.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		
		str = sdf.format(utilDateTime).split(" ");
		return str; 
	}
	
	public static Timestamp getDateTime(String dateTimeString){
		if (dateTimeString == null || dateTimeString.isEmpty()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		
		java.util.Date utilDateTime = null;
		try {
			utilDateTime = sdf.parse(dateTimeString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//util类型   
		java.sql.Timestamp dateTime = new java.sql.Timestamp(utilDateTime.getTime());//Timestamp类型   
		
		return dateTime; 
	}
	
	public static Date getDate(String dateString) {
		if (dateString == null || dateString.isEmpty()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		
		java.util.Date utilDate = null;
		try {
			utilDate = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//util类型   
		java.sql.Date date = new java.sql.Date(utilDate.getTime());
		
		return date; 
	}
	
	public static Date getCurDate(){
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}
	
	public static Timestamp getCurDateTime(){
		java.util.Date utilDateTime = new java.util.Date();
		java.sql.Timestamp dateTime = new java.sql.Timestamp(utilDateTime.getTime());//Timestamp类型   
		return dateTime;
	}
	
	
	public static String getDateTimeSectionString(Timestamp startDateTime,Timestamp endDateTime) {
		if (startDateTime == null || endDateTime == null) {
			return "";
		}
		java.util.Date utilStartDateTime = new java.util.Date (startDateTime.getTime());
		java.util.Date utilEndDateTime = new java.util.Date (endDateTime.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		
		String startDateTimeString = sdf.format(utilStartDateTime);
		String endDateTimeString = sdf.format(utilEndDateTime);
		
		String startDate = startDateTimeString.split(" ")[0];
		String endDate = endDateTimeString.split(" ")[0];
		String startTime = startDateTimeString.split(" ")[1];
		String endTime = endDateTimeString.split(" ")[1];
		
		if(startDate.equals(endDate)){
			return startDate + " " + startTime + "-" + endTime;
		}
		else{
			return startDateTimeString + "-" + endDateTimeString;
		}
	}
	
	
	
	public static void main(String[] args) throws ParseException{
		//String date = "2013/09/03";
		//String date2 = date.replace("/", "-");
		//System.out.println(Date.valueOf(date2));
		
		//String date = "2013/09/03";
		//System.out.println(getDate(date));
		
		//Timestamp t1 = getDateTime("2013/11/12 23:55");
		//Timestamp t2 = getDateTime("2013/11/13 23:57");
		//System.out.println(getDateTimeSectionString(t1,t2));
		
		//Timestamp t1 = getDateTime("2013/11/12 23:55");
		//String str[] = getDateTimeStringArray(t1);
		//for(int i=0;i<str.length;i++)
		//	System.out.println(str[i]);
		
		
		//String s = getDateTimeString(t);
		//System.out.println(s);
		
		//System.out.println(getCurDate());
		/*
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime());
		cal.set(Calendar.YEAR,2013); 
		cal.set(Calendar.MONTH, 0);//Java月份从0开始算 
		cal.set(Calendar.DATE, 31);//日期设为1号
		System.out.println(cal.getTime());
		java.util.Date date = new java.util.Date();
		cal.setTime(date);
		System.out.println(cal.getTime());
		*/
		System.out.println(getCurDateTime());
	}
	
}
