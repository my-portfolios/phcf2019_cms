package egovframework.phcf.indiplus.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("IndiplusDAO")
public class IndiplusDAO extends EgovComAbstractDAO{

	public List<HashMap<String, Object>> selectRestDay(HashMap<String, Object> paramMap) {
		return selectList("IndiplusDAO.selectRestDay",paramMap);
	}
	
	public int selectRestDayCnt() {
		return selectOne("IndiplusDAO.selectRestDayCnt");
	}

	public void insertRestDay(String date) {
		insert("IndiplusDAO.insertRestDay",date);
	}
	
	public void updateRestDay(String seq, String date) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("seq", seq);
		paramMap.put("date", date);
		
		insert("IndiplusDAO.updateRestDay",paramMap);
	}
	
}
