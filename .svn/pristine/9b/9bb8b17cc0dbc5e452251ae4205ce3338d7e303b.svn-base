package egovframework.phcf.statistic.web;

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
import egovframework.phcf.statistic.service.StatisticService;
import egovframework.phcf.util.JsonUtil;

/**
 * 통계 집계 결과 관련 컨트롤러 클래스
 * @author	권혜진
 * @since	2019-09-26
 * */
@Controller
public class StatisticController {

	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name="StatisticService")
	private StatisticService service;
	
	@SuppressWarnings("unchecked")
	@IncludedInfo(name="홈페이지통계", order=30000, gid=100) //name 즉, 메뉴명은 message-common_ko.properties에 등록해줘야 한다.
	@RequestMapping(value="/cms/statistic/phcfStatusReport.do")
	public String support(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {

		String conectCnt = service.selectConnectCnt();
		model.addAttribute("conectCnt", conectCnt);
		
		System.out.println("=== === MODEL에 담겼낭 : " + model);
		
		return "egovframework/phcf/statistic/statusReport";
	}
	
	@RequestMapping(value="/cms/statistic/selectMonthlyReport.do")
	public ModelAndView getMonthlyReport(ModelMap model) throws Exception {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		List<StatisticVO> monthlyList = service.selectMonthlyReport();
		mav.addObject("data", monthlyList);
		
		return mav;
		
	}
	
	@RequestMapping(value="cms/statistic/selectRcntBbsList.do")
	public String getRcntBbsList(ModelMap model) throws Exception {
		List<HashMap<String, Object>> rcntBbsList = service.selectRcntBbsList();
		model.addAttribute("value", rcntBbsList);
		return "jsonView";
	}
	
	@RequestMapping(value="/cms/statistic/selectPopulMenuList.do")
	public String getPopulMenuList(ModelMap model) throws Exception {
		List<HashMap<String, Object>> populMenuList = service.selectPopulMenuList();
		model.addAttribute("value", populMenuList);
		System.out.println("=== === model : " + model);
		return "jsonView";
	}
}
