package egovframework.phcf.popbill.message.service.impl;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.phcf.popbill.message.service.PopbillMessageVO;

@Repository("PopbillMessageDAO")
public class PopbillMessageDAO extends EgovComAbstractDAO {
	public void insertRegistMessage(PopbillMessageVO messageVO) {
		insert("popbillMessageDAO.insertRegistMessage", messageVO);
	}
}
