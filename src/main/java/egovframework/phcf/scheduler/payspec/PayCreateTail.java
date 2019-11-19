package egovframework.phcf.scheduler.payspec;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.cmm.service.EgovProperties;

/**
 * 회원신청 전문 생성용 Tail 부문 클래스
 * 
 *  변경될 항목이 없다... 그냥 고정으로 박아 넣고 쓰자....
 *  
 * @author 김경식
 */
public class PayCreateTail {
	
	String recordTp = "T";											// 1자리		Record구분 : TAIL RECORD
	String dealID = EgovProperties.getProperty("dealID");			// 8자리 	거래사ID(필수) : 이용 거래사 ID
	String dealGroup = " ";											// 1자리 	거래사그룹여부 : 그룹거래사 ID 사용여부 space 이거나 'G'
	String payApplyCnt = "";										// 6자리 	출금신청건수 : 총신청건수(DATA부분건수)
	String payApplyPrice = "";										// 15자리	출금신청금액 : 총신청금액(DATA부분금액)
	String applySuccessCnt = "";									// 6자리		출금신청성공건수 : 출금신청성공건수 -> 나이스 입력
	String applySuccessPrice = "";									// 15자리	출금신청성공금액 : 출금신청성공금액 -> 나이스 입력
	String space236 = "";											// 236자리	SPACE
	String procResult = " ";										// 1자리		처리결과 : Y:정상, N:오류 -> 나이스 입력
	String procResultCode = "    ";									// 4자리		결과코드 : T*** -> 나이스 입력 -> 오류코드표 참조
	String procResultMsg = "";										// 30자리	결과메세지 : 결과코드의 메시지 -> 나이스 입력 -> 오류코드표 참조
	String space20 = "";											// 20자리	SPACE
	
	public PayCreateTail(int payApplyCnt, int payApplyPrice) {
		this.payApplyCnt = String.valueOf( payApplyCnt );
		this.payApplyPrice = String.valueOf( payApplyPrice );
	}
	
	public String getTailStr() {
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(recordTp);
		strBuf.append(dealID);
		strBuf.append(dealGroup);
		strBuf.append(StringUtils.leftPad(payApplyCnt, 6, '0'));
		strBuf.append(StringUtils.leftPad(payApplyPrice, 15, '0'));
		strBuf.append(StringUtils.leftPad(applySuccessCnt, 6, ' '));
		strBuf.append(StringUtils.leftPad(applySuccessPrice, 15, ' '));
		strBuf.append(StringUtils.leftPad(space236, 236, ' '));
		strBuf.append(procResult);
		strBuf.append(procResultCode);
		strBuf.append(StringUtils.leftPad(procResultMsg, 30, ' '));
		strBuf.append(StringUtils.leftPad(space20, 20, ' '));
		strBuf.append("\r\n");
		
		return strBuf.toString();
		
	}
}
