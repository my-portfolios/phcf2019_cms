package egovframework.phcf.common.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.common.service.CommonService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 문화공간 공통적으로 사용할 기능(특히 공연,전시) 관련 서비스
 * @author	권혜진
 * @since	2019-12-13
 * */

@Service("CommonService")
public class CommonServiceImpl extends EgovAbstractServiceImpl implements CommonService{

	@Resource(name="CommonDAO")
	private CommonDAO dao;

	@Override
	public HashMap<String, String> selectContent(HashMap<String, String> paramMap) throws Exception {
		return dao.selectContent(paramMap);
	}
	
}
