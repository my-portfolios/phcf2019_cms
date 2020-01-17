package egovframework.com.sec.phcf.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.sec.phcf.service.AuthManageVO;

@Repository("egovPhcfAuthorDAO")
public class EgovPhcfAuthorDAO  extends EgovComAbstractDAO {
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<AuthManageVO> selectEgovPhcfAuthList(AuthManageVO map) throws Exception {
		return (List<AuthManageVO>) list("egovPhcfAuthorDAO.selectEgovPhcfAuthList", map);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<AuthManageVO> selectAllEgovPhcfAuthList(AuthManageVO map) throws Exception {
		return (List<AuthManageVO>) list("egovPhcfAuthorDAO.selectAllEgovPhcfAuthList", map);
	}
	
	public void insertEgovPhcfAuthList(AuthManageVO map) throws Exception {
		insert("egovPhcfAuthorDAO.insertEgovPhcfAuthList", map);
	}
	
	public void updateEgovPhcfAuthList(AuthManageVO map) throws Exception {
		update("egovPhcfAuthorDAO.updateEgovPhcfAuthList", map);
	}
	
	public void deleteEgovPhcfAuthList(AuthManageVO map) throws Exception {
		delete("egovPhcfAuthorDAO.deleteEgovPhcfAuthList", map);
	}
	
	public int selectEgovPhcfAuthListCnt(AuthManageVO map) throws Exception {
		return (Integer)selectOne("egovPhcfAuthorDAO.selectEgovPhcfAuthListCnt", map);
	}
}
