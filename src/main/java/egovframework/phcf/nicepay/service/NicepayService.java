package egovframework.phcf.nicepay.service;

import java.util.HashMap;
import java.util.List;

import egovframework.phcf.common.service.ParamMap;

public interface NicepayService {

	List<HashMap<String, Object>> selectAgreeCheckList() throws Exception;

	void updateAgreeSendYn(String[] userIdArr) throws Exception;

	List<HashMap<String, Object>> selectCreatePayList(int ctPayDay) throws Exception;

	void updatePaySendYn(List<String> userIdList, List<HashMap<String, Object>> agreeNumList) throws Exception;

	List<HashMap<String, Object>> selectCreateUserList() throws Exception;

	void updateUserSendYn(List<String> cmsIdList, String name) throws Exception;

	void updateDelSendYn(List<String> cmsIdDelList, String name) throws Exception;

	String getOrderNumber() throws Exception;

	boolean checkAgreeUser(String accUserId) throws Exception;

	void insertUserCmsInfo(ParamMap paramMap) throws Exception;

	void insertProc(ParamMap paramMap) throws Exception;

	void insertCardInfoProc(ParamMap paramMap) throws Exception;

}
