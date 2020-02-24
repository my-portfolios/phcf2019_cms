package egovframework.phcf.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cop.bbs.service.BoardAddedColmnsVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.phcf.performance.service.PerformanceService;

@Controller
public class PerformanceController {
	@Resource(name="PerformanceService")
	PerformanceService service;
	
	@Resource(name="EgovArticleService")
	EgovArticleService egovArticleService;
	
	
	
	@RequestMapping(value = "/performance/list.do")
	public ModelAndView performanceList(@RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/performance/list");

		return mav;
	}
	
	@RequestMapping(value = "/performance/selectPerformanceApplierListJson.do")
	public ModelAndView selectPerformanceApplierListJson(@RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		List<HashMap<String, Object>> performanceAndApllierList = new ArrayList<>();
		List<HashMap<String, Object>> performanceApplierList = service.selectPerformanceApplierList(paramMap);
		int performanceAndApllierListCnt = service.selectPerformanceApplierListCnt(paramMap);
		
		for(HashMap<String, Object> applier : performanceApplierList) {
			if(Integer.parseInt(applier.get("ORD").toString()) != 0) continue;
			BoardVO boardVO = new BoardVO();
			boardVO.setBbsId(applier.get("BBS_ID").toString());
			boardVO.setNttId(Long.parseLong(applier.get("NTT_ID").toString()));
			
			boardVO = egovArticleService.selectArticleDetail(boardVO);
			List<BoardAddedColmnsVO> addedColmnList = egovArticleService.selectArticleAddedColmnsDetail(boardVO);
			
			HashMap<String, Object> applyInfo = new HashMap<>();
			applyInfo.putAll(applier);
			
			String visitorInfo = "";
			for(HashMap<String, Object> visitor : performanceApplierList) {
				if(visitor.get("APL_ID").equals(applier.get("APL_ID"))) {
					visitorInfo += visitor.get("PERSON_NAME").toString() + " " + visitor.get("PERSON_PHONE").toString() + "<br/>";
				}
			}
			
			applyInfo.put("visitorInfo", visitorInfo);
			applyInfo.put("subject", boardVO.getNttSj());
			applyInfo.put("author", boardVO.getNtcrNm());
			applyInfo.put("category", boardVO.getCateName());
			
			for(BoardAddedColmnsVO addedColmn : addedColmnList) {
				if(addedColmn.getOrd() == 0) {
					applyInfo.put("cost", addedColmn.getAc5());
				}
				if(addedColmn.getOrd() == Integer.parseInt(applier.get("EPISODE").toString())-1) applyInfo.put("date", addedColmn.getAc3());
			}
			
			performanceAndApllierList.add(applyInfo);
		}
		
		mav.addObject("performanceAndApllierList", performanceAndApllierList);
		mav.addObject("performanceAndApllierListCnt", performanceAndApllierListCnt);
		return mav;
	}
	
	@RequestMapping(value = "/performance/updatePerformanceApplierItem.do", method=RequestMethod.POST)
	public ModelAndView updateReservationMaster(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mav = new ModelAndView("jsonView");
		System.out.println("paramMap " + paramMap);
		String result = "success";
		try {
			service.updatePerformanceApplierItem(paramMap);
		}
		catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}
		
		mav.addObject("result", result);
		return mav;
	}
	
}
