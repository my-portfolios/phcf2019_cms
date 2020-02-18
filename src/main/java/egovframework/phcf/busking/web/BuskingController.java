package egovframework.phcf.busking.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.phcf.busking.BuskingGroupVO;
import egovframework.phcf.busking.service.BuskingService;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.util.JsonUtil;

@Controller
public class BuskingController {
	
	@Resource(name="BuskingService")
	private BuskingService service;
	
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@RequestMapping(value="/busking/buskingGroupList.do") 
	public ModelAndView buskingGroupList(ModelMap model, @RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/busking/groupList");
		/*List<String> resultCode = new ArrayList<>();
		for(CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC019", cmmUseService)) {
			resultCode.add(code.getCodeNm());
		}
		mav.addObject("resultCode",JsonUtil.getJsonArrayFromVOList(resultCode).toString());
		*/
		return mav;
	}
	
	@RequestMapping(value="/busking/selectGroupListToJson.do", method=RequestMethod.POST)
	public ModelAndView selectReservationListToJson(HttpServletRequest request, ModelMap model
			, @RequestParam HashMap<String, Object> paramMap
			, @ModelAttribute("buskingGroupVO") BuskingGroupVO buskingGroupVO
			) {
		ModelAndView mav = new ModelAndView("jsonView");
		System.out.println("여기보세요");
		System.out.println(buskingGroupVO.getArea());
		System.out.println(buskingGroupVO.getGenre());
		System.out.println(buskingGroupVO.getSearchKeyword());
		System.out.println(paramMap.get("genre"));
		System.out.println(paramMap.get("area"));
		try {
		
		Integer pageIndex = buskingGroupVO.getPageIndex();
		Integer pageSize = buskingGroupVO.getPageSize();
		
		int pageOffset  = 0;
		if(pageIndex != null && pageSize != null) {
			pageOffset = (Integer.parseInt(pageIndex.toString())-1) * Integer.parseInt(pageSize.toString());
			buskingGroupVO.setPageOffset(pageOffset);
		}
		
		int groupListCnt = service.selectBuskingGroupRegDefaultCnt(buskingGroupVO);
		List<HashMap<String, Object>> groupList = service.selectBuskingGroupRegList(buskingGroupVO);
		/*List<HashMap<String, Object>> mergedVenueReservationRegList = new ArrayList<>();*/
		
		String day[] = new String[5];
		/*String date, stTime, edTime;*/
		
		/*for(HashMap<String, Object> obj : groupList) {
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
		}*/
		
		String groupListJson = JsonUtil.getJsonArrayFromList(groupList).toString();
		
		mav.addObject("groupListCnt", groupListCnt);
		mav.addObject("groupListJson",groupListJson);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/busking/searchView.do")
	public ModelAndView searchView(HttpServletRequest request, ModelMap model
			, @RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/busking/search"); 
		
		List<String> genreCodeNmList = new ArrayList<>();
		List<String> timeCodeNmList = new ArrayList<>();
		List<String> areaCodeNmList = new ArrayList<>();
		
		
		for(CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC016", cmmUseService)) {
			genreCodeNmList.add(code.getCodeNm());
		}
		for(CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC015", cmmUseService)) {
			timeCodeNmList.add(code.getCodeNm());
		}
		for(CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC018", cmmUseService)) {
			areaCodeNmList.add(code.getCodeNm());
		}
		
		mav.addObject("genreCodeNmList",genreCodeNmList);
		mav.addObject("timeCodeNmList", timeCodeNmList);
		mav.addObject("areaCodeNmList",areaCodeNmList);
		
		
		return mav;
	}
	@RequestMapping(value="/busking/updateApprove.do")
	public ModelAndView updateApprove(HttpServletRequest request, ModelMap model
			, @RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		String result = "success";
		try {
			service.updateGroupApprove(paramMap);
		}
		catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}
		
		mav.addObject("result", result);
		return mav;
	}
	
}
