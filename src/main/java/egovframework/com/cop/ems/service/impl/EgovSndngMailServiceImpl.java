package egovframework.com.cop.ems.service.impl;

import egovframework.com.cop.ems.service.EgovMultiPartEmail;
import egovframework.com.cop.ems.service.EgovSndngMailService;
import egovframework.com.cop.ems.service.SndngMailVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

/**
 * 메일 솔루션과 연동해서 이용해서 메일을 보내는 서비스 구현 클래스
 * @since 2011.09.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.09.09  서준식       최초 작성
 *  2011.12.06  이기하       메일 첨부파일이 기능 추가
 *  2013.05.23  이기하       메일 첨부파일이 없을 때 로직 추가
 *
 *  </pre>
 */
@Service("egovSndngMailService")
public class EgovSndngMailServiceImpl extends EgovAbstractServiceImpl implements EgovSndngMailService {

	@Resource(name = "egovMultiPartEmail")
	private EgovMultiPartEmail egovMultiPartEmail;

	/** SndngMailRegistDAO */
	@Resource(name = "sndngMailRegistDAO")
	private SndngMailRegistDAO sndngMailRegistDAO;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSndngMailServiceImpl.class);

	/**
	 * 메일을 발송한다
	 * @param vo SndngMailVO
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	@SuppressWarnings("unused")
	public boolean sndngMail(SndngMailVO sndngMailVO) throws Exception {

		String recptnPerson = (sndngMailVO.getRecptnPerson() == null) ? "" : sndngMailVO.getRecptnPerson(); // 수신자
		String subject = (sndngMailVO.getSj() == null) ? "" : sndngMailVO.getSj(); // 메일제목
		String emailCn = (sndngMailVO.getEmailCn() == null) ? "" : sndngMailVO.getEmailCn(); // 메일내용
		String atchmnFileNm = (sndngMailVO.getOrignlFileNm() == null) ? "" : sndngMailVO.getOrignlFileNm(); // 첨부파일이름
		String atchmnFilePath = (sndngMailVO.getFileStreCours() == null) ? "" : sndngMailVO.getFileStreCours(); // 첨부파일경로

		try {
			EmailAttachment attachment = new EmailAttachment();
			// 첨부파일이 있을 때
			if (atchmnFileNm != "" && atchmnFileNm != null && atchmnFilePath != "" && atchmnFilePath != null) {
				// 첨부할 attachment 정보를 생성합니다
				attachment.setPath(atchmnFilePath);
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				attachment.setDescription("첨부파일입니다");
				//attachment.setName(new String(atchmnFileNm.getBytes("UTF-8"),"latin1")); // 구버전의 경우 필요
				attachment.setName(atchmnFileNm);

				// 2015.05.08 주석수정 - 첨부파일 정보를 포함한 메일을 전송합니다 
				egovMultiPartEmail.send(recptnPerson, subject, emailCn, attachment);
			}
			else
			{
				// 메일을 전송합니다
				egovMultiPartEmail.send(recptnPerson, subject, emailCn);
			}

			Throwable t = new Throwable();

		} catch (MailParseException ex) {
			sndngMailVO.setSndngResultCode("F"); // 발송결과 실패
			sndngMailRegistDAO.updateSndngMail(sndngMailVO); // 발송상태를 DB에 업데이트 한다.
			LOGGER.error("Sending Mail Exception : {} [failure when parsing the message]", ex.getCause());
			return false;
		} catch (MailAuthenticationException ex) {
			sndngMailVO.setSndngResultCode("F"); // 발송결과 실패
			sndngMailRegistDAO.updateSndngMail(sndngMailVO); // 발송상태를 DB에 업데이트 한다.
			LOGGER.error("Sending Mail Exception : {} [authentication failure]", ex.getCause());
			return false;
		} catch (MailSendException ex) {
			sndngMailVO.setSndngResultCode("F"); // 발송결과 실패
			sndngMailRegistDAO.updateSndngMail(sndngMailVO); // 발송상태를 DB에 업데이트 한다.
			LOGGER.error("Sending Mail Exception : {} [failure when sending the message]", ex.getCause());
			return false;
		} catch (Exception ex) {
			sndngMailVO.setSndngResultCode("F"); // 발송결과 실패
			sndngMailRegistDAO.updateSndngMail(sndngMailVO); // 발송상태를 DB에 업데이트 한다.
			LOGGER.error("Sending Mail Exception : {} [unknown Exception]", ex.getCause());
			LOGGER.debug(ex.getMessage());
			return false;
		}

		return true;
	}
	
	
	
	/**
	 * 여러 메일을 한 번에 보낸다.
	 * 
	 * @author 김경민
	 * @version 2021-08-03
	 * @param emailAddresses 수신 메일 주소 목록
	 * @param dividedSize 한 번에 보낼 메일 수 (최대 10건)
	 * @param sndngMailVO
	 * @return boolean 메일 전송 성공/실패 여부
	 * @exception Exception
	 */
	@Override
	public boolean sendMultiMail(String[] emailAddresses, int dividedSize, SndngMailVO sndngMailVO) throws Exception {
		if(dividedSize > 10 || dividedSize <= 0) {
			throw new Exception("dividedSize should be 1~10");
		}
		try {
			int emailAddresseSize = emailAddresses.length;
			int partitionedFirstIndex = 0;
			
			do {
				String[] addressArr = new String[dividedSize]; 
				int addressIndex = 0;
				try {
					for(addressIndex = 0; addressIndex < dividedSize; addressIndex++) {
							addressArr[addressIndex] = emailAddresses[partitionedFirstIndex + addressIndex];
					}
					egovMultiPartEmail.send(addressArr, sndngMailVO.getSj(), sndngMailVO.getEmailCn());
					partitionedFirstIndex += dividedSize;
				}
				catch(ArrayIndexOutOfBoundsException e) {
					String[] lastArr =  Arrays.copyOf(addressArr, addressIndex);
					egovMultiPartEmail.send(lastArr, sndngMailVO.getSj(), sndngMailVO.getEmailCn());
					break;
				}
			}
			while(partitionedFirstIndex < emailAddresseSize);
			
		} catch (EmailException e) {
			LOGGER.error("error in sending multi emails");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			LOGGER.error("unknown error in sending multi emails");
			return false;
		}
		
		return true;
	}

}
