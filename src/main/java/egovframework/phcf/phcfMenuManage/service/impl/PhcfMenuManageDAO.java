package egovframework.phcf.phcfMenuManage.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * 유료멤버십 관련 DAO 클래스
 * @author	김량래
 * @since	2019-11-11
 **/

@Repository("PhcfMenuManageDAO")
public class PhcfMenuManageDAO extends EgovComAbstractDAO{
	public List<HashMap<String, String>> selectMenuInfoList(HashMap<String, String> paramMap) {
		return selectList("PhcfMenuManageDAO.selectMenuInfoList", paramMap);
	}

	public HashMap<String, String> selectMenuInfoDetail(HashMap<String, String> paramMap) {
		return selectOne("PhcfMenuManageDAO.selectMenuInfoDetail", paramMap);
	}
	
	public void insertRegMenu(HashMap<String, String> paramMap) {
		update("PhcfMenuManageDAO.insertRegMenu", paramMap);
	}

	public void updateRegMenu(HashMap<String, String> paramMap) {
		update("PhcfMenuManageDAO.updateRegMenu", paramMap);
	}
}
