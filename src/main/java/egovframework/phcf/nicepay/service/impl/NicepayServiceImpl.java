package egovframework.phcf.nicepay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.phcf.common.service.ParamMap;
import egovframework.phcf.nicepay.service.NicepayService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("NicepayService")
public class NicepayServiceImpl extends EgovAbstractServiceImpl implements NicepayService {
	
	@Resource(name = "NicepayDAO")
	NicepayDAO nicepayDAO;
	
	@Override
	public String getOrderNumber() throws Exception {
		return nicepayDAO.getOrderNumber();
	}
	
	@Override
	public boolean checkAgreeUser(String accUserId) throws Exception {
		return checkAgreeUser(accUserId, false);
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkAgreeUser(String accUserId, boolean isCheckAgreeSendYn) throws Exception {
		if(accUserId == null || accUserId.equalsIgnoreCase("null") || accUserId.length() < 1) {
			throw new IllegalArgumentException("accUserId 값은 필수 항목 입니다.");
		}
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("accUserId", accUserId);
		if(isCheckAgreeSendYn) {
			paramMap.put("isAgreeSendYn", isCheckAgreeSendYn);
		}
		
		return Boolean.valueOf( nicepayDAO.checkAgreeUser(paramMap) );
	}
	
	@Override
	public void insertUserCmsInfo(ParamMap paramMap) throws Exception {
		nicepayDAO.insertUserCmsInfo(paramMap);
	}
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void insertProc(ParamMap paramMap) throws Exception {
			
		// 주소의 경우 &#40; &#41; <== 이런 값이 들어간다.. ( ) 값인데.. protocol 으로 변환되면서 값(ASCII)이 들어 가는듯 하다.
		String user_add = String.valueOf( paramMap.get("user_add") );
		paramMap.put("user_add", StringUtils.replaceEach(user_add, new String[] { "&#40;", "&#41;" }, new String[] { "(", ")" }));
		
		String user_add_detail = String.valueOf( paramMap.get("user_add_detail") );
		paramMap.put("user_add_detail", StringUtils.replaceEach(user_add_detail, new String[] { "&#40;", "&#41;" }, new String[] { "(", ")" }));
		
		nicepayDAO.insertProc(paramMap);
	}
	
	@Override
	public void insertCardInfoProc(ParamMap paramMap) throws Exception {
		nicepayDAO.insertCardInfoProc(paramMap);
	}

}
