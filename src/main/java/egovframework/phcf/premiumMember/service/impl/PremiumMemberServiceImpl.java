package egovframework.phcf.premiumMember.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.premiumMember.service.PremiumMemberService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


/**
 * 유료멤버십 서비스 구현 클래스
 * @author	김량래
 * @since	2019-11-11
 * */

@Service("PremiumMemberService")
public class PremiumMemberServiceImpl extends EgovAbstractServiceImpl implements PremiumMemberService {

	@Resource(name="PremiumMemberDAO")
	private PremiumMemberDAO dao;

	@Override
	public List<HashMap<String, Object>> selectMembershipRegList(HashMap<String, String> paramMap) throws Exception {
		return dao.selectMembershipRegList(paramMap);
	}
	
	@Override
	public int selectMembershipRegListCnt(HashMap<String, String> paramMap) throws Exception {
		return dao.selectMembershipRegListCnt(paramMap);
	}
	
	@Override
	public void updateMembershipStatus(HashMap<String, String> paramMap) throws Exception {
		dao.updateMembershipStatus(paramMap);
	}
	
	@Override
	public void updateMembershipGrade(HashMap<String, String> paramMap) throws Exception {
		dao.updateMembershipGrade(paramMap);
	}
}





