package egovframework.phcf.hallreq.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.phcf.hallreq.service.HallReqService;
import egovframework.phcf.util.JsonUtil;

/**
 * 대관 신청 관련 컨트롤러 클래스
 * @author	권혜진
 * @since	2019-09-26
 * */
@Controller
public class HallReqController {

	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name="HallReqService")
	private HallReqService service;
	
	@SuppressWarnings("unchecked")
	@IncludedInfo(name="대관신청관리", order=30100, gid=100) //name 즉, 메뉴명은 message-common_ko.properties에 등록해줘야 한다.
	@RequestMapping(value="/cms/hallreq/phcfHallReqView.do")
	public String support(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {

//		String conectCnt = service.getConnectCnt();
//		model.addAttribute("conectCnt", conectCnt);
		
		
		return "egovframework/phcf/hallreq/phcfHallReqView";
	}
	
//	@RequestMapping(value="/cms/statistic/getMonthlyReport.do")
//	public ModelAndView getMonthlyReport(ModelMap model) throws Exception {
//		
//		ModelAndView mav = new ModelAndView("jsonView");
//		
//		List<HallReqVO> monthlyList = service.getMonthlyReport();
//		mav.addObject("data", monthlyList);
//		
//		return mav;
//		
//	}
//	
//	@RequestMapping(value="cms/statistic/getRcntBbsList.do")
//	public String getRcntBbsList(ModelMap model) throws Exception {
//		List<HashMap<String, Object>> rcntBbsList = service.getRcntBbsList();
//		model.addAttribute("value", rcntBbsList);
//		return "jsonView";
//	}
//	
//	@RequestMapping(value="/cms/statistic/getPopulMenuList.do")
//	public String getPopulMenuList(ModelMap model) throws Exception {
//		List<HashMap<String, Object>> populMenuList = service.getpopulMenuList();
//		model.addAttribute("value", populMenuList);
//		System.out.println("=== === model : " + model);
//		return "jsonView";
//	}
}
