package egovframework.com.cmm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.EgovComIndexService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * Egov 공통 서비스
 * @author	김량래
 * @since	2020-01-03
 * */

@Service("EgovComIndexService")
public class EgovComIndexServiceImpl extends EgovAbstractServiceImpl implements EgovComIndexService{

	@Resource(name="EgovComIndexDAO")
	private EgovComIndexDAO dao;
	
	@Override
	public List<HashMap<String, Object>> selectMenuInfoList(String pageNm) throws Exception {
		return dao.selectMenuInfoList(pageNm);
	}
	
	@Override
	public Map<String, Object> selectAllMenuInfoList(ComDefaultVO vo) throws Exception {
		List<?> list = dao.selectAllMenuInfoList(vo);
		int cnt = dao.selectAllMenuInfoListCnt(vo);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", list);
		map.put("resultCnt", Integer.toString(cnt));
		return map;
	}
	
	@Override
	public List<?> selectMenuDidntMapped() throws Exception{
		return dao.selectMenuDidntMapped();
	}
	
	@Override
	public List<?> selectContentsDidntMapped() throws Exception{
		return dao.selectContentsDidntMapped();
	}
	
	@Override
	public void contentsPageYN(HashMap<String, String> vo) throws Exception{
		dao.contentsMenuMapping(vo);
	}
	
	@Override
	public void contentsMenuMapping(HashMap<String, String> vo) throws Exception{
		dao.contentsMenuMapping(vo);
	}
	
}
