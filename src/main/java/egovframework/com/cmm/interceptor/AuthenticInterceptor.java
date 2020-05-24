package egovframework.com.cmm.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.utl.cas.service.EgovSessionCookieUtil;
import egovframework.phcf.hubizCommonMethod.CommonMethod;

/**
 * 인증여부 체크 인터셉터
 * @author 공통서비스 개발팀 서준식
 * @since 2011.07.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.07.01  서준식          최초 생성
 *  2011.09.07  서준식          인증이 필요없는 URL을 패스하는 로직 추가
 *  2017.08.31  장동한          인증된 사용자 체크로직 변경 및 관리자 권한 체크 로직 추가 
 *  </pre>
 */


public class AuthenticInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private Environment environment;
	
	/** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;
	
	/** log */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticInterceptor.class);
	
	/** 관리자 접근 권한 패턴 목록 */
	private List<String> adminAuthPatternList;
	
	public List<String> getAdminAuthPatternList() {
		return adminAuthPatternList;
	}

	public void setAdminAuthPatternList(List<String> adminAuthPatternList) {
		this.adminAuthPatternList = adminAuthPatternList;
	}

	/**
	 * 인증된 사용자 여부로 인증 여부를 체크한다.
	 * 관리자 권한에 따라 접근 페이지 권한을 체크한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//인증된사용자 여부
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	
		//미민증사용자 체크
		if(!isAuthenticated) {
			throw new ModelAndViewDefiningException(CommonMethod.generalAlertThrowing("/uat/uia/egovLoginUsr.do", "_parent", "세션이 만료되었습니다."));
		}
		else {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			if(!user.getUserSe().equals("USR")) {
				request.getSession().setAttribute("loginVO", null);
				throw new ModelAndViewDefiningException(CommonMethod.generalAlertThrowing("/uat/uia/egovLoginUsr.do", "_parent", "관리자만 이용하실 수 있는 서비스입니다!"));
			}
		}
		//인증된 권한 목록
		List<String> authList = (List<String>)EgovUserDetailsHelper.getAuthorities();
		//관리자인증여부
		boolean adminAuthUrlPatternMatcher = false;
		//AntPathRequestMatcher
		AntPathRequestMatcher antPathRequestMatcher = null;
		//관리자가 아닐때 체크함
		for(String adminAuthPattern : adminAuthPatternList){
			antPathRequestMatcher = new AntPathRequestMatcher(adminAuthPattern);
			if(antPathRequestMatcher.matches(request)){
				adminAuthUrlPatternMatcher = true;
			}
		}
		//관리자 권한 체크
		if(adminAuthUrlPatternMatcher && !authList.contains("ADMIN")){
			ModelAndView modelAndView = new ModelAndView("redirect:/uat/uia/egovLoginUsr.do?auth_error=1");
			throw new ModelAndViewDefiningException(modelAndView);
		}
		
		//쿠키 로그인
		LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");

		// 우리가 만들어 논 쿠키를 꺼내온다.
        Cookie phcfCmsLoginCookie = WebUtils.getCookie(request,"phcfCmsLoginCookie");
        if (phcfCmsLoginCookie != null){// 쿠키가 존재하는 경우(이전에 로그인때 생성된 쿠키가 존재한다는 것)
            // loginCookie의 값을 꺼내오고 -> 즉, 저장해논 세션Id를 꺼내오고
            String cmsSessionId = phcfCmsLoginCookie.getValue();
            // 세션Id를 checkUserWithSessionKey에 전달해 이전에 로그인한적이 있는지 체크하는 메서드를 거쳐서
            // 유효시간이 > now() 인 즉 아직 유효시간이 지나지 않으면서 해당 sessionId 정보를 가지고 있는 사용자 정보를 반환한다.
            HashMap<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("sessionId", cmsSessionId);
            
            LoginVO resultVO = loginService.cookieLogin(paramMap);
            if(vo != null) System.out.println("=== vo(cms) : "+ vo.getSessionId());
			if ( resultVO != null ){// 그런 사용자가 있다면
			            	
            	if(resultVO.getId() != null && !resultVO.getId().equals("")) {
	                // 세션을 생성시켜 준다.
	            	request.getSession().setAttribute("loginVO", null);
	            	request.getSession().setAttribute("loginVO", resultVO);
            	} else if(resultVO.getSessionId().equals("none")) {
            		if(vo != null && !vo.getSessionId().equals(resultVO.getSessionId())) {
                		if(vo.getSessionId().equals(cmsSessionId)) request.getSession().setAttribute("loginVO", null);
                	}
            	}
            	
            } //else if(vo != null && resultVO == null && vo.getUserSe().equals("USR")) request.getSession().setAttribute("loginVO", null);
        }
        
		return true;
	}

}
