package egovframework.phcf.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;


/**
 * 유료 회원 만료 처리를 위한 Service
 * 매일 자정에 시행
 * 멤버십이 만료되었으면 Membership_type을 'N'(무료 회원으로 변경)
 *  Cron Expression: 0 0 0 1/1 * ? *
 * @author 김경민
 * @version 1.0
 * 
 */
@Service("CmsMembershipExpireCronQuartz")
public class CmsMembershipExpireCronQuartz {

	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;
	
	@Transactional
	public void updateMembershipTypeWhenExpire() throws Exception {
		
		System.out.println("updateMembershipTypeWhenExpire: !!");
		// 무료 회원이 아닌 모든 회원의 멤버십 관룐 정보를 가져온다.
			List<MberManageVO> membershipMberList = mberManageService.selectMberListExcept("N");
//				MberManageVO me = mberManageService.selectMberWithId("hkimkm1");
//				System.out.println("me: !!" + me.getMberId());
	//
//				if(me.getMembershipExpireDt() != null) {
//					mberManageService.updateMberTypeAfterExpire(me);
//				}
			int i = 0;
			for(MberManageVO mVO : membershipMberList) {
				/* MEMBERSHIP_EXPIRE_DT가 NULL이 아닐 때만 수행 */
				if(mVO.getMembershipExpireDt() != null) {
					System.out.println("mberId: " + mVO.getMberId());
					i++;
//					mberManageService.updateMberTypeAfterExpire(mVO);
				}
			}
			System.out.println("변경 전 유료 회원 size: " + membershipMberList.size());
			System.out.println("i: " + i);
			System.out.println("~~0시 1분 동작 완료~~");
	}
	
	
}
