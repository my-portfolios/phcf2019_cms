package egovframework.phcf.busking.service;

import java.util.HashMap;
import java.util.List;

import egovframework.phcf.busking.BuskingGroupVO;

public interface BuskingService {
	public List<HashMap<String, Object>> selectBuskingGroupRegList(BuskingGroupVO buskingGroupVO) throws Exception;
	public int selectBuskingGroupRegDefaultCnt(BuskingGroupVO buskingGroupVO) throws Exception;
	public void updateGroupApprove(HashMap<String, String> paramMap);
	public void deleteBusking(HashMap<String, String> paramMap);
	public int selectBuskingStageRegDefaultCnt(HashMap<String, Object> paramMap);
	public List<HashMap<String, Object>> selectBuskingStageRegList(HashMap<String, Object> paramMap);
	public void updateApproveMulti(HashMap<String, Object> paramMap);
	public void insertBuskingStageReg(HashMap<String, Object> paramMap);
}
