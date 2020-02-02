package egovframework.phcf.scheduler;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;


@Service("MberDormantCronQuartz")
public class MberDormantCronQuartz extends TheBillCronQuartz {
	
	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;
	
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
		
		
	}
	
}
