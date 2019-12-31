package egovframework.com.cmm.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
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

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sec.phcf.service.AuthManage;
import egovframework.com.sec.phcf.service.EgovPhcfAuthorService;
import egovframework.com.sec.phcf.service.impl.EgovPhcfAuthorDAO;
import egovframework.com.utl.cas.service.EgovSessionCookieUtil;

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
	
	@Resource(name="EgovPhcfAuthorService")
    private EgovPhcfAuthorService egovPhcfAuthorService;
	
	/** 관리자 접근 권한 패턴 목록 */
	private List<String> phcfAuthPatternList;
	
	public List<String> getPhcfAuthPatternList() {
		return phcfAuthPatternList;
	}

	public void setPhcfAuthPatternList(List<String> phcfAuthPatternList) {
		this.phcfAuthPatternList = phcfAuthPatternList;
	}
	/**
	 * 인증된 사용자 여부로 인증 여부를 체크한다.
	 * 관리자 권한에 따라 접근 페이지 권한을 체크한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String banGoToUrl = "/sec/phcf/EgovPhcfAuthorBanGoToUrl.do";
		
		String currentUrl = "";
		if(request.getQueryString()!=null) currentUrl = request.getServletPath()+"?"+request.getQueryString();
		else currentUrl = request.getServletPath();
		
		//로그인정보 가져오기
		if(EgovUserDetailsHelper.isAuthenticated()) {
			LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");
			System.out.println("ORGID : " + vo.getOrgnztId());
			System.out.println("MEMID : " + vo.getId());
			System.out.println("UNIQUEID : " + vo.getUniqId());
			System.out.println("IP : " + vo.getIp());
			System.out.println("GROUPID : " + vo.getGroupId());
		
		
			HashMap<String, String> paramMap = new HashMap<String, String>();
			
			paramMap.put("orgnztId", vo.getOrgnztId());
			paramMap.put("groupId", vo.getGroupId());
			
			
			List<String> phcfAuthAcceptUrlList = new ArrayList<String>();
			List<String> phcfAuthBanUrlList = new ArrayList<String>();
			
			List<AuthManage> authManageList = egovPhcfAuthorService.selectEgovPhcfAuthList(paramMap);
			
			for(AuthManage authManage : authManageList) {
				phcfAuthAcceptUrlList.add(authManage.getAcceptLink());
				phcfAuthBanUrlList.add(authManage.getBanLink());
			}
			
			boolean phcfAuthCheck = true;
	
			AntPathRequestMatcher antPathRequestMatcher = null;
			AntPathRequestMatcher phcfAcceptMatcher = null;
			AntPathRequestMatcher phcfBanMatcher = null;
			
			for(String phcfAuthPattern : phcfAuthPatternList){
				antPathRequestMatcher = new AntPathRequestMatcher(phcfAuthPattern);
				//현재주소와 맞는지 확인
				if(antPathRequestMatcher.matches(request)) {
					//Allow 권한체크
					if(phcfAuthAcceptUrlList!=null) {
						for(String phcfAuthAcceptUrl : phcfAuthAcceptUrlList) {
							if(phcfAuthAcceptUrl!=null && !phcfAuthAcceptUrl.equals("")) {
								phcfAcceptMatcher = new AntPathRequestMatcher(phcfAuthAcceptUrl);
								if(phcfAcceptMatcher.matches(request) || currentUrl.contains(phcfAuthAcceptUrl)) {
									phcfAuthCheck = true;
								}
							}
						}
					}
					//Ban 권한체크
					if(phcfAuthBanUrlList!=null) {
						for(String phcfAuthBanUrl : phcfAuthBanUrlList) {
							if(phcfAuthBanUrl!=null && !phcfAuthBanUrl.equals("")) {
								phcfBanMatcher = new AntPathRequestMatcher(phcfAuthBanUrl);
								if(phcfBanMatcher.matches(request) || currentUrl.contains(phcfAuthBanUrl)) {
									phcfAuthCheck = false;
								}
							}
						}
					}
				}
			}
			
			if(!phcfAuthCheck) {
				ModelAndView modelAndView = new ModelAndView("redirect:"+banGoToUrl);
				throw new ModelAndViewDefiningException(modelAndView);
			}
		}
		return true;
	}
}
