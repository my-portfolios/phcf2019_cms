package egovframework.com.sec.phcf.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.sec.phcf.service.AuthManageVO;
import egovframework.com.sec.phcf.service.EgovPhcfAuthorService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("egovPhcfAuthorService")
public class EgovPhcfAuthorServiceImpl extends EgovAbstractServiceImpl implements EgovPhcfAuthorService {
	
	@Resource(name="egovPhcfAuthorDAO")
    private EgovPhcfAuthorDAO egovPhcfAuthorDAO;

	@Override
	public List<AuthManageVO> selectEgovPhcfAuthList(AuthManageVO map) throws Exception {
		return egovPhcfAuthorDAO.selectEgovPhcfAuthList(map);
	}
	
	@Override
	public List<AuthManageVO> selectAllEgovPhcfAuthList(AuthManageVO map) throws Exception {
		return egovPhcfAuthorDAO.selectAllEgovPhcfAuthList(map);
	}
	
	@Override
	public void insertEgovPhcfAuthList(AuthManageVO map) throws Exception {
		egovPhcfAuthorDAO.insertEgovPhcfAuthList(map);
	}
	
	@Override
	public void updateEgovPhcfAuthList(AuthManageVO map) throws Exception {
		egovPhcfAuthorDAO.updateEgovPhcfAuthList(map);
	}
	
	@Override
	public void deleteEgovPhcfAuthList(AuthManageVO map) throws Exception {
		egovPhcfAuthorDAO.deleteEgovPhcfAuthList(map);
	}

	@Override
	public int selectEgovPhcfAuthListCnt(AuthManageVO map) throws Exception {
		return egovPhcfAuthorDAO.selectEgovPhcfAuthListCnt(map);
	}
}
