package egovframework.phcf.busking.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.phcf.busking.BuskingGroupVO;
import egovframework.phcf.busking.service.BuskingService;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.util.JsonUtil;

@Controller
public class BuskingController {

	@Resource(name = "BuskingService")
	private BuskingService service;

	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;

	@RequestMapping(value = "/busking/buskingGroupList.do")
	public ModelAndView buskingGroupList(ModelMap model, @RequestParam HashMap<String, Object> paramMap)
			throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/busking/groupList");

		return mav;
	}

	@RequestMapping(value = "/busking/selectGroupListToJson.do", method = RequestMethod.POST)
	public ModelAndView selectGroupListToJson(HttpServletRequest request, ModelMap model,
			@RequestParam HashMap<String, Object> paramMap,
			@ModelAttribute("buskingGroupVO") BuskingGroupVO buskingGroupVO) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			int pageCheck = Integer.parseInt(paramMap.get("pageCheck").toString());
			buskingGroupVO.setPageNm(pageCheck + "");
			if (pageCheck == 1) {
				Integer pageIndex = buskingGroupVO.getPageIndex();
				Integer pageSize = buskingGroupVO.getPageSize();

				int pageOffset = 0;
				if (pageIndex != null && pageSize != null) {
					pageOffset = (Integer.parseInt(pageIndex.toString()) - 1) * Integer.parseInt(pageSize.toString());
					buskingGroupVO.setPageOffset(pageOffset);
				}
			}
			// ???????????? ?????? ??????
			if (buskingGroupVO.getApproveYN() != null && !buskingGroupVO.getApproveYN().equals("")) {
				ComDefaultCodeVO codeVo = new ComDefaultCodeVO();
				codeVo.setCodeNm(buskingGroupVO.getApproveYN());
				codeVo.setCodeId("PHC019");
				List<CmmnDetailCode> codeList = cmmUseService.selectCmmCodeDetail(codeVo);
				buskingGroupVO.setApproveYN(codeList.get(0).getCode());
			}

			int groupListCnt = service.selectBuskingGroupRegDefaultCnt(buskingGroupVO);
			List<HashMap<String, Object>> groupList = service.selectBuskingGroupRegList(buskingGroupVO);

			String groupListJson = JsonUtil.getJsonArrayFromList(groupList).toString();

			mav.addObject("groupListCnt", groupListCnt);
			mav.addObject("groupListJson", groupListJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/busking/searchGroupView.do")
	public ModelAndView searchGroupView(HttpServletRequest request, ModelMap model,
			@RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/busking/searchGroup");

		List<String> genreCodeNmList = new ArrayList<>();
		List<String> areaCodeNmList = new ArrayList<>();
		List<String> approveCodeNmList = new ArrayList<>();

		for (CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC016", cmmUseService)) {
			genreCodeNmList.add(code.getCodeNm());
		}
		for (CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC018", cmmUseService)) {
			areaCodeNmList.add(code.getCodeNm());
		}
		for (CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC019", cmmUseService)) {
			approveCodeNmList.add(code.getCodeNm());
		}

		mav.addObject("genreCodeNmList", genreCodeNmList);
		mav.addObject("areaCodeNmList", areaCodeNmList);
		mav.addObject("approveCodeNmList", approveCodeNmList);

		return mav;
	}

	@RequestMapping(value = "/busking/searchStageView.do")
	public ModelAndView searchStageView(HttpServletRequest request, ModelMap model,
			@RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/busking/searchStage");

		List<String> placeCodeNmList = new ArrayList<>();
		List<String> approveCodeNmList = new ArrayList<>();
		List<String> timeCodeNmList = new ArrayList<>();

		for (CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC014", cmmUseService)) {
			placeCodeNmList.add(code.getCodeNm());
		}
		for (CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC019", cmmUseService)) {
			approveCodeNmList.add(code.getCodeNm());
		}
		for (CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC015", cmmUseService)) {
			timeCodeNmList.add(code.getCodeNm());
		}

		mav.addObject("placeCodeNmList", placeCodeNmList);
		mav.addObject("approveCodeNmList", approveCodeNmList);
		mav.addObject("timeCodeNmList", timeCodeNmList);

		return mav;
	}

	@RequestMapping(value = "/busking/updateApprove.do")
	public ModelAndView updateApprove(HttpServletRequest request, ModelMap model,
			@RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		String result = "success";
		/*
		 * if(paramMap.get("DATE")!=null) { String dateStr = paramMap.get("DATE");
		 * dateStr=dateStr.replace(" ", ""); dateStr=dateStr.trim(); String date[] =
		 * dateStr.split("~"); 
		 * paramMap.put("DATE_BGN", date[0]);
		 * paramMap.put("DATE_END", date[1]); }
		 */
		try {
			service.updateGroupApprove(paramMap);
		} catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}

		mav.addObject("result", result);
		return mav;
	}

	@RequestMapping(value = "/busking/deleteBusking.do")
	public ModelAndView deleteBusking(HttpServletRequest request, ModelMap model,
			@RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		paramMap.put("DELETE_YN", "1");
		String result = "success";
		try {
			service.deleteBusking(paramMap);
		} catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}

		mav.addObject("result", result);
		return mav;
	}

	@RequestMapping(value = "/busking/buskingStageList.do")
	public ModelAndView buskingStageList(ModelMap model, @RequestParam HashMap<String, Object> paramMap)
			throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/busking/stageList");
		ObjectMapper mapper = new ObjectMapper();
		ComDefaultCodeVO codeVo = new ComDefaultCodeVO();
		String jsonStr;

		// ????????????
		codeVo.setCodeId("PHC019");
		List<CmmnDetailCode> approveCodeList = cmmUseService.selectCmmCodeDetail(codeVo);

		List<HashMap<String, Object>> paramArpproveList = new ArrayList<HashMap<String, Object>>();

		for (CmmnDetailCode approve : approveCodeList) {
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("Name", approve.getCodeNm());
			hashMap.put("Id", approve.getCode());
			paramArpproveList.add(hashMap);
		}
		jsonStr = mapper.writeValueAsString(paramArpproveList);
		mav.addObject("approveCodeList", jsonStr);

		// ??????
		codeVo.setCodeId("PHC014");
		List<CmmnDetailCode> palceCodeList = cmmUseService.selectCmmCodeDetail(codeVo);

		List<HashMap<String, Object>> paramPlaceList = new ArrayList<HashMap<String, Object>>();

		for (CmmnDetailCode approve : palceCodeList) {
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("Name", approve.getCodeNm());
			hashMap.put("Id", approve.getCodeNm());
			paramPlaceList.add(hashMap);
		}
		jsonStr = mapper.writeValueAsString(paramPlaceList);
		mav.addObject("palceCodeList", jsonStr);

		// ??????
		codeVo.setCodeId("PHC015");
		List<CmmnDetailCode> timeCodeList = cmmUseService.selectCmmCodeDetail(codeVo);

		List<HashMap<String, Object>> paramTimeList = new ArrayList<HashMap<String, Object>>();

		for (CmmnDetailCode approve : timeCodeList) {
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("Name", approve.getCodeNm());
			hashMap.put("Id", approve.getCodeNm());
			paramTimeList.add(hashMap);
		}
		jsonStr = mapper.writeValueAsString(paramTimeList);
		mav.addObject("timeCodeList", jsonStr);

		return mav;
	}

	@RequestMapping(value = "/busking/selectStageListToJson.do", method = RequestMethod.POST)
	public ModelAndView selectStageListToJson(HttpServletRequest request, ModelMap model,
			@RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		Object pageIndex = paramMap.get("pageIndex");
		Object pageSize = paramMap.get("pageSize");
		int pageCheck = Integer.parseInt(paramMap.get("pageCheck").toString());

		// ???????????? ?????? ??????
		if (paramMap.get("approveYN") != null && !paramMap.get("approveYN").equals("")) {
			ComDefaultCodeVO codeVo = new ComDefaultCodeVO();
			codeVo.setCodeNm(paramMap.get("approveYN").toString());
			codeVo.setCodeId("PHC019");
			List<CmmnDetailCode> codeList = cmmUseService.selectCmmCodeDetail(codeVo);
			paramMap.put("approveYN", codeList.get(0).getCode());
		}

		int pageOffset = 0;

		if (pageCheck == 1) {
			if (pageIndex != null && pageSize != null) {
				pageOffset = (Integer.parseInt(pageIndex.toString()) - 1) * Integer.parseInt(pageSize.toString());
				paramMap.put("pageOffset", pageOffset);
			}
		}
		try {
			int stageListCnt = service.selectBuskingStageRegDefaultCnt(paramMap);
			List<HashMap<String, Object>> stageList = service.selectBuskingStageRegList(paramMap);

			String stageListJson = JsonUtil.getJsonArrayFromList(stageList).toString();

			mav.addObject("stageListCnt", stageListCnt);
			mav.addObject("stageListJson", stageListJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/busking/updateApproveMulti.do", method = RequestMethod.POST)
	public ModelAndView updateApproveMulti(
			@RequestParam(value = "arrayParam[]", required = false) List<Integer> arrayParam,
			@RequestParam(required = false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		if (arrayParam.size() == 0)
			return mav;
		paramMap.put("arrayParam", arrayParam);
		// ???????????? ?????? ??????
		if (paramMap.get("approveYN") != null) {
			ComDefaultCodeVO codeVo = new ComDefaultCodeVO();
			codeVo.setCodeNm(paramMap.get("approveYN").toString());
			codeVo.setCodeId("PHC019");
			List<CmmnDetailCode> codeList = cmmUseService.selectCmmCodeDetail(codeVo);
			paramMap.put("approveYN", codeList.get(0).getCode());
		}
		service.updateApproveMulti(paramMap);

		return mav;
	}

	@RequestMapping(value = "/busking/insertStage.do")
	public ModelAndView insertStageGET(ModelMap model, @RequestParam HashMap<String, Object> paramMap)
			throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/busking/insertStage");
		List<String> placeCodeNmList = new ArrayList<>();
		List<String> timeCodeNmList = new ArrayList<>();

		for (CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC014", cmmUseService)) {
			placeCodeNmList.add(code.getCodeNm());
		}
		for (CmmnDetailCode code : CommonMethod.getCodeDetailVOList("PHC015", cmmUseService)) {
			timeCodeNmList.add(code.getCodeNm());
		}
		mav.addObject("placeCodeNmList", placeCodeNmList);
		mav.addObject("timeCodeNmList", timeCodeNmList);
		return mav;
	}

	@RequestMapping(value = "/busking/insertStage.do", method = RequestMethod.POST)
	public String insertStagePOST(MultipartHttpServletRequest request, ModelMap model,
			@RequestParam HashMap<String, Object> paramMap) throws Exception {
		List<FileVO> _result = null;
		String _atchFileId = "";
		final Map<String, MultipartFile> files = request.getFileMap();
		if (!files.isEmpty()) {
			_result = fileUtil.parseFileInf(files, "APL_", 0, "", "");
			_atchFileId = fileMngService.insertFileInfs(_result); // ????????? ?????????????????? ????????? ???????????? ID??? ????????????.
		}
		paramMap.put("FILE", _atchFileId);

		EgovUserDetailsHelper.isAuthenticated();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		if (loginVO != null) {
			paramMap.put("MBER_ID", loginVO.getId());
		}
		String phone = paramMap.get("PHONE").toString();
		if (!phone.contains("-")) {
			String phoneChg = "";
			if (phone.length() == 11) {
				phoneChg = phone.substring(0, 3) + "-";
				phoneChg += phone.substring(3, 7) + "-";
				phoneChg += phone.substring(7, 11);
			}
			if (phone.length() == 10) {
				phoneChg = phone.substring(0, 3) + "-";
				phoneChg += phone.substring(3, 6) + "-";
				phoneChg += phone.substring(6, 10);
			}
			paramMap.put("PHONE", phoneChg);
		}
		service.insertBuskingStageReg(paramMap);
		return "redirect:/busking/buskingStageList.do";
	}

}
