package egovframework.phcf.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.phcf.common.service.ParamMap;


@Repository("SendMailDAO")
public class SendMailDAO extends EgovComAbstractDAO {
	
	public void insertSendMailLog(ParamMap paramMap) {
		insert("SendMail.insertSendMailLog", paramMap);
	}

	public List<ParamMap> getMailUserList() {
		return selectList("SendMail.getMailUserList");
	}

	public String getSendMailContents(String page_seq) {
		return (String) selectOne("SendMail.getSendMailContents", page_seq);
	}

}
