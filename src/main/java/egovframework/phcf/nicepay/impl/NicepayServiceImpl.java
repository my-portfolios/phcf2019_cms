package egovframework.phcf.nicepay.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.nicepay.NicepayService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("NicepayService")
public class NicepayServiceImpl extends EgovAbstractServiceImpl implements NicepayService {
	
	@Resource(name = "NicepayDAO")
	NicepayDAO nicepayDAO;
	
	@Override
	public List<HashMap<String, Object>> selectAgreeCheckList() throws Exception {
		return nicepayDAO.selectAgreeCheckList();
	}

	@Override
	public void updateAgreeSendYn(String[] userIdArr) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userIdArr", userIdArr);
		
		nicepayDAO.updateAgreeSendYn(paramMap);
	}

	@Override
	public List<HashMap<String, Object>> selectCreatePayList(int intTodayPlus1Day) throws Exception {
		return nicepayDAO.selectCreatePayList(String.valueOf(intTodayPlus1Day));
	}

	@Override
	public void updatePaySendYn(List<String> userIdList, List<HashMap<String, Object>> agreeNumList) throws Exception {
		if(userIdList == null || userIdList.isEmpty()) { throw new NullPointerException("userIdList 는 필수 값 입니다."); }
		if(agreeNumList == null || agreeNumList.isEmpty()) { throw new NullPointerException("agreeNumList는 필수 값 입니다."); }
		
		List<HashMap<String, Object>> insertList = new ArrayList<HashMap<String, Object>>();
		
		for(String userId : userIdList) {
			
			String agreeNum = null;
			// agreeNumList에서 해당 userId가 존재 하는지 확인 해봐야 된다.
			for(HashMap<String, Object> agreeNumParam : agreeNumList) {
				String tempId = String.valueOf(agreeNumParam.get("userId"));
				
				if(!tempId.equals(userId)) { continue; }
				agreeNum = String.valueOf( agreeNumParam.get("agreeNum") );
			}
			
			// 매핑 된게 없다면 continue 처리
			if(agreeNum == null || agreeNum.length() < 1) { continue; }
			
			// 매핑 된게 있으면 paramMap 생성 및 insertList에 추가.
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			paramMap.put("agreeNum", agreeNum);
			
			insertList.add(paramMap);
		}
		
		if(insertList.isEmpty()) {
			System.out.println("== 출금정보를 업데이트 할 데이터가 없습니다.");
			return; 
		}
		
		for(HashMap<String, Object> insertInfo : insertList) {
			nicepayDAO.updatePaySendYn(insertInfo);
		}
	}

	@Override
	public List<HashMap<String, Object>> selectCreateUserList() throws Exception {
		return nicepayDAO.selectCreateUserList();
	}

	@Override
	public void updateUserSendYn(List<String> cmsIdList, String userMemFileName) throws Exception {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateUserSendList", cmsIdList);
		paramMap.put("userMemFileName", userMemFileName);
		
		nicepayDAO.updateUserSendYn(paramMap);
	}

	@Override
	public void updateDelSendYn(List<String> cmsIdList, String userMemFileName) throws Exception {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateDelSendList", cmsIdList);
		paramMap.put("userMemFileName", userMemFileName);
		
		nicepayDAO.updateDelSendYn(paramMap);
	}

}
