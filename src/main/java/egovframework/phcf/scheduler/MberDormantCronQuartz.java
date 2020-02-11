package egovframework.phcf.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.cop.ems.service.EgovSndngMailRegistService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.com.cop.ems.service.impl.SndngMailRegistDAO;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;


@Service("MberDormantCronQuartz")
public class MberDormantCronQuartz extends TheBillCronQuartz {
	
	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;
	
	/** EgovSndngMailRegistService */
	@Resource(name = "sndngMailRegistService")
	private EgovSndngMailRegistService sndngMailRegistService;
	
	@Transactional
	public void transferToDormant() throws Exception {
		System.out.println("transferToDormanttransferToDormanttransferToDormanttransferToDormant");
		/*
		 * 1년이 지난 회원들의 목록을 가져온다.
		 * */
		List<MberManageVO> mberDormantList = mberManageService.getDormantMber();
		
		/*
		 * 1년이 지난 회원들을 휴먼회원 테이블이 저장시킨다.
		 * 휴먼회원 테이블에 저장된 회원들의 회원테이블의 정보를 삭제하고 회원상태를 변경시킨다.
		 * */
		System.out.println("mberDormantList.size()mberDormantList.size()mberDormantList.size()"+mberDormantList.size());
		for(MberManageVO mBervo : mberDormantList) {
			System.out.println("mBervomBervomBervomBervomBervo"+mBervo.getMberId());
			mberManageService.transferDormantMber(mBervo);
			mberManageService.updatetransferedDormantMberCode(mBervo);
		}
		
		/*
		 * 안내메일을 발송한다.
		 * 
		 * */
		SndngMailVO sndngMailVO = new SndngMailVO();
		sndngMailVO.setSj("휴먼회원 전환되었음");
		sndngMailVO.setEmailCn("휴먼회원 전환되었음:내용");
		sndngMailVO.setDsptchPerson("phcf01");
		sndngMailVO.setFileStreCours("");
		for(MberManageVO mBervo : mberDormantList) {
			System.out.println("=== mBervo.getMberEmailAdres()"+mBervo.getMberEmailAdres());
			if(mBervo.getMberEmailAdres() != null && !mBervo.getMberEmailAdres().equals("") && 
					Pattern.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",mBervo.getMberEmailAdres())) {
				sndngMailVO.setRecptnPerson(mBervo.getMberEmailAdres());
				sndngMailRegistService.insertSndngMail(sndngMailVO);
			}
				
		}
	}
	
}
