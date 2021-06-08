package egovframework.phcf.premiumMember.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;

/**
 * 유료멤버십 관련 DAO 클래스
 * @author	김량래
 * @since	2019-11-11
 **/

@Repository("PremiumMemberDAO")
public class PremiumMemberDAO extends EgovComAbstractDAO{
	public List<HashMap<String, Object>> selectMembershipRegList(HashMap<String, String> paramMap) {
		return selectList("PremiumMemberDAO.selectMembershipRegList", paramMap);
	}
	
	public int selectMembershipRegListCnt(HashMap<String, String> paramMap) {
		return (int) selectOne("PremiumMemberDAO.selectMembershipRegListCnt", paramMap);
	}

	public void updateMembershipStatus(HashMap<String, String> paramMap) {
		update("PremiumMemberDAO.updateMembershipStatus", paramMap);
	}

	public void updateMembershipGrade(HashMap<String, String> paramMap) {
		update("PremiumMemberDAO.updateMembershipGrade", paramMap);
	}
	
	public List<MberManageVO> selectMembershipList(UserDefaultVO userSearchVO) throws Exception {
		return selectList("PremiumMemberDAO.selectMembershipList");
		
	}
}
