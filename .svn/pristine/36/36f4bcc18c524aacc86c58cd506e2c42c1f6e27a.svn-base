package egovframework.phcf.support.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
}
