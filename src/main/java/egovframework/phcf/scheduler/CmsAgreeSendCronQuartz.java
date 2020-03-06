package egovframework.phcf.scheduler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.phcf.nicepay.service.NicepayService;

/**
 * CMS 계좌이체 CMS자동이체 동의서 전송 스케줄링.
 * 
 * 사전 작업내역 : 
 * 
 * 1. Demon형으로 실행 되는 모듈에서 CMS이체동의서 Img 파일을 전송 한다.
 * 2. 전송 결과인 send_ok 폴더를 스케줄링 체크 하여 이체 동의 여부(tb_support_cms)를 업데이트 한다.
 * 3. 폴더 체크 확인은 5분 단위로 수행 한다.
 * 
 * ==== 개발 정보 ====
 * 생성일 : 2018.09.14
 * 개발사 : 휴비즈ICT
 * 
 * ==== 개발 이력 ====
 *    날짜       |        변경 정보       |   변경자
 * 2018.09.14 |         최초등록       |   김경식
 * 
 * @version 1.0
 */
@Service("CmsAgreeSendCronQuartz")
public class CmsAgreeSendCronQuartz extends TheBillCronQuartz {
	
	@Resource(name = "NicepayService")
	NicepayService nicepayService;
	
	protected void executeInternal() {
		
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		System.out.println("===== CmsAgreeSendCronQuartz ===== \n");
		System.out.println("=============== CMS이체 동의서 전송 내역 체크를 시작 합니다. : current time = " + sdf.format(time));
		
		try {
			
			// send_ok 폴더를 조회해 와서 목록을 가지고 있자..
			// send_ok 폴더가 없다면 굳이 돌필요가 없네..
			SimpleDateFormat sdfDay = new SimpleDateFormat("yyyyMMdd");
			File sendOkDir = new File(theBillHome + File.separator + "data" + File.separator + "agree" + File.separator + "send_ok" + File.separator + sdfDay.format(time));
			if(!sendOkDir.exists()) {
				System.out.println("== 체크 폴더가 없습니다. Quartz를 종료 합니다. : current time = " + sdf.format(time));
				return;
			}
			
			// sendOkDir 안에 파일들을 가져 온다.
			File[] sendOkFileArr = sendOkDir.listFiles();
			if(sendOkFileArr == null || sendOkFileArr.length < 1) {
				System.out.println("== 체크할 파일이 없습니다. Quartz를 종료 합니다. : current time = " + sdf.format(time));
				return;
			}
			
			List<String> fileUserNameList = new ArrayList<String>();
			for(File sendOkFile : sendOkFileArr) {
				// 파일 이름이 상점ID.유저명.1.jpg 형태 일 것이다.
				// 유저명 만 가져오면 된다.
				String[] fileNameSplit = StringUtils.split(sendOkFile.getName(), '.');
				
				fileUserNameList.add(fileNameSplit[1]);
			}
			
			// 점검 대상 리스트를 가져온다.
			List<HashMap<String, Object>> checkList = nicepayService.selectAgreeCheckList();
			if(checkList == null || checkList.isEmpty()) { 
				System.out.println("== 체크 리스트가 없습니다. Quartz를 종료 합니다. : current time = " + sdf.format(time));
				return;
			}
			
			// for구문 돌면서 처리 하지 말자
			// update 할 내용이 많다면 서버에 부하를 줄수도 있다..
			List<String> updateTargetList = new ArrayList<String>();
			
			// 점검 대상 리스트가 있다면 순차적으로 돌면 send_ok 폴더를 조회 하여
			for(HashMap<String, Object> checkObj : checkList)  {
				String userId = String.valueOf( checkObj.get("user_id") );
				// fileUserNameList에 해당 유저의 값이 있다면
				// tb_surpport_cms 테이블에 agree_send_yn 값을 'Y'로 입력 처리 한다.
				// 없다면 안하면 되지.....
				if(!fileUserNameList.contains(userId)) { continue; }
				
				updateTargetList.add(userId);
			}
			
			if(updateTargetList.isEmpty()) {
				System.out.println("== 업데이트할 리스트가 없습니다. Quartz를 종료 합니다. : current time = " + sdf.format(time));
				return;
			}
			
			nicepayService.updateAgreeSendYn( updateTargetList.toArray(new String[updateTargetList.size()]) );
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
