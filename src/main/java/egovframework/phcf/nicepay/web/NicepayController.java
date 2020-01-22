package egovframework.phcf.nicepay.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import egovframework.phcf.common.service.ParamMap;
import egovframework.phcf.nicepay.service.NicepayService;
import egovframework.phcf.util.PropertiesUtil;

@Controller
public class NicepayController {
	
   
   @Resource(name = "NicepayService")
   NicepayService nicepayService;
   
   @SuppressWarnings("unchecked")
	@RequestMapping(value="/nicepay/insertProc")
	public ModelAndView insertProc(
			@RequestParam(value="user_id") String user_id
			, @RequestParam(value="sp_mh_tp") String sp_mh_tp
			, @RequestParam(value="sp_tp_arr") String sp_tp_arr
			, @RequestParam(value="user_tp") String user_tp
			, @RequestParam(value="user_nm") String user_nm
			, @RequestParam(value="user_mf") String user_mf
			, @RequestParam(value="user_birth_y") String user_birth_y
			, @RequestParam(value="user_birth_m") String user_birth_m
			, @RequestParam(value="user_birth_d") String user_birth_d
			, @RequestParam(value="user_phone1") String user_phone1
			, @RequestParam(value="user_phone2") String user_phone2
			, @RequestParam(value="user_phone3") String user_phone3
			, @RequestParam(value="user_email1") String user_email1
			, @RequestParam(value="user_email2") String user_email2
			, @RequestParam(value="user_tax_yn") String user_tax_yn
			, @RequestParam(value="user_post") String user_post
			, @RequestParam(value="user_add") String user_add
			, @RequestParam(value="user_add_detail") String user_add_detail
			, @RequestParam(value="user_revinfo_tp_arr") String user_revinfo_tp_arr
			, @RequestParam(value="sc_tp") String sc_tp
			, @RequestParam(value="sc_price") String sc_price
			, @RequestParam(value="sc_price_tp") String sc_price_tp
			, @RequestParam(value="orderNum") String orderNum
			, @RequestParam(value="browser") String browser) throws Exception {
		
		ParamMap paramMap = new ParamMap();
		paramMap.put("user_id", user_id);
		paramMap.put("sp_mh_tp", sp_mh_tp);
		paramMap.put("sp_tp_arr", sp_tp_arr);
		paramMap.put("user_tp", user_tp);
		paramMap.put("user_nm", user_nm);
		paramMap.put("user_mf", user_mf);
		paramMap.put("user_birth_y", user_birth_y);
		paramMap.put("user_birth_m", user_birth_m);
		paramMap.put("user_birth_d", user_birth_d);
		paramMap.put("user_phone1", user_phone1);
		paramMap.put("user_phone2", user_phone2);
		paramMap.put("user_phone3", user_phone3);
		paramMap.put("user_email1", user_email1);
		paramMap.put("user_email2", user_email2);
		paramMap.put("user_tax_yn", user_tax_yn);
		paramMap.put("user_post", user_post);
		paramMap.put("user_add", user_add);
		paramMap.put("user_add_detail", user_add_detail);
		paramMap.put("user_revinfo_tp_arr", user_revinfo_tp_arr);
		paramMap.put("sc_tp", sc_tp);
		paramMap.put("sc_price", sc_price);
		paramMap.put("sc_price_tp", sc_price_tp);
		paramMap.put("orderNum", orderNum);
		paramMap.put("browser", browser);
		
		nicepayService.insertProc(paramMap);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", true);
		
		return mav;
	}
	
	/**
	 * 승인 결과 페이지
	 * PC와 모바일 Controller를 분리해서 처리 할려고 했는데...
	 * 셋팅해줘야 되는 부분이 동일해서..구분 하는 항목만 넣어줌..
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/nicepay/payResult")
	public ModelAndView payResult(HttpServletRequest request) throws Exception {
		
		ParamMap paramMap = parseRequestCleanXss(request);
		
		String browser = String.valueOf( paramMap.get("browser") );
		
		ModelAndView mav = null;
		if(browser == null || browser.equalsIgnoreCase("null") || browser.length() < 1) {
			paramMap.put("browser", "pc");
			// PC 결과 화면
			mav = new ModelAndView("common/nicepay/payResult");
			
		} else {
			paramMap.put("browser", browser);
			// 모바일 결과 화면
			mav = new ModelAndView("common/nicepay_mobile/payResult_utf");
			mav.addObject("nicePayHomeLog", PropertiesUtil.getValue("nicePayHomeLog"));	// 모바일일 경우 nicePayHomeLog 폴더가 필요하다.
		}
		
		mav.addObject("paramMap", paramMap);
		mav.addObject("nicePayHome", PropertiesUtil.getValue("nicePayHome"));	// nicepay Home Dir
		mav.addObject("merchantID", PropertiesUtil.getValue("merchantID"));			// nicepay 상점ID
		
		// 결과 메세지 받기
		mav.addObject("AuthResultCode", String.valueOf( paramMap.get("AuthResultCode") ));
		mav.addObject("AuthResultMsg", String.valueOf( paramMap.get("AuthResultMsg") ));
		
		return mav;
	}
	
	/**
	 * 승인 요청 페이지.
	 * PC와 모바일 Controller를 분리해서 처리 할려고 했는데...
	 * 셋팅해줘야 되는 부분이 동일해서..구분 하는 항목만 넣어줌..
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/nicepay/payRequest")
	public ModelAndView payRequest(HttpServletRequest request) throws Exception {
		
		ParamMap paramMap = parseRequestCleanXss(request);
		
		String browser = String.valueOf(paramMap.get("browser"));
		if(!browser.equalsIgnoreCase("mobile") && !browser.equalsIgnoreCase("pc") && !browser.equalsIgnoreCase("qr")) { 
			throw new IllegalArgumentException("접속 환경 정보를 확인 할 수 없습니다.");
		}
		
		ModelAndView mav = null;
		if(browser.equalsIgnoreCase("pc")) {	// 접속 환경이 PC 인지
			mav = new ModelAndView("common/nicepay/payRequest");
		} else {	// 접속 환경이 모바일인지..
			mav = new ModelAndView("common/nicepay_mobile/payRequest_utf");
		}
		
		mav.addObject("merchantKey", PropertiesUtil.getValue("merchantKey"));	// nicepay 상점키
		mav.addObject("merchantID", PropertiesUtil.getValue("merchantID"));			// nicepay 상점ID
		
		String userId = (String) WebUtils.getSessionAttribute(request, "USER_ID");
		// 회원 결제일 경우 USER_ID를 추가해 넘겨준다.
		if(userId != null && !userId.equalsIgnoreCase("null") && userId.length() > 0) {
			mav.addObject("user_id", userId);
		}
		
		
		// user_revinfo_tp_arr[] 값은 배열로 넘어 오게 되어 있다..
		// StringUtils.join 을 이용하여 String 구분자 '&' 로 하여 String으로 변환하여 user_revinfo_tp_arr <== 키에 다시 담아주어야 한다.
		paramMap.put("user_revinfo_tp_arr", StringUtils.join(paramMap.getStrings("user_revinfo_tp_arr[]"), '&'));
		
		
		// 주문번호 입력(번호 구조 : phcf + YYYYMM + (해당월에 신청한 후원수 cnt + 1 값);
		mav.addObject("orderNum", nicepayService.getOrderNumber());
		mav.addObject("sc_tp", "순수후원금");	// 지금은 상품명이 순수후원금 밖에 없음..
		mav.addObject("sc_price_tp", paramMap.get("sc_price_tp"));	// 결제 방식
		mav.addObject("sc_price", paramMap.get("sc_price"));			// 결제 금액
		mav.addObject("user_nm", paramMap.get("user_nm"));			// 이름
		mav.addObject("user_email", String.valueOf( paramMap.get("user_email1") ) + "@" + String.valueOf( paramMap.get("user_email2") ));
		mav.addObject("user_phone", String.valueOf( paramMap.get("user_phone1") ) + String.valueOf( paramMap.get("user_phone2") ) + String.valueOf( paramMap.get("user_phone3")) );
		
		mav.addObject("paramMap", paramMap);
		
		return mav;
	}
	
	/**
	 * <b>parameter mapping</b></p>
	 * @param request
	 * @param viewPath
	 * @return
	 */
	protected ParamMap parseRequestCleanXss(HttpServletRequest request) {
		ParamMap paramMap = new ParamMap();
		paramMap.parseRequestCleanXss(request);
		
		// CSRF Hacking 차단 Token 전달
		//paramMap.put("CSRFToken",CSRFTokenManager.getTokenForSession(request.getSession()));	
		// CSRF Hacking 차단 Token 전달
		
		return paramMap;
	}	
	
}