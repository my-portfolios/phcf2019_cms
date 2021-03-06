package egovframework.phcf.scheduler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.phcf.nicepay.service.NicepayService;
import egovframework.phcf.scheduler.userspec.UserCreateData;
import egovframework.phcf.scheduler.userspec.UserCreateHeader;
import egovframework.phcf.scheduler.userspec.UserCreateTail;
import egovframework.phcf.scheduler.userspec.UserDeleteData;

/**
 * CMS 계좌이체 회원등록 스케줄링.
 * 
 * 사전 작업내역 : 
 * 
 * 1(이렇게 하면 안됨). 5, 10, 15, 20, 25일 날 출금을 할 수 있도록 설정 하므로 최소 2일 전에 작업을 처리 해야 된다. 
 *     실제 수행일은 3, 8, 13, 18, 23 일날 오전 11시50분에 실행 한다(설정 : applicationContext.xml)
 *     
 *     <== 위 내용대로 처리 하면 안된다.. 회원 정보 등록은 이미지 파일 전송 완료 후 처리 해야 된다.
 *          일정 시간이 지난 후 처리 하면 등록시간경과에 대한 오류가 발생 한다.
 *     
 * 1. 5분 단위로 실행한다.(AgreeSend 모듈이 데몬형으로 5분 마다 돌기 때문이다..)    
 * 2. 신규 및 수정될 대상 회원을 찾아와 MEM 파일을 만들고
 * 3. CMS 모듈의 batch_send.sh 파일을 실행한다.
 * 4. 전송 성공 여부를 체크 하여 DB 테이블에 반영 처리 한다.
 * 
 * ==== 개발 정보 ====
 * 생성일 : 2018.08.30
 * 개발사 : 휴비즈ICT
 * 
 * ==== 개발 이력 ====
 *    날짜       |        변경 정보       |   변경자
 * 2018.08.30 |         최초등록       |   김경식
 * 
 * @version 1.0
 */
@Service("CmsUserCreateCronQuartz")
public class CmsUserCreateCronQuartz extends TheBillCronQuartz {
	
	@Resource(name = "NicepayService")
	NicepayService nicepayService;
	
	protected void executeInternal() {
		
		// Autowired 작업을 위해 Context에 Injection 해야 된다..
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		// 회원 등록의 경우 매일 오전 12시에 수행 한다.
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		System.out.println("== 신규회원 등록을 시작 합니다. : current time = " + sdf.format(time));
		
		// 신규회원 등록 MEM 파일..
		File userMemFile = null;
		// 신규회원 등록 Seq
		List<String> cmsIdList = new ArrayList<String>();
		
		// 삭제회원 등록 Seq
		List<String> cmsIdDelList = new ArrayList<String>();
		
		Writer writer = null;
		try {
			
			// tb_support_cms 테이블을 조회 하여 등록 대상 회원을 가져 온다.
			// 등록 할 회원이 없다면 해당 스케줄러를 종료 한다.
			List<HashMap<String, Object>> createUserList = nicepayService.selectCreateUserList();
			if(createUserList == null || createUserList.size() < 1) {
				System.out.println("== 등록 할 신규 회원이 없습니다. : current time = " + sdf.format(System.currentTimeMillis()));
				return; 
			}
			
			// 신규회원을 등록 할 File을 만든다.
			File sendDir = new File(theBillHome, "data" + File.separator + "cms" + File.separator + "send");
			if(!sendDir.exists()) { throw new NullPointerException("== send 폴더가 존재 하지 않습니다. : current time = " + sdf.format(System.currentTimeMillis())); }
			
			// 현재 날짜..
			SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyyMMdd");
			String currentDate = currentDateFormat.format(System.currentTimeMillis());
			
			// 이용기관코드
			String dealID = EgovProperties.getProperty("dealID");
			
			userMemFile = new File(sendDir.getAbsolutePath(), currentDate + "." + dealID + ".MEM");
			// 기존에 파일이 이미 존재 하면 삭제 처리 한다.
			if(userMemFile.exists()) {
				System.out.println("== 기존 파일이 존재 합니다. 파일을 삭제 합니다. : current time = " + sdf.format(System.currentTimeMillis()));
				userMemFile.delete(); 
			}
			
			System.out.println("== 신규 회원 등록 파일을 만듭니다. : current time = " + sdf.format(System.currentTimeMillis()));
			
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userMemFile), "euc-kr"));
			
			// 실제 데이터를 쓰는 부분...
			// Header
			writer.write(new UserCreateHeader(createUserList.size()).getHeaderStr());
			
			// Data 처리...
			for(int i = 0; i < createUserList.size(); i++) {
				HashMap<String, Object> createUser = createUserList.get(i);
				
				String accTp = String.valueOf( createUser.get("acc_tp") );
				String userId = String.valueOf( createUser.get("user_id") );
				String userNm = String.valueOf( createUser.get("user_name") );
				
				String delTargetYn = String.valueOf( createUser.get("del_target_yn") );
				// 삭제 대상이 아니라면 신규 등록 전문을 만든다.
				if(delTargetYn.equalsIgnoreCase("N")) {
					
					// 신규회원 등록 완료 후 user_send_yn 테이블 변경용으로 사용함
					cmsIdList.add(String.valueOf(createUser.get("cms_id")));
					
					String userPhone = String.valueOf( createUser.get("user_phone") );
					String userEmail = String.valueOf( createUser.get("user_email") );
					
					UserCreateData userCreateData = new UserCreateData(accTp, i+1, userId, userNm, userPhone, userEmail); 
					
					if(accTp.equalsIgnoreCase("D")) {	// 정기 카드 데이터 셋팅
						
						String cardNum = String.valueOf( createUser.get("card_num") );
						String cardYYYYMM = String.valueOf( createUser.get("card_year_month") );
						
						userCreateData.setCardUserCreateData(cardNum, cardYYYYMM);
						
					} else if(accTp.equalsIgnoreCase("S")) { // 정기 CMS 데이터 셋팅
						
						String bankCode = String.valueOf( createUser.get("bank_code") );
						String bankNum = String.valueOf( createUser.get("bank_acc_num") );
						String bankAccUserNm = String.valueOf( createUser.get("bank_acc_user_nm") );
						String bankAccUserNum = String.valueOf( createUser.get("bank_acc_user_num") );
						
						userCreateData.setCmsUserCreateData(bankCode, bankNum, bankAccUserNm, bankAccUserNum);
											
					} else {
						throw new IllegalArgumentException("== 잘못된 accTp 코드 값입니다. : current time = " + sdf.format(System.currentTimeMillis()));
					}
					
					writer.write(userCreateData.getDataStr());
					continue; // 다음 for 구문 실행
				}
				
				// 삭제 대상이 이라면 회원신청 취소 전문을 만든다.
				if(delTargetYn.equalsIgnoreCase("Y")) {
					
					// 회원삭제 신청 전송 후 del_send_yn을 변경 처리 한다.
					cmsIdDelList.add(String.valueOf(createUser.get("cms_id")));
					
					UserDeleteData userDeleteData = new UserDeleteData(accTp, i+1, userId, userNm);
					
					writer.write(userDeleteData.getDataStr());
					continue; // 다음 for 구문 실행
				}
			}
			
			writer.write(new UserCreateTail(createUserList.size()).getTailStr());
			
			System.out.println("== MEM 파일 생성이 완료 되었습니다. :  current time = " + sdf.format(System.currentTimeMillis()));
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			// 파일 쓰기 종료..
			try {
				if(writer != null) writer.close();
			} catch (IOException e) { 
				e.printStackTrace();
				System.out.println("== 파일 쓰기 종료 중 오류 발생 : current time = " + sdf.format(System.currentTimeMillis()));
			}
		}
		
		// 생성된 파일을 전송 한다.
		if(!sendTheBillFile(userMemFile)) { return; }
		
		try {
			
			// user_send_yn 변경 처리
			if(cmsIdList.size() > 0) {
				// user_send_yn 정보를 업데이트 처리 한다.
				nicepayService.updateUserSendYn(cmsIdList, userMemFile.getName());
			}
			
			// del_send_yn 변경 처리
			if(cmsIdDelList.size() > 0) {
				// del_send_yn 정보를 업데이트 처리 한다.
				nicepayService.updateDelSendYn(cmsIdDelList, userMemFile.getName());
			}
			
			System.out.println("== user_send_yn 정보 변경 완료 : current time = " + sdf.format(System.currentTimeMillis()));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
