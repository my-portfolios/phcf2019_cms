package egovframework.com.sec.phcf.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.sec.phcf.service.AuthManage;

@Repository("egovPhcfAuthorDAO")
public class EgovPhcfAuthorDAO  extends EgovComAbstractDAO {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<AuthManage> selectEgovPhcfAuthList(HashMap<String, String> map) throws Exception {
		return (List<AuthManage>) list("egovPhcfAuthorDAO.selectEgovPhcfAuthList", map);
	}
	
}
