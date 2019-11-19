package egovframework.phcf.support.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.phcf.support.service.SupportService;
import egovframework.phcf.util.JsonUtil;

@Controller
public class SupportController {
	
	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "SupportService")
	SupportService supportService;
	
	@IncludedInfo(name="후원신청 관리", order=10000, gid=100)
	@RequestMapping(value="/cms/support/listView.do")
	public String support(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {

		// 회원종류를 가져온다.
		ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	voComCode.setCodeId("PHC004");
    	model.addAttribute("userTp", JsonUtil.getJsonArrayFromVOList( cmmUseService.selectCmmCodeDetail(voComCode)) );
		
		// 개인회원 등급 리스트를 가져온다.
		model.addAttribute("gradeList", JsonUtil.getJsonArrayFromList( supportService.selectGradeCodeList("PHC003")) );
		
		// 후원방식 코드 리스트를 가져온다.
		voComCode.setCodeId("PHC005");
		model.addAttribute("spMhTp", JsonUtil.getJsonArrayFromVOList( cmmUseService.selectCmmCodeDetail(voComCode) ));
		
		// 결제방식 코드 리스트를 가져온다.
		voComCode.setCodeId("PHC006");
		model.addAttribute("scPriceTp", JsonUtil.getJsonArrayFromVOList( cmmUseService.selectCmmCodeDetail(voComCode) ));
		
		return "egovframework/phcf/support/list";
	}
	
	@RequestMapping(value="/cms/support/getCmsSupportList.do")
	public String getCmsSupportList(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {
		
		System.out.println("== getCmsSupportList paramMap : " + paramMap);
		
		// 리스트 및 total count 값 적용..
		List<HashMap<String, Object>> cmsSupportList = supportService.selectCmsSupportList(paramMap);
		model.addAttribute("value", cmsSupportList);
		// 전체 건수
		int totalCnt = supportService.selectCmsSupportCnt(paramMap);
		model.addAttribute("totCnt", totalCnt);
		
		return "jsonView";
	}
	
	@RequestMapping(value="/cms/support/updateCmsSupportItem.do")
	public String updateCmsSupportItem(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {
		
		System.out.println("== updateCmsSupportItem paramMap : " + paramMap);
		
		supportService.updateCmsSupportItem(paramMap);
		
		model.addAttribute("msg", "success");
		
		return "jsonView";
	}
	
	@RequestMapping(value="/cms/support/deleteCmsSupportItem.do")
	public String deleteCmsSupportItem(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {
		
		System.out.println("== deleteCmsSupportItem paramMap : " + paramMap);
		
		supportService.deleteCmsSupportItem(paramMap);
		
		model.addAttribute("msg", "success");
		
		return "jsonView";
	}
}