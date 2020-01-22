package egovframework.phcf.common.service;

public interface SendMailService {

	void insertSendMailLog(String mail_tp, String create_id, String recv_mail, String recv_nm) throws Exception;

	void sendMail(ParamMap paramMap) throws Exception;		

}
