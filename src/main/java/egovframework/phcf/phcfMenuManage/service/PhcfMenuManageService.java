package egovframework.phcf.phcfMenuManage.service;

import java.util.HashMap;
import java.util.List;


/**
 * 유료멤버십 관련 서비스
 * @author	김량래
 * @since	2019-11-11
 * */

public interface PhcfMenuManageService {
	public List<HashMap<String, String>> selectMenuInfoList(HashMap<String, String> paramMap) throws Exception;
	public HashMap<String, String> selectMenuInfoDetail(HashMap<String, String> paramMap) throws Exception;
	public void insertRegMenu(HashMap<String, String> paramMap) throws Exception;
	public void updateRegMenu(HashMap<String, String> paramMap) throws Exception;
}
