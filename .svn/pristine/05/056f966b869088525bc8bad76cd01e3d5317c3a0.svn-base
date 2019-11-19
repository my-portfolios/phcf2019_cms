package egovframework.phcf.nicepay;

import java.util.HashMap;
import java.util.List;

public interface NicepayService {

	List<HashMap<String, Object>> selectAgreeCheckList() throws Exception;

	void updateAgreeSendYn(String[] userIdArr) throws Exception;

	List<HashMap<String, Object>> selectCreatePayList(int ctPayDay) throws Exception;

	void updatePaySendYn(List<String> userIdList, List<HashMap<String, Object>> agreeNumList) throws Exception;

	List<HashMap<String, Object>> selectCreateUserList() throws Exception;

	void updateUserSendYn(List<String> cmsIdList, String name) throws Exception;

	void updateDelSendYn(List<String> cmsIdDelList, String name) throws Exception;

}
