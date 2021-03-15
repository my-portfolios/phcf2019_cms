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
import egovframework.phcf.hubizCommonMethod.CommonMethod;


@Service("MberDormantCronQuartz")
public class MberDormantCronQuartz {
	
	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;
	
	/** EgovSndngMailRegistService */
	@Resource(name = "sndngMailRegistService")
	private EgovSndngMailRegistService sndngMailRegistService;
	
	@Transactional
	public void checkMemberList() throws Exception {
		
		try {
			CommonMethod commonMethod = new CommonMethod();
			
			/*
			 * 365일 동안 로그인 하지 않은 회원들의 목록을 가져온다.
			 * */
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("rangeDays", 365);
			List<Map<String, Object>> mberDormantList = mberManageService.selectNotLoggedMberList(paramMap);
			
			
			/*
			 * 365일이 지난 회원들을 휴면회원 예정 테이블에 저장시킨다.
			 * 안내 메일을 발송한다.
			 * */
			
			SndngMailVO sndngMailVO = new SndngMailVO();
			sndngMailVO.setSj("[포항문화재단] 휴먼회원 전환 예정 안내 메일");
			sndngMailVO.setEmailCn(commonMethod.getFileContent("egovframework/mail/rest_guide.html"));
			sndngMailVO.setDsptchPerson("phcf01");
			sndngMailVO.setFileStreCours("");
	
			for(Map<String, Object> mber : mberDormantList) {
				Map<String, Object> paramMap2 = new HashMap<>();
				paramMap2.put("mberId", mber.get("mberId").toString());
				List<Map<String, Object>> dormantReserveList = mberManageService.selectDormantReserveMemberList(paramMap2);
				if(dormantReserveList.size() > 0) { continue; }
				
				System.out.println("mBervomBervomBervomBervomBervo"+mber.get("mberId"));
				
				if(mber.get("mberEmailAdres") != null && !mber.get("mberEmailAdres").equals("") && 
						Pattern.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", mber.get("mberEmailAdres").toString())) {
					
					sndngMailVO.setRecptnPerson(mber.get("mberEmailAdres").toString());
					System.out.println("==== checkMemberList" + mber.get("mberEmailAdres").toString());
					sndngMailRegistService.insertSndngMail(sndngMailVO);
					
					MberManageVO mberManageVO = new MberManageVO();
					mberManageVO.setMberId(mber.get("mberId").toString());
					mberManageService.insertDormantReserveMember(mberManageVO); // 휴면 회원 대상자로 삽입
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Transactional
	public void transferToDormant() throws Exception {
		
		try {
			CommonMethod commonMethod = new CommonMethod();
			
			/*
			 * 휴면 예정인 회원의 정보를 가져온다.
			 * */
			Map<String, Object> paramMap = new HashMap<>();
			List<Map<String, Object>> dormantReserveMemberList = mberManageService.selectDormantReserveMemberList(paramMap);
			
			/*
			 * 휴먼회원 전환 테이블에 저장된 회원들의 정보를 삭제하고 회원상태를 변경시킨다.
			 * 휴면 전환 메일을 보낸다
			 * */
			SndngMailVO sndngMailVO = new SndngMailVO();
			sndngMailVO.setSj("[포항문화재단] 휴먼회원 전환 안내 메일");
			sndngMailVO.setEmailCn(commonMethod.getFileContent("egovframework/mail/email_rest.html"));
			sndngMailVO.setDsptchPerson("phcf01");
			sndngMailVO.setFileStreCours("");
	
			for(Map<String, Object> dormantReserveMember : dormantReserveMemberList) {
				Map<String, Object> paramMap2 = new HashMap<>();
				paramMap2.put("seq", Integer.parseInt(dormantReserveMember.get("SEQ").toString()));
				
				String createdDt = dormantReserveMember.get("createdDt").toString();
				String format = "yyyy-MM-dd";
				// 휴면 메일 발송일로 부터 15일 뒤 작업 실행 
				if(CommonMethod.checkDateCompare(CommonMethod.getTodayDate(format), CommonMethod.calcDate(createdDt, Calendar.DATE, 15, format), format).equals("small")) continue;
				
				MberManageVO mBervo = mberManageService.selectMberWithId(dormantReserveMember.get("MBER_ID").toString());
				if(!mBervo.getMberSttus().equals("P")) { continue; } // 회원 상태가 정상이 아니면 다음 차례로 이동
				
				if(mBervo.getMberEmailAdres() != null && !mBervo.getMberEmailAdres().equals("") && 
						Pattern.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",mBervo.getMberEmailAdres())) {
					sndngMailVO.setRecptnPerson(mBervo.getMberEmailAdres());
					System.out.println("==== transferToDormant" + mBervo.getMberEmailAdres());
					sndngMailRegistService.insertSndngMail(sndngMailVO);
					
					mberManageService.transferDormantMber(mBervo); // 회원 데이터를 휴면 회원 테이블로 이전
					mberManageService.updateMberToDormant(mBervo); // 회원 상태 휴면회원으로 변경 및 개인정보 삭제
					mberManageService.deleteDormantReserveMemberList(paramMap2); // 휴면 회원 대상자 테이블에서 삭제
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
