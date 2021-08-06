package egovframework.phcf.popbill.message.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.popbill.api.MessageService;
import com.popbill.api.PopbillException;
import com.popbill.api.message.Message;

import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.popbill.PopbillProperties;
import egovframework.phcf.popbill.message.service.PopbillMessageService;
import egovframework.phcf.popbill.message.service.PopbillMessageVO;

@Service("PopbillMessageService")
public class PopbillMessageServiceImpl extends PopbillProperties implements PopbillMessageService {
	
	@Autowired
	MessageService messageService;
	
	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;
	
	@Resource(name = "PopbillMessageDAO")
	private PopbillMessageDAO popbillMessageDAO;
	
	@Override
	public void insertRegistMessage(PopbillMessageVO messageVO) {
		popbillMessageDAO.insertRegistMessage(messageVO);
	}
	
	@Override
	public void sendSmsSingle(String receiver, String content, Date reserveDT, boolean adsYn) throws Exception {
		try {
			String requestNum =  getMessageRequestNumber("SS");
			String receiptNum = messageService.sendSMS(POPBILL_CORP_NUM, POPBILL_SENDER_NUM, receiver, "고객",
	                 content, reserveDT, adsYn, POPBILL_USER_ID, requestNum);
			
			PopbillMessageVO messageVO = new PopbillMessageVO();
			messageVO.setReceiptNum(receiptNum);
			messageVO.setRequestNum(requestNum);
			messageVO.setServiceType(SMS_SINGLE);
			messageVO.setSender(POPBILL_SENDER_NUM);
			messageVO.setReceiver(receiver);
			messageVO.setContent(content);
			messageVO.setReserveDT(reserveDT);
			messageVO.setAdsYn(adsYn);
			
			
			insertRegistMessage(messageVO);
		
			} catch (PopbillException e) {
				System.out.println("오류 코드" + e.getCode());
	            System.out.println("오류 메시지" + e.getMessage());
			}
	}
	
	@Override
	public void sendSmsMulti(String content, Date reserveDT, boolean adsYN, boolean toNormal, boolean toMembership)
			throws Exception {
		
		HashMap<String, Object> searchMap = new HashMap<>();
		
		searchMap.put("toNormal", toNormal);
		searchMap.put("toMembership", toMembership);
		
		List<MberManageVO> targetMberList= mberManageService.selectMberListForSndngMail(searchMap);
		System.out.println("target size===" + targetMberList.size());
		Message[] messages = new Message[targetMberList.size()];
		int msgSize = messages.length;
		for(int index = 0; index < msgSize; index++) {
			messages[index] = new Message();
			String mberPhoneNo = targetMberList.get(index).getMoblphonNo();
			System.out.println("mberPhoneNo==" + mberPhoneNo);
			messages[index].setReceiver(mberPhoneNo);
		}
		/*try {
		String receiptNum = messageService.sendSMS(POPBILL_CORP_NUM, POPBILL_SENDER_NUM, POPBILL_SENDER_NAME, 
                 content, messages, reserveDT, adsYN, POPBILL_USER_ID, getMessageRequestNumber("SM"));
	
		} catch (PopbillException e) {
			System.out.println("오류 코드" + e.getCode());
            System.out.println("오류 메시지" + e.getMessage());
		}*/
		
		
	}
	
	
	@Override
	public void sendLmsSingle(String receiver, String receiverName, String subject, String content, Date reserveDT, boolean adsYn)
			throws Exception {
		
		try {
			String requestNum = getMessageRequestNumber("LS");
			String receiptNum = messageService.sendLMS(POPBILL_CORP_NUM, POPBILL_SENDER_NUM, receiver, receiverName, subject,
	                 content, reserveDT, adsYn, POPBILL_USER_ID, requestNum);
			
			PopbillMessageVO messageVO = new PopbillMessageVO();
			messageVO.setReceiptNum(receiptNum);
			messageVO.setRequestNum(requestNum);
			messageVO.setServiceType(LMS_SINGLE);
			messageVO.setSender(POPBILL_SENDER_NUM);
			messageVO.setSubject(subject);
			messageVO.setReceiver(receiver);
			messageVO.setReceiverName(receiverName);
			messageVO.setContent(content);
			messageVO.setReserveDT(reserveDT);
			messageVO.setAdsYn(adsYn);
			
			
			insertRegistMessage(messageVO);
	
		} catch (PopbillException e) {
			System.out.println("오류 코드" + e.getCode());
            System.out.println("오류 메시지" + e.getMessage());
		}
	}
	
	@Override
	public void sendLmsMulti(String subject, String content, Date reserveDT, boolean adsYN, boolean toNormal,
			boolean toMembership) throws Exception {
		HashMap<String, Object> searchMap = new HashMap<>();
		
		searchMap.put("toNormal", toNormal);
		searchMap.put("toMembership", toMembership);
		
		List<MberManageVO> targetMberList= mberManageService.selectMberListForSndngMail(searchMap);
		System.out.println("target size===" + targetMberList.size());
		Message[] messages = new Message[targetMberList.size()];
		int msgSize = messages.length;
		for(int index = 0; index < msgSize; index++) {
			messages[index] = new Message();
			String mberPhoneNo = targetMberList.get(index).getMoblphonNo();
			System.out.println("mberPhoneNo==" + mberPhoneNo);
			messages[index].setReceiver(mberPhoneNo);
		}
		/*try {
		String receiptNum = messageService.sendLMS(POPBILL_CORP_NUM, POPBILL_SENDER_NUM, POPBILL_SENDER_NAME, subject,
                 content, messages, reserveDT, adsYN, POPBILL_USER_ID, getMessageRequestNumber("LM"));
	
		} catch (PopbillException e) {
			System.out.println("오류 코드" + e.getCode());
            System.out.println("오류 메시지" + e.getMessage());
		}*/
		
	}
	
	@Override
	public void sendLmsMulti(PopbillMessageVO messageVO) throws Exception {
		try {
			String requestNum = getMessageRequestNumber("LM");
			String receiptNum = messageService.sendLMS(POPBILL_CORP_NUM, messageVO.getSender(), messageVO.getSenderName(), messageVO.getSubject(),
	                 messageVO.getContent(), messageVO.getReceivers(), messageVO.getReserveDT(), messageVO.isAdsYn(), POPBILL_USER_ID, requestNum);
			
			messageVO.setReceiptNum(receiptNum);
			messageVO.setRequestNum(requestNum);
			messageVO.setServiceType(LMS_MULTI);
			messageVO.setNumReceiver(messageVO.getReceivers().length);
			
			insertRegistMessage(messageVO);
			
		} catch (PopbillException e) {
			System.out.println("오류 코드" + e.getCode());
            System.out.println("오류 메시지" + e.getMessage());
		}
	}
	
	@Override
	public void sendMmsMulti(String subject, String content, File file, Date reserveDT, boolean adsYN, boolean toNormal,
			boolean toMembership) throws Exception {
		HashMap<String, Object> searchMap = new HashMap<>();
		
		searchMap.put("toNormal", toNormal);
		searchMap.put("toMembership", toMembership);
		
		List<MberManageVO> targetMberList= mberManageService.selectMberListForSndngMail(searchMap);
		System.out.println("target size===" + targetMberList.size());
		Message[] messages = new Message[targetMberList.size()];
		int msgSize = messages.length;
		for(int index = 0; index < msgSize; index++) {
			messages[index] = new Message();
			String mberPhoneNo = targetMberList.get(index).getMoblphonNo();
			System.out.println("mberPhoneNo==" + mberPhoneNo);
			messages[index].setReceiver(mberPhoneNo);
		}
		/*try {
		String receiptNum = messageService.sendMMS(POPBILL_CORP_NUM, POPBILL_SENDER_NUM, POPBILL_SENDER_NAME, subject,
                 content, messages, file, reserveDT, adsYN, POPBILL_USER_ID, getMessageRequestNumber("MM"));
	
		} catch (PopbillException e) {
			System.out.println("오류 코드" + e.getCode());
            System.out.println("오류 메시지" + e.getMessage());
		}*/
		
	}

	@Override
	public String getMessageRequestNumber(String serviceIdentifier) {
		String randomText = "";
    	for (int i = 1; i <= 8; i++) {
    		randomText += EgovStringUtil.getRandomStr('A', 'Z').toUpperCase();
    	}
    	
		return serviceIdentifier + "_" + CommonMethod.getTodayDate("yyyyMMddHHmmssSSS") + "-" + randomText;
	}

	

}
