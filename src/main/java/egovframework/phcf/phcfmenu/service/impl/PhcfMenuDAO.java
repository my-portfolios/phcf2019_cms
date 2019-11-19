package egovframework.phcf.phcfmenu.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("phcfMenuDAO")
public class PhcfMenuDAO extends EgovComAbstractDAO{
	// 사용 여부가 'Y'인 메뉴들을 가져온다.
	public List<HashMap<String, Object>> selectMenuList() {
		return selectList("PhcfMenuDAO.selectMenuList");
	}

}
