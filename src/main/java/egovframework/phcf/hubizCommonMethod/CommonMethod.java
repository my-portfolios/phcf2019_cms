package egovframework.phcf.hubizCommonMethod;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.phcf.common.service.CommonService;

public class CommonMethod {
	
	/**
	 * 날짜 비교
	 * @param date1
	 * @param date2
	 * @return date1 > date2
	 * @author 김량래
	 * @since 2019-12-03
	 */
	
	@Resource(name="CommonService")
	private CommonService commonService;
	
	private static Logger logger = Logger.getLogger(CommonMethod.class);
	
	public static String checkDateCompare(String date1,String date2,String format) throws Exception {
		Date dt1 = stringToDate(date1,format);
		Date dt2 = stringToDate(date2,format);
		
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
	public static boolean checkDateRange(String date, String date1,String date2,String format) throws Exception {
		
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
	public static Date stringToDate(String date, String format) {
		Date dt = null;
		
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(format);
			dt = fmt.parse(date);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
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
	public static String dateToString(Date date, String format) {
		String dt = null;
		
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(format);
			dt = fmt.format(date);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
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
	public static ArrayList<Object> addList(List<Object> list, Object obj) {
		ArrayList<Object> copiedList = new ArrayList<>();
		copiedList.addAll(list);
		
		if(!copiedList.contains(obj)) copiedList.add(obj);
		return copiedList;
	}

	/**
	 * 날짜를 입력하면 이번주의 첫번째 날을 가져온다.
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfWeek(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
          
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  
        cal.add(Calendar.DATE, (dayOfWeek-1)*-1);  
          
        return cal.getTime();  
    }  
	
	public static String getTodayDate(String format) {  
        return dateToString(new Date(),format);
    }  
	
	
	/**
	 * 권혜진
	 * 컨텐츠 페이지마다 공통적으로 들어가는 부분 - 템플릿 적용
	*/
	public static ModelAndView ContentIntoTemplate(HttpServletRequest request, String jspPath, String skinNm) {
		
		if(skinNm==null || skinNm.equals("")) skinNm="basic";
		String templatePath = "template/_layout/"+skinNm;
		
		String tmpltDir = request.getServletContext().getRealPath("/WEB-INF/jsp/"+templatePath+".jsp");
		String mavUrl = ""; 
		File fTmplt = new File(tmpltDir);
		
		ModelAndView mav = new ModelAndView();
		
		if(fTmplt.exists()) {
			String isDir = request.getServletContext().getRealPath(jspPath); 
			File f = new File(isDir);
			if(f.exists()) mavUrl = templatePath;
			else mavUrl = "error/noTmpltErrorPage";
			mav = new ModelAndView(mavUrl);  
			mav.addObject("jspPath", jspPath); 
		} else {
			mavUrl = "error/noContentErrorPage";
			mav = new ModelAndView(mavUrl); 
		}
		
		return mav;
	}
	
	/**
	 * 권혜진
	 * 컨텐츠 페이지마다 공통적으로 들어가는 부분 - 템플릿 적용
	 * @throws Exception 
	*/
	public static ModelAndView ContentsIntoTemplate(HttpServletRequest request, String jspPath, String skinNm, CommonService commonService) throws Exception {
		
		ModelAndView mav=new ModelAndView();
		if(skinNm==null || skinNm.equals("")) skinNm="basic";
		
		@SuppressWarnings("unchecked")
		HashMap<String, Object> x = (HashMap<String, Object>) request.getAttribute("CurrentMenuMap");
		if(commonService != null && x.get("CONTENT_ID").toString() != null && !x.get("CONTENT_ID").toString().equals("0") && !x.get("CONTENT_ID").toString().equals("")) {
			HashMap<String, String> paramMap = new HashMap<String, String>();
			
			paramMap.put("bbsId", "BBSMSTR_000000000002");
			paramMap.put("nttId", x.get("CONTENT_ID").toString());
			
			HashMap<String, String> contentMap = commonService.selectContent(paramMap);
			String templatePath = "template/_layout/"+skinNm;
			
			String tmpltDir = request.getServletContext().getRealPath("/WEB-INF/jsp/"+templatePath+".jsp");
			File fTmplt = new File(tmpltDir);
			
			if(fTmplt.exists()) {
				mav = new ModelAndView(templatePath);
				mav.addObject("content", contentMap.get("NTT_CN").toString());
				mav.addObject("contentYN", "Y");
			} else {
				mav = new ModelAndView("error/noContentErrorPage"); 
			}
		} else {
			String templatePath = "template/_layout/"+skinNm;
			
			String tmpltDir = request.getServletContext().getRealPath("/WEB-INF/jsp/"+templatePath+".jsp");
			String mavUrl = ""; 
			File fTmplt = new File(tmpltDir);
			
			if(fTmplt.exists()) {
				String isDir = request.getServletContext().getRealPath(jspPath); 
				
				mavUrl = "error/noTmpltErrorPage";
				if(isDir != null) {
					File f = new File(isDir);
					if(f.exists()) mavUrl = templatePath;
				}
				mav = new ModelAndView(mavUrl);  
				mav.addObject("jspPath", jspPath); 
				mav.addObject("contentYN", "N");
			} else {
				mavUrl = "error/noContentErrorPage";
				mav = new ModelAndView(mavUrl); 
			}
		}
		
		return mav;
	}
	
	public static boolean base64ImageDecoder(String base64, String target, String title){
		String savePath = EgovProperties.getProperty("Globals.fileStorePath")+target+"/"+title+".png";
		System.out.println("savePath = " + savePath);
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
		try {
			BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
			ImageIO.write(bufImg, "png", new File(savePath));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean removeFile(String target, String title) {
		String filePath = EgovProperties.getProperty("Globals.fileStorePath") + target + "/" + title;
		File file = new File(filePath);
		logger.info("Deleted File : " + filePath);
		if(file.delete()) return true;
		else return false;
	}
	
	public static ModelAndView generalAlertThrowing(String url, String target, String msg) {
		ModelAndView modelAndView = new ModelAndView("error/generalErrorPage");
		
		modelAndView.addObject("url", url);
		modelAndView.addObject("target", target);
		modelAndView.addObject("msg", msg);
		
		return modelAndView;
	}
	
	public static List<CmmnDetailCode> getCodeDetailVOList(String code, EgovCmmUseService cmmUseService) throws Exception {
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId(code);
		List<CmmnDetailCode> codeList = cmmUseService.selectCmmCodeDetail(vo);
		return codeList;
	}
	
	public static int zeroConvert(Object src) {

		if (src == null || src.equals("null") || src.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(src.toString());
		}
	}
	
	public static int numberConvert(Object src, int defaultValue) {

		if (src == null || src.equals("null") || src.equals("")) {
			return defaultValue;
		} else {
			return Integer.parseInt(src.toString());
		}
	}
	
	public static String stringConvert(Object src, String defaultValue) {

		if (src == null || src.equals("null") || src.equals("")) {
			return defaultValue;
		} else {
			return src.toString();
		}
	}
	
	/*
	 * 파일 경로를 넣으면 파일의 내용을 반환한다.
	 */
	public String getFileContent(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        StringBuilder sb = new StringBuilder("");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
