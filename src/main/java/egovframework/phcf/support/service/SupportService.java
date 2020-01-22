package egovframework.phcf.support.service;

import java.util.HashMap;
import java.util.List;

import egovframework.phcf.common.service.ParamMap;

public interface SupportService {

	List<HashMap<String, Object>> selectCmsSupportList(HashMap<String, Object> paramMap) throws Exception;

	int selectCmsSupportCnt(HashMap<String, Object> paramMap) throws Exception;

	List<HashMap<String, Object>> selectGradeCodeList(String string) throws Exception;

	void updateCmsSupportItem(HashMap<String, Object> paramMap) throws Exception;

	void deleteCmsSupportItem(HashMap<String, Object> paramMap) throws Exception;

	List<ParamMap> getCmsSupportList(ParamMap paramMap) throws Exception;

	void insertUserSupportLog(ParamMap paramMap) throws Exception;

	int cmsUserChkCnt(String userId) throws Exception;

	List<ParamMap> cmsGetUserInfo(String userId) throws Exception;

	List<ParamMap> getCompLogList(String userTp, String compNm, String compNumber) throws Exception;

	void insertCompSupportLog(ParamMap paramMap) throws Exception;

	ParamMap getCompLogoInfo(String userTp, String compNm, String compNumber) throws Exception;

	void upsertSupportCgInfo(ParamMap paramMap) throws Exception;

	List<ParamMap> getCmsStatusList(ParamMap paramMap) throws Exception;

	void compCardInsertProc(ParamMap paramMap) throws Exception;

	void compCmsInsertProc(ParamMap paramMap) throws Exception;

	List<ParamMap> getGradeCodeList(String userTp) throws Exception;

	void updateGrade(String user_id, String grade) throws Exception;

	void updateAdminMsg(String sp_id, String admin_msg) throws Exception;

	ParamMap getStatusDetailInfo(ParamMap paramMap) throws Exception;

	void updateStatusModify(String cms_id, String use_yn);

	ParamMap getSupportDetailInfo(String sp_id) throws Exception;

	void updateTargetYn(String cms_id, String del_target_yn);

	void deleteStatus(String cms_id);

	void updateDetailinfo(ParamMap paramMap) throws Exception;

	void deleteDetailInfo(ParamMap paramMap) throws Exception;

}
