package egovframework.com.cmm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * eGov index DAO
 * @author	김량래
 * @since	2020-01-03
 **/

@Repository("EgovComIndexDAO")
public class EgovComIndexDAO extends EgovComAbstractDAO{
	public List<HashMap<String, Object>> selectMenuInfoList(String pageNm) throws Exception {
		return selectList("EgovComIndexDAO.selectMenuInfoList", pageNm);
	}
}
