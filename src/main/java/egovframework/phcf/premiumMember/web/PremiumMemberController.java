package egovframework.phcf.premiumMember.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.phcf.premiumMember.service.PremiumMemberService;


/**
 * 유료멤버십 관련 Controller
 * @author	김량래
 * @since	2019-11-11
 * */

@Controller
public class PremiumMemberController {
	@Resource(name="PremiumMemberService")
	private PremiumMemberService service;
	
	@IncludedInfo(name="유료멤버십신청관리", order=40000, gid=100) //name 즉, 메뉴명은 message-common_ko.properties에 등록해줘야 한다.
	@RequestMapping(value="/premiumMember/selectMembershipRegList.do")
	public String selectMembershipRegList(ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		
		List<HashMap<String, String>> payList = service.selectMembershipRegList(paramMap);
		
		model.addAttribute("payList",payList);
		return "egovframework/phcf/premiumMember/view";
	}
	
	@RequestMapping(value="/premiumMember/updateMembershipStatus.do", method=RequestMethod.POST)
	public ModelAndView updateMembershipStatus(@RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		service.updateMembershipStatus(paramMap);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		List<HashMap<String, String>> payList = service.selectMembershipRegList(paramMap);
		
		hashMap.put("TYPE", payList.get(0).get("PRE_TYPE"));
		hashMap.put("ID", payList.get(0).get("MEM_ID"));
		
		String result = payList.get(0).get("RESULT");
		mav.addObject("result",result);
		
		if(result.equals("Y")) {
			service.updateMembershipGrade(hashMap);
		}
		
		return mav;
	}
	
	
}
