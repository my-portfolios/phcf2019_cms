package egovframework.phcf.busking.service;

import java.util.HashMap;
import java.util.List;

import egovframework.phcf.busking.BuskingGroupVO;

public interface BuskingService {
	public List<HashMap<String, Object>> selectBuskingGroupRegList(BuskingGroupVO buskingGroupVO) throws Exception;
	public int selectBuskingGroupRegDefaultCnt(BuskingGroupVO buskingGroupVO) throws Exception;
	public void updateGroupApprove(HashMap<String, String> paramMap);

}
