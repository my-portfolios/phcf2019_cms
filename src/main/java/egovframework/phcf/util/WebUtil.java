package egovframework.phcf.util;

import javax.servlet.http.HttpServletRequest;

import egovframework.phcf.common.service.ParamMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

@SuppressWarnings("unchecked")
public class WebUtil {
	
	/**
	 * 토큰키명
	 * @return
	 */
	public static String getTokenKey(){
		return TokenProcessor.TOKEN_KEY;
	}
	
	/**
	 * 토큰키 설정
	 * @param request
	 * @param entity
	 */
	public static void setToken(HttpServletRequest request, ParamMap paramMap){
		saveToken(request);
		paramMap.put(TokenProcessor.TOKEN_KEY, getToken(request));
	}
	
	/**
	 * Token 저장
	 * 
	 * @param request
	 */
	public static void saveToken(HttpServletRequest request) {
		TokenProcessor.getInstance().saveToken(request);
	}

	/**
	 * Token 초기화
	 * 
	 * @param request
	 */
	public static void resetToken(HttpServletRequest request) {
		TokenProcessor.getInstance().resetToken(request);
	}

	/**
	 * Token 정보
	 * 
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request) {
		return (String) WebUtils.getSessionAttribute(request, TokenProcessor.TRANSACTION_TOKEN_KEY);
	}
	
	/**
	 * <p>Token 유효성체크 </p>
	 * <p>true 이면 토큰 초기화</p>
	 * <p>false 이면 토큰 초기화 안함</p>
	 * @param request
	 * @param reset
	 * @throws Exception
	 */
	public static void validToken(HttpServletRequest request, boolean reset) throws Exception {
		
		if (!TokenProcessor.getInstance().isTokenValid(request, reset)) {
			throw new Exception("잘못된 글쓰기입니다.");
		}
	}

	/**
	 * Token 유효성체크
	 * 
	 * @param request
	 * @param token
	 * @param reset
	 * @throws Exception
	 */
	public static void validToken(HttpServletRequest request, String token, boolean reset) throws Exception {
		
		if (!TokenProcessor.getInstance().isTokenValid(request, token, reset)) {
			throw new Exception("잘못된 글쓰기입니다.");
		}
	}

	/**
	 * Token 유효성체크
	 * 
	 * @param request
	 * @param reset
	 * @throws Exception
	 */
	public static void validToken(HttpServletRequest request, boolean reset,String msg) throws Exception {
		
		if(StringUtils.isBlank(msg)){
			msg = "잘못된 글쓰기입니다.";
		}
		
		if (!TokenProcessor.getInstance().isTokenValid(request, reset)) {
			throw new Exception(msg);
		}
	}

	/**
	 * Token 유효성체크
	 * 
	 * @param request
	 * @param token
	 * @param reset
	 * @throws Exception
	 */
	public static void validToken(HttpServletRequest request, String token, boolean reset,String msg) throws Exception {
		if(StringUtils.isBlank(msg)){
			msg = "잘못된 글쓰기입니다.";
		}
		
		if (!TokenProcessor.getInstance().isTokenValid(request, token, reset)) {
			throw new Exception(msg);
		}
	}
	
	/**
	 * REQUEST METHOD의 POST 여부
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isPost(HttpServletRequest request) {
		return "POST".equals(request.getMethod().toUpperCase());
	}

	/**
	 * REQUEST METHOD의 GET 여부
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isGet(HttpServletRequest request) {
		return "GET".equals(request.getMethod().toUpperCase());
	}
	
	/**
	 * 정규화 표현식을 문자열로 변환
	 * @param str
	 * @return
	 */
	public static String codeToStr(String str) {
		str = str.replaceAll("&lt;", "<")
		 		 .replaceAll("&gt;", ">")
		 		 .replaceAll("&#039;", "'")
		 		 .replaceAll("&#034;", "\"")
		 		 .replaceAll("&amp;", "&");
		
		return str;
	}
	
	/**
	 * 문자열을 정규화 표현식으로 변환
	 * @param str
	 * @return
	 */
	public static String strToCode(String str) {
		str = str.replaceAll("&", "&amp;")
		 		 .replaceAll("<", "&lt;")
		 		 .replaceAll(">", "&gt;")
		 		 .replaceAll("'", "&#039;")
		 		 .replaceAll("\"", "&#034;");
		
		return str;
	}
}
