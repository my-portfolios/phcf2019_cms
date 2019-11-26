package egovframework.phcf.phcfMenuManage.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.phcfMenuManage.service.PhcfMenuManageService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


/**
 * 유료멤버십 서비스 구현 클래스
 * @author	김량래
 * @since	2019-11-11
 * */

@Service("PhcfMenuManageService")
public class PhcfMenuManageServiceImpl extends EgovAbstractServiceImpl implements PhcfMenuManageService {

	@Resource(name="PhcfMenuManageDAO")
	private PhcfMenuManageDAO dao;

	@Override
	public List<HashMap<String, String>> selectMenuInfoList(HashMap<String, String> paramMap) throws Exception {
		return dao.selectMenuInfoList(paramMap);
	}
	
	@Override
	public HashMap<String, String> selectMenuInfoDetail(HashMap<String, String> paramMap) throws Exception {
		return dao.selectMenuInfoDetail(paramMap);
	}
	
	@Override
	public void insertRegMenu(HashMap<String, String> paramMap) throws Exception {
		dao.insertRegMenu(paramMap);
	}
	
	@Override
	public void updateRegMenu(HashMap<String, String> paramMap) throws Exception {
		dao.updateRegMenu(paramMap);
	}
}





