package egovframework.phcf.artTour.service;

import java.util.HashMap;
import java.util.List;

public interface ArtTourService {

	public List<HashMap<String, Object>> selectArtTourApplierList(HashMap<String, Object> paramMap) throws Exception;
	public int selectArtTourApplierListCnt(HashMap<String, Object> paramMap) throws Exception;
	public void updateArtTourApplierItem(HashMap<String, Object> paramMap) throws Exception;
	List<HashMap<String, Object>> selectAppliedVisitorArtTourList(HashMap<String, Object> paramMap) throws Exception;
}
