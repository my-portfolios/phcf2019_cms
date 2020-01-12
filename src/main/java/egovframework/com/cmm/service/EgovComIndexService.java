package egovframework.com.cmm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultVO;

/**
 * 문화공간 공통적으로 사용할 기능(특히 공연,전시) 관련 서비스
 * @author	권혜진
 * @since	2019-12-13
 * */
public interface EgovComIndexService {
	public List<HashMap<String, Object>> selectMenuInfoList(String pageNm) throws Exception;
	
	public Map<String, Object> selectAllMenuInfoList(ComDefaultVO vo) throws Exception;
	
	public List<?> selectMenuDidntMapped() throws Exception;
	
	public List<?> selectContentsDidntMapped() throws Exception;
	
	public void contentsPageYN(HashMap<String, String> vo) throws Exception;
	
	public void contentsMenuMapping(HashMap<String, String> vo) throws Exception;
}
