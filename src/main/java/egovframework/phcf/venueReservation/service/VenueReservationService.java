package egovframework.phcf.venueReservation.service;

import java.util.HashMap;
import java.util.List;

public interface VenueReservationService {
	public List<HashMap<String, Object>> selectVenueReservationMaster() throws Exception;
	public List<HashMap<String, Object>> selectVenueReservationRegList(HashMap<String, Object> paramMap) throws Exception;
	public List<HashMap<String, Object>> selectVenueReservationDatesList(int regId);
	public int selectVenueReservationRegListCnt(HashMap<String, Object> paramMap) throws Exception;
	public void updateReservationMaster(HashMap<String,Object> paramMap) throws Exception;
	public void updateReservationItem(HashMap<String,Object> paramMap) throws Exception;
	public List<HashMap<String, Object>> selectDetailCodeList(String code) throws Exception;
	public List<HashMap<String, Object>> selectVenueReservationInfo(String SEQ);
	public void deleteVenueReservationDates(String SEQ);
}
