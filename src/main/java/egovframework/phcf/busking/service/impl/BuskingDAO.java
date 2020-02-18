package egovframework.phcf.busking.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.phcf.busking.BuskingGroupVO;

@Repository("BuskingDAO")
public class BuskingDAO extends EgovComAbstractDAO{
	
	public List<HashMap<String, Object>> selectBuskingGroupRegList(BuskingGroupVO buskingGroupVO) {
		return selectList("BuskingDAO.selectBuskingGroupRegList", buskingGroupVO);
	}
	public int selectBuskingGroupRegDefaultCnt(BuskingGroupVO buskingGroupVO) {
		return selectOne("BuskingDAO.selectBuskingGroupRegDefaultCnt",buskingGroupVO);
	}
	public void updateGroupApprove(HashMap<String, String> paramMap) {
		update("BuskingDAO.updateGroupApprove",paramMap);
	}
	
}
