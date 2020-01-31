package egovframework.phcf.indiplus.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.phcf.indiplus.service.IndiplusService;

@Controller
public class IndiplusController {
	
	@Resource(name="IndiplusService")
	private IndiplusService service;
	
	@RequestMapping(value="/indiplus/selectRestDay.do") 
	public ModelAndView selectRestDay(ModelMap model, @RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/indiplus/restDay");
		
		return mav;
	}
	
	@RequestMapping(value="/indiplus/selectRestDayListJson.do") 
	public ModelAndView selectRestDayListJson(ModelMap model, @RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		String pageIndex = EgovStringUtil.isNullToString(paramMap.get("pageIndex"));
		String pageSize = EgovStringUtil.isNullToString(paramMap.get("pageSize"));
		int pageOffset  = 0;
		if(!pageIndex.equals("") && !pageSize.equals("")) {
			pageOffset = (Integer.parseInt(pageIndex.toString())-1) * Integer.parseInt(pageSize.toString());
			paramMap.put("pageOffset", pageOffset + "");
		}
		
		List<HashMap<String, Object>> restDayList = service.selectRestDay(paramMap);
		int restDayListCnt = service.selectRestDayCnt();
		
		mav.addObject("restDayList", restDayList);
		mav.addObject("restDayListCnt", restDayListCnt);
		return mav;
	}
	
	@RequestMapping(value="/indiplus/insertRestDay.do", method=RequestMethod.POST) 
	public ModelAndView insertRestDay(ModelMap model, @RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		String date = EgovStringUtil.isNullToString(paramMap.get("DATE"));
		
		service.insertRestDay(date);
		
		return mav;
	}
	
	@RequestMapping(value="/indiplus/updateRestDay.do", method=RequestMethod.POST) 
	public ModelAndView updateRestDay(ModelMap model, @RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		String seq = EgovStringUtil.isNullToString(paramMap.get("SEQ"));
		String date = EgovStringUtil.isNullToString(paramMap.get("DATE"));
		
		service.updateRestDay(seq, date);
		
		return mav;
	}
	
	@RequestMapping(value="/indiplus/deleteRestDay.do", method=RequestMethod.POST) 
	public ModelAndView deleteRestDay(ModelMap model, @RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		String seq = EgovStringUtil.isNullToString(paramMap.get("SEQ"));
		service.deleteRestDay(seq);
		
		return mav;
	}
}
