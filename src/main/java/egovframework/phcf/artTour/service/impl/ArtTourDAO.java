package egovframework.phcf.artTour.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("ArtTourDAO")
public class ArtTourDAO extends EgovComAbstractDAO {

	public List<HashMap<String, Object>> selectArtTourApplierList(HashMap<String, Object> paramMap) {
		return selectList("ArtTourDAO.selectArtTourApplierList", paramMap);
	}

	public int selectArtTourApplierListCnt(HashMap<String, Object> paramMap) {
		return selectOne("ArtTourDAO.selectArtTourApplierListCnt", paramMap);
	}
	
	public void updateArtTourApplierItem(HashMap<String, Object> paramMap) {
		update("ArtTourDAO.updateArtTourApplierItem", paramMap);
	}

	public List<HashMap<String, Object>> selectAppliedVisitorArtTourList(HashMap<String, Object> paramMap) {
		return selectList("ArtTourDAO.selectAppliedVisitorArtTourList", paramMap);
	}
}
