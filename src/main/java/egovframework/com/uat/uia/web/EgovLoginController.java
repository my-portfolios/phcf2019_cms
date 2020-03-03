package egovframework.com.uat.uia.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovComponentChecker;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.config.EgovLoginConfig;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.utl.cas.service.EgovSessionCookieUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovClntInfo;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/*
import com.gpki.gpkiapi.cert.X509Certificate;
import com.gpki.servlet.GPKIHttpServletRequest;
import com.gpki.servlet.GPKIHttpServletResponse;
*/

/**
 * 일반 로그인, 인증서 로그인을 처리하는 컨트롤러 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자          수정내용
 *  ----------  --------  ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.8.26	정진오          IncludedInfo annotation 추가
 *  2011.09.07  서준식          스프링 시큐리티 로그인 및 SSO 인증 로직을 필터로 분리
 *  2011.09.25  서준식          사용자 관리 컴포넌트 미포함에 대한 점검 로직 추가
 *  2011.09.27  서준식          인증서 로그인시 스프링 시큐리티 사용에 대한 체크 로직 추가
 *  2011.10.27  서준식          아이디 찾기 기능에서 사용자 리름 공백 제거 기능 추가
 *  2017.07.21  장동한          로그인인증제한 작업
 *  2018.10.26  신용호          로그인 화면에 message 파라미터 전달 수정
 *  </pre>
 */
@Controller
public class EgovLoginController {

	/** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;

	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	@Resource(name = "egovLoginConfig")
	EgovLoginConfig egovLoginConfig;

	/** log */
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovLoginController.class);

	/**
	 * 로그인 화면으로 들어간다
	 * @param vo - 로그인후 이동할 URL이 담긴 LoginVO
	 * @return 로그인 페이지
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/egovLoginUsr.do")
	public ModelAndView loginUsrView(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/com/uat/uia/EgovLoginUsr");
		
		if(EgovUserDetailsHelper.isAuthenticated()) 
			return new ModelAndView("redirect:/");
		
		return mav;
	}

	/**
	 * 일반(세션) 로그인을 처리한다
	 * @param vo - 아이디, 비밀번호가 담긴 LoginVO
	 * @param request - 세션처리를 위한 HttpServletRequest
	 * @return result - 로그인결과(세션정보)
	 * @exception Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/uat/uia/actionLogin.do")
	public ModelAndView actionLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		loginVO.setUserSe("USR");
		
		// 2. 로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		
		// 3. 일반 로그인 처리
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
			request.getSession().setAttribute("loginVO", resultVO);
			mav.addObject("result","success");

		} else {
			mav.addObject("result","fail");
		}
		
		return mav;
	}

	/**
	 * 로그인 후 메인화면으로 들어간다
	 * @param
	 * @return 로그인 페이지
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/actionMain.do")
	public String actionMain(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 쿠키저장 로그인 유지를 위해		
		LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");
		//Cookie phcfLoginCookie = WebUtils.getCookie(request,"phcfLoginCookie");
		
		//System.out.println("=== LoginVo.getSessionId() : "+vo);
		int amount =60 *60 *24 *7;
		//String cookieDomain = ".phcf.or.kr";
		
		/*if(vo != null && phcfCmsLoginCookie != null) {
			HashMap<String, String> cookieValue = new HashMap<String, String>();
			cookieValue.put("phcfUserId", vo.getUniqId());
			cookieValue.put("phcfUserAgent", request.getHeader("user-agent"));
			
			cookieValue.forEach((key, value) -> {
				try {
					EgovSessionCookieUtil.setCookie(response, key, value, amount, cookieDomain);
					//CookieUtil.createCookie(response, key, value, ckSite, ckPath, amount);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			
		}*/
		// currentTimeMills()가 1/1000초 단위임으로 1000곱해서 더해야함
        Date sessionLimit =new Date(System.currentTimeMillis() + (1000*amount));
        // 현재 세션 id와 유효시간을 사용자 테이블에 저장한다.
        if(vo != null) loginService.keepLogin(vo.getUniqId(), request.getSession().getId(), sessionLimit);
		
		//쿠키 로그인
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			System.out.println("=== 쿠키명 : " + cookie.getName());
			System.out.println("=== 쿠키값 : " + cookie.getValue());
		}
		
		// 3. 메인 페이지 이동
		String main_page = Globals.MAIN_PAGE;

		LOGGER.debug("Globals.MAIN_PAGE > " + Globals.MAIN_PAGE);
		LOGGER.debug("main_page > {}", main_page);

		if (main_page.startsWith("/")) {
			return "forward:" + main_page;
		} else {
			return main_page;
		}
	}

	/**
	 * 로그아웃한다.
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/actionLogout.do")
	public String actionLogout(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");
		Date sessionLimit =new Date(System.currentTimeMillis());
		if(vo != null) loginService.keepLogin(vo.getUniqId(), "none", sessionLimit);
		
		request.getSession().setAttribute("loginVO", null);
		
		return "redirect:" + Globals.MAIN_PAGE;
	}
	
	

	/**
	 * 개발 시스템 구축 시 발급된 GPKI 서버용인증서에 대한 암호화데이터를 구한다.
	 * 최초 한번만 실행하여, 암호화데이터를 EgovGpkiVariables.js의 ServerCert에 넣는다.
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/getEncodingData.do")
	public void getEncodingData() throws Exception {

		/*
		X509Certificate x509Cert = null;
		byte[] cert = null;
		String base64cert = null;
		try {
			x509Cert = Disk.readCert("/product/jeus/egovProps/gpkisecureweb/certs/SVR1311000011_env.cer");
			cert = x509Cert.getCert();
			Base64 base64 = new Base64();
			base64cert = base64.encode(cert);
			log.info("+++ Base64로 변환된 인증서는 : " + base64cert);

		} catch (GpkiApiException e) {
			e.printStackTrace();
		}
		*/
	}

	/**
	 * 인증서 DN추출 팝업을 호출한다.
	 * @return 인증서 등록 페이지
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/EgovGpkiRegist.do")
	public String gpkiRegistView(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		/** GPKI 인증 부분 */
		// OS에 따라 (local NT(로컬) / server Unix(서버)) 구분
		String os = System.getProperty("os.arch");
		LOGGER.debug("OS : {}", os);
		
		//String virusReturn = null;

		/*
		// 브라우저 이름을 받기위한 처리
		String webKind = EgovClntInfo.getClntWebKind(request);
		String[] ss = webKind.split(" ");
		String browser = ss[1];
		model.addAttribute("browser",browser);
		// -- 여기까지
		if (os.equalsIgnoreCase("x86")) {
		    //Local Host TEST 진행중
		} else {
		    if (browser.equalsIgnoreCase("Explorer")) {
		GPKIHttpServletResponse gpkiresponse = null;
		GPKIHttpServletRequest gpkirequest = null;

		try {
		    gpkiresponse = new GPKIHttpServletResponse(response);
		    gpkirequest = new GPKIHttpServletRequest(request);

		    gpkiresponse.setRequest(gpkirequest);
		    model.addAttribute("challenge", gpkiresponse.getChallenge());

		    return "egovframework/com/uat/uia/EgovGpkiRegist";

		} catch (Exception e) {
		    return "egovframework/com/cmm/egovError";
		}
		}
		}
		*/
		return "egovframework/com/uat/uia/EgovGpkiRegist";
	}

	/**
	 * 인증서 DN값을 추출한다
	 * @return result - dn값
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/actionGpkiRegist.do")
	public String actionGpkiRegist(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		/** GPKI 인증 부분 */
		// OS에 따라 (local NT(로컬) / server Unix(서버)) 구분
		String os = System.getProperty("os.arch");
		LOGGER.debug("OS : {}", os);
		
		// String virusReturn = null;
		@SuppressWarnings("unused")
		String dn = "";

		// 브라우저 이름을 받기위한 처리
		String webKind = EgovClntInfo.getClntWebKind(request);
		String[] ss = webKind.split(" ");
		String browser = ss[1];
		model.addAttribute("browser", browser);
		/*
		// -- 여기까지
		if (os.equalsIgnoreCase("x86")) {
			// Local Host TEST 진행중
		} else {
			if (browser.equalsIgnoreCase("Explorer")) {
				GPKIHttpServletResponse gpkiresponse = null;
				GPKIHttpServletRequest gpkirequest = null;
				try {
					gpkiresponse = new GPKIHttpServletResponse(response);
					gpkirequest = new GPKIHttpServletRequest(request);
					gpkiresponse.setRequest(gpkirequest);
					X509Certificate cert = null;

					// byte[] signData = null;
					// byte[] privatekey_random = null;
					// String signType = "";
					// String queryString = "";

					cert = gpkirequest.getSignerCert();
					dn = cert.getSubjectDN();

					model.addAttribute("dn", dn);
					model.addAttribute("challenge", gpkiresponse.getChallenge());

					return "egovframework/com/uat/uia/EgovGpkiRegist";
				} catch (Exception e) {
					return "egovframework/com/cmm/egovError";
				}
			}
		}
		*/
		return "egovframework/com/uat/uia/EgovGpkiRegist";
	}
}