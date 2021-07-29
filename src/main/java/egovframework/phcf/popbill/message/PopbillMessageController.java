package egovframework.phcf.popbill.message;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.popbill.api.MessageService;
import com.popbill.api.PopbillException;
import com.popbill.api.message.SenderNumber;

import egovframework.phcf.popbill.PopbillProperties;

@Controller
public class PopbillMessageController {
	
	@Autowired
    private MessageService messageService;
	
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
        String receiverName = "";

        // 메시지 내용, 90byte 초과된 내용은 삭제되어 전송
        String content = "반갑습니다. 문자 테스트입니다.";

        // 예약전송일시, null 처리시 즉시전송
        Date reserveDT = null;

        // 광고문자 전송여부
        Boolean adsYN = false;

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "test00000001";

        try {

            String receiptNum = messageService.sendSMS(corpNum, sender, receiver,
                    receiverName, content, reserveDT, adsYN, userID, requestNum);

//            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            // 예외 발생 시, e.getCode() 로 오류 코드를 확인하고, e.getMessage()로 오류 메시지를 확인합니다.
            System.out.println("오류 코드" + e.getCode());
            System.out.println("오류 메시지" + e.getMessage());
        }
		
		return "redirect:/performance/articleInfos.do";
		
	}
}
