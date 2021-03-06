package egovframework.com.cmm.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;


import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovComIndexService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sec.phcf.service.AuthManage;
import egovframework.com.sec.phcf.service.AuthManageVO;
import egovframework.com.sec.phcf.service.EgovPhcfAuthorService;
import egovframework.com.utl.cas.service.EgovSessionCookieUtil;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.util.JsonUtil;

/**
 * 문화재단 권한 체크 인터셉터
 * @author 대외사업부
 * @since 2019.12.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2019.12.20  윤병훈          최초 생성
 *  </pre>
 */


public class PhcfAuthenticInterceptor extends HandlerInterceptorAdapter {
	
	@Resource(name="egovPhcfAuthorService")
    private EgovPhcfAuthorService egovPhcfAuthorService;
	
	@Resource(name="EgovComIndexService")
	private EgovComIndexService egovComIndexService;
	
	/** 관리자 접근 권한 패턴 목록 */
	private List<String> phcfAuthPatternList;
	
	private List<HashMap<String,Object>> DBMenuList;
	
	public List<String> getPhcfAuthPatternList() {
		return phcfAuthPatternList;
	}

	public void setPhcfAuthPatternList(List<String> phcfAuthPatternList) {
		this.phcfAuthPatternList = phcfAuthPatternList;
	}
	
	private static Logger logger = Logger.getLogger(PhcfAuthenticInterceptor.class);
	
	/**
	 * 인증된 사용자 여부로 인증 여부를 체크한다.
	 * 관리자 권한에 따라 접근 페이지 권한을 체크한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//외부 주소
		List<String> externalPageUrl = new ArrayList<>(Arrays.asList(
				EgovProperties.getProperty("Globals.main_url"),
				EgovProperties.getProperty("Globals.cms_url"),
				EgovProperties.getProperty("Globals.place_url"),
				EgovProperties.getProperty("Globals.festival_url")));
		request.setAttribute("externalPageUrl", externalPageUrl);
				
		String banGoToUrl = "/sec/phcf/EgovPhcfAuthorBanGoToUrl.do";
		
		//현재주소 가져오기
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		
		String currentUrl = urlPathHelper.getOriginatingRequestUri(request);
		String currentQueryString = urlPathHelper.getOriginatingQueryString(request);
		
		if(currentQueryString!=null) currentUrl += "?" + currentQueryString;
		
		List<AuthManageVO> authManageList = new ArrayList<AuthManageVO>();
		
		if(!currentUrl.equals(banGoToUrl)) {
			//로그인정보 가져오기
			if(EgovUserDetailsHelper.isAuthenticated()) {
				LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");
			
				AuthManageVO amvo = new AuthManageVO();
				
				amvo.setOrgnztId(vo.getOrgnztId());
				amvo.setGroupId(vo.getGroupId());
				amvo.setPage(Globals.SITE_NAME);
				
				authManageList = egovPhcfAuthorService.selectEgovPhcfAuthList(amvo);
				
				boolean phcfAuthCheck = phcfAuthCheck(request, authManageList, currentUrl);
				
				// 현재메뉴에 대한 권한 정보를 저장 True or False
				request.setAttribute("phcfAuthCheck",phcfAuthCheck);
				
				if(!phcfAuthCheck) {
					ModelAndView modelAndView = CommonMethod.generalAlertThrowing("/", "_self", "접근 권한이 없습니다!"); 
							/*new ModelAndView("forward:"+banGoToUrl);*/
					throw new ModelAndViewDefiningException(modelAndView);
				}
			}
		}
		
		//전체 메뉴구조 및 현재 메뉴위치 가져오기
		if(DBMenuList == null || currentUrl.contains("/phcf/menuRefresh.do")) DBMenuList = egovComIndexService.selectMenuInfoList(Globals.SITE_NAME);
		
		List<HashMap<String, Object>> AllMenuList = new ArrayList<>();
		AllMenuList.addAll(DBMenuList);
		
		HashMap<String, Object> cmMap = new HashMap<String, Object>();
		
		if(AllMenuList!=null) {
			List<HashMap<String, Object>> tempMapList = new ArrayList<HashMap<String,Object>>();
			for(HashMap<String, Object> entry : AllMenuList) {
				if(entry!=null && authManageList!=null) {					
					if(phcfAuthCheck(request, authManageList, entry.get("link").toString())) tempMapList.add(entry);
				}
			}
			AllMenuList.clear();
			AllMenuList = tempMapList;
			for(HashMap<String, Object> entry : AllMenuList) {
				if(entry!=null) {					
					if(currentUrl.matches(".*"+entry.get("link").toString()+".*") || currentUrl.equals(entry.get("link").toString())) {
						cmMap.putAll(entry);
					}
				}
			}
		}
		
		String AllMenuListJson = "";
		if(AllMenuList!=null) {
			AllMenuListJson = JsonUtil.getJsonStringFromList(AllMenuList);
		}
		
		try {
			if(!AllMenuListJson.equals("")) request.setAttribute("AllMenuListJson", AllMenuListJson);
			if(AllMenuList!=null) {
				request.setAttribute("AllMenuList",AllMenuList);
				List<HashMap<String, Object>> HeaderMenuList = new ArrayList<HashMap<String,Object>>();
				for(HashMap<String, Object> entry : AllMenuList) {
					if(entry!=null) {			
						if(entry.get("headerYn").toString().equals("Y")) {
							HeaderMenuList.add(entry);
						}
					}
				}
				request.setAttribute("HeaderMenuList",HeaderMenuList);
			}
			if(cmMap!=null) request.setAttribute("CurrentMenuMap",cmMap);
			//logger.debug("=== currentURL : " + currentUrl);
			//logger.debug("=== menuMAP : " + cmMap);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private Boolean phcfAuthCheck (HttpServletRequest request, List<AuthManageVO> authManageList, String url) {
		
		boolean phcfAuthCheck = true;
		//로그인정보 가져오기
			
		//AUTH_PRIORITY 적용 (우선순위, authpriority > INS_DT > ban_link > SEQ(적은것)
		// 내림차순 정렬
		authManageList.sort(new Comparator<AuthManage>() {
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public int compare(AuthManage arg0, AuthManage arg1) {
				int priority0 = 0;
				int priority1 = 0;
				
				if(arg0!=null && !arg0.equals("")) {
					priority0 = Integer.parseInt(arg0.getAuthPriority());
				}
				if(arg1!=null && !arg1.equals("")) {
					priority1 = Integer.parseInt(arg1.getAuthPriority());
				}
				if (priority0 == priority1) {
					if (arg0.getInsDt().compareTo(arg1.getInsDt())==0)
						return 0;
					else if (arg0.getInsDt().compareTo(arg1.getInsDt())==-1)
						return 1;
					else
						return -1;
				}
				else if (priority1 > priority0)
					return 1;
				else
					return -1;
			}
		});

		AntPathRequestMatcher antPathRequestMatcher = null;
		
		for(String phcfAuthPattern : phcfAuthPatternList){
			AntPathRequestMatcher phcfAcceptMatcher = null;
			AntPathRequestMatcher phcfBanMatcher = null;
			antPathRequestMatcher = new AntPathRequestMatcher(phcfAuthPattern);
			//현재주소와 맞는지 확인
			if(antPathRequestMatcher.matches(request)) {
				for(AuthManage authManage : authManageList) {
					//Allow 권한체크
					if(authManage.getAcceptLink()!=null && !authManage.getAcceptLink().equals("")) {
						phcfAcceptMatcher = new AntPathRequestMatcher(authManage.getAcceptLink());
						if(phcfAcceptMatcher.matches(request) || url.contains(authManage.getAcceptLink())) {
							phcfAuthCheck = true;
						}
					}
					//Ban 권한체크
					if(authManage.getBanLink()!=null && !authManage.getBanLink().equals("")) {
						phcfBanMatcher = new AntPathRequestMatcher(authManage.getBanLink());
						if(phcfBanMatcher.matches(request) || url.contains(authManage.getBanLink())) {
							phcfAuthCheck = false;
						}
					}
					
				}
			}
		}
		
		return phcfAuthCheck;
	}
	
}
