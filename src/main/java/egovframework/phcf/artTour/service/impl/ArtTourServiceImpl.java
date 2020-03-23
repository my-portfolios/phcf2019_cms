package egovframework.phcf.artTour.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.artTour.service.ArtTourService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("ArtTourService")
public class ArtTourServiceImpl extends EgovAbstractServiceImpl implements ArtTourService {
	
	@Resource(name = "ArtTourDAO")
	ArtTourDAO dao;

	@Override
	public List<HashMap<String, Object>> selectArtTourApplierList(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectArtTourApplierList(paramMap);
	}
	
	@Override
	public int selectArtTourApplierListCnt(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectArtTourApplierListCnt(paramMap);
	}

	@Override
	public void updateArtTourApplierItem(HashMap<String, Object> paramMap) throws Exception {
		dao.updateArtTourApplierItem(paramMap); 	
	}

	@Override
	public List<HashMap<String, Object>> selectAppliedVisitorArtTourList(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectAppliedVisitorArtTourList(paramMap);
	}

}
