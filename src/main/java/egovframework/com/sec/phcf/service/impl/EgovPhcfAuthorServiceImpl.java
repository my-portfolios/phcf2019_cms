package egovframework.com.sec.phcf.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.sec.phcf.service.AuthManage;
import egovframework.com.sec.phcf.service.EgovPhcfAuthorService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("EgovPhcfAuthorService")
public class EgovPhcfAuthorServiceImpl extends EgovAbstractServiceImpl implements EgovPhcfAuthorService {
	
	@Resource(name="egovPhcfAuthorDAO")
    private EgovPhcfAuthorDAO egovPhcfAuthorDAO;

	@Override
	public List<AuthManage> selectEgovPhcfAuthList(HashMap<String, String> map) throws Exception {
		return egovPhcfAuthorDAO.selectEgovPhcfAuthList(map);
	}


}
