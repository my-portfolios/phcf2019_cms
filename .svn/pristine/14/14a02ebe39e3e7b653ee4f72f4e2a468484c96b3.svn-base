package egovframework.phcf.scheduler.payspec;

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
public class PayCreateData {
	
	// -----------------------------------------------------
	// 여기서 길이는 byte길이 이며 한글 입력시 2byte로 처리 되어야 한다..
	// -----------------------------------------------------
	String recordTp = "D";											// 1자리		Record구분(필수) : DATA RECORD
	String dealID = EgovProperties.getProperty("dealID");				// 8자리		거래사ID(필수) : 이용 거래사 ID
	String dealGroup = "";											// 8자리		거래사그룹ID : 거래사그룹ID -> leftpad ' '
	String dataSeq = "1";											// 6자리		연번(필수) : DATA순번(000001 ~ 순차적증가)
	String procResult = "N";										// 1자리		처리내역(필수) : 신청:'N' 취소:'C' -> 취소는 카드만 해당
	String userId = "";												// 20자리	회원ID(필수) : 회원 ID:반드시 유일한값이어야 함
	String userNm = "";												// 20자리	회원명(필수) : 회원성명:기호불가 -> utf-8 한글 byte 처리가 필요함
	String bankTurnCnt = "";										// 2자리		통장기재내역 : 통장기재내역(월,회차표시) : 숫자만 허용
	String scPrice = "";												// 10자리	출금신청금액(필수) : 출금하고자하는금액
	String divAccTpKey1 = "00000000";						// 8자리 	분할계좌항목 Key(1)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice1 = "000000000";							// 9자리		분할입금금액(1)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey2 = "00000000";						// 8자리 	분할계좌항목 Key(2)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice2 = "000000000";							// 9자리		분할입금금액(2)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey3 = "00000000";						// 8자리 	분할계좌항목 Key(3)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice3 = "000000000";							// 9자리		분할입금금액(3)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey4 = "00000000";						// 8자리 	분할계좌항목 Key(4)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice4 = "000000000";							// 9자리		분할입금금액(4)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey5 = "00000000";						// 8자리 	분할계좌항목 Key(5)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice5 = "000000000";							// 9자리		분할입금금액(5)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey6 = "00000000";						// 8자리 	분할계좌항목 Key(6)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice6 = "000000000";							// 9자리		분할입금금액(6)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey7 = "00000000";						// 8자리 	분할계좌항목 Key(7)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice7 = "000000000";							// 9자리		분할입금금액(7)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey8 = "00000000";						// 8자리 	분할계좌항목 Key(8)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice8 = "000000000";							// 9자리		분할입금금액(8)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey9 = "00000000";						// 8자리 	분할계좌항목 Key(9)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice9 = "000000000";							// 9자리		분할입금금액(9)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String divAccTpKey10 = "00000000";						// 8자리 	분할계좌항목 Key(10)(필수) : 첫번째항목 분할계좌항목 Key -> 미사용시 '0'으로 체움
	String divScPrice10 = "000000000";						// 9자리		분할입금금액(10)(필수) : 첫번째항목 분할입금금액 -> 미사용시 '0'으로 체움
	String realPayAgreeDate = "";								// 8자리		실제출금(승인)일 : 실제 출금(승인)된일자 -> 나이스 입력 -> 취소시 승인일 입력
	String realPayAgreePrice = "";								// 10자리	실제출금(승인)금액 : 실제 출금(승인)된금액 -> 나이스 입력
	String payTaxPrice = "";										// 6자리		출금수수료 : 계약된수수료(미납시의뢰수수료) -> 나이스 입력
	String agreeNum = "";											// 10자리	승인번호 : 승인번호 -> 나이스,업체 입력 -> 취소시 승인번호 입력(승인번호를 가지고 있을 테이블이 필요 할 듯 함..
	String taxYn = "Y";												// 1자리		현금영수증발행여부 -> 발행:Y, 미발행:N -> 미입력시 : Y
	String space6 = "";												// 6자리		공백
	String serviceCode = "B";										// 1자리		서비스코드(필수) : 은행B, 카드:C -> 미입력시 B처리
	String procCode = " ";											// 1자리		처리결과 : Y: 정상, N: 오류 -> 나이스 입력
	String resultCode = "    ";										// 4자리		결과코드 : D*** : 오류코드표 참조 -> 나이스 입력
	String resultMsg = "";											// 30자리	결과메세지 : 결과코드의 메시지 : 오류코드표 참조 -> 나이스 입력
	String userMam = "";											// 20자리	사용자정의 : 미사용시 Space 처리
		
	/**
	 * 생성자
	 * @param dataTp 내부코드로 사용 -> D:카드, S:CMS
	 */
	public PayCreateData(String dataTp, int dataSeq, String userId, String userNm) {
		if(dataTp.equalsIgnoreCase("D")) { serviceCode = "C"; }
		else { serviceCode = "B"; }
		
		this.dataSeq = String.valueOf(dataSeq);
		this.userId = userId;
		this.userNm = userNm;
	}
	
	/**
	 * Pay전문 데이터를 생성한다.
	 * @param bankTurnCnt : 회차 정보
	 * @param scPrice        : 출금 금액
	 * @param agreeNum    : 승인번호(출금파일생성날짜(8자리) + 순번(2자리))
	 */
	public void setCmsPayCreateData(String bankTurnCnt, String scPrice, String agreeNum) {
		
		this.bankTurnCnt = bankTurnCnt;
		this.scPrice = scPrice;
		this.agreeNum = agreeNum;
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
		strBuf.append(StringUtils.rightPad(bankTurnCnt, 2, ' '));
		strBuf.append(StringUtils.rightPad(scPrice, 10, ' '));
		strBuf.append(divAccTpKey1);
		strBuf.append(divScPrice1);
		strBuf.append(divAccTpKey2);
		strBuf.append(divScPrice2);
		strBuf.append(divAccTpKey3);
		strBuf.append(divScPrice3);
		strBuf.append(divAccTpKey4);
		strBuf.append(divScPrice4);
		strBuf.append(divAccTpKey5);
		strBuf.append(divScPrice5);
		strBuf.append(divAccTpKey6);
		strBuf.append(divScPrice6);
		strBuf.append(divAccTpKey7);
		strBuf.append(divScPrice7);
		strBuf.append(divAccTpKey8);
		strBuf.append(divScPrice8);
		strBuf.append(divAccTpKey9);
		strBuf.append(divScPrice9);
		strBuf.append(divAccTpKey10);
		strBuf.append(divScPrice10);
		strBuf.append(StringUtils.rightPad(realPayAgreeDate, 8, ' '));
		strBuf.append(StringUtils.rightPad(realPayAgreePrice, 10, ' '));
		strBuf.append(StringUtils.rightPad(payTaxPrice , 6, ' '));
		strBuf.append(StringUtils.rightPad(agreeNum, 10, ' '));
		strBuf.append(taxYn);
		strBuf.append(StringUtils.rightPad(space6, 6, ' '));
		strBuf.append(serviceCode);
		strBuf.append(procCode);
		strBuf.append(resultCode);
		strBuf.append(StringUtils.rightPad(resultMsg, 30, ' '));
		strBuf.append(StringUtils.rightPad(userMam, 20, ' '));
		strBuf.append("\r\n");
		
		return strBuf.toString();
	}
	
}
