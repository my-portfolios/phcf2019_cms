package egovframework.phcf.popbill.message.service;

import java.io.File;
import java.util.Date;

public interface PopbillMessageService {
	
	public void insertRegistMessage(PopbillMessageVO messageVO);

	public void sendSmsSingle(String receiver, String content, Date reserveDT, boolean adsYn) throws Exception;
	public void sendSmsMulti(String content, Date reserveDT, boolean adsYn, boolean toNormal, boolean toMembership) throws Exception;
	
	public void sendLmsSingle(String receiver, String receiverName, String subject, String content, Date reserveDT, 
			boolean adsYN) throws Exception;
	public void sendLmsMulti(String subject, String content, Date reserveDT, boolean adsYn, boolean toNormal,
			boolean toMembership) throws Exception;
	public void sendLmsMulti(PopbillMessageVO messageVO) throws Exception;
	
	public void sendMmsMulti(String subject, String content, File file, Date reserveDT, boolean adsYn, 
			boolean toNormal, boolean toMembership) throws Exception;
	
	public String getMessageRequestNumber(String serviceIdentifier);
}
