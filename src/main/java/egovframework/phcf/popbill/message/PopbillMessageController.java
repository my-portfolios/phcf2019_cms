package egovframework.phcf.popbill.message;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.popbill.api.MessageService;
import com.popbill.api.PopbillException;
import com.popbill.api.message.Message;
import com.popbill.api.message.SenderNumber;

import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.popbill.PopbillProperties;
import egovframework.phcf.popbill.message.service.PopbillMessageService;
import egovframework.phcf.popbill.message.service.PopbillMessageVO;
import egovframework.phcf.popbill.message.service.impl.PopbillMessageServiceImpl;

@Controller
public class PopbillMessageController {
	
	@Autowired
    private MessageService messageService;
	
	@Resource(name = "PopbillMessageService")
	private PopbillMessageService popbillMessageService;
	
	@RequestMapping(value = "/message/test.do")
	public String test(ModelMap model, HttpServletRequest request)
			throws Exception{
		
		
		// 팝빌회원 사업자번호
    	String corpNum = PopbillProperties.POPBILL_CORP_NUM;
		System.out.println("corpnum==" + PopbillProperties.POPBILL_CORP_NUM);
		
//    	String corpNum = "5068131922";
    	// 팝빌회원 아이디
    	String userID = PopbillProperties.POPBILL_USER_ID;
//    	String userID = "hubizict";
    	SenderNumber[] senderNumberList = messageService.getSenderNumberList(corpNum, userID);
    	for(SenderNumber sn : senderNumberList) {
    		System.out.println("sendernumber=="+sn.getNumber());
    	}

        // 발신번호
        String sender = PopbillProperties.POPBILL_SENDER_NUM;

        // 수신번호
        String receiver = "01082838052";

        // 수신자명
        String receiverName = null;

        // 메시지 내용, 90byte 초과된 내용은 삭제되어 전송
        String content = "문자 테스트입니다.";
        String content_lms = "LMS 문자 테스트입니다.~~~~!!! test test test LMSLMSLMSLMS !@#$%^&*() 문자테스트 문자테스트 문자테스트 문자테스트 abcdefgabcdefg";
        String content_ad = "(광고) 포항 문화 재단 \n 내용 수신거부 080-xxxx-xxxx";
        
        String subject = "테스트 문자";
        // 예약전송일시, null 처리시 즉시전송
        Date testDate = new Date();
        String currentDateStr = CommonMethod.getTodayDate("yyyyMMdd");
       
        testDate = CommonMethod.stringToDate(currentDateStr + "104030", "yyyyMMddHHmmss");
        Date reserveDT = testDate;
        
        System.out.println("testDate==" + testDate);
        System.out.println("testDate==" + CommonMethod.dateToString(testDate, "yyyy년MM월dd일HH시mm분ss초"));
        // 광고문자 전송여부
        Boolean adsYN = false;
        
        Message[] receivers = new Message[2];
		receivers[0] = new Message();
		receivers[0].setReceiver("01082838052");
		
		
        PopbillMessageVO messageVO = new PopbillMessageVO();
        messageVO.setReceiver(receiver);
        messageVO.setReceiverName(receiverName);
        messageVO.setSubject(subject);
        messageVO.setContent(content);
        messageVO.setReceivers(receivers);
        messageVO.setReserveDT(reserveDT);
        messageVO.setAdsYn(adsYN);
 
//        	popbillMessageService.sendSmsSingle(receiver, content, null, false);
//        	popbillMessageService.sendSmsSingle(content, reserveDT, adsYN, true, true);
    	popbillMessageService.sendLmsMulti(messageVO);
//    	popbillMessageService.sendLmsSingle(receiver, receiverName, null, content_lms, null, adsYN);
        	
//            String receiptNum = messageService.sendSMS(corpNum, sender, receiver,
//                    receiverName, content, reserveDT, adsYN, userID, requestNum);
            
//            String receiptNum = messageService.sendSMS(corpNum, sender, receiver,
//                    receiverName, content, null, adsYN, userID, requestNum);
          
//            m.addAttribute("Result", receiptNum);
        	

		
		return "redirect:/";
		
	}
}
