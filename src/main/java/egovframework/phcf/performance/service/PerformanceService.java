package egovframework.phcf.performance.service;

import java.util.HashMap;
import java.util.List;

public interface PerformanceService {

	public List<HashMap<String, Object>> selectPerformanceApplierList(HashMap<String, Object> paramMap) throws Exception;
	public int selectPerformanceApplierListCnt(HashMap<String, Object> paramMap) throws Exception;
	public void updatePerformanceApplierItem(HashMap<String, Object> paramMap) throws Exception;
	List<HashMap<String, Object>> selectAppliedVisitorPerformanceList(HashMap<String, Object> paramMap) throws Exception;
}
