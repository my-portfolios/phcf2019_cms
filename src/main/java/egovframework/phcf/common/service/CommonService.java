package egovframework.phcf.common.service;

import java.util.HashMap;
import java.util.List;

/**
 * 문화공간 공통적으로 사용할 기능(특히 공연,전시) 관련 서비스
 * @author	권혜진
 * @since	2019-12-13
 * */
public interface CommonService {
	public List<HashMap<String, Object>> selectGyList(HashMap<String, String> paramMap) throws Exception;

	// 인원 체크를 ....... 추가칼럼 id랑  ntt_id가 있는데 sum(정원) where ntt_id 로 하자니 재사용성이 걸리고 따로 하자니 좀 비효율적인가 싶고
	public boolean getUserChk(String added_id) throws Exception; 
	
	public HashMap<String, String> selectContent(HashMap<String, String> paramMap) throws Exception; 	

}
