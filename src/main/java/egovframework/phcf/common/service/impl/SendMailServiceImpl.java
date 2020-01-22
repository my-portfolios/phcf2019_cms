package egovframework.phcf.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.phcf.common.service.CommonService;
import egovframework.phcf.common.service.ParamMap;
import egovframework.phcf.common.service.SendMailService;
import egovframework.phcf.common.service.impl.SendMailDAO;
import egovframework.phcf.util.MailUtil;
import egovframework.phcf.util.PropertiesUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("SendMailService")
public class SendMailServiceImpl extends EgovAbstractServiceImpl implements SendMailService {
	
	@Resource(name="SendMailDAO")
	SendMailDAO sendMailDAO;
	
	/**
	 * 메일 발송시 tb_send_maillog 테이블에 발송 이력을 기록 하는 기능
	 * @param mail_tp
	 * @param create_id
	 * @param recv_mail
	 * @param recv_nm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insertSendMailLog(String mail_tp, String create_id, String recv_mail, String recv_nm) throws Exception {
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("mail_tp", mail_tp);
		paramMap.put("create_id", create_id);
		paramMap.put("recv_mail", recv_mail);
		paramMap.put("recv_nm", recv_nm);
		
		sendMailDAO.insertSendMailLog(paramMap);
	}
	
	/**
	 * 공연, 전시 정보 등록시 메일을 발송 하는 기능
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public void sendMail(ParamMap paramMap) throws Exception {
		System.out.println("== 공연/전시 회원 메일 발송 : " + paramMap);
		
		String page_seq = paramMap.getString("PAGE_SEQ");
		// page_seq key 값이 대문자인지, 소문자인지.. 개발 페이지 마다 달라서 한번 더 체크 한다.
		if(page_seq == null || page_seq.length() < 1) { page_seq = paramMap.getString("page_seq"); }
		
		String bd_seq = paramMap.getString("BD_SEQ");
		if(bd_seq == null || bd_seq.length() < 1) { bd_seq = paramMap.getString("bd_seq"); }
		
		// page_seq : 10291(공지사항)
		switch(page_seq) {
			case "10221" :	// 공연정보
				sendMailInfo(paramMap, page_seq, "GY"); break;
			case "10280" :	// 전시정보
				sendMailInfo(paramMap, page_seq, "JC"); break;
			default: break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void sendMailInfo(ParamMap paramMap, String page_seq, String mail_tp) throws Exception {
		
		// 보내는 사람 정보를 가져온다.
		String fromMail = PropertiesUtil.getValue("mail_from");
		String fromName = PropertiesUtil.getValue("mail_name");
		
		// 회원 정보 중 메일 발송여부를 Y로 한 회원 목록만 가져 온다.
		List<ParamMap> mailUserList = sendMailDAO.getMailUserList();
		System.out.println("== 전송 대상 회원 명수 : " + mailUserList.size());
		
		// 보낼 사람이 없다면 넘긴다.
		if(mailUserList.isEmpty()) { return; }
		
		// 메일 컨텐츠 정보를 가져 온다.
		String content = this.getSendMailContents("10451");
		
		for(ParamMap mailUser : mailUserList) {
			
			mailUser.put("fromMail", fromMail);
			mailUser.put("fromName", fromName);
			
			// 제목을 셋팅한다.
			mailUser.put("subject", "(재)포항문화재단 공연/전시 안내 메일");
			
			// 내용을 셋팅 한다.
			// content 내용 중 #toName# 부분을 회원 이름으로 변경 처리 한다.
			content = content.replaceAll("#toName#", mailUser.getString("toName"));
			
			// content 내용 중 등록된 이미지 정보(공연/전시)를 포스터 이미지로 변경 작업 한다.
			List<ParamMap> fileList = (List<ParamMap>)paramMap.get("fileList");
			// 이미지는 무조건 한개가 들어가 있어야 된다??
			String upload_file_name = String.valueOf( fileList.get(0).get("upload_file_name") );
			String imgUrl = paramMap.getString("front_site_url") + "/common/file/download.do?upload_file_name=" + upload_file_name ;
			
			content = content.replaceAll("#front_site_url#", paramMap.getString("front_site_url"));
			content = content.replaceAll("#imgUrl#", imgUrl);
			content = content.replaceAll("#TITLE#", paramMap.getString("TITLE"));
			content = content.replaceAll("#PAGE_SEQ#", page_seq);
			
			mailUser.put("content", content);
		}
		
		MailUtil.sendHtmlMail(mailUserList, this, mail_tp, paramMap.getString("reg_id"));
		
	}
	
	private String getSendMailContents(String page_seq) {
		return sendMailDAO.getSendMailContents(page_seq);
	}
	
}
