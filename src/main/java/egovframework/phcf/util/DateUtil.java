package egovframework.phcf.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DateUtil {
    /**
     * 오늘 날짜를 yyyyMMdd 형식으로 가져온다.
     * @return
     */
    public static String getToday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return dateToString(cal.getTime(), "yyyyMMdd");
    }
    
    /**
     * 오늘 날짜를 원하는 날짜형식으로 가져온다
     * @param pattern
     * @return
     */
    public static String getToday(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return dateToString(cal.getTime(), pattern);
    }
    
    /**
     * 패턴에 맞는 날짜 형식으로 변환 (Date => String)
     * @param date		Date 날짜
     * @param pattern	날짜 형식
     * @return
     */
	public static String dateToString(Date date, String pattern) {
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);	    
	    return formatter.format(date);
	}
	
	/**
     * 패턴에 맞는 날짜 형식으로 변환 (Date => String)
     * @param date		Date 날짜
     * @param pattern	날짜 형식
     * @return
     */
	public static String dateToString(java.sql.Date date, String pattern) {
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);	    
	    return formatter.format(date);
	}
	
	/**
	 * 패턴에 맞는 날짜 형식으로 변환 (String => Date)
	 * @param stringDate		String 형 날짜
	 * @param stringPattern		String 형 날짜 형식
	 * @return
	 * @throws Exception
	 */
	public Date stringToDate(String stringDate, String stringPattern) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(stringPattern, Locale.KOREA);	 
   		return formatter.parse(stringDate);
	}
	
	/**
	 * 입력된 일자 문자열을 확인하고 8자리로 리턴
	 * @param dateStr
	 * @return
	 */
	private static String validChkDate(String dateStr) {
		String _dateStr = dateStr;
		if (dateStr == null || !(dateStr.trim().length() == 8 || dateStr.trim().length() == 10)) {
			throw new IllegalArgumentException("Invalid date format: " + dateStr);
		}
		if (dateStr.length() == 10) {
			_dateStr = StringUtils.substring(dateStr, 0, 4) + StringUtils.substring(dateStr, 4, 6) + StringUtils.substring(dateStr, 6, 8);
		}
		return _dateStr;
	}
	
	/**
	 * 날짜 연산
	 * @param sDate
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private static String addYearMonthDay(String sDate, int year, int month, int day) {
		String dateStr = validChkDate(sDate);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		try {
			cal.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format: " + dateStr);
		}
		if (year != 0) {
			cal.add(Calendar.YEAR, year);
		}
		if (month != 0) {
			cal.add(Calendar.MONTH, month);
		}
		if (day != 0) {
			cal.add(Calendar.DATE, day);
		}
		
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 년도를 더한다.
	 * @param dateStr
	 * @param year
	 * @return
	 */
	public static String addYear(String dateStr, int year) {
		return addYearMonthDay(dateStr, year, 0, 0);
	}
	
	/**
	 * 월을 더한다.
	 * @param dateStr
	 * @param month
	 * @return
	 */
	public static String addMonth(String dateStr, int month) {
		return addYearMonthDay(dateStr, 0, month, 0);
	}
	
	/**
	 * 일을 더한다.
	 * @param dateStr
	 * @param day
	 * @return
	 */
	public static String addDay(String dateStr, int day) {
		return addYearMonthDay(dateStr, 0, 0, day);
	}
	
	/**
	 * yyyyMMdd 포맷의 String 문자열을 gubun으로 변경한다.
	 * @param stringDate
	 * @param gubun
	 * @return
	 */
	public static String StringFormat(String stringDate, String gubun) {
		if(StringUtils.isEmpty(stringDate) || stringDate.length() != 8) {
			return stringDate;
		}
		
		String tmpDate = StringUtils.substring(stringDate, 0, 4) + gubun;
		tmpDate += StringUtils.substring(stringDate, 4, 6) + gubun;
		tmpDate += StringUtils.substring(stringDate, 6, 8);
		
		return tmpDate;
	}
	
	/**
	 * yyyyMMdd 기간
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	public static int getDaysDiff(String dateStr1, String dateStr2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf.parse(dateStr1);
			date2 = sdf.parse(dateStr2);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format: args[0]=" + dateStr1 + " args[1]=" + dateStr2);
		}
		int days1 = (int) ((date1.getTime() / 3600000) / 24);
		int days2 = (int) ((date2.getTime() / 3600000) / 24);

		return days2 - days1;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// 2018.09.21 : 날짜 관련 Util 추가 : 김경식
	//현재 날짜 월요일
 	public static String getCurMonday(){

 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
 		Calendar c = Calendar.getInstance();
 		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
	 		return formatter.format(c.getTime());
 	}

 	//현재 날짜 일요일
 	public static String getCurSunday(){

 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
 		Calendar c = Calendar.getInstance();
 		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
 		c.add(c.DATE,7);
 		return formatter.format(c.getTime());
 	}
 	

 	//현재 날짜 주차
 	public static String getWeek(){

 		Calendar c = Calendar.getInstance();
 		String week = String.valueOf(c.get(Calendar.WEEK_OF_MONTH));
 		
 		return week;
 	}
 	
 	// 현재 날짜의 마지막 주를 돌려준다.
 	public static String getLastWeek() {
 		
 		Calendar c = Calendar.getInstance();
 		String lastWeek = String.valueOf( c.getMaximum(Calendar.WEEK_OF_MONTH) );
 		
 		return lastWeek;
 	}

 	//특정 년,월,주 차에 월요일 구하기
 	public static String getMonday(String yyyy,String mm, String wk){

 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
 		Calendar c = Calendar.getInstance();
 		int y=Integer.parseInt(yyyy);
 		int m=Integer.parseInt(mm)-1;
 		int w=Integer.parseInt(wk);

 		c.set(Calendar.YEAR,y);
 		c.set(Calendar.MONTH,m);
 		c.set(Calendar.WEEK_OF_MONTH,w);
 		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

 		return formatter.format(c.getTime());
 	}

 	//특정 년,월,주 차에 토요일 구하기
 	public static String getSaturday(String yyyy,String mm, String wk){
 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
 		Calendar c = Calendar.getInstance();

 		int y=Integer.parseInt(yyyy);
 		int m=Integer.parseInt(mm) - 1;
 		int w=Integer.parseInt(wk) - 1;

 		c.set(Calendar.YEAR,y);
 		c.set(Calendar.MONTH,m);
 		c.set(Calendar.WEEK_OF_MONTH,w);
 		c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
 		c.add(c.DATE,7);

 		return formatter.format(c.getTime());
 	}
 	
 	//특정 년,월,주 차에 일요일 구하기
 	public static String getSunday(String yyyy,String mm, String wk){
 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
 		Calendar c = Calendar.getInstance();

 		int y=Integer.parseInt(yyyy);
 		int m=Integer.parseInt(mm) - 1;
 		int w=Integer.parseInt(wk);

 		c.set(Calendar.YEAR,y);
 		c.set(Calendar.MONTH,m);
 		c.set(Calendar.WEEK_OF_MONTH,w);
 		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
 		c.add(c.DATE,7);

 		return formatter.format(c.getTime());
 	}
 	
 	/**
 	 * 년도(yyyy), 월(mm) 값을 입력 받아 공공데이터 포털 api를 이용해 휴일 정보를 가져 온다.
 	 * @param yyyy	: 검색 할 년도
 	 * @param mm : 검색 할 월
 	 * @return
 	 * @throws Exception
 	 */
 	public static List<String> getHolidayList(String yyyy, String mm) throws Exception {
 		
 		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /*URL*/
 		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + "9pooQFOw84mpp7FiV5NtwVJKHWuDCSQFmJH5hii7TkDeI5iHgHt7fzuXHyyAqGyeaMb00RYhrbEjXubt2QxxHg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode(yyyy, "UTF-8")); /*연*/
        urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode(mm, "UTF-8")); /*월*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        	
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
//        System.out.println(sb.toString());
        
        List<String> holidayList = new ArrayList<String>();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(sb.toString())));
		
		NodeList nodeList = document.getElementsByTagName("locdate");
//			System.out.println("== nodeList length : " + nodeList.getLength());
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node textNode = nodeList.item(i).getChildNodes().item(0);
//				System.out.println("== textNode value : " + textNode.getNodeValue());
			
			holidayList.add(textNode.getNodeValue());
		}
			
        return holidayList;
 	}
 	
 	// 공휴일을 포함한 주말 일정을 List형태로 넘겨준다.
 	public static List<String> getHollydayWithWeekendList(String yyyy, String mm) throws Exception {
 		
 		// 공휴일을 가져온다.
 		List<String> holidayList = DateUtil.getHolidayList(yyyy, mm);
//		System.out.println("== holidayList : " + holidayList);
 		
 		// 주말 정보를 가져온다.
 		// 현재 월의 마지막 주차를 가져온다.
 		String lastWeek = DateUtil.getLastWeek();
// 			System.out.println("== last week : " + lastWeek);

 		// 첫째 주 부터 마지막 주 까지 돌면서 토요일 일요일을 가져온다.
 		for(int i = 1; i <= Integer.valueOf(lastWeek); i++) {
 			
 			String saturday = DateUtil.getSaturday(yyyy, mm, String.valueOf( i ));
// 			System.out.println("== saturday : " + saturday);
 			holidayList.add(saturday);
 			
 			String sunday = DateUtil.getSunday(yyyy, mm, String.valueOf( i ));
// 			System.out.println("== sunday : " + sunday);
 			holidayList.add(sunday);
 		}
 			
		return holidayList;
 	}
 	
 	
 	// 김경식 DateUtil 추가 끝...
 	/////////////////////////////////////////////////////////////////////////////////////////////////
}
