package egovframework.phcf.common.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.phcf.common.service.CommonService;
import egovframework.phcf.hubizCommonMethod.CommonMethod;



/**
 * 공통으로 겹치는 페이지 관련 Controller
 * @author	권혜진
 * @since	2019-11-25
 * */

@Controller
public class CommonPageController {
	
	@Resource(name="CommonService")
	private CommonService service;
	
	@RequestMapping(value="/munhwaArt/subMain.do")
	public ModelAndView subMain(HttpServletRequest request,@RequestParam(value="place", required = false) String place, @RequestParam(value="gubun", required = false) String gubun) throws Exception {
		

		//		jspPath 지정
		String jspPath = "/WEB-INF/jsp/egovframework/phcf/munhwaArtCenter/subMain.jsp";
		ModelAndView mav = CommonMethod.ContentIntoTemplate(request, jspPath, null);

		return mav;
	}
	
	
	// 공연/전시 리스트에서 접수하기 버튼 눌러서 폼을 보여준다.
	@RequestMapping(value="/applyForm.do")
	public ModelAndView applyForm(HttpServletRequest request, @RequestParam(value="id", required = false) String added_id, @RequestParam(value="ntt_id", required = false) String ntt_id) throws Exception {
		
		String mavUrl = "template/gongyeon/form"; 
		ModelAndView mav = new ModelAndView(mavUrl);  

		HashMap<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("nttId", request.getParameter("nttId"));
		resultMap.put("addedId", request.getParameter("id"));
		resultMap.put("ac3", request.getParameter("ac3"));
		resultMap.put("ac19", request.getParameter("ac19"));
		resultMap.put("nttSj", request.getParameter("nttSj"));
		
		return mav;
	}
	
	@RequestMapping(value="/insertInfo.do")
	public void insertInfo(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("ntt_id", request.getParameter("nttId"));
		paramMap.put("added_id", request.getParameter("addedId"));
		paramMap.put("performance_date", request.getParameter("performance_date"));
		paramMap.put("nth_round", request.getParameter("nthRound"));
		System.out.println("=== do you have it ? "  + paramMap);
		
		
//		String jspPath = "/WEB-INF/jsp/egovframework/phcf/munhwaArtCenter/municipalArtCmpny.jsp";
//		String templatePath = "template/_layout/basic";
//		ModelAndView mav = CommonMethod.ContentIntoTemplate(request, templatePath, jspPath);

//		service.insertInfo()
		
//		return mav;
	}
	
	@RequestMapping(value="/availableChk")
	public ModelAndView availableChk(@RequestParam(value="NTT_ID") String ntt_id, @RequestParam(value="ID") String added_id) throws Exception {
		
//		HashMap<String, Integer> chkMap = service.selectCurrentCnt(added_id);
//		if(chkCnt <= )
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", service.getUserChk(added_id));
		
		return mav;
	}
}
