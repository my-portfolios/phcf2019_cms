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
public class UserCreateData {
	
	// -----------------------------------------------------
	// 여기서 길이는 byte길이 이며 한글 입력시 2byte로 처리 되어야 한다..
	// -----------------------------------------------------
	String recordTp = "D";											// 1자리		Record구분(필수) : DATA RECORD
	String dealID = EgovProperties.getProperty("dealID");		// 8자리		거래사ID(필수) : 이용 거래사 ID
	String dealGroup = "";											// 8자리		거래사그룹ID : 거래사그룹ID -> leftpad ' '
	String dataSeq = "1";											// 6자리		연번(필수) : DATA순번(000001 ~ 순차적증가)
	String procResult = "N";										// 1자리		처리내역(필수) : 신청:'N'
	String userId = "";												// 20자리	회원ID(필수) : 회원 ID:반드시 유일한값이어야 함
	String userNm = "";												// 20자리	회원명(필수) : 회원성명:기호불가 -> utf-8 한글 byte 처리가 필요함
	String bankCode = "003";										// 3자리		출금은행코드(은행필수) : 출금계좌 은행코드 -> 기본코드(003:기업은행)	
	String bankNum = "";											// 16자리	출금계좌번호(은행필수) : 출금계좌(10~16자리사용) 
	String bankAccUserNm = "";									// 20자리	예금주성명(은행필수) : 출금계좌 예금주 성명 -> utf-8 한글 byte 처리가 필요함.
	String bankAccUserNum = "";								// 13자리	예금주주민번호(은행,핸드폰필수) : 출금계좌 예금주 주민등록번호(생년월일만 기입함 뒷자리 다 넣으면 오류 발생)
	String firstAccYYYYMM = "";									// 6자리		최초납부년월(미사용) : 공백처리 해야 됨...
	String scPrice = "";												// 12자리	거래금액(미사용) : 공백처리
	String userPhone = "";											// 16자리	연락처(필수) : 회원 휴대폰 번호:'-'제외
	String userEmail = "";											// 30자리	이메일주소 : 회원 이메일 주소:미입력시 공란처리
	String prodName = "순수후원금";							// 30자리	상품명 : 상품명 기재:미입력시 공란
	String agreeYn = "   ";											// 3자리		동의여부(미사용) : ON:온라인인증서인증, OFF:오프라인서면동의
	String cardNum = "";											// 16자리	카드번호(카드필수) : 카드번호
	String cardYYMM = "    ";										// 4자리		유효년월(카드필수) : 유효년월
	String agencyCode = " ";										// 1자리		통신사코드(휴대폰필수)(미사용) : 통신사
	String phoneNum = "";											// 16자리	휴대폰번호(휴대폰필수)(미사용) : 휴대폰 결제 이용시
	String taxNum = "";												// 20자리	현금영수증인증번호 : 현금영수증발행시	
	String space17 = "";												// 17자리	공백
	String serviceCode = "B";										// 1자리		서비스코드(필수) : 은행B, 카드:C -> 미입력시 B처리
	String procCode = " ";											// 1자리		처리결과 : Y: 정상, N: 오류 -> 나이스 입력
	String resultCode = "    ";										// 4자리		결과코드 : D*** : 오류코드표 참조 -> 나이스 입력
	String resultMsg = "";											// 30자리	결과메세지 : 결과코드의 메시지 : 오류코드표 참조 -> 나이스 입력
	String userMam = "";											// 20자리	사용자정의 : 미사용시 Space 처리
	
	/**
	 * 생성자
	 * @param dataTp 내부코드로 사용 -> D:카드, S:CMS
	 */
	public UserCreateData(String dataTp, int dataSeq, String userId, String userNm, String userPhone, String userEmail) {
		if(dataTp.equalsIgnoreCase("D")) { serviceCode = "C"; }
		else { serviceCode = "B"; }
		
		this.dataSeq = String.valueOf(dataSeq);
		this.userId = userId;
		this.userNm = userNm;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
	}
	
	/**
	 * CMS 정기이체 정보를 셋팅한다.
	 * @param bankCode
	 * @param bankNum
	 * @param bankAccUserNm
	 * @param bankAccUserNum
	 */
	public void setCmsUserCreateData(String bankCode, String bankNum, String bankAccUserNm, String bankAccUserNum) {
		
		this.bankCode = bankCode;
		this.bankNum = bankNum;
		this.bankAccUserNm = bankAccUserNm;
		this.bankAccUserNum = bankAccUserNum;
	}
	
	/**
	 * 카드 정기이체 정보를 셋팅한다.
	 * @param cardNum
	 * @param cardYYMM
	 */
	public void setCardUserCreateData(String cardNum, String cardYYMM) {
		
		this.cardNum = cardNum;
		this.cardYYMM = cardYYMM;
	}
	
	/**
	 * 현금영수증 인증번호를 입력 한다.(현금영수증 발행시..)
	 * @param taxNum
	 */
	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
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
		strBuf.append(bankCode);
		strBuf.append(StringUtils.rightPad(bankNum, 16, ' '));
		
		byte[] bankAccUserNmByteArr = bankAccUserNm.getBytes("euc-kr");
		strBuf.append(StringUtils.rightPad(bankAccUserNm, 20 - (int)Math.ceil( bankAccUserNmByteArr.length / 2 ), ' ')); // utf-8 한글 byte 처리가 필요함..
		strBuf.append(StringUtils.rightPad(bankAccUserNum, 13, ' '));
		strBuf.append(StringUtils.rightPad(firstAccYYYYMM, 6, ' '));
		strBuf.append(StringUtils.rightPad(scPrice, 12, ' '));
		strBuf.append(StringUtils.rightPad(userPhone, 16, ' '));
		strBuf.append(StringUtils.rightPad(userEmail, 30, ' '));
		
		byte[] prodNameByteArr = prodName.getBytes("euc-kr");
		strBuf.append(StringUtils.rightPad(prodName, 30 - (int)Math.ceil( prodNameByteArr.length / 2 ), ' '));	// utf-8 한글 byte 처리가 필요함..
		
		strBuf.append(agreeYn);
		strBuf.append(StringUtils.rightPad(cardNum, 16, ' '));
		strBuf.append(cardYYMM);
		strBuf.append(agencyCode);
		strBuf.append(StringUtils.rightPad(phoneNum, 16, ' '));
		strBuf.append(StringUtils.rightPad(taxNum, 20, ' '));
		strBuf.append(StringUtils.rightPad(space17, 17, ' '));
		strBuf.append(serviceCode);
		strBuf.append(procCode);
		strBuf.append(resultCode);
		strBuf.append(StringUtils.rightPad(resultMsg, 30, ' '));
		strBuf.append(StringUtils.rightPad(userMam, 20, ' '));
		strBuf.append("\r\n");
		
		return strBuf.toString();
	}
	
}
