package egovframework.phcf.venueReservation.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("VenueReservationDAO")
public class VenueReservationDAO extends EgovComAbstractDAO {
	
	public List<HashMap<String, Object>> selectVenueReservationMaster() {
		return selectList("VenueReservationDAO.selectVenueReservationMaster");
	}
	
	public List<HashMap<String, Object>> selectVenueReservationRegList(HashMap<String, Object> paramMap) {
		return selectList("VenueReservationDAO.selectVenueReservationRegList", paramMap);
	}
	
	public int selectVenueReservationRegListCnt(HashMap<String, Object> paramMap) {
		return (int) selectOne("VenueReservationDAO.selectVenueReservationRegListCnt", paramMap);
	}
	
	public void updateReservationMaster(HashMap<String, Object> paramMap) {
		insert("VenueReservationDAO.updateReservationMaster",paramMap);
	}
	
	public void updateReservationItem(HashMap<String, Object> paramMap) {
		update("VenueReservationDAO.updateReservationItem",paramMap);
	}
	
	public List<HashMap<String, Object>> selectDetailCodeList(String code) throws Exception {
		return selectList("VenueReservationDAO.selectDetailCodeList", code);
	}
}
