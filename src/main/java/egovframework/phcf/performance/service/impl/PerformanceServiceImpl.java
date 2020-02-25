package egovframework.phcf.performance.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.phcf.nicepay.service.NicepayService;
import egovframework.phcf.performance.service.PerformanceService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("PerformanceService")
public class PerformanceServiceImpl extends EgovAbstractServiceImpl implements PerformanceService {
	
	@Resource(name = "PerformanceDAO")
	PerformanceDAO dao;

	@Override
	public List<HashMap<String, Object>> selectPerformanceApplierList(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectPerformanceApplierList(paramMap);
	}
	
	@Override
	public int selectPerformanceApplierListCnt(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectPerformanceApplierListCnt(paramMap);
	}

	@Override
	public void updatePerformanceApplierItem(HashMap<String, Object> paramMap) throws Exception {
		dao.updatePerformanceApplierItem(paramMap); 	
	}

	@Override
	public List<HashMap<String, Object>> selectAppliedVisitorPerformanceList(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectAppliedVisitorPerformanceList(paramMap);
	}

}
