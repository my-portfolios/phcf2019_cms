package egovframework.phcf.scheduler;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

/**
 * CMS 계좌이체 회원수신 스케줄링.
 * 
 * 사전 작업내역 : 
 * 
 * 1. 3, 8, 13, 18, 23일 날 회원등록 스케줄링을 처리 한다. 
 * 		따리서 다음날인 4, 9, 14, 19, 24일 오후 12시10분에 실행 한다(설정 : applicationContext.xml)
 * 2. CMS 모듈의 batch_recv.sh 파일을 실행한다. 
 * 		Recv로 받은 파일은 전날 등록한 데이터의 결과이다.
 * 3. 데이터를 읽어서 회
 * 원등록이 정상 처리 됐는지 확인한다...??? ( 할 필요가 있을까 한다... )
 *      우선 Recv 데이터를 읽어서 DB 반영할지 여부는 차후에 고민해 보자...
 *      파일을 받는다는거 자체가.. 정상 등록 되었다는 얘기가 되겠지만...
 *      세부 오류 코드를 통해 왜 등록이 안되었는지 여부를 판단해야될 필요가 있을 듯 하다..
 * 
 * ==== 개발 정보 ====
 * 생성일 : 2018.09.04
 * 개발사 : 휴비즈ICT
 * 
 * ==== 개발 이력 ====
 *    날짜       |        변경 정보       |   변경자
 * 2018.09.04 |         최초등록       |   김경식
 * 
 * @version 1.0
 */
@Service("CmsUserRecvCronQuartz")
public class CmsUserRecvCronQuartz extends TheBillCronQuartz {

	protected void executeInternal() {
		
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		System.out.println("== 신규회원 등록 결과를 수신 합니다. : current time = " + sdf.format(time));
		
		recvTheBillFile();
	}

}
