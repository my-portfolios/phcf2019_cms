package egovframework.phcf.nicepay.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.phcf.common.service.ParamMap;

@Repository("NicepayDAO")
public class NicepayDAO extends EgovComAbstractDAO {
	
	public String getOrderNumber() {
		return (String) selectOne("NicepayDAO.getOrderNumber");
	}
	
	public String checkAgreeUser(ParamMap paramMap) {
		return (String) selectOne("NicepayDAO.checkAgreeUser", paramMap);
	}
	
	public void insertUserCmsInfo(ParamMap paramMap) {
		insert("NicepayDAO.insertUserCmsInfo", paramMap);
	}
	
	public List<HashMap<String, Object>> selectAgreeCheckList() throws Exception {
		return selectList("NicepayDAO.selectAgreeCheckList");
	}

	public void updateAgreeSendYn(HashMap<String, Object> paramMap) throws Exception {
		update("NicepayDAO.updateAgreeSendYn", paramMap);
	}

	public List<HashMap<String, Object>> selectCreatePayList(String intTodayPlus1Day) throws Exception {
		return selectList("NicepayDAO.selectCreatePayList", intTodayPlus1Day);
	}

	public void updatePaySendYn(HashMap<String, Object> paramMap) throws Exception {
		update("NicepayDAO.updatePaySendYn", paramMap);
	}

	public List<HashMap<String, Object>> selectCreateUserList() throws Exception {
		return selectList("NicepayDAO.selectCreateUserList");
	}

	public void updateUserSendYn(HashMap<String, Object> paramMap) throws Exception {
		update("NicepayDAO.updateUserSendYn", paramMap);
	}

	public void updateDelSendYn(HashMap<String, Object> paramMap) throws Exception {
		update("NicepayDAO.updateDelSendYn", paramMap);
	}
	
	public void insertProc(ParamMap paramMap) {
		insert("NicepayDAO.insertProc", paramMap);
	}
	
	public void insertCardInfoProc(ParamMap paramMap) {
		insert("NicepayDAO.insertCardInfoProc", paramMap);
	}
	
	
}
