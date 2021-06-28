package egovframework.phcf.scheduler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.cop.ems.service.EgovSndngMailRegistService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * 유료 회원 만료 처리를 위한 Service
 * <p>매일 자정에 시행
 * <p>멤버십이 만료되었으면 Membership_type을 'N'(무료 회원으로 변경)
 * <p> Cron Expression: 0 1 0 1/1 * ? *
 * 
 * @author 김경민
 * @version 1.0 (2021-06-22)
 * 
 */
@Service("CmsMembershipExpireCronQuartz")
public class CmsMembershipExpireCronQuartz {

	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;
	
	/** EgovSndngMailRegistService */
	@Resource(name = "sndngMailRegistService")
	private EgovSndngMailRegistService sndngMailRegistService;
	
	// email regular expression
	private String regEx = "^[0-9a-zA-Z]([-_\\.0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
	
	@Transactional
	public void updateMembershipTypeWhenExpire() throws Exception {
		
		System.out.println("updateMembershipTypeWhenExpire: !!");
		// 무료 회원이 아닌 모든 회원의 멤버십 관룐 정보를 가져온다.
			List<MberManageVO> membershipMberList = mberManageService.selectMberListExcept("N");
			
			int i = 0;
			for(MberManageVO mVO : membershipMberList) {
				/* MEMBERSHIP_EXPIRE_DT가 NULL이 아닐 때만 멤버십 만료 여부를 판단할 수 있다. */
				if(mVO.getMembershipExpireDt() != null) {
					System.out.println("mberId: " + mVO.getMberId());
					i++;
					mberManageService.updateMberTypeAfterExpire(mVO);
				}
			}
			System.out.println("변경 전 유료 회원 size: " + membershipMberList.size());
			System.out.println("expier_dt not null : " + i);
			System.out.println("~~0시 1분 동작 완료~~");
//			test();
	}
	// email match test
	/*public void test() {
		UserDefaultVO uVO = new UserDefaultVO();
		uVO.setFirstIndex(-1);
		try {
			List<?> membershipMberList = mberManageService.selectMberList(uVO);
			int size = membershipMberList.size();
//			System.out.println("match or not: "+Pattern.matches(regEx, "hkimkm1@hubizict.com"));
			for(int i = 0; i < size; i ++) {
				EgovMap mber = (EgovMap)membershipMberList.get(i);
				
				if(!Pattern.matches(regEx, (String) mber.get("emailAdres"))){
					System.out.println("mberId from table: " + mber.get("emailAdres"));
					System.out.println("not match!!");
				}
//				System.out.println("match or not: "+(Pattern.matches(regEx, (String) mber.get("emailAdres")) 
//						|| !(mber.get("emailAdres").toString().length() > 0) 
//						)
//						); 
			}
			System.out.println("number of all members: " + size);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@Transactional
	public void sendMailNoticeExpire() {
		try {
			int daysBefore = 30;
			String dateFormat = "yyyy-MM-dd";
			String mailSubject = "[포항문화재단] 멤버십 만료 예정 안내 메일";
			
			CommonMethod commonMethod = new CommonMethod();
			
			/*
			 * 멤버십 만료 전까지 30일이 남지 않은 회원 목록을 조회한다. 
			 * */
			Map<String, Object> paramMap = new HashMap<>();
			// 만료 전까지 30일이 남지 않은 남은 멤버들의 목록 조회
			paramMap.put("daysBefore", daysBefore);
			List<MberManageVO> mberNearExpireList = mberManageService.selectMberNearExpireList(paramMap);
			System.out.println("mberNearExpireList sie: " + mberNearExpireList.size());
			
			/*
			 * 메일 양식 작성
			 * */
			
			SndngMailVO sndngMailVO = new SndngMailVO();
			// 이메일 제목
			sndngMailVO.setSj(mailSubject);
			// 이메일 내용
			sndngMailVO.setEmailCn(commonMethod.getFileContent("egovframework/mail/rest_guide.html"));
			// 발신자
			sndngMailVO.setDsptchPerson("phcf01");
			// 첨부파일 경로
			sndngMailVO.setFileStreCours("");
			
			
			/*String mberEmailAdres = "hkkyoungmin@gmail.com";
			if(!mberEmailAdres.equals("") && mberEmailAdres != null &&  
					Pattern.matches(regEx
							, mberEmailAdres)) {
				
				sndngMailVO.setRecptnPerson(mberEmailAdres.toString());
				System.out.println("==== mail address: " + mberEmailAdres.toString());
				// 메일 발송 및 등록
//				sndngMailRegistService.insertSndngMail(sndngMailVO);
				System.out.println("=== mail send complete");
			}*/
			// 각 멤버에게 메일 발송	
			for(MberManageVO mber : mberNearExpireList) {
				
				String mailSendingDay = CommonMethod.calcDate(mber.getMembershipExpireDt(),
						Calendar.DATE, -daysBefore, dateFormat);
				
				System.out.println("mailSendingDay: " + mailSendingDay);
				//메일을 보내야할 날짜가 오늘이 맞는지 체크(각 회원마다 안내 메일은 한 번만 보내야 한다.)
				if(mailSendingDay.equals(CommonMethod.getTodayDate(dateFormat))) {
				
					System.out.println("==== mberId: "+mber.getMberId());
					String mberEmailAdres = mber.getMberEmailAdres();
					// 유효한 메일인지 검사
					if(!mberEmailAdres.equals("") && mberEmailAdres != null &&  
							Pattern.matches(regEx, mberEmailAdres)) {
						
						// 수신자 설정
						sndngMailVO.setRecptnPerson(mberEmailAdres.toString());
						System.out.println("==== emailAdres: " + mberEmailAdres.toString());
						System.out.println("===expireDT: "+mber.getMembershipExpireDt());
						
						// 메일 발송 및 등록
//						sndngMailRegistService.insertSndngMail(sndngMailVO);
						
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
