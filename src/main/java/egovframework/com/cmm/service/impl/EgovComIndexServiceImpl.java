package egovframework.com.cmm.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
	
	public List<HashMap<String, Object>> selectMenuInfoList(String pageNm) throws Exception {
		return dao.selectMenuInfoList(pageNm);
	}
	
	
}
