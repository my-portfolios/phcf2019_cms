package egovframework.phcf.util;

import java.util.HashMap;
import java.util.List;

import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.premiumMember.service.PremiumMemberService;

public class PremiumUtil {
	
	public static int getMembershipDurationYear(String membershipType, EgovCmmUseService cmmUseService) throws Exception {
//		MberManageVO mberManageVO = egovMberManageService.selectMberWithId(membershipType);
//		String membershipType = mberManageVO.getMembershipType();
		// 															맴버십 유효기간을 나타내는 코드
		List<CmmnDetailCode> codeVOList = CommonMethod.getCodeDetailVOList("PHC025", cmmUseService);
//		int year = Integer.parseInt(codeVOList.get(0).getCodeNm());
//		String checkDate = CommonMethod.checkDateCompare(startDt, "2021-06-08", "yyyy-MM-dd");
		
		return membershipType.equals("M") ? Integer.parseInt(codeVOList.get(1).getCodeNm()) 
				: Integer.parseInt(codeVOList.get(0).getCodeNm());
	}
}
