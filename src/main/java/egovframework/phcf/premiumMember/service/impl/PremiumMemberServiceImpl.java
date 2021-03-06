package egovframework.phcf.premiumMember.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.phcf.premiumMember.service.PremiumMemberService;
import egovframework.phcf.ticketLink.service.TicketLinkService;
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
	
	@Resource(name="mberManageService")
	private EgovMberManageService egovMberManageService;
	
	/** TicketLinkService */
	@Resource(name="TicketLinkService")
	private TicketLinkService ticketLinkService;

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
		MberManageVO mberManageVO = egovMberManageService.selectMberWithId(paramMap.get("ID"));
		ticketLinkService.checkJoinThenModifyMemberDetailTkLink(mberManageVO);
	}
	
	/* 프리미엄 멤버 조회*/
	@Override
	public List<MberManageVO> selectMembershipList(UserDefaultVO userSearchVO) throws Exception {
		return dao.selectMembershipList(userSearchVO);
		
	}
	
	/**
	 * id로 유저의 멤버십 정보 선택
	 * */
	@Override
	public HashMap<String, Object> selectMemberInfoList(String id) throws Exception {
		return dao.selectMemberInfoList(id);
	}
	
}





