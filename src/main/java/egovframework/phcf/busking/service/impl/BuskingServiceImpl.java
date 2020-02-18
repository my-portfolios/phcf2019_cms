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

	

	
	
}
