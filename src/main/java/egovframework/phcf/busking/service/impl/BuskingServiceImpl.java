package egovframework.phcf.busking.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.busking.BuskingGroupVO;
import egovframework.phcf.busking.service.BuskingService;


@Service("BuskingService")
public class BuskingServiceImpl implements BuskingService {

	@Resource(name="BuskingDAO")
	private BuskingDAO dao;
	
	@Override
	public List<HashMap<String, Object>> selectBuskingGroupRegList(BuskingGroupVO buskingGroupVO) throws Exception {
		return dao.selectBuskingGroupRegList(buskingGroupVO);
	}

	@Override
	public int selectBuskingGroupRegDefaultCnt(BuskingGroupVO buskingGroupVO) throws Exception {
		return dao.selectBuskingGroupRegDefaultCnt(buskingGroupVO);
	}

	@Override
	public void updateGroupApprove(HashMap<String, String> paramMap) {
		dao.updateGroupApprove(paramMap);
	}

	@Override
	public void deleteBusking(HashMap<String, String> paramMap) {
		dao.deleteBusking(paramMap);
	}

	@Override
	public int selectBuskingStageRegDefaultCnt(HashMap<String, Object> paramMap) {
		return dao.selectBuskingStageRegDefaultCnt(paramMap);
	}

	@Override
	public List<HashMap<String, Object>> selectBuskingStageRegList(HashMap<String, Object> paramMap) {
		return dao.selectBuskingStageRegList(paramMap);
	}

	@Override
	public void updateApproveMulti(HashMap<String, Object> paramMap) {
		dao.updateApproveMulti(paramMap);
	}

	@Override
	public void insertBuskingStageReg(HashMap<String, Object> paramMap) {
		dao.insertBuskingStageReg(paramMap);
	}

	
}
