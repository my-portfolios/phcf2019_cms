package egovframework.phcf.venueReservation.web;

import java.util.ArrayList;

/**
 * @Class Name  : VenueReservationController.java
 * @Description : 대관 신청 관리 Controller
 * @Modification Information
 * @
 * @  수정일             수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2019.12.03   김량래              최초 생성
 *
 *  @author 대외사업부
 *  @since 2019.12.03
 *  @version 1.0
 *  @see
 *
 */


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

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.util.JsonUtil;
import egovframework.phcf.venueReservation.service.VenueReservationService;

@Controller
public class VenueReservationController {
	
	@Resource(name="VenueReservationService")
	private VenueReservationService service;
	
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	 
	@Resource(name="EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	@RequestMapping(value="/venueReservation/selectReservationList.do")
	public ModelAndView selectReservationList(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, String> paramMap) {
		ModelAndView mav = new ModelAndView("egovframework/phcf/venueReservation/list"); 
		
		return mav;
	}
	
	@RequestMapping(value="/venueReservation/selectReservationCalendar.do")
	public ModelAndView selectReservationCalendar(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, String> paramMap) {
		ModelAndView mav = new ModelAndView("egovframework/phcf/venueReservation/calendar"); 
		
		return mav;
	}
	
	@RequestMapping(value="/venueReservation/searchView.do")
	public ModelAndView searchView(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, String> paramMap) {
		ModelAndView mav = new ModelAndView("egovframework/phcf/venueReservation/search"); 
		
		return mav;
	}
	
	@RequestMapping(value="/venueReservation/selectReservationManageList.do")
	public ModelAndView selectReservationManageList(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/venueReservation/manage");
		
		mav.addObject("codeList",service.selectDetailCodeList("PHC007"));
		mav.addObject("venueReservationMaster",service.selectVenueReservationMaster());	
		
		return mav;
	}
	
	@RequestMapping(value="/venueReservation/selectReservationListToJson.do", method=RequestMethod.POST)
	public ModelAndView selectReservationListToJson(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
		
		Object pageIndex = paramMap.get("pageIndex");
		Object pageSize = paramMap.get("pageSize");
		int pageOffset  = 0;
		if(pageIndex != null && pageSize != null) {
			pageOffset = (Integer.parseInt(pageIndex.toString())-1) * Integer.parseInt(pageSize.toString());
			paramMap.put("pageOffset", pageOffset);
		}

		int venueReservationRegListCnt = service.selectVenueReservationRegListCnt(paramMap);
		List<HashMap<String, Object>> venueReservationRegList = service.selectVenueReservationRegList(paramMap);
		List<HashMap<String, Object>> mergedVenueReservationRegList = new ArrayList<>();
		
		String day[] = new String[5];
		String date, stTime, edTime;
		
		System.out.println("venueReservationRegList" + venueReservationRegList);
		for(HashMap<String, Object> obj : venueReservationRegList) {
			HashMap<String, Object> newObj = new HashMap<>();
			newObj.putAll(obj);
			
			for(int i=1;i<=5;i++) {
				date = "";stTime = "";edTime = "";
				if(obj.get("USE_DATE" + i) != null) {
					date = obj.get("USE_DATE" + i).toString();
					stTime = obj.get("USE_START_TIME" + i).toString().substring(0,5);
					edTime = obj.get("USE_END_TIME" + i).toString().substring(0,5);
					day[i-1] = date + "<br/>"  + stTime + " ~ " + edTime;
					newObj.put("USE_DATE" + i, day[i-1]);
				}
			}			
			mergedVenueReservationRegList.add(newObj);
		}
		
		String venueReservationRegJson = JsonUtil.getJsonArrayFromList(mergedVenueReservationRegList).toString();
		
		mav.addObject("venueReservationRegListCnt", venueReservationRegListCnt);
		mav.addObject("venueReservationRegJson",venueReservationRegJson);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/venueReservation/updateReservationItem.do", method=RequestMethod.POST)
	public ModelAndView updateReservationItem(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mav = new ModelAndView("jsonView");
		
		String result = "success";
		try {
			service.updateReservationItem(paramMap);
		}
		catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}
		
		mav.addObject("result", result);
		return mav;
	}
	
	@RequestMapping(value="/venueReservation/updateReservationMaster.do", method=RequestMethod.POST)
	public ModelAndView updateReservationMaster(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mav = new ModelAndView("jsonView");
		
		String result = "success";
		try {
			service.updateReservationMaster(paramMap);
		}
		catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}
		
		mav.addObject("result", result);
		return mav;
	}
	
	
	/**
	 * 코드를 내용으로 변환
	 * @param code 영문 시설
	 * @return code 한글 시설
	 */
	private String codeToString(String code) throws Exception {
		List<HashMap<String, Object>> codeList = service.selectDetailCodeList("PHC007");
		String placeName = null;
		
		for(int i=0;i<codeList.size();i++) {
			if(codeList.get(i).get("CODE").equals(code)) {
				placeName = codeList.get(i).get("CODE_NM").toString();
			}
		}
		if(placeName==null) placeName = "error";
		
		return placeName;
	}
}
