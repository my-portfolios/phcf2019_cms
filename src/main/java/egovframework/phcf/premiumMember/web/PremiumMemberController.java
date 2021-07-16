package egovframework.phcf.premiumMember.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.premiumMember.service.PremiumMemberService;
import egovframework.phcf.util.DateUtil;
import egovframework.phcf.util.ExcelUtil;
import egovframework.phcf.util.JsonUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * 유료멤버십 관련 Controller
 * @author	김량래
 * @since	2019-11-11
 * @see
 * <pre>
 * 	<< 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2021-06-08	김경민         	엑셀 다운로드 기능 추가
 *   
 * </pre>
 * */

@Controller
public class PremiumMemberController {
	@Resource(name="PremiumMemberService")
	private PremiumMemberService service;
	
	@Resource(name="mberManageService")
	private EgovMberManageService egovMberManageService;
	
	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	
	@RequestMapping(value="/premiumMember/selectMembershipRegList.do")
	public ModelAndView selectMembershipRegList(ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/premiumMember/view");
		
		return mav;
	}
	
	@RequestMapping(value="/premiumMember/selectMembershipRegListJson.do")
	public ModelAndView selectMembershipRegListJson(ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		String pageIndex = paramMap.get("pageIndex");
		String pageSize = paramMap.get("pageSize");
		int pageOffset  = 0;
		if(pageIndex != null && pageSize != null) {
			pageOffset = (Integer.parseInt(pageIndex.toString())-1) * Integer.parseInt(pageSize.toString());
			paramMap.put("pageOffset", pageOffset + "");
		}

		int payListCnt = service.selectMembershipRegListCnt(paramMap);
		List<HashMap<String, Object>> payList = service.selectMembershipRegList(paramMap);
		for(HashMap<String, Object> pay : payList) {
			MberManageVO mberManageVO = egovMberManageService.selectMberWithId(pay.get("MEM_ID").toString());
			if(mberManageVO != null) {  
//				pay.put("MEM_NM", mberManageVO.getMberNm());
				if(!pay.get("RESULT").toString().equals("Y") || mberManageVO.getMembershipStartDt() == null) {
					pay.remove("MEMBERSHIP_START_DT");
					pay.remove("MEMBERSHIP_EXPIRE_DT");
				}
			} else {
				pay.put("MBER_NM", "회원정보없음");
			}
		}
		String payListJson = JsonUtil.getJsonArrayFromList(payList).toString();
		
		model.addAttribute("payListCnt",payListCnt);
		model.addAttribute("payListJson",payListJson);
		return mav;
	}
	
	@RequestMapping(value="/premiumMember/updateMembershipStatus.do", method=RequestMethod.POST)
	public ModelAndView updateMembershipStatus(@RequestParam HashMap<String, String> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		
		service.updateMembershipStatus(paramMap);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		List<HashMap<String, Object>> payList = service.selectMembershipRegList(paramMap);
		
		hashMap.put("TYPE", payList.get(0).get("PRE_TYPE").toString());
		hashMap.put("ID", payList.get(0).get("MEM_ID").toString());
		hashMap.put("membershipDurationYear", String.valueOf(
				getMembershipDurationYear(
						payList.get(0).get("PRE_TYPE").toString())));
		
		String result = payList.get(0).get("RESULT").toString();
		hashMap.put("RESULT", result);
		mav.addObject("result",result);
		
		
		//result : "Y", "C", "N", ""
		//승인으로 변경시 "Y', 접수 취소 "C", 반려 "N", 접수 요청 ""
//		if(result.equals("Y")) {
		service.updateMembershipGrade(hashMap);
//		}
		
		return mav;
	}
	
	@RequestMapping(value = "/premiumMember/exportExcelMberList.do")
	public void exportExcelMberList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		if (!isAuthenticated || loginVO.getUserSe() != "USR") {
			CommonMethod.generalAlertThrowing("/", "", "권한이 없습니다.");
		}
		
//		UserDefaultVO userSearchVO = new UserDefaultVO(); 
//		userSearchVO.setLastIndex(-1);
//		userSearchVO.setRecordCountPerPage(-1);
//		List<?> mberList = service.selectMembershipList(userSearchVO);
		List<HashMap<String, Object >> regList = service.selectMembershipRegList(new HashMap<String, String>());
		
		
		//멤버십 여부, 멤버십 종류를 위한 코드인듯.
		
		/*ComDefaultCodeVO comDefaultCodeVO = new ComDefaultCodeVO();
		comDefaultCodeVO.setTableNm("COMTNORGNZTINFO");
		List<CmmnDetailCode> groupList = cmmUseService.selectGroupIdDetail(comDefaultCodeVO);
		List<CmmnDetailCode> membershipTypeList = CommonMethod.getCodeDetailVOList("PHC010", cmmUseService);
		
		Map<String, Object> groupMap = new HashMap<>();
		Map<String, Object> membershipTypeMap = new HashMap<>();
		
		for(CmmnDetailCode cmmnCode : groupList) {
			groupMap.put(cmmnCode.getCode(), cmmnCode.getCodeNm());
		}
		
		membershipTypeMap.put("N", "무료회원");
		for(CmmnDetailCode cmmnCode : membershipTypeList) {
			membershipTypeMap.put(cmmnCode.getCode(), cmmnCode.getCodeNm());
		}*/
		
		String text = "text";
		String value = "value";
		
		List<Map<String, Object>> headList = new ArrayList<>();
		Map<String, Object> headMap = new HashMap<>();
		headMap.put(text, "이름"); headMap.put(value, "userNm");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "아이디"); headMap.put(value, "userId");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "멤버십 유형"); headMap.put(value, "preType");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "금액"); headMap.put(value, "payPrice");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "문자 수신 여부"); headMap.put(value, "sendSms");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "메일 수신 여부"); headMap.put(value, "sendMail");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "우편물 수신 여부"); headMap.put(value, "sendPost");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "신청 일시"); headMap.put(value, "createDt");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "수정 일시"); headMap.put(value, "updateDt");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "멤버십 시작일"); headMap.put(value, "startDt");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "멤버십 종료일"); headMap.put(value, "expireDt");		
		headList.add(headMap);
		
		headMap = new HashMap<>();
		headMap.put(text, "상태"); headMap.put(value, "result");		
		headList.add(headMap);
		

		//관리자 승인 여부
		Map<String, String> adminApprovalMap = new HashMap<>();
		adminApprovalMap.put("", "접수 요청");
		adminApprovalMap.put("C", "접수 취소");
		adminApprovalMap.put("Y", "승인");
		adminApprovalMap.put("N", "반려");
		
		List<Map<String, Object>> valueList = getValueList(regList, adminApprovalMap);
		
		
		ExcelUtil excelUtil = new ExcelUtil();
		excelUtil.exportExcel(request, response, headList, valueList);

	}
	
	private List<Map<String, Object>> getValueList(List<HashMap<String, Object >> regList, Map<String, String> adminApprovalMap) throws Exception {
		List<Map<String, Object>> valueList = new ArrayList<>();
		for(int i=0;i<regList.size();i++) {
			HashMap<String, Object> vo = regList.get(i);
			Map<String, Object> valueMap = new HashMap<>();
//			if(vo.get("mberSttus") == null || !vo.get("mberSttus").equals("P")) { continue; }
			
			valueMap.put("userNm", vo.get("MBER_NM"));
			valueMap.put("userId", vo.get("MEM_ID"));
			valueMap.put("preType", getPreType(vo.get("PRE_TYPE")));
			valueMap.put("payPrice", vo.get("PAY_PRICE"));
			valueMap.put("sendSms", CommonMethod.stringConvert(vo.get("SEND_SMS"), "N").equals("Y") ? "예" : "아니오");
			valueMap.put("sendMail", CommonMethod.stringConvert(vo.get("SEND_MAIL"), "N").equals("Y") ? "예" : "아니오");
			valueMap.put("sendPost", CommonMethod.stringConvert(vo.get("SEND_POST"), "N").equals("Y") ? "예" : "아니오");
			valueMap.put("createDt", vo.get("CREATE_DT"));
			valueMap.put("updateDt", vo.get("UPDATE_DT"));
			
			String result = adminApprovalMap.get(CommonMethod.stringConvert(vo.get("RESULT"), ""));
			valueMap.put("result", result);
			
			//회원 상태가 '접수 취소'가 아닐 때만 시작일, 종료일을 기록한다.
			if(!result.equals(adminApprovalMap.get("C"))) {
				// startDt는 멤버십 시작일이다.
				Date startDt = (Date) vo.get("MEMBERSHIP_START_DT");
				
				if(startDt != null) {
					valueMap.put("startDt", vo.get("MEMBERSHIP_START_DT"));
					
			        // expireDt는 멤버십 만료일이다.
			        //valueMap.put("expireDt", getExpireDt(CommonMethod.dateToString(startDt, "yyyy-MM-dd"), (String)vo.get("preType")));
			        valueMap.put("expireDt", vo.get("MEMBERSHIP_EXPIRE_DT"));

				}
			}
			
			valueList.add(valueMap);
		}
		
		return valueList;
	}
	
	private int getMembershipDurationYear(String membershipType) throws Exception {
//		MberManageVO mberManageVO = egovMberManageService.selectMberWithId(membershipType);
//		String membershipType = mberManageVO.getMembershipType();
		// 															맴버십 유효기간을 나타내는 코드
		List<CmmnDetailCode> codeVOList = CommonMethod.getCodeDetailVOList("PHC025", cmmUseService);
//		int year = Integer.parseInt(codeVOList.get(0).getCodeNm());
//		String checkDate = CommonMethod.checkDateCompare(startDt, "2021-06-08", "yyyy-MM-dd");
		
		return membershipType.equals("M") ? Integer.parseInt(codeVOList.get(1).getCodeNm()) 
				: Integer.parseInt(codeVOList.get(0).getCodeNm());
	}
	
	// 쓰지 않음
	private String getExpireDt(String startDt, String membershipType) throws Exception {
		Date startDate = CommonMethod.stringToDate(startDt, "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.YEAR, getMembershipDurationYear(membershipType));
		return df.format(cal.getTime());
	}
	
	private String getPreType(Object membershipType) throws Exception {
		
		List<CmmnDetailCode> membershipTypeList = CommonMethod.getCodeDetailVOList("PHC010", cmmUseService);
		Map<String, Object> membershipTypeMap = new HashMap<>();
		for(CmmnDetailCode code : membershipTypeList) {			
			membershipTypeMap.put(code.getCode(), code.getCodeNm());
		}
		//membershipType : B, P, M 
		String membership = CommonMethod.stringConvert(membershipTypeMap.get(membershipType), "");
		return membership;
	}
}
