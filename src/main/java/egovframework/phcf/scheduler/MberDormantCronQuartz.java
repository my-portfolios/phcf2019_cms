package egovframework.phcf.scheduler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
public class MberDormantCronQuartz extends TheBillCronQuartz {
	
	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;
	
	/** EgovSndngMailRegistService */
	@Resource(name = "sndngMailRegistService")
	private EgovSndngMailRegistService sndngMailRegistService;
	
	@Transactional
	public void checkMemberList() throws Exception {
		CommonMethod commonMethod = new CommonMethod();
		
		/*
		 * 1년이 지난 회원들의 목록을 가져온다.
		 * */
		List<MberManageVO> mberDormantList = mberManageService.getDormantMber();
		
		
		/*
		 * 6개월이 지난 회원들을 휴면회원 예정 테이블에 저장시킨다.
		 * 안내 메일을 발송한다.
		 * */
		
		SndngMailVO sndngMailVO = new SndngMailVO();
		sndngMailVO.setSj("[포항문화재단] 휴먼회원 전환 예정 안내 메일");
		sndngMailVO.setEmailCn(commonMethod.getFileContent("egovframework/mail/rest_guide.html"));
		sndngMailVO.setDsptchPerson("phcf01");
		sndngMailVO.setFileStreCours("");
		
		System.out.println("mberDormantList.size()mberDormantList.size()mberDormantList.size()"+mberDormantList.size());
		for(MberManageVO mBervo : mberDormantList) {
			System.out.println("mBervomBervomBervomBervomBervo"+mBervo.getMberId());
			int result = mberManageService.insertDormantReserveMember(mBervo.getMberId());
			if(result >= 1) {
				if(mBervo.getMberEmailAdres() != null && !mBervo.getMberEmailAdres().equals("") && 
						Pattern.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",mBervo.getMberEmailAdres())) {
					sndngMailVO.setRecptnPerson(mBervo.getMberEmailAdres());
					sndngMailRegistService.insertSndngMail(sndngMailVO);
				}
			}
		}
	}
	
	
	@Transactional
	public void transferToDormant() throws Exception {
		System.out.println("transferToDormanttransferToDormanttransferToDormanttransferToDormant");
		CommonMethod commonMethod = new CommonMethod();
		
		/*
		 * 휴면 예정인 회원의 정보를 가져온다.
		 * */
		List<HashMap<String, Object>> dormantReserveMemberList = mberManageService.selectDormantReserveMemberList();
		
		/*
		 * 휴먼회원 테이블에 저장된 회원들의 회원테이블의 정보를 삭제하고 회원상태를 변경시킨다.
		 * 휴면 전환 메일을 보낸다
		 * */
		SndngMailVO sndngMailVO = new SndngMailVO();
		sndngMailVO.setSj("[포항문화재단] 휴먼회원 전환 안내 메일");
		sndngMailVO.setEmailCn(commonMethod.getFileContent("egovframework/mail/email_rest.html"));
		sndngMailVO.setDsptchPerson("phcf01");
		sndngMailVO.setFileStreCours("");

		for(HashMap<String, Object> dormantReserveMember : dormantReserveMemberList) {
			String createdDt = dormantReserveMember.get("createdDt").toString();
			String format = "yyyy-MM-dd";
			// 휴면 메일 발송일로 부터 15일 뒤 작업 실행 
			if(CommonMethod.checkDateCompare(CommonMethod.getTodayDate(format), CommonMethod.calcDate(createdDt, Calendar.DATE, 15, format), format).equals("small")) continue;
			
			System.out.println("dormantReserveMember  "+ dormantReserveMember);
			MberManageVO mBervo = mberManageService.selectMberWithId(dormantReserveMember.get("MBER_ID").toString());
			
			mberManageService.transferDormantMber(mBervo);
			mberManageService.updatetransferedDormantMberCode(mBervo);
			
			if(mBervo.getMberEmailAdres() != null && !mBervo.getMberEmailAdres().equals("") && 
					Pattern.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",mBervo.getMberEmailAdres())) {
				sndngMailVO.setRecptnPerson(mBervo.getMberEmailAdres());
				sndngMailRegistService.insertSndngMail(sndngMailVO);
			}
			
			mberManageService.deleteDormantReserveMemberList(Integer.parseInt(dormantReserveMember.get("SEQ").toString()));
		}
	}
	
}
