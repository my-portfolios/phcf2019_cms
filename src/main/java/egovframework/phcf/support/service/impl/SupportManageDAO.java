package egovframework.phcf.support.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("SupportManageDAO")
public class SupportManageDAO extends EgovComAbstractDAO {

	public List<HashMap<String, Object>> selectCmsSupportList(HashMap<String, Object> paramMap) throws Exception {
		return selectList("SupportManageDAO.selectCmsSupportList", paramMap);
	}

	public int selectCmsSupportCnt(HashMap<String, Object> paramMap) throws Exception {
		return selectOne("SupportManageDAO.selectCmsSupportCnt", paramMap);
	}

	public List<HashMap<String, Object>> selectGradeCodeList(String param) throws Exception {
		return selectList("SupportManageDAO.selectGradeCodeList", param);
	}

	public void updateCmsSupportItem(HashMap<String, Object> paramMap) throws Exception {
		update("SupportManageDAO.updateCmsSupportItem", paramMap);
	}

	public void updateUserGrade(HashMap<String, Object> paramMap) throws Exception {
		update("SupportManageDAO.updateUserGrade", paramMap);
	}

	public void deleteCmsSupportItem(HashMap<String, Object> paramMap) throws Exception {
		delete("SupportManageDAO.deleteCmsSupportItem", paramMap);
	}
}
