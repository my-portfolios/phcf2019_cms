package egovframework.phcf.hubizCommonMethod;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public class CommonMethod {
	
	/**
	 * 날짜 비교
	 * @param date1
	 * @param date2
	 * @return date1 > date2
	 * @author 김량래
	 * @since 2019-12-03
	 */
	
	public static String checkDateCompare(String date1,String date2) throws Exception {
		Date dt1 = stringToDate(date1,"yyyy-MM-dd");
		Date dt2 = stringToDate(date2,"yyyy-MM-dd");
		
		if(dt1.compareTo(dt2) > 0) return "large";
		else if(dt1.compareTo(dt2) < 0) return "small";
		return "equal";
	}
	
	/**
	 * 날짜 범위 내에 있는 지 확인
	 * @param date
	 * @param date1
	 * @param date2
	 * @return date1 <= date <= date2 
	 * @throws Exception
	 * @author 김량래
	 * @since 2019-12-03
	 */
	public static boolean checkDateRange(String date, String date1,String date2, String format) throws Exception {
		
		Date dt = stringToDate(date,format);
		Date dt1 = stringToDate(date1,format);
		Date dt2 = stringToDate(date2,format);
		
		if(dt.compareTo(dt1) >= 0) {
			if(dt.compareTo(dt2) <= 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * String형을 Date형으로 변환 
	 * @param date
	 * @param format (ex. yyyy-MM-dd)
	 * @return dt
	 * @throws Exception
	 * @author 김량래
	 * @since 2019-12-03
	 */
	public static Date stringToDate(String date, String format) throws Exception {		
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		Date dt = fmt.parse(date);
		
		return dt;
	}
	
	/**
	 * Date형을 String형으로 변환
	 * @param date
	 * @param format (ex. yyyy-MM-dd)
	 * @return dt
	 * @throws Exception
	 * @author 김량래
	 * @since 2019-12-03
	 */
	public static String dateToString(Date date, String format) throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		String dt = fmt.format(date);
		
		return dt;
	}
	
	/**
	 * 날짜 계산
	 * @param date (더할 날짜)
	 * @param to (대상 Calendar.MONTH...)
	 * @param number (더할 값 1)
	 * @param format (yyyy-MM-dd)
	 * @return 계산된 값
	 * @throws Exception
	 */
	public static String calcDate(String date, int to, int number, String format) throws Exception {
		Date dt = stringToDate(date,format);
         
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(to, number);
         
        return dateToString(cal.getTime(),format);
	}
	
	/**
	 * 중복되지 않도록 list add
	 * @param list
	 * @param date
	 * @return list
	 */
	public static ArrayList<Object> addList(ArrayList<Object> list, String date) {
		if(!list.contains(date)) list.add(date);
		return list;
	}
	
	/**
	 * 권혜진
	 * 컨텐츠 페이지마다 공통적으로 들어가는 부분 - 템플릿 적용
	*/
	public static ModelAndView ContentIntoTemplate(HttpServletRequest request, String TemplatePath, String JspPath) {
		
		String jspPath =JspPath;
		String isDir = request.getServletContext().getRealPath(jspPath);
		String mavUrl = ""; 
		File f = new File(isDir);
		if(f.exists()) mavUrl =TemplatePath;
		else mavUrl = "error/noTmpltErrorPage";
		ModelAndView mav = new ModelAndView(mavUrl);  
		mav.addObject("jspPath", jspPath); 
		
		return mav;
	}
}
