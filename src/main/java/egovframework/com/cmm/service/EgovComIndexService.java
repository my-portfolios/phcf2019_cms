package egovframework.com.cmm.service;

import java.util.HashMap;
import java.util.List;

/**
 * 문화공간 공통적으로 사용할 기능(특히 공연,전시) 관련 서비스
 * @author	권혜진
 * @since	2019-12-13
 * */
public interface EgovComIndexService {
	public List<HashMap<String, Object>> selectMenuInfoList(String pageNm) throws Exception;

}
