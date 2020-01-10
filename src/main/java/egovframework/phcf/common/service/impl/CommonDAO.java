package egovframework.phcf.common.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * 유료멤버십 관련 DAO 클래스
 * @author	권혜진
 * @since	2019-12-13
 **/

@Repository("CommonDAO")
public class CommonDAO extends EgovComAbstractDAO{

	public List<HashMap<String, Object>> selectGyList(HashMap<String, String> paramMap) {
		return selectList("CommonPage.selectGyList", paramMap);
	}
	
	public HashMap<String, String> selectContent(HashMap<String, String> paramMap) {
		return selectOne("CommonPage.selectContent", paramMap);
	}

}
