package egovframework.phcf.premiumMember.service;

import java.util.HashMap;
import java.util.List;

import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;


/**
 * 유료멤버십 관련 서비스
 * @author	김량래
 * @since	2019-11-11
 * */

public interface PremiumMemberService {

	public List<HashMap<String, Object>> selectMembershipRegList(HashMap<String, String> paramMap) throws Exception;
	public int selectMembershipRegListCnt(HashMap<String, String> paramMap) throws Exception;
	public void updateMembershipStatus(HashMap<String, String> paramMap) throws Exception;
	public void updateMembershipGrade(HashMap<String, String> paramMap) throws Exception;
	public List<MberManageVO> selectMembershipList(UserDefaultVO userSearchVO) throws Exception;
}
