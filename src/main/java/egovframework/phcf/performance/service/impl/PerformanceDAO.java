package egovframework.phcf.performance.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("PerformanceDAO")
public class PerformanceDAO extends EgovComAbstractDAO {

	public List<HashMap<String, Object>> selectPerformanceApplierList(HashMap<String, Object> paramMap) {
		return selectList("PerformanceDAO.selectPerformanceApplierList", paramMap);
	}

	public int selectPerformanceApplierListCnt(HashMap<String, Object> paramMap) {
		return selectOne("PerformanceDAO.selectPerformanceApplierListCnt", paramMap);
	}
	
	public void updatePerformanceApplierItem(HashMap<String, Object> paramMap) {
		update("PerformanceDAO.updatePerformanceApplierItem", paramMap);
	}
}
