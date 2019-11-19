package egovframework.phcf.scheduler.userspec;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.cmm.service.EgovProperties;

/**
 * 회원신청 전문 생성용 Data 부문 클래스
 * 
 *  일부 입력 사항이 있다..
 *  getter, setter 부분 필요 함...
 *  
 * @author 김경식
 */
public class UserDeleteData {
	
	// -----------------------------------------------------
	// 여기서 길이는 byte길이 이며 한글 입력시 2byte로 처리 되어야 한다..
	// -----------------------------------------------------
	String recordTp = "D";											// 1자리		Record구분(필수) : DATA RECORD
	String dealID = EgovProperties.getProperty("dealID");			// 8자리		거래사ID(필수) : 이용 거래사 ID
	String dealGroup = "";											// 8자리		거래사그룹ID : 거래사그룹ID -> leftpad ' '
	String dataSeq = "1";											// 6자리		연번(필수) : DATA순번(000001 ~ 순차적증가)
	String procResult = "D";										// 1자리		처리내역(필수) : 해지:'D'
	String userId = "";												// 20자리	회원ID(필수) : 회원 ID:반드시 유일한값이어야 함
	String userNm = "";												// 20자리	회원명(필수) : 회원성명:기호불가 -> utf-8 한글 byte 처리가 필요함
	String space223 = "";											// 223자리	공백
	String serviceCode = "B";										// 1자리		서비스코드(필수) : 은행B, 카드:C -> 미입력시 B처리
	String procCode = " ";											// 1자리		처리결과 : Y: 정상, N: 오류 -> 나이스 입력
	String resultCode = "    ";										// 4자리		결과코드 : D*** : 오류코드표 참조 -> 나이스 입력
	String resultMsg = "";											// 30자리	결과메세지 : 결과코드의 메시지 : 오류코드표 참조 -> 나이스 입력
	String userMam = "";											// 20자리	사용자정의 : 미사용시 Space 처리
	
	/**
	 * 생성자
	 * @param dataTp 내부코드로 사용 -> D:카드, S:CMS
	 */
	public UserDeleteData(String dataTp, int dataSeq, String userId, String userNm) {
		if(dataTp.equalsIgnoreCase("D")) { serviceCode = "C"; }
		else { serviceCode = "B"; }
		
		this.dataSeq = String.valueOf(dataSeq);
		this.userId = userId;
		this.userNm = userNm;
	}
	
	/**
	 *  Data부분을 조합하여 return 해준다.
	 *  각 데이터 별로 별도의 getter, setter를 만들려고 했는데....
	 *  현재까진 필요성을 못 느끼겠다...
	 * @return
	 */
	public String getDataStr() throws Exception {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(recordTp);
		strBuf.append(dealID);
		strBuf.append(StringUtils.leftPad(dealGroup, 8, ' '));
		strBuf.append(StringUtils.leftPad(dataSeq, 6, '0'));
		strBuf.append(procResult);
		strBuf.append(StringUtils.rightPad(userId, 20, ' '));
		
		byte[] nameByteArr = userNm.getBytes("euc-kr");
		strBuf.append(StringUtils.rightPad(userNm, 20 - (int)Math.ceil( nameByteArr.length / 2 ), ' ')); // utf-8 한글 byte 처리가 필요함..
		
		strBuf.append(StringUtils.rightPad(space223, 223, ' ') );
		strBuf.append(serviceCode);
		strBuf.append(procCode);
		strBuf.append(resultCode);
		strBuf.append(StringUtils.rightPad(resultMsg, 30, ' '));
		strBuf.append(StringUtils.rightPad(userMam, 20, ' '));
		strBuf.append("\r\n");
		
		return strBuf.toString();
	}
	
}
