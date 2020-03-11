package egovframework.com.cmm.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 컴포넌트 설치 후 설치된 컴포넌트들을 IncludedInfo annotation을 통해 찾아낸 후
 * 화면에 표시할 정보를 처리하는 Controller 클래스
 * <Notice>
 * 		개발시 메뉴 구조가 잡히기 전에 배포파일들에 포함된 공통 컴포넌트들의 목록성 화면에
 * 		URL을 제공하여 개발자가 편하게 활용하도록 하기 위해 작성된 것으로,
 * 		실제 운영되는 시스템에서는 적용해서는 안 됨
 *      실 운영 시에는 삭제해서 배포해도 좋음
 * <Disclaimer>
 * 		운영시에 본 컨트롤을 사용하여 메뉴를 구성하는 경우 성능 문제를 일으키거나
 * 		사용자별 메뉴 구성에 오류를 발생할 수 있음
 * @author 공통컴포넌트 정진오
 * @since 2011.08.26
 * @version 2.0.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일		수정자		수정내용
 *  -------    	--------    ---------------------------
 *  2011.08.26	정진오 		최초 생성
 *  2011.09.16  서준식		컨텐츠 페이지 생성
 *  2011.09.26  이기하		header, footer 페이지 생성
 * </pre>
 */

import javax.annotation.Resource;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovComIndexService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.impl.EgovArticleDAO;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.com.uss.umt.service.impl.MberManageDAO;
import egovframework.phcf.hubizCommonMethod.CommonMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EgovComIndexController implements ApplicationContextAware, InitializingBean {
	
	@Resource(name="EgovComIndexService")
	private EgovComIndexService egovComIndexService;
	
	@Resource(name = "EgovArticleDAO")
    private EgovArticleDAO egovArticleDao;
    
	/** mberManageDAO */
	@Resource(name="mberManageDAO")
	private MberManageDAO mberManageDAO;
	
	private ApplicationContext applicationContext;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovComIndexController.class);

	private List<HashMap<String, Object>> map;

	public void afterPropertiesSet() throws Exception {}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
		LOGGER.info("EgovComIndexController setApplicationContext method has called!");
	}

	@RequestMapping("/index.do")
	public String index(ModelMap model) throws Exception {
//		return "egovframework/phcf/com/PhcfUnitMain";
		return "egovframework/com/cmm/EgovUnitMain";
	}

	@RequestMapping("/EgovTop.do")
	public String top(ModelMap model) {    
		return "egovframework/com/cmm/EgovUnitTop";
	}

	@RequestMapping("/EgovBottom.do")
	public String bottom() {
		return "egovframework/com/cmm/EgovUnitBottom";
	}

	@RequestMapping("/EgovContent.do")
	public String setContent(ModelMap model) {

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("loginVO", loginVO);
		
		//날짜 설정
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.add(Calendar.DATE, -1);
        String startT = df.format(cal.getTime());
        cal.add(Calendar.DATE, +1);
        String endT = df.format(cal.getTime());
        
		BoardVO bvo = new BoardVO();
		bvo.setSearchBgnDe(startT); //검색시작일
		bvo.setSearchEndDe(endT); //검색종료일
		
		model.addAttribute("currentDateTime", endT);
		
		// 재단 공지사항 갯수
		bvo.setBbsId("BBSMSTR_000000000297");
		int fndNoticeArticleCnt = egovArticleDao.selectArticleListCnt(bvo);
		model.addAttribute("fndNoticeArticleCnt", fndNoticeArticleCnt);
		
		// 외부 공지사항 갯수
		bvo.setBbsId("BBSMSTR_000000000307");
		int extNoticeArticleCnt = egovArticleDao.selectArticleListCnt(bvo);
		model.addAttribute("extNoticeArticleCnt", extNoticeArticleCnt);
		
		// 채용공고 갯수
		bvo.setBbsId("BBSMSTR_000000000310");
		int rcrtArticleCnt = egovArticleDao.selectArticleListCnt(bvo);
		model.addAttribute("rcrtArticleCnt", rcrtArticleCnt);
		
		// 입찰공고 갯수
		bvo.setBbsId("BBSMSTR_000000000309");
		int auctArticleCnt = egovArticleDao.selectArticleListCnt(bvo);
		model.addAttribute("auctArticleCnt", auctArticleCnt);
		
		UserManageVO uvo = new UserManageVO();
		
		//날짜 재설정
		cal = Calendar.getInstance();
        cal.setTime(new Date());
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.add(Calendar.DATE, -30); // 최근 한달(30일)내 신규회원
        startT = df.format(cal.getTime());
        cal.add(Calendar.DATE, +30);
        endT = df.format(cal.getTime());
        
        // 신규회원 수
		uvo.setSbscrbDeBegin(startT);
		uvo.setSbscrbDeEnd(endT);
		int newMemCnt = mberManageDAO.selectMberListTotCnt(uvo);
		model.addAttribute("newMemCnt", newMemCnt);
		
		// 유료회원 수
		uvo.setSbscrbDeBegin(startT);
		uvo.setSbscrbDeEnd(endT);
		uvo.setMembershipType("B");
		int newPrmMemCnt = mberManageDAO.selectMberListTotCnt(uvo);
		uvo.setMembershipType("P");
		newPrmMemCnt += mberManageDAO.selectMberListTotCnt(uvo);
		model.addAttribute("newPrmMemCnt", newPrmMemCnt);
		
		return "egovframework/com/cmm/EgovUnitContent";
	}

	@RequestMapping("/EgovLeft.do")
	public String setLeftMenu(ModelMap model) throws Exception { 
		return "egovframework/com/cmm/EgovUnitLeft";
	
	}
}
