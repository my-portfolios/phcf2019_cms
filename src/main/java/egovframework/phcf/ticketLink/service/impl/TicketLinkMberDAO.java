package egovframework.phcf.ticketLink.service.impl;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.phcf.ticketLink.service.TicketLinkMberVO;

@Repository("TicketLinkMberDAO")
public class TicketLinkMberDAO extends EgovComAbstractDAO {
	public void insertTklinkMber(TicketLinkMberVO mberVO) {
		insert("TicketLinkMberDAO.insertTklinkMber", mberVO);
	}
	public void updateTklinkMber(TicketLinkMberVO mberVO) {
		update("TicketLinkMberDAO.updateTklinkMber", mberVO);
	}
}

