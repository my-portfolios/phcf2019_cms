package egovframework.phcf.support.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.common.service.ParamMap;
import egovframework.phcf.support.service.SupportService;
import egovframework.phcf.util.PagingUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("SupportService")
public class SupportServiceImpl extends EgovAbstractServiceImpl implements SupportService {

	@Resource(name = "SupportManageDAO")
	SupportManageDAO supportManageDAO;
	
	@Override
	public List<HashMap<String, Object>> selectCmsSupportList(HashMap<String, Object> paramMap) throws Exception {
		
		return supportManageDAO.selectCmsSupportList( PagingUtil.addFirstRecordIndex(paramMap) );
	}

	@Override
	public int selectCmsSupportCnt(HashMap<String, Object> paramMap) throws Exception {
		return supportManageDAO.selectCmsSupportCnt(paramMap);
	}

	@Override
	public List<HashMap<String, Object>> selectGradeCodeList(String param) throws Exception {
		return supportManageDAO.selectGradeCodeList(param);
	}

	@Override
	public void updateCmsSupportItem(HashMap<String, Object> paramMap) throws Exception {
		
		// 회원등급 변경은 회원ID가 존재 할때 가능 하다.
		// 회원ID 가 없다면 후원메시지만 업데이트 하고 종료 처리 한다.
		String userId = String.valueOf( paramMap.get("user_id") );
		if(userId == null || userId.length() < 1) {
			supportManageDAO.updateCmsSupportItem(paramMap);
			return;
		}
		
		supportManageDAO.updateUserGrade(paramMap);
		supportManageDAO.updateCmsSupportItem(paramMap);
	}

	@Override
	public void deleteCmsSupportItem(HashMap<String, Object> paramMap) throws Exception {
		supportManageDAO.deleteCmsSupportItem(paramMap);
	}	
	
	/**
	 * CMS용 후원관리 List를 조회 한다.
	 * @param paramMap
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ParamMap> getCmsSupportList(ParamMap paramMap) throws Exception {
		
		// 페이징 처리...
		String pageNoStr = String.valueOf( paramMap.get("page_no") );
		int pageNo = (pageNoStr == null || pageNoStr.equalsIgnoreCase("null"))? 0 : (Integer.valueOf(pageNoStr) - 1) * 10;
		paramMap.put("page_no", pageNo);
		
		// 조회 목록
		List<ParamMap> resultList = supportManageDAO.getCmsSupportList(paramMap);
		// 전체 건수
		int totalCnt = supportManageDAO.getCmsSupportCnt(paramMap);
		paramMap.put("total_article", totalCnt);
		
		resultList = setTotalParamLimit(resultList, paramMap);
		
		return resultList;
	}
	
	@Override
	public void insertUserSupportLog(ParamMap paramMap) throws Exception {
		supportManageDAO.insertUserSupportLog(paramMap);
	}
	
	@Override
	public int cmsUserChkCnt(String userId) throws Exception {
		if(userId == null || userId.equalsIgnoreCase("null") || userId.length() < 1) { throw new NullPointerException("유저아이디는 필수 값 입니다."); }
		
		return supportManageDAO.cmsUserChkCnt(userId);
	}

	@Override
	public List<ParamMap> cmsGetUserInfo(String userId) throws Exception {
		if(userId == null || userId.equalsIgnoreCase("null") || userId.length() < 1) { throw new NullPointerException("유저아이디는 필수 값 입니다."); }
		
		return supportManageDAO.cmsGetUserInfo(userId);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ParamMap> getCompLogList(String userTp, String compNm, String compNumber) throws Exception {
		if(userTp == null || userTp.equalsIgnoreCase("null") || userTp.length() < 1) { throw new NullPointerException("회원 종류는 필수 값 입니다."); }
		if(compNm == null || compNm.equalsIgnoreCase("null") || compNm.length() < 1) { throw new NullPointerException("회사명은 필수 값 입니다."); } 
		
		ParamMap paramMap = new ParamMap();
		
		paramMap.put("userTp", userTp);
		paramMap.put("compNm", compNm);
		paramMap.put("compNumber", compNumber);
		
		return supportManageDAO.getCompLogList(paramMap);
	}
	
	@Override
	public void insertCompSupportLog(ParamMap paramMap) throws Exception {
		supportManageDAO.insertCompSupportLog(paramMap);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ParamMap getCompLogoInfo(String userTp, String compNm, String compNumber) throws Exception {
		if(userTp == null || userTp.equalsIgnoreCase("null") || userTp.length() < 1) { throw new NullPointerException("회원 종류는 필수 값 입니다."); }
		if(compNm == null || compNm.equalsIgnoreCase("null") || compNm.length() < 1) { throw new NullPointerException("회사명은 필수 값 입니다."); }
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("userTp", userTp);
		paramMap.put("compNm", compNm);
		paramMap.put("compNumber", compNumber);
		
		return supportManageDAO.getCompLogoInfo(paramMap);
	}
	
	@Override
	public void upsertSupportCgInfo(ParamMap paramMap) throws Exception {
		
		supportManageDAO.upsertSupportCgInfo(paramMap);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ParamMap> getCmsStatusList(ParamMap paramMap) throws Exception {
		
		// 페이징 처리...
		String pageNoStr = String.valueOf( paramMap.get("page_no") );
		int pageNo = (pageNoStr == null || pageNoStr.equalsIgnoreCase("null"))? 0 : (Integer.valueOf(pageNoStr) - 1) * 10;
		paramMap.put("page_no", pageNo);
		
		// 조회 목록
		List<ParamMap> resultList = supportManageDAO.getCmsStatusList(paramMap);
		
		// 전체 건수
		int totalCnt = supportManageDAO.getCmsStatusTotalCnt(paramMap);
		paramMap.put("total_article",  totalCnt);
		
		resultList = setTotalParamLimit(resultList, paramMap);
		
		return resultList;
	}
	
	@Override
	public void compCardInsertProc(ParamMap paramMap) throws Exception {
		// tb_support_log 테이블 저장 해야 된다.
		supportManageDAO.compCardInsertProcLog(paramMap);
		
		// tb_support_cms 테이블에 저장 해야 된다.
		supportManageDAO.compCardInsertProcCms(paramMap);
	}
	
	@Override
	public void compCmsInsertProc(ParamMap paramMap) throws Exception {
		
		// tb_support_log 테이블 저장 해야 된다.
		supportManageDAO.compCmsInsertProcLog(paramMap);
		
		// tb_support_cms 테이블에 저장 해야 된다.
		supportManageDAO.compCmsInsertProcCms(paramMap);
	}
	
	@Override
	public List<ParamMap> getGradeCodeList(String userTp) throws Exception {
		return supportManageDAO.getGradeCodeList(userTp);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void updateGrade(String user_id, String grade) throws Exception {
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("user_id", user_id);
		paramMap.put("user_grade", grade);
		
		supportManageDAO.updateGrade(paramMap);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void updateAdminMsg(String sp_id, String admin_msg) throws Exception {
		if(sp_id == null || sp_id.equalsIgnoreCase("null") || sp_id.length() < 1) { throw new NullPointerException("sp_id 값은 필수 입니다."); }
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("sp_id", sp_id);
		paramMap.put("admin_msg", admin_msg);
		
		supportManageDAO.updateAdminMsg(paramMap);
	}

	/**
	 * CMS전송현황 상세 정보를 조회 하여 돌려준다.
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public ParamMap getStatusDetailInfo(ParamMap paramMap) throws Exception {
		String cms_id = paramMap.getString("select_cms_id");
		if(cms_id == null || cms_id.equalsIgnoreCase("null") || cms_id.length() < 1) {
			throw new NullPointerException("cms_id 값은 필수 입니다.");
		}
		
		return supportManageDAO.getStatusDetailInfo(cms_id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateStatusModify(String cms_id, String use_yn) {
		if(cms_id == null || cms_id.equalsIgnoreCase("null") || cms_id.length() < 1) {
			throw new NullPointerException("cms_id는 필수값 입니다.");
		}
		if(use_yn == null || use_yn.equalsIgnoreCase("null") || use_yn.length() < 1) {
			throw new NullPointerException("use_yn는 필수값 입니다.");
		}
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("cms_id", cms_id);
		paramMap.put("use_yn", use_yn);
		
		supportManageDAO.updateStatusModify(paramMap);
	}
	
	@Override
	public ParamMap getSupportDetailInfo(String sp_id) throws Exception {
		if(sp_id == null || sp_id.equalsIgnoreCase("null") || sp_id.length() < 1) {
			throw new NullPointerException("sp_id 값은 필수 입니다.");
		}
		
		return supportManageDAO.getSupportDetailInfo(sp_id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void updateTargetYn(String cms_id, String del_target_yn) {
		if(cms_id == null || cms_id.equalsIgnoreCase("null") || cms_id.length() < 1) { throw new NullPointerException("cms_id는 필수 값 입니다."); }
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("cms_id", cms_id);
		paramMap.put("del_target_yn", del_target_yn);
		
		supportManageDAO.updateTargetYn(paramMap);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void deleteStatus(String cms_id) {
		if(cms_id == null || cms_id.equalsIgnoreCase("null") || cms_id.length() < 1) { throw new NullPointerException("cms_id는 필수 값 입니다."); }
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("cms_id", cms_id);
		
		supportManageDAO.deleteStatus(paramMap);
	}
	
	/**
	 * 후원신청 관리 상세정보 수정
	 * @param paramMap
	 */
	@Override
	public void updateDetailinfo(ParamMap paramMap) throws Exception {
		
		supportManageDAO.updateDetailInfo(paramMap);
	}

	/**
	 * 후원 회원 테이블 삭제 처리
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public void deleteDetailInfo(ParamMap paramMap) throws Exception {
		
		if(paramMap.getString("user_tp").equalsIgnoreCase("C")) {
		
			// 기업 회원일 경우 tb_support_cg_info 테이블이 같이 삭제 되어야 함.
			supportManageDAO.deleteCgInfo(paramMap);
		}
		
		// 회원 정보 테이블 삭제 처리
		supportManageDAO.deleteDetailInfo(paramMap);		
	}

	@SuppressWarnings("unchecked")
	public List<ParamMap> setTotalParamLimit(List<ParamMap> list, ParamMap paramMap) {
		int total_page = 1; 	// 전체 페이지 수
		String totalArticleStr = String.valueOf( paramMap.get("total_article") );
		int total_article = (totalArticleStr == null || totalArticleStr.equalsIgnoreCase("null"))? 0 : Integer.valueOf( totalArticleStr ) ; 	// 전체 게시물 수
		
		if(list != null && list.size() > 0) {
			
			total_page = (int)Math.ceil(total_article / paramMap.getDouble(paramMap.getString("page_size_name","page_size")));
		}
		
		paramMap.put("total_article", total_article);
		paramMap.put("total_page", total_page);
		
		return list;
	}
}
