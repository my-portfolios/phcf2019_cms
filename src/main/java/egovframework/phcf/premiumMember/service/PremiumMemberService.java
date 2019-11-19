package egovframework.phcf.premiumMember.service;

import java.util.HashMap;
import java.util.List;


/**
 * 유료멤버십 관련 서비스
 * @author	김량래
 * @since	2019-11-11
 * */

public interface PremiumMemberService {

	public List<HashMap<String, String>> selectMembershipRegList(HashMap<String, String> paramMap) throws Exception;
	public void updateMembershipStatus(HashMap<String, String> paramMap) throws Exception;
}
