package egovframework.phcf.support.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.phcf.common.service.ParamMap;

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

	public List<ParamMap> getCmsSupportList(ParamMap paramMap) {
		return selectList("SupportManageDAO.getCmsSupportList", paramMap);
	}

	public int getCmsSupportCnt(ParamMap paramMap) {
		return (int)selectOne("SupportManageDAO.getCmsSupportCnt", paramMap);
	}

	public void insertUserSupportLog(ParamMap paramMap) {
		insert("SupportManageDAO.insertUserSupportLog", paramMap);
	}

	public int cmsUserChkCnt(String userId) {
		return (int) selectOne("SupportManageDAO.cmsUserChkCnt", userId);
	}

	public List<ParamMap> cmsGetUserInfo(String userId) {
		return selectList("SupportManageDAO.cmsGetUserInfo", userId);
	}

	public List<ParamMap> getCompLogList(ParamMap paramMap) {
		return selectList("SupportManageDAO.getCompLogList", paramMap);
	}

	public void insertCompSupportLog(ParamMap paramMap) {
		insert("SupportManageDAO.insertCompSupportLog", paramMap);
	}

	public ParamMap getCompLogoInfo(ParamMap paramMap) {
		return (ParamMap) selectOne("SupportManageDAO.getCompLogoInfo", paramMap);
	}

	public void upsertSupportCgInfo(ParamMap paramMap) {
		insert("SupportManageDAO.upsertSupportCgInfo", paramMap);
	}

	public List<ParamMap> getCmsStatusList(ParamMap paramMap) {
		return selectList("SupportManageDAO.getCmsStatusList", paramMap);
	}

	public int getCmsStatusTotalCnt(ParamMap paramMap) {
		return (int) selectOne("SupportManageDAO.getCmsStatusTotalCnt", paramMap);
	}

	public void compCardInsertProcLog(ParamMap paramMap) {
		insert("SupportManageDAO.compCardInsertProcLog", paramMap);
	}

	public void compCardInsertProcCms(ParamMap paramMap) {
		insert("SupportManageDAO.compCardInsertProcCms", paramMap);
	}
	
	public void compCmsInsertProcLog(ParamMap paramMap) {
		insert("SupportManageDAO.compCmsInsertProcLog", paramMap);
	}

	public void compCmsInsertProcCms(ParamMap paramMap) {
		insert("SupportManageDAO.compCmsInsertProcCms", paramMap);
	}

	public List<ParamMap> getGradeCodeList(String userTp) {
		return selectList("SupportManageDAO.getGradeCodeList", userTp);
	}

	public void updateGrade(ParamMap paramMap) {
		update("SupportManageDAO.updateGrade", paramMap);
	}

	public void updateAdminMsg(ParamMap paramMap) {
		update("SupportManageDAO.updateAdminMsg", paramMap);
	}

	public ParamMap getStatusDetailInfo(String cms_id) {
		return (ParamMap) selectOne("SupportManageDAO.getStatusDetailInfo", cms_id);
	}

	public void updateStatusModify(ParamMap paramMap) {
		update("SupportManageDAO.updateStatusModify", paramMap);
	}

	public ParamMap getSupportDetailInfo(String sp_id) {
		
		return (ParamMap) selectOne("SupportManageDAO.getSupportDetailInfo", sp_id);
	}

	public void updateTargetYn(ParamMap paramMap) {
		update("SupportManageDAO.updateTargetYn", paramMap);
	}

	public void deleteStatus(ParamMap paramMap) {
		delete("SupportManageDAO.deleteStatus", paramMap);
	}

	public void updateDetailInfo(ParamMap paramMap) {
		update("SupportManageDAO.updateDetailInfo", paramMap);
	}

	public void deleteDetailInfo(ParamMap paramMap) {
		delete("SupportManageDAO.deleteDetailInfo", paramMap);
	}

	public void deleteCgInfo(ParamMap paramMap) {
		delete("SupportManageDAO.deleteCgInfo", paramMap);
	}
}
