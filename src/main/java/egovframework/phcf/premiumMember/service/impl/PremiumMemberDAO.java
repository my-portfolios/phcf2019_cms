package egovframework.phcf.premiumMember.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

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
}
