package egovframework.phcf.indiplus.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.indiplus.service.IndiplusService;
import egovframework.phcf.premiumMember.service.PremiumMemberService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("IndiplusService")
public class IndiplusServiceImpl extends EgovAbstractServiceImpl implements IndiplusService {

	@Resource(name="IndiplusDAO")
	private IndiplusDAO dao;

	@Override
	public List<HashMap<String, Object>> selectRestDay(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectRestDay(paramMap);
	}
	
	@Override
	public int selectRestDayCnt() throws Exception {
		return dao.selectRestDayCnt();
	}

	@Override
	public void insertRestDay(String date) throws Exception {
		dao.insertRestDay(date);
	}
	
	@Override
	public void updateRestDay(String seq, String date) throws Exception {
		dao.updateRestDay(seq, date);
	}

	
}





