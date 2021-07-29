package egovframework.phcf.popbill.message.service;

public interface PopbillMessageService {
	
	
	public void sendSMS_Multi(String content, String reserveDT, String adsYN, boolean toNormal, boolean toMembership) throws Exception;
	public void sendMMS_Multi(String subject, String content, String reserveDT, String adsYN, boolean toNormal, boolean toMembership) throws Exception;
	public String getMessageRequestNumber(String serviceIdentifier);
}
