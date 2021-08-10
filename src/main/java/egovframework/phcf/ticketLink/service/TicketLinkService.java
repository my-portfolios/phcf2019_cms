package egovframework.phcf.ticketLink.service;

import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.uss.umt.service.MberManageVO;

public interface TicketLinkService {
	public void insertTklinkMber(TicketLinkMberVO mberVO) throws Exception;
	public void updateTklinkMber(TicketLinkMberVO mberVO) throws Exception;
	
	public HashMap<String, Object> getMemberInfoForTklink(String id) throws Exception;
	public HashMap<String, Object> getCryptoInfoForTklink(String id) throws Exception;
	
	public int joinTkLink(HashMap<String, Object> paramMap) throws Exception;
	
	public int checkTkLinkJoin(HashMap<String, Object> paramMap) throws Exception;
	public JsonNode getUserDetailTkLink(HashMap<String, Object> paramMap) throws Exception;
	
	public JsonNode getReservePageTkLink(HashMap<String, Object> paramMap) throws Exception;
	
	public int modifyMemberDetailTkLink(HashMap<String, Object> paramMap) throws Exception;
	
	public int checkJoinThenModifyMemberDetailTkLink(MberManageVO mberManageVO) throws Exception;
	public int checkJoinThenModifyMemberDetailTkLink(HashMap<String, Object> paramMap) throws Exception;
}
