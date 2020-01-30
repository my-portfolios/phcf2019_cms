package egovframework.phcf.common.web;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.phcf.common.PhcfMailer;

@Controller
public class PhcfMailSenderController {
	
	@Autowired
	private PhcfMailer phcfMailer;  //사용

	@RequestMapping(value = "/phcf/sendmail.do")
	@ResponseBody
	public String sendmail(@RequestParam HashMap<Object, Object> paramMap) {
		try {
			phcfMailer.sendMail("endlesstar@naver.com", "이것은 제목", "스프링으로 구현해서 보내본다.\n파일없이 보낸다.");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";			
		}
		
		/*try {
			phcfMailer.sendMail("받는대상", "이것은 제목", "스프링으로 구현해서 보내본다.","E:/파일위치","보낼파일명.확장자");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";			
		}	*/	
		return "SUCC";
	}	
}
