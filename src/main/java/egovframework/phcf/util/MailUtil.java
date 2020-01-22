package egovframework.phcf.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;

import egovframework.phcf.common.service.ParamMap;
import egovframework.phcf.common.service.SendMailService;
import com.sun.mail.smtp.SMTPAddressFailedException;

public class MailUtil {
	
	/**
	 * 입력받은 email이 패턴에 맞는지 확인해 돌려 준다.
	 * @param email : 검사 대상 email 주소
	 * @return true, false
	 */
	public static boolean isEmailPattern(String email){
	    Pattern pattern=Pattern.compile("\\w+[@]\\w+\\.\\w+");
	    Matcher match=pattern.matcher(email);
	    return match.find();
   }
	
	/**
	 * 메일 발송을 위한 SMTP host, port 및 auth, ssl 설정 정보등을 셋팅하여 Session 정보렬 돌려준다.
	 * @return
	 */
	public static Session getSessionInfo() {
		
		Session session = null;
	
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", PropertiesUtil.getValue("mail_host"));
			props.put("mail.smtp.port", PropertiesUtil.getValue("mail_port"));
			props.put("mail.debug", "true");
			props.put("mail.debug.auth", "true");
			props.put("mail.smtp.auth", "true");
	        
			String emailId = PropertiesUtil.getValue("mail_id");
			String emailPw = PropertiesUtil.getValue("mail_pw");
			
			System.out.println("== emailId : " + emailId);
			System.out.println("== emailPw : " + emailPw);
			
			if (StringUtils.isNotEmpty(emailId) && StringUtils.isNotEmpty(emailPw)) {
				session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropertiesUtil.getValue("mail_id"), PropertiesUtil.getValue("mail_pw"));
					}
				});
			}
			else {
				session = Session.getDefaultInstance(props, null);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return session;
		}
		
		return session;
	}
	
	/**
	 * 메일 발송
	 * @param toMail : 받는 사람 메일
	 * @param toName : 받는 사람 이름
	 * @param fromMail : 보내는 사람 메일
	 * @param formName : 보내는 사람 이름
	 * @param subject : 제목
	 * @param content : 내용
	 * @return
	 */
	public static boolean sendHtmlMail(String toMail, String toName, String fromMail, String formName, String subject, String content, SendMailService sendMailService, String mail_tp, String create_id) {
		
		try {
			
			Session session = getSessionInfo();
			if(session == null) { throw new NullPointerException("session 정보를 확득 할 수 없습니다."); }

			Multipart mp = new MimeMultipart();
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromMail));

			InternetAddress[] toAddress = InternetAddress.parse(toMail);
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject(subject, "UTF-8");
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(content, "text/html; charset=UTF-8");
			
			mp.addBodyPart(mbp1);
			msg.setContent(mp);
			msg.setSentDate(new Date());

			Transport.send(msg);
			
			// 메일 전송 로그를 기록 한다.
			sendMailService.insertSendMailLog(mail_tp, create_id, toMail, toName);
			
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(" sendHtmlMail makes exception");
			return false;
		}
	}
	
	/**
	 * session은 한번만 Auth 받고 나서 여러번 동시에 날릴 수 있는 기능이 필요함...
	 * 매번 보낼때 마다 Auth를 받을 필요는 없을 듯 함..
	 * @param sendMailList 
	 *   paramMap info : { String toMail, String toName, String fromMail, String formName, String subject, String content }
	 * @return
	 */
	public static boolean sendHtmlMail(List<ParamMap> sendMailList, SendMailService sendMailService, String mail_tp, String create_id) {
		
		try {
			
			Session session = getSessionInfo();
			if(session == null) { throw new NullPointerException("session 정보를 확득 할 수 없습니다."); }
			
			for(ParamMap paramMap : sendMailList) {
				
				try {
				
					Multipart mp = new MimeMultipart();
					MimeMessage msg = new MimeMessage(session);
					
					msg.setFrom(new InternetAddress(paramMap.getString("fromMail")));
		
					InternetAddress[] toAddress = InternetAddress.parse(paramMap.getString("toMail"));
					msg.setRecipients(Message.RecipientType.TO, toAddress);
					msg.setSubject(paramMap.getString("subject"), "UTF-8");
					MimeBodyPart mbp1 = new MimeBodyPart();
					mbp1.setContent(paramMap.getString("content"), "text/html; charset=UTF-8");
					
					mp.addBodyPart(mbp1);
					msg.setContent(mp);
					msg.setSentDate(new Date());
		
					Transport.send(msg);
					
					// 메일 전송 로그를 기록 한다.
					sendMailService.insertSendMailLog(mail_tp, create_id, paramMap.getString("toMail"), paramMap.getString("toName"));
					
				} catch (AddressException aex) {
					// 메일 Address를 가져 오는  부분에서 입력이 잘못 되었을 경우 Exception 이 발생한다.
					// 사용자가 입력을 잘못 하였을 경우 이므로 스킵 하고 다음 메일 전송을 실행한다. 
					System.out.println("== 잘못 입력된 mail 주소가 있음");
					aex.printStackTrace();
					continue;
				} catch(SMTPAddressFailedException sfex) {
					System.out.println("== SMTP 주소 접근에 실패함");
					sfex.printStackTrace();
					continue;
				} catch(Exception ex) {
					System.out.println("== 메일 발송 실패");
					ex.printStackTrace();
					continue;
				}
			}
			
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}	
}
