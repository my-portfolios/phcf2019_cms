package egovframework.phcf.artTour.web;

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
import egovframework.phcf.artTour.service.ArtTourService;


@Controller
public class ArtTourController {
	@Resource(name="ArtTourService")
	private ArtTourService service;
	
	@RequestMapping(value = "/artTour/list.do")
	public ModelAndView performanceList(@RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/artTour/list");

		return mav;
	}
	
	@RequestMapping(value = "/artTour/selectArtTourApplierListJson.do")
	public ModelAndView selectArtTourApplierListJson(@RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		Object pageIndex = paramMap.get("pageIndex");
		Object pageSize = paramMap.get("pageSize");
		int pageOffset  = 0;
		if(pageIndex != null && pageSize != null) {
			pageOffset = (Integer.parseInt(pageIndex.toString())-1) * Integer.parseInt(pageSize.toString());
			paramMap.put("pageOffset", pageOffset);
		}
		
		List<HashMap<String, Object>> performanceApplierList = service.selectArtTourApplierList(paramMap);
		int performanceAndApllierListCnt = service.selectArtTourApplierListCnt(paramMap);
		
		for(HashMap<String, Object> applier : performanceApplierList) {
			List<HashMap<String, Object>> applyVisitorList = service.selectAppliedVisitorArtTourList(applier);
			System.out.println("applyVisitorList " + applyVisitorList);
			
			String visitorInfo = "";
			for(HashMap<String, Object> visitor : applyVisitorList) {
				visitorInfo += visitor.get("PERSON_NAME").toString() + " | " + visitor.get("PERSON_GENDER").toString() +  " | " + visitor.get("PERSON_BIRTH").toString() +  " | " + visitor.get("PERSON_PHONE").toString() + "<br/>";
			}
			
			applier.put("visitorInfo", visitorInfo);
		}
		
		mav.addObject("artTourApplierList", performanceApplierList);
		mav.addObject("artTourApllierListCnt", performanceAndApllierListCnt);
		return mav;
	}
	
	@RequestMapping(value = "/artTour/updateArtTourApplierItem.do", method=RequestMethod.POST)
	public ModelAndView updateReservationMaster(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mav = new ModelAndView("jsonView");
		System.out.println("paramMap " + paramMap);
		String result = "success";
		try {
			service.updateArtTourApplierItem(paramMap);
		}
		catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}
		
		mav.addObject("result", result);
		return mav;
	}
	
}
