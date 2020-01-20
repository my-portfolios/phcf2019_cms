package egovframework.phcf.premiumMember.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.json.Json;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.phcf.premiumMember.service.PremiumMemberService;
import egovframework.phcf.util.JsonUtil;


/**
 * 유료멤버십 관련 Controller
 * @author	김량래
 * @since	2019-11-11
 * */

@Controller
public class PremiumMemberController {
	@Resource(name="PremiumMemberService")
	private PremiumMemberService service;
	
	@RequestMapping(value="/premiumMember/selectMembershipRegList.do")
	public ModelAndView selectMembershipRegList(ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/premiumMember/view");
		
		return mav;
	}
	
	@RequestMapping(value="/premiumMember/selectMembershipRegListJson.do")
	public ModelAndView selectMembershipRegListJson(ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		String pageIndex = paramMap.get("pageIndex");
		String pageSize = paramMap.get("pageSize");
		int pageOffset  = 0;
		if(pageIndex != null && pageSize != null) {
			pageOffset = (Integer.parseInt(pageIndex.toString())-1) * Integer.parseInt(pageSize.toString());
			paramMap.put("pageOffset", pageOffset + "");
		}

		int payListCnt = service.selectMembershipRegListCnt(paramMap);
		List<HashMap<String, Object>> payList = service.selectMembershipRegList(paramMap);
		
		String payListJson = JsonUtil.getJsonArrayFromList(payList).toString();
		
		model.addAttribute("payListCnt",payListCnt);
		model.addAttribute("payListJson",payListJson);
		return mav;
	}
	
	@RequestMapping(value="/premiumMember/updateMembershipStatus.do", method=RequestMethod.POST)
	public ModelAndView updateMembershipStatus(@RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		
		service.updateMembershipStatus(paramMap);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		List<HashMap<String, Object>> payList = service.selectMembershipRegList(paramMap);
		
		hashMap.put("TYPE", payList.get(0).get("PRE_TYPE").toString());
		hashMap.put("ID", payList.get(0).get("MEM_ID").toString());
		
		String result = payList.get(0).get("RESULT").toString();
		mav.addObject("result",result);
		
		if(result.equals("Y")) {
			service.updateMembershipGrade(hashMap);
		}
		
		return mav;
	}
	
	
}
