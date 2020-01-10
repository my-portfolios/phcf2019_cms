package egovframework.com.cmm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.ComDefaultVO;
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
	
	public List<?> selectAllMenuInfoList(ComDefaultVO vo) throws Exception{
		return selectList("EgovComIndexDAO.selectAllMenuInfoList", vo);
	}
	
	public int selectAllMenuInfoListCnt(ComDefaultVO vo) throws Exception{
		return (Integer)selectOne("EgovComIndexDAO.selectAllMenuInfoListCnt", vo);
	}
	
	public List<?> selectMenuDidntMapped() throws Exception{
		return selectList("EgovComIndexDAO.selectMenuDidntMapped");
	}
	
	public List<?> selectContentsDidntMapped() throws Exception{
		return selectList("EgovComIndexDAO.selectContentsDidntMapped");
	}
	
	public void contentsMenuMapping(HashMap<String, String> vo) throws Exception{
		update("EgovComIndexDAO.contentsMenuMapping", vo);
	}
	
}
