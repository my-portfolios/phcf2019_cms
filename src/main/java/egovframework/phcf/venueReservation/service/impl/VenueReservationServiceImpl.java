package egovframework.phcf.venueReservation.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import egovframework.phcf.venueReservation.service.VenueReservationService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("VenueReservationService")
public class VenueReservationServiceImpl extends EgovAbstractServiceImpl implements VenueReservationService{
	@Resource(name="VenueReservationDAO")
	private VenueReservationDAO dao;
	
	@Override
	public List<HashMap<String, Object>> selectVenueReservationMaster() throws Exception {
		return dao.selectVenueReservationMaster();
	}
	
	@Override
	public List<HashMap<String, Object>> selectVenueReservationRegList(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectVenueReservationRegList(paramMap);
	}
	
	@Override
	public List<HashMap<String, Object>> selectVenueReservationDatesList(int regId) {
		return dao.selectVenueReservationDatesList(regId);
	}
	
	@Override
	public int selectVenueReservationRegListCnt(HashMap<String, Object> paramMap) throws Exception {
		return dao.selectVenueReservationRegListCnt(paramMap);
	}
	
	@Override
	public void updateReservationMaster(HashMap<String,Object> paramMap) throws Exception {
		dao.updateReservationMaster(paramMap);
	}
	
	@Override
	public void updateReservationItem(HashMap<String,Object> paramMap) throws Exception {
		dao.updateReservationItem(paramMap);
	}
	
	@Override
	public List<HashMap<String, Object>> selectDetailCodeList(String code) throws Exception {
		return dao.selectDetailCodeList(code);
	}

	@Override
	public List<HashMap<String, Object>> selectVenueReservationInfo(String SEQ) {
		return dao.selectVenueReservationInfo(SEQ);
	}

	@Override
	public void deleteVenueReservationDates(String SEQ) {
		dao.deleteVenueReservationDates(SEQ);
	}

	@Override
	public void updateVenueReservationDates(HashMap<String, Object> paramMap) {
		dao.updateVenueReservationDates(paramMap);
	}

}
