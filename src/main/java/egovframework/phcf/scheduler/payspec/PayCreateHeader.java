package egovframework.phcf.scheduler.payspec;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.cmm.service.EgovProperties;

/**
 * 회원신청 전문 생성용 헤더 데이터 클래스
 *  변경될 항목이 없다... 그냥 고정으로 박아 넣고 쓰자....
 * @author 김경식
 */
public class PayCreateHeader {
	
	String recordTp = "H";											// 1자리		Record 구분(필수)
	String dealID = EgovProperties.getProperty("dealID");			// 8자리 	거래사ID(필수) 8자리
	String dealGroup = " ";											// 1자리 	거래사그룹여부 : space 이거나 'G'
	String payApplyCnt = "";										// 6자리 	출금신청건수(필수) : 총신청건수(DATA 부분건수)
	String payApplyPrice = "";										// 15자리	출금신청금액(필수) : 총신청금액(DATA 부분금액)
	String space257 = "";											// 257자리 공백 -> leftpad ' '
	String procResult = " ";										// 1자리 	처리결과(나이스입력 : Y->정상, N->오류)
	String resultCode = "    ";										// 4자리		결과코드 : H*** -> 나이스 입력	
	String resultMsg = "";											// 30자리 	결과코드의 메시지(나이스입력) -> leftpad ' '
	String space20 = "";											// 20자리 	공백 -> leftpad ' '
	
	/**
	 * 클래스 생성자 입력할 대상 회원수가 필수 항목이여야 한다.
	 * @param createUserCnt
	 */
	public PayCreateHeader(int payApplyCnt, int payApplyPrice) {
		this.payApplyCnt = String.valueOf(payApplyCnt);
		this.payApplyPrice = String.valueOf(payApplyPrice);
	}

	public String getHeaderStr() {
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(recordTp);
		strBuf.append(dealID);
		strBuf.append(dealGroup);
		strBuf.append(StringUtils.leftPad(payApplyCnt, 6, '0'));
		strBuf.append(StringUtils.leftPad(payApplyPrice, 15, '0'));
		strBuf.append(StringUtils.leftPad(space257, 257, ' '));
		strBuf.append(procResult);
		strBuf.append(resultCode);
		strBuf.append(StringUtils.leftPad(resultMsg, 30, ' '));
		strBuf.append(StringUtils.leftPad(space20, 20, ' '));
		strBuf.append("\r\n");
		
		return strBuf.toString();
		
	}
}
