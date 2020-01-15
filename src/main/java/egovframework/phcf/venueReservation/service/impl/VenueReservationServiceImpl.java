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

}