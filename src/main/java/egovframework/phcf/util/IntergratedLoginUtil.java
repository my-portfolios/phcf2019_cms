package egovframework.phcf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 *  포항시 통합인증 관련 Util (이츠엠 통합인증 서비즈 연계)
 * 
 * 사전 작업내역 : 
 * 
 * Token 생성 및 수신 처리등 Util성 기능을 제공 함
 * 
 * ==== 개발 정보 ====
 * 생성일 : 2018.10.10
 * 개발사 : 휴비즈ICT
 * 
 * ==== 개발 이력 ====
 *    날짜       |        변경 정보       |   변경자
 * 2018.10.10 |         최초등록       |   김경식
 * 
 * @author KimKyoungsik
 *
 */
public class IntergratedLoginUtil {
	
	private final static String client_id = "gbd2XTaFl7";
	private final static String client_secret = "OM3SVjCNYxgnDXqVlVFaYAPtjDvBM80h";
	private final static String authUrl = "https://smart.pohang.go.kr/auth";
	private final static String USER_AGENT = "Mozilla/5.0";

	public static String getClientToken() throws Exception {
		
		String url = authUrl+"/pohang/token";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("User-Agent", USER_AGENT);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes("{\"grant_type\": \"client_credentials\",\"client_id\": \"" + client_id + "\", \"client_secret\": \"" + client_secret + "\"}");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		String responseMessage = con.getResponseMessage();
		System.out.println(responseMessage);
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		String access_token = null;
		
		access_token = response.toString();
		access_token = access_token.replaceAll("\"","").replaceAll("}","").split(":")[3];
		
		return access_token;
	}
	
	private final static String apiUrl = "https://smart.pohang.go.kr/api"; //실서버
	
	/**
	 * access_token 키 값을 이용해 통합회원API를 회원 식별키를 돌려 받는다.
	 * @param access_token : access_token
	 * @param session_id : access_token
	 * 
	 * @return 통합회원API 수행을 통한 회원 식별키
	 * @throws Exception
	 */
	public static String getSubKey(String access_token, String session_id) throws Exception {
		
		
		String url = apiUrl+"/user/session?session_id=" + session_id;

		URL obj = new URL(url);
		//HttpURLConnection con = (HttpURLConnection) obj.openConnection();//테스트서버
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();//실서버(ssl)
		con.setRequestMethod("GET");
		con.setDoOutput(true);
		con.setRequestProperty("Authorization", "Bearer "+access_token);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		String responseMessage = con.getResponseMessage();
		System.out.println(responseMessage);
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		String subKey = null;
		
		subKey = response.toString();
		subKey = subKey.replaceAll("\"","").replaceAll("}","").split(":")[1];
		
		return subKey;
	}
}
