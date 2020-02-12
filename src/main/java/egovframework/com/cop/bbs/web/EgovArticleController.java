package egovframework.com.cop.bbs.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cmm.util.EgovXssChecker;
import egovframework.com.cop.bbs.service.BlogVO;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardAddedColmnsVO;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.cop.bbs.service.EgovBBSSatisfactionService;
import egovframework.com.cop.cmt.service.CommentVO;
import egovframework.com.cop.cmt.service.EgovArticleCommentService;
import egovframework.com.cop.tpl.service.EgovTemplateManageService;
import egovframework.com.cop.tpl.service.TemplateInfVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.string.EgovStringUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2009.06.29	한성곤			2단계 기능 추가 (댓글관리, 만족도조사)
 *   2011.07.01 안민정		 	댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *   2011.09.07 서준식           유효 게시판 게시일 지나도 게시물이 조회되던 오류 수정
 *   2016.06.13 김연호			표준프레임워크 3.6 개선
 * </pre>
 */

@Controller
public class EgovArticleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovArticleController.class);
	
	@Resource(name = "EgovArticleService")
    private EgovArticleService egovArticleService;

    @Resource(name = "EgovBBSMasterService")
    private EgovBBSMasterService egovBBSMasterService;

    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "EgovArticleCommentService")
    protected EgovArticleCommentService egovArticleCommentService;

    @Resource(name = "EgovBBSSatisfactionService")
    private EgovBBSSatisfactionService bbsSatisfactionService;

	@Resource(name = "EgovTemplateManageService")
	private EgovTemplateManageService egovTemplateManageService;
    
    @Autowired
    private DefaultBeanValidator beanValidator;

    //protected Logger log = Logger.getLogger(this.getClass());
    
    /**
     * XSS 방지 처리.
     * 
     * @param data
     * @return
     */
    protected String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }
        
        String ret = data;
        
        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
        
        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
        
        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
        
        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        
        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }

    /**
     * 게시물에 대한 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectArticleList.do")
    public String selectArticleList(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
        
		BoardMasterVO vo = new BoardMasterVO();
		
		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId(user.getUniqId());
		BoardMasterVO master = egovBBSMasterService.selectBBSMasterInf(vo);
		
		//방명록은 방명록 게시판으로 이동
		if(master.getBbsTyCode().equals("BBST03")){
			return "forward:/cop/bbs/selectGuestArticleList.do";
		}
		
		
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = egovArticleService.selectArticleList(boardVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		List<BoardAddedColmnsVO> addedColmnsList = egovArticleService.selectArticleAddedColmnsDetail(boardVO);
		model.addAttribute("articleACVO", addedColmnsList);
		
		//공지사항 추출
		List<BoardVO> noticeList = egovArticleService.selectNoticeArticleList(boardVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		//-------------------------------
		// 기본 BBS template 지정 
		//-------------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		    master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
		////-----------------------------
		//----------------------------
		// 카테고리 리스트 가져오기
		//----------------------------
		if(master.getCateUse()!=null) {
			if(master.getCateUse().equals("Y")) {
				ArrayList<String> cateNames = new ArrayList<String>();
				String[] cateNamesTmp = master.getCateList().split("\\|");
				for(String cmp : cateNamesTmp) {
					cateNames.add(cmp);
				}
				master.setCateNames(cateNames);
			}
		}
		if(user != null) {
	    	model.addAttribute("sessionUniqId", user.getUniqId());
	    }
		
		//-------------------------------------------
		// 2019. 10. 31, 윤병훈
		// 게시판의 템플릿 정보가 없을 때 기본값적용
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		if(master.getTmplatId().equals("") || master.getTmplatId().equals(null)) {
			master.setTmplatId("basic");
		}
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("noticeList", noticeList);
		
		//템플릿 파일 유무를 검사한다.
		String isDir = request.getServletContext().getRealPath("/WEB-INF/jsp/template/"+master.getTmplatId()+"/list.jsp");
		File f = new File(isDir);
		if(f.exists()) return "egovframework/com/cop/bbs/EgovArticleList";
		else return "error/noTmpltErrorPage";
    }
    
    
    /**
     * 게시물에 대한 상세 정보를 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectArticleDetail.do")
    public String selectArticleDetail(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }

	
		//boardVO.setLastUpdusrId(user.getUniqId());
		BoardVO vo = egovArticleService.selectArticleDetail(boardVO);
		List<BoardAddedColmnsVO> addedColmnsList = egovArticleService.selectArticleAddedColmnsDetail(boardVO);
		
		model.addAttribute("result", vo);
		model.addAttribute("articleACVO", addedColmnsList);
		model.addAttribute("sessionUniqId", user.getUniqId());
		
		//비밀글은 작성자만 볼수 있음 
		if(!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y") && !user.getUniqId().equals(vo.getFrstRegisterId()))
			return"forward:/cop/bbs/selectArticleList.do";
		
		//----------------------------
		// template 처리 (기본 BBS template 지정  포함)
		//----------------------------
		BoardMasterVO master = new BoardMasterVO();
		
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId(user.getUniqId());
		
		BoardMasterVO masterVo = egovBBSMasterService.selectBBSMasterInf(master);
	
		if (masterVo.getTmplatCours() == null || masterVo.getTmplatCours().equals("")) {
		    masterVo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
	
		////-----------------------------
		
		//----------------------------
		// 2009.06.29 : 2단계 기능 추가
		// 2011.07.01 : 댓글, 만족도 조사 기능의 종속성 제거
		//----------------------------
		if (egovArticleCommentService != null){
			if (egovArticleCommentService.canUseComment(boardVO.getBbsId())) {
			    model.addAttribute("useComment", "true");
			}
		}
		if (bbsSatisfactionService != null) {
			if (bbsSatisfactionService.canUseSatisfaction(boardVO.getBbsId())) {
			    model.addAttribute("useSatisfaction", "true");
			}
		}
		////--------------------------
		
		//템플릿이 없을때 기본값으로 적용
		if(masterVo.getTmplatId().equals("") || masterVo.getTmplatId().equals(null)) {
			masterVo.setTmplatId("basic");
		}
		
		model.addAttribute("boardMasterVO", masterVo);
		
		//템플릿에 따른 View를 보여준다.
		String isDir = request.getServletContext().getRealPath("/WEB-INF/jsp/template/"+masterVo.getTmplatId()+"/view.jsp");
		File f = new File(isDir);
		if(f.exists()) return "egovframework/com/cop/bbs/EgovArticleDetail";
		else return "error/noTmpltErrorPage";
    }

    /**
     * 게시물 등록을 위한 등록페이지로 이동한다.
     * 
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/insertArticleView.do")
    public String insertArticleView(HttpServletRequest request, @ModelAttribute("searchVO") BoardAddedColmnsVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		BoardMasterVO bdMstr = new BoardMasterVO();
		BoardVO board = new BoardVO();
		if (isAuthenticated) {
	
		    BoardMasterVO vo = new BoardMasterVO();
		    vo.setBbsId(boardVO.getBbsId());
		    vo.setUniqId(user.getUniqId());
	
		    bdMstr = egovBBSMasterService.selectBBSMasterInf(vo);
		}
	
		//----------------------------
		// 기본 BBS template 지정 
		//----------------------------
		if (bdMstr.getTmplatCours() == null || bdMstr.getTmplatCours().equals("")) {
		    bdMstr.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
		//----------------------------
		// 카테고리 리스트 가져오기
		//----------------------------
		if(bdMstr.getCateUse().equals("Y")) {
			ArrayList<String> cateNames = new ArrayList<String>();
			String[] cateNamesTmp = bdMstr.getCateList().split("\\|");
			for(String cmp : cateNamesTmp) {
				cateNames.add(cmp);
			}
			bdMstr.setCateNames(cateNames);
		}
	
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", bdMstr);
		////-----------------------------
		
		
		//템플릿이 없을때 기본값으로 적용
		if(bdMstr.getTmplatId().equals("") || bdMstr.getTmplatId().equals(null)) {
			bdMstr.setTmplatId("basic");
		}
		//템플릿에 따른 View를 보여준다.
		//return "egovframework/com/cop/bbs/EgovArticleRegist";
		String isDir = request.getServletContext().getRealPath("/WEB-INF/jsp/template/"+bdMstr.getTmplatId()+"/write.jsp");
		File f = new File(isDir);
		if(f.exists()) return "egovframework/com/cop/bbs/EgovArticleRegist";
		else return "error/noTmpltErrorPage";
		
    }

    /**
     * 게시물을 등록한다.
     * 
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/insertArticle.do")
    public String insertArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardAddedColmnsVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") BoardAddedColmnsVO board, 
	    @RequestParam(required=false, defaultValue="") String[] ac1, @RequestParam(required=false, defaultValue="") String[] ac2, @RequestParam(required=false, defaultValue="") String[] ac3, @RequestParam(required=false, defaultValue="") String[] ac4, @RequestParam(required=false, defaultValue="") String[] ac5, 
	    @RequestParam(required=false, defaultValue="") String[] ac6, @RequestParam(required=false, defaultValue="") String[] ac7, @RequestParam(required=false, defaultValue="") String[] ac8, @RequestParam(required=false, defaultValue="") String[] ac9, @RequestParam(required=false, defaultValue="") String[] ac10, 
	    @RequestParam(required=false, defaultValue="") String[] ac11, @RequestParam(required=false, defaultValue="") String[] ac12, @RequestParam(required=false, defaultValue="") String[] ac13, @RequestParam(required=false, defaultValue="") String[] ac14, @RequestParam(required=false, defaultValue="") String[] ac15, 
	    @RequestParam(required=false, defaultValue="") String[] ac16, @RequestParam(required=false, defaultValue="") String[] ac17, @RequestParam(required=false, defaultValue="") String[] ac18, @RequestParam(required=false, defaultValue="") String[] ac19, @RequestParam(required=false, defaultValue="") String[] ac20, 
	    BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		BoardMasterVO master = new BoardMasterVO();
	    
	    master.setBbsId(boardVO.getBbsId());
	    master.setUniqId(user.getUniqId());

	    master = egovBBSMasterService.selectBBSMasterInf(master);
	
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    //----------------------------
		    // 기본 BBS template 지정 
		    //----------------------------
		    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		    	master.setTmplatCours("css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		    }
		    
			//템플릿이 없을때 기본값으로 적용
			if(master.getTmplatId().equals("") || master.getTmplatId().equals(null)) {
				master.setTmplatId("basic");
			}
	
		    model.addAttribute("boardMasterVO", master);
		    ////-----------------------------
	
		    String isDir = multiRequest.getServletContext().getRealPath("/WEB-INF/jsp/template/"+master.getTmplatId()+"/write.jsp");
			File f = new File(isDir);
			if(f.exists()) return "egovframework/com/cop/bbs/EgovArticleRegist";
			else return "error/noTmpltErrorPage";
		}
	
		if (isAuthenticated) {
		    List<FileVO> result = null;
		    String atchFileId = "";
		    
		    final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
			atchFileId = fileMngService.insertFileInfs(result);
		    }
		    board.setAtchFileId(atchFileId);
		    board.setFrstRegisterId(user.getUniqId());
		    board.setBbsId(boardVO.getBbsId());
		    board.setBlogId(boardVO.getBlogId());
		    
		    
		    //익명등록 처리 
		    if(board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")){
		    	board.setNtcrId("anonymous"); //게시물 통계 집계를 위해 등록자 ID 저장
		    	board.setNtcrNm("익명"); //게시물 통계 집계를 위해 등록자 Name 저장
		    	board.setFrstRegisterId("anonymous");
		    	
		    } else {
		    	board.setNtcrId(user.getId()); //게시물 통계 집계를 위해 등록자 ID 저장
		    	board.setNtcrNm(user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장
		    	
		    }
		    
		    //컨텐츠 관리 게시판은 태그제거 생략
		    if(master.getBbsId().equals("BBSMSTR_000000000002")) board.setNttCn(board.getNttCn());
		    else board.setNttCn(unscript(board.getNttCn()));	// XSS 방지
		    
		    board.setAcYn(master.getAcYn());
		    egovArticleService.insertArticle(board);
		    
		    // 추가칼럼 있을시 삽입
		    if(master.getAcYn().equals("Y")) {
		    	// 추가칼럼 갯수 구하기
		    	int[] acMaxSize = getMaxAcList(ac1, ac2, ac3, ac4, ac5, ac6, ac7, ac8, ac9, ac10, 
		        		ac11, ac12, ac13, ac14, ac15, ac16, ac17, ac18, ac19, ac20);
		        
		    	// 추가칼럼 등록하기
		    	for(int acseq=0; acseq<acMaxSize[20]; acseq++) {
		    		board.setOrd(acseq);
		    		if(acMaxSize[0]>acseq) board.setAc1(ac1[acseq]); else board.setAc1("");
		    		if(acMaxSize[1]>acseq) board.setAc2(ac2[acseq]); else board.setAc2("");
		    		if(acMaxSize[2]>acseq) board.setAc3(ac3[acseq]); else board.setAc3("");
		    		if(acMaxSize[3]>acseq) board.setAc4(ac4[acseq]); else board.setAc4("");
		    		if(acMaxSize[4]>acseq) board.setAc5(ac5[acseq]); else board.setAc5("");
		    		if(acMaxSize[5]>acseq) board.setAc6(ac6[acseq]); else board.setAc6("");
		    		if(acMaxSize[6]>acseq) board.setAc7(ac7[acseq]); else board.setAc7("");
		    		if(acMaxSize[7]>acseq) board.setAc8(ac8[acseq]); else board.setAc8("");
		    		if(acMaxSize[8]>acseq) board.setAc9(ac9[acseq]); else board.setAc9("");
		    		if(acMaxSize[9]>acseq) board.setAc10(ac10[acseq]); else board.setAc10("");
		    		if(acMaxSize[10]>acseq) board.setAc11(ac11[acseq]); else board.setAc11("");
		    		if(acMaxSize[11]>acseq) board.setAc12(ac12[acseq]); else board.setAc12("");
		    		if(acMaxSize[12]>acseq) board.setAc13(ac13[acseq]); else board.setAc13("");
		    		if(acMaxSize[13]>acseq) board.setAc14(ac14[acseq]); else board.setAc14("");
		    		if(acMaxSize[14]>acseq) board.setAc15(ac15[acseq]); else board.setAc15("");
		    		if(acMaxSize[15]>acseq) board.setAc16(ac16[acseq]); else board.setAc16("");
		    		if(acMaxSize[16]>acseq) board.setAc17(ac17[acseq]); else board.setAc17("");
		    		if(acMaxSize[17]>acseq) board.setAc18(ac18[acseq]); else board.setAc18("");
		    		if(acMaxSize[18]>acseq) board.setAc19(ac19[acseq]); else board.setAc19("");
		    		if(acMaxSize[19]>acseq) board.setAc20(ac20[acseq]); else board.setAc20("");
		    		
		    		egovArticleService.insertArticle(board);
		    	}
		    }
		    
		}

		return "redirect:/cop/bbs/selectArticleList.do?bbsId=" + boardVO.getBbsId();
		
    }

    /**
     * 게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
     * 
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/replyArticleView.do")
    public String addReplyBoardArticle(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
        
		BoardMasterVO master = new BoardMasterVO();
		BoardVO articleVO = new BoardVO();
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId(user.getUniqId());
	
		master = egovBBSMasterService.selectBBSMasterInf(master);
		boardVO = egovArticleService.selectArticleDetail(boardVO);
		
		//----------------------------
		// 기본 BBS template 지정 
		//----------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		    master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
	
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("result", boardVO);
	
		model.addAttribute("articleVO", articleVO);
		
		if(boardVO.getBlogAt().equals("chkBlog")){
			return "egovframework/com/cop/bbs/EgovArticleBlogReply";
		}else{
			String isDir = request.getServletContext().getRealPath("/WEB-INF/jsp/template/"+master.getTmplatId()+"/reply.jsp");
			File f = new File(isDir);
			if(f.exists()) return "egovframework/com/cop/bbs/EgovArticleReply";
			else return "error/noTmpltErrorPage";
		}
    }

    /**
     * 게시물에 대한 답변을 등록한다.
     * 
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/replyArticle.do")
    public String replyBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") BoardAddedColmnsVO board, BindingResult bindingResult, ModelMap model
	    ) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		BoardMasterVO master = new BoardMasterVO();
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
		    
		    master.setBbsId(boardVO.getBbsId());
		    master.setUniqId(user.getUniqId());
	
		    master = egovBBSMasterService.selectBBSMasterInf(master);
		    
	
		    //----------------------------
		    // 기본 BBS template 지정 
		    //----------------------------
		    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		    }
	
		    model.addAttribute("articleVO", boardVO);
		    model.addAttribute("boardMasterVO", master);
		    ////-----------------------------
	
		    return "egovframework/com/cop/bbs/EgovArticleReply";
		}
	
		if (isAuthenticated) {
		    final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    String atchFileId = "";
	
		    if (!files.isEmpty()) {
			List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
			atchFileId = fileMngService.insertFileInfs(result);
		    }
	
		    board.setAtchFileId(atchFileId);
		    board.setReplyAt("Y");
		    board.setFrstRegisterId(user.getUniqId());
		    board.setBbsId(board.getBbsId());
		    board.setParnts(Long.toString(boardVO.getNttId()));
		    board.setSortOrdr(boardVO.getSortOrdr());
		    board.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));
		    
		  //익명등록 처리 
		    if(board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")){
		    	board.setNtcrId("anonymous"); //게시물 통계 집계를 위해 등록자 ID 저장
		    	board.setNtcrNm("익명"); //게시물 통계 집계를 위해 등록자 Name 저장
		    	board.setFrstRegisterId("anonymous");
		    	
		    } else {
		    	board.setNtcrId(user.getId()); //게시물 통계 집계를 위해 등록자 ID 저장
		    	board.setNtcrNm(user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장
		    	
		    }
		    //컨텐츠 관리 게시판은 태그제거 생략
		    if(master.getBbsId().equals("BBSMSTR_000000000002")) board.setNttCn(board.getNttCn());
		    else board.setNttCn(unscript(board.getNttCn()));	// XSS 방지
		    
		    egovArticleService.insertArticle(board);
		}
		
		return "forward:/cop/bbs/selectArticleList.do";
    }

    /**
     * 게시물 수정을 위한 수정페이지로 이동한다.
     * 
     * @param boardVO
     * @param vo
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/updateArticleView.do")
    public String updateArticleView(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardAddedColmnsVO vo, ModelMap model)
	    throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		boardVO.setFrstRegisterId(user.getUniqId());
		
		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();
		List<BoardAddedColmnsVO> addedColmnsList = null;
		
		vo.setBbsId(boardVO.getBbsId());
		
		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId(user.getUniqId());
	
		if (isAuthenticated) {
		    bmvo = egovBBSMasterService.selectBBSMasterInf(bmvo);
		    bdvo = egovArticleService.selectArticleDetail(boardVO);
		    addedColmnsList = egovArticleService.selectArticleAddedColmnsDetail(boardVO);
		}
	
		//----------------------------
		// 기본 BBS template 지정 
		//----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
		    bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
		//----------------------------
		// 카테고리 리스트 가져오기
		//----------------------------
		if(bmvo.getCateUse().equals("Y")) {
			ArrayList<String> cateNames = new ArrayList<String>();
			String[] cateNamesTmp = bmvo.getCateList().split("\\|");
			for(String cmp : cateNamesTmp) {
				cateNames.add(cmp);
			}
			bmvo.setCateNames(cateNames);
		}
	
		//익명 등록글인 경우 수정 불가 
		if(bdvo.getNtcrId().equals("anonymous")){
			model.addAttribute("result", bdvo);
			model.addAttribute("boardMasterVO", bmvo);
			return "egovframework/com/cop/bbs/EgovArticleDetail";
		}
		
		model.addAttribute("articleVO", bdvo);
		model.addAttribute("articleACVO", addedColmnsList);
		model.addAttribute("boardMasterVO", bmvo);
		
		//템플릿이 없을때 기본값으로 적용
		if(bmvo.getTmplatId().equals("") || bmvo.getTmplatId().equals(null)) {
			bmvo.setTmplatId("basic");
		}
		
		if(boardVO.getBlogAt().equals("chkBlog")){
			return "egovframework/com/cop/bbs/EgovArticleBlogUpdt";
		}else{
			String isDir = request.getServletContext().getRealPath("/WEB-INF/jsp/template/"+bmvo.getTmplatId()+"/update.jsp");
			File f = new File(isDir);
			if(f.exists())return "egovframework/com/cop/bbs/EgovArticleUpdt";
			else return "error/noTmpltErrorPage";
			
		}
		
    }

    /**
     * 게시물에 대한 내용을 수정한다.
     * 
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/updateArticle.do")
    public String updateBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardAddedColmnsVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") BoardAddedColmnsVO board,
	    @RequestParam(required=false, defaultValue="") String[] ac1, @RequestParam(required=false, defaultValue="") String[] ac2, @RequestParam(required=false, defaultValue="") String[] ac3, @RequestParam(required=false, defaultValue="") String[] ac4, @RequestParam(required=false, defaultValue="") String[] ac5, 
	    @RequestParam(required=false, defaultValue="") String[] ac6, @RequestParam(required=false, defaultValue="") String[] ac7, @RequestParam(required=false, defaultValue="") String[] ac8, @RequestParam(required=false, defaultValue="") String[] ac9, @RequestParam(required=false, defaultValue="") String[] ac10, 
	    @RequestParam(required=false, defaultValue="") String[] ac11, @RequestParam(required=false, defaultValue="") String[] ac12, @RequestParam(required=false, defaultValue="") String[] ac13, @RequestParam(required=false, defaultValue="") String[] ac14, @RequestParam(required=false, defaultValue="") String[] ac15, 
	    @RequestParam(required=false, defaultValue="") String[] ac16, @RequestParam(required=false, defaultValue="") String[] ac17, @RequestParam(required=false, defaultValue="") String[] ac18, @RequestParam(required=false, defaultValue="") String[] ac19, @RequestParam(required=false, defaultValue="") String[] ac20,
	    BindingResult bindingResult, ModelMap model) throws Exception {
    	
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		BoardMasterVO bmvo = new BoardMasterVO();
	    BoardVO bdvo = new BoardVO();
	    
	    bmvo.setBbsId(boardVO.getBbsId());
	    bmvo.setUniqId(user.getUniqId());

	    bmvo = egovBBSMasterService.selectBBSMasterInf(bmvo);
	    bdvo = egovArticleService.selectArticleDetail(boardVO);
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		//--------------------------------------------------------------------------------------------
    	// @ XSS 대응 권한체크 체크  START
    	// param1 : 사용자고유ID(uniqId,esntlId)
    	//--------------------------------------------------------
    	LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
    	//step1 DB에서 해당 게시물의 uniqId 조회
    	BoardVO vo = egovArticleService.selectArticleDetail(boardVO);
    	
    	//step2 EgovXssChecker 공통모듈을 이용한 권한체크
    	EgovXssChecker.checkerUserXss(multiRequest, vo.getFrstRegisterId()); 
      	LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
    	//--------------------------------------------------------
    	// @ XSS 대응 권한체크 체크 END
    	//--------------------------------------------------------------------------------------------
	
		String atchFileId = boardVO.getAtchFileId();
	
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    boardVO.setFrstRegisterId(user.getUniqId());
	
		    model.addAttribute("articleVO", bdvo);
		    model.addAttribute("boardMasterVO", bmvo);
	
		    return "egovframework/com/cop/bbs/EgovArticleUpdt";
		}
		
		if (isAuthenticated) {
		    final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    if (!files.isEmpty()) {
				if ("".equals(atchFileId)) {
				    List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
				    atchFileId = fileMngService.insertFileInfs(result);
				    board.setAtchFileId(atchFileId);
				} else {
				    FileVO fvo = new FileVO();
				    fvo.setAtchFileId(atchFileId);
				    int cnt = fileMngService.getMaxFileSN(fvo);
				    List<FileVO> _result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
				    fileMngService.updateFileInfs(_result);
				}
		    }
	
		    board.setLastUpdusrId(user.getUniqId());
		    
		    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    
		    //컨텐츠 관리 게시판은 태그제거 생략
		    if(bmvo.getBbsId().equals("BBSMSTR_000000000002")) board.setNttCn(board.getNttCn());
		    else board.setNttCn(unscript(board.getNttCn()));	// XSS 방지
		    
		    board.setAcYn(bmvo.getAcYn());
		    egovArticleService.updateArticle(board);
		    
		    //추가칼럼이 있으면 수정한다.
		    if(board.getAcYn().equals("Y")) {
		    	
		    	int[] acMaxSize = getMaxAcList(ac1, ac2, ac3, ac4, ac5, ac6, ac7, ac8, ac9, ac10, 
		        		ac11, ac12, ac13, ac14, ac15, ac16, ac17, ac18, ac19, ac20);
		    	
		    	for(int acseq=0; acseq<acMaxSize[20]; acseq++) {
		    		board.setOrd(acseq);
		    		if(acMaxSize[0]>acseq) board.setAc1(ac1[acseq]); else board.setAc1("");
		    		if(acMaxSize[1]>acseq) board.setAc2(ac2[acseq]); else board.setAc2("");
		    		if(acMaxSize[2]>acseq) board.setAc3(ac3[acseq]); else board.setAc3("");
		    		if(acMaxSize[3]>acseq) board.setAc4(ac4[acseq]); else board.setAc4("");
		    		if(acMaxSize[4]>acseq) board.setAc5(ac5[acseq]); else board.setAc5("");
		    		if(acMaxSize[5]>acseq) board.setAc6(ac6[acseq]); else board.setAc6("");
		    		if(acMaxSize[6]>acseq) board.setAc7(ac7[acseq]); else board.setAc7("");
		    		if(acMaxSize[7]>acseq) board.setAc8(ac8[acseq]); else board.setAc8("");
		    		if(acMaxSize[8]>acseq) board.setAc9(ac9[acseq]); else board.setAc9("");
		    		if(acMaxSize[9]>acseq) board.setAc10(ac10[acseq]); else board.setAc10("");
		    		if(acMaxSize[10]>acseq) board.setAc11(ac11[acseq]); else board.setAc11("");
		    		if(acMaxSize[11]>acseq) board.setAc12(ac12[acseq]); else board.setAc12("");
		    		if(acMaxSize[12]>acseq) board.setAc13(ac13[acseq]); else board.setAc13("");
		    		if(acMaxSize[13]>acseq) board.setAc14(ac14[acseq]); else board.setAc14("");
		    		if(acMaxSize[14]>acseq) board.setAc15(ac15[acseq]); else board.setAc15("");
		    		if(acMaxSize[15]>acseq) board.setAc16(ac16[acseq]); else board.setAc16("");
		    		if(acMaxSize[16]>acseq) board.setAc17(ac17[acseq]); else board.setAc17("");
		    		if(acMaxSize[17]>acseq) board.setAc18(ac18[acseq]); else board.setAc18("");
		    		if(acMaxSize[18]>acseq) board.setAc19(ac19[acseq]); else board.setAc19("");
		    		if(acMaxSize[19]>acseq) board.setAc20(ac20[acseq]); else board.setAc20("");
		    		
		    		egovArticleService.updateArticle(board);
		    		
		    	}
		    }
		    
		}
		
		return "forward:/cop/bbs/selectArticleList.do";
    }

    /**
     * 게시물에 대한 내용을 삭제한다.
     * 
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/deleteArticle.do")
    public String deleteBoardArticle(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO board,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, ModelMap model) throws Exception {
	
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		//--------------------------------------------------------------------------------------------
    	// @ XSS 대응 권한체크 체크  START
    	// param1 : 사용자고유ID(uniqId,esntlId)
    	//--------------------------------------------------------
    	LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
    	//step1 DB에서 해당 게시물의 uniqId 조회
    	BoardVO vo = egovArticleService.selectArticleDetail(boardVO);
    	
    	//step2 EgovXssChecker 공통모듈을 이용한 권한체크
    	EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId()); 
      	LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
    	//--------------------------------------------------------
    	// @ XSS 대응 권한체크 체크 END
    	//--------------------------------------------------------------------------------------------
		
		BoardVO bdvo = egovArticleService.selectArticleDetail(boardVO);
		//익명 등록글인 경우 수정 불가 
		if(bdvo.getNtcrId().equals("anonymous")){
			model.addAttribute("result", bdvo);
			model.addAttribute("boardMasterVO", bdMstr);
			return "egovframework/com/cop/bbs/EgovArticleDetail";
		}
		
		if (isAuthenticated) {
		    board.setLastUpdusrId(user.getUniqId());
		    board.setAcYn(bdvo.getAcYn());
		    board.setNttSj(bdvo.getNttSj());
		    egovArticleService.deleteArticle(board);
		}
		
		if(boardVO.getBlogAt().equals("chkBlog")){
			return "forward:/cop/bbs/selectArticleBlogList.do";
		}else{
			return "forward:/cop/bbs/selectArticleList.do";
		}
    }
    
    /**
     * 방명록에 대한 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectGuestArticleList.do")
    public String selectGuestArticleList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		// 수정 및 삭제 기능 제어를 위한 처리
		model.addAttribute("sessionUniqId", user.getUniqId());
		
		BoardVO vo = new BoardVO();
	
		vo.setBbsId(boardVO.getBbsId());
		vo.setBbsNm(boardVO.getBbsNm());
		vo.setNtcrNm(user.getName());
		vo.setNtcrId(user.getUniqId());
	
		BoardMasterVO masterVo = new BoardMasterVO();
		
		masterVo.setBbsId(vo.getBbsId());
		masterVo.setUniqId(user.getUniqId());
		
		BoardMasterVO mstrVO = egovBBSMasterService.selectBBSMasterInf(masterVo);
	
		vo.setPageIndex(boardVO.getPageIndex());
		vo.setPageUnit(propertyService.getInt("pageUnit"));
		vo.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getPageUnit());
		paginationInfo.setPageSize(vo.getPageSize());
	
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = egovArticleService.selectGuestArticleList(vo);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("user", user);
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("boardMasterVO", mstrVO);
		model.addAttribute("articleVO", vo);
		model.addAttribute("paginationInfo", paginationInfo);
	
		return "egovframework/com/cop/bbs/EgovGuestArticleList";
    }
    
	
    /**
     * 방명록에 대한 내용을 등록한다.
     * 
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/insertGuestArticle.do")
    public String insertGuestList(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("Board") BoardAddedColmnsVO board, BindingResult bindingResult,
	    ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    BoardVO vo = new BoardVO();
	
		    vo.setBbsId(boardVO.getBbsId());
		    vo.setBbsNm(boardVO.getBbsNm());
		    vo.setNtcrNm(user.getName());
		    vo.setNtcrId(user.getUniqId());
	
		    BoardMasterVO masterVo = new BoardMasterVO();
		    
		    masterVo.setBbsId(vo.getBbsId());
		    masterVo.setUniqId(user.getUniqId());
		    
		    BoardMasterVO mstrVO = egovBBSMasterService.selectBBSMasterInf(masterVo);
	
		    vo.setPageUnit(propertyService.getInt("pageUnit"));
		    vo.setPageSize(propertyService.getInt("pageSize"));
	
		    PaginationInfo paginationInfo = new PaginationInfo();
		    paginationInfo.setCurrentPageNo(vo.getPageIndex());
		    paginationInfo.setRecordCountPerPage(vo.getPageUnit());
		    paginationInfo.setPageSize(vo.getPageSize());
	
		    vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		    vo.setLastIndex(paginationInfo.getLastRecordIndex());
		    vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		    Map<String, Object> map = egovArticleService.selectGuestArticleList(vo);
		    int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		    
		    paginationInfo.setTotalRecordCount(totCnt);
	
		    model.addAttribute("resultList", map.get("resultList"));
		    model.addAttribute("resultCnt", map.get("resultCnt"));
		    model.addAttribute("boardMasterVO", mstrVO);
		    model.addAttribute("articleVO", vo);	    
		    model.addAttribute("paginationInfo", paginationInfo);
	
		    return "egovframework/com/cop/bbs/EgovGuestArticleList";
	
		}
	
		if (isAuthenticated) {
		    board.setFrstRegisterId(user.getUniqId());
		    
		    egovArticleService.insertArticle(board);
	
		    boardVO.setNttCn("");
		    boardVO.setPassword("");
		    boardVO.setNtcrId("");
		    boardVO.setNttId(0);
		}
	
		return "forward:/cop/bbs/selectGuestArticleList.do";
    }
    
    /**
     * 방명록에 대한 내용을 삭제한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/deleteGuestArticle.do")
    public String deleteGuestList(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("articleVO") Board board, ModelMap model) throws Exception {
		@SuppressWarnings("unused")
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if (isAuthenticated) {
		    egovArticleService.deleteArticle(boardVO);
		}
		
		return "forward:/cop/bbs/selectGuestArticleList.do";
    }
    
    /**
     * 방명록 수정을 위한 특정 내용을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/updateGuestArticleView.do")
    public String updateGuestArticleView(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("boardMasterVO") BoardMasterVO brdMstrVO,
	    ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		// 수정 및 삭제 기능 제어를 위한 처리
		model.addAttribute("sessionUniqId", user.getUniqId());
		
		BoardVO vo = egovArticleService.selectArticleDetail(boardVO);
	
		boardVO.setBbsId(boardVO.getBbsId());
		boardVO.setBbsNm(boardVO.getBbsNm());
		boardVO.setNtcrNm(user.getName());
	
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = egovArticleService.selectGuestArticleList(boardVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", vo);
		model.addAttribute("paginationInfo", paginationInfo);
	
		return "egovframework/com/cop/bbs/EgovGuestArticleList";
    }
    
    /**
     * 방명록을 수정하고 게시판 메인페이지를 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/updateGuestArticle.do")
    public String updateGuestArticle(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute BoardAddedColmnsVO board, BindingResult bindingResult,
	    ModelMap model) throws Exception {

		//BBST02, BBST04
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    BoardVO vo = new BoardVO();
	
		    vo.setBbsId(boardVO.getBbsId());
		    vo.setBbsNm(boardVO.getBbsNm());
		    vo.setNtcrNm(user.getName());
		    vo.setNtcrId(user.getUniqId());
	
		    BoardMasterVO masterVo = new BoardMasterVO();
		    
		    masterVo.setBbsId(vo.getBbsId());
		    masterVo.setUniqId(user.getUniqId());
		    
		    BoardMasterVO mstrVO = egovBBSMasterService.selectBBSMasterInf(masterVo);
	
		    vo.setPageUnit(propertyService.getInt("pageUnit"));
		    vo.setPageSize(propertyService.getInt("pageSize"));
	
		    PaginationInfo paginationInfo = new PaginationInfo();
		    paginationInfo.setCurrentPageNo(vo.getPageIndex());
		    paginationInfo.setRecordCountPerPage(vo.getPageUnit());
		    paginationInfo.setPageSize(vo.getPageSize());
	
		    vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		    vo.setLastIndex(paginationInfo.getLastRecordIndex());
		    vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		    Map<String, Object> map = egovArticleService.selectGuestArticleList(vo);
		    int totCnt = Integer.parseInt((String)map.get("resultCnt"));
	
		    paginationInfo.setTotalRecordCount(totCnt);
		    
		    model.addAttribute("resultList", map.get("resultList"));
		    model.addAttribute("resultCnt", map.get("resultCnt"));
		    model.addAttribute("boardMasterVO", mstrVO);
		    model.addAttribute("articleVO", vo);
		    model.addAttribute("paginationInfo", paginationInfo);
	
		    return "egovframework/com/cop/bbs/EgovGuestArticleList";
		}
	
		if (isAuthenticated) {
		    egovArticleService.updateArticle(board);
		    boardVO.setNttCn("");
		    boardVO.setPassword("");
		    boardVO.setNtcrId("");
		    boardVO.setNttId(0);
		}
	
		return "forward:/cop/bbs/selectGuestArticleList.do";
    }
    
    /*********************
     * 블로그관련
     * ********************/
    
    /**
     * 블로그 게시판에 대한 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectArticleBlogList.do")
    public String selectArticleBlogList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		BlogVO blogVo = new BlogVO();
		blogVo.setFrstRegisterId(user.getUniqId());
		blogVo.setBbsId(boardVO.getBbsId());
		blogVo.setBlogId(boardVO.getBlogId());
		BlogVO master = egovBBSMasterService.selectBlogDetail(blogVo);
		
		boardVO.setFrstRegisterId(user.getUniqId());

		//블로그 카테고리관리 권한(로그인 한 사용자만 가능)
		int loginUserCnt =  egovArticleService.selectLoginUser(boardVO);
		
		//블로그 게시판 제목 추출
		List<BoardVO> blogNameList = egovArticleService.selectBlogNmList(boardVO);

		if(user != null) {
	    	model.addAttribute("sessionUniqId", user.getUniqId());
	    }
		
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("blogNameList", blogNameList);
		model.addAttribute("loginUserCnt", loginUserCnt);
		
		return "egovframework/com/cop/bbs/EgovArticleBlogList";
    }
    
    /**
     * 블로그 게시물에 대한 상세 타이틀을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectArticleBlogDetail.do")
    public ModelAndView selectArticleBlogDetail(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

	        if(!isAuthenticated) {
	        	throw new IllegalAccessException("Login Required!");
	        }
	        
		BoardVO vo = new BoardVO();
		
		boardVO.setLastUpdusrId(user.getUniqId());
		
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
		
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<BoardVO> blogSubJectList = egovArticleService.selectArticleDetailDefault(boardVO);
		vo = egovArticleService.selectArticleCnOne(boardVO);
		
		int totCnt = egovArticleService.selectArticleDetailDefaultCnt(boardVO);
		paginationInfo.setTotalRecordCount(totCnt);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("blogSubJectList", blogSubJectList);
		mav.addObject("paginationInfo", paginationInfo);
		
		if(vo.getNttCn() != null){
			mav.addObject("blogCnOne", vo);
		}
		
		//비밀글은 작성자만 볼수 있음 
		if(!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y") && !user.getUniqId().equals(vo.getFrstRegisterId()))
		mav.setViewName("forward:/cop/bbs/selectArticleList.do");
		return mav;
    }
    
    /**
     * 블로그 게시물에 대한 상세 내용을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectArticleBlogDetailCn.do")
    public ModelAndView selectArticleBlogDetailCn(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("commentVO") CommentVO commentVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		boardVO.setLastUpdusrId(user.getUniqId());
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
        	throw new IllegalAccessException("Login Required!");
        }
		
		BoardVO vo = egovArticleService.selectArticleDetail(boardVO);
		
		//----------------------------
		// 댓글 처리
		//----------------------------
		CommentVO articleCommentVO = new CommentVO();
		commentVO.setWrterNm(user.getName());
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
		paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
		paginationInfo.setPageSize(commentVO.getSubPageSize());
	
		commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
		commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
		commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = egovArticleCommentService.selectArticleCommentList(commentVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
		
	    //댓글 처리 END
		//----------------------------
		
		List<BoardVO> blogCnList = egovArticleService.selectArticleDetailCn(boardVO);
		ModelAndView mav = new ModelAndView("jsonView");
		
		// 수정 처리된 후 댓글 등록 화면으로 처리되기 위한 구현
		if (commentVO.isModified()) {
		    commentVO.setCommentNo("");
		    commentVO.setCommentCn("");
		}
		
		// 수정을 위한 처리
		if (!commentVO.getCommentNo().equals("")) {
			mav.setViewName ("forward:/cop/cmt/updateArticleCommentView.do");
		}
		
		mav.addObject("blogCnList", blogCnList);
		mav.addObject("resultUnder", vo);
		mav.addObject("paginationInfo", paginationInfo);
		mav.addObject("resultList", map.get("resultList"));
		mav.addObject("resultCnt", map.get("resultCnt"));
		mav.addObject("articleCommentVO", articleCommentVO);	// validator 용도
		
		commentVO.setCommentCn("");	// 등록 후 댓글 내용 처리
		
		//비밀글은 작성자만 볼수 있음 
		if(!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y") && !user.getUniqId().equals(vo.getFrstRegisterId()))
		mav.setViewName("forward:/cop/bbs/selectArticleList.do");
		return mav;
		
    }
    
    /**
     * 개인블로그 관리 
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectBlogListManager.do")
    public String selectBlogMasterList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	
    	boardVO.setPageUnit(propertyService.getInt("pageUnit"));
    	boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		boardVO.setFrstRegisterId(user.getUniqId());
		
		Map<String, Object> map = egovArticleService.selectBlogListManager(boardVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));	
		model.addAttribute("paginationInfo", paginationInfo);
    	
    	return "egovframework/com/cop/bbs/EgovBlogListManager";
    }

    /**
     * 템플릿에 대한 미리보기용 게시물 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/previewBoardList.do")
    public String previewBoardArticles(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	
		String template = boardVO.getSearchWrd();	// 템플릿 URL
		
		BoardMasterVO master = new BoardMasterVO();
		
		master.setBbsNm("미리보기 게시판");
	
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		BoardVO target = null;
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		target = new BoardVO();
		target.setNttSj("게시판 기능 설명");
		target.setFrstRegisterId("ID");
		target.setFrstRegisterNm("관리자");
		target.setFrstRegisterPnttm("2019-01-01");
		target.setInqireCo(7);
		target.setParnts("0");
		target.setReplyAt("N");
		target.setReplyLc("0");
		target.setUseAt("Y");
		
		list.add(target);
		
		target = new BoardVO();
		target.setNttSj("게시판 부가 기능 설명");
		target.setFrstRegisterId("ID");
		target.setFrstRegisterNm("관리자");
		target.setFrstRegisterPnttm("2019-01-01");
		target.setInqireCo(7);
		target.setParnts("0");
		target.setReplyAt("N");
		target.setReplyLc("0");
		target.setUseAt("Y");
		
		list.add(target);
		
		boardVO.setSearchWrd("");
	
		int totCnt = list.size();
		
		//공지사항 추출
		List<BoardVO> noticeList = egovArticleService.selectNoticeArticleList(boardVO);
	
		paginationInfo.setTotalRecordCount(totCnt);
	
		master.setTmplatCours(template);
		
		model.addAttribute("resultList", list);
		model.addAttribute("resultCnt", Integer.toString(totCnt));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("noticeList", noticeList);
		
		model.addAttribute("preview", "true");
	
		return "egovframework/com/cop/bbs/EgovArticleList";
    }

    /**
     * 미리보기 커뮤니티 메인페이지를 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/previewBlogMainPage.do")
    public String previewBlogMainPage(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

    	String tmplatCours = boardVO.getSearchWrd();
    	
		BlogVO master = new BlogVO();
		master.setBlogNm("미리보기 블로그");
		master.setBlogIntrcn("미리보기를 위한 블로그입니다.");
		master.setUseAt("Y");
		master.setFrstRegisterId(user.getUniqId());
		
		boardVO.setFrstRegisterId(user.getUniqId());

		//블로그 카테고리관리 권한(로그인 한 사용자만 가능)
		int loginUserCnt =  egovArticleService.selectLoginUser(boardVO);
		
		//블로그 게시판 제목 추출
		List<BoardVO> blogNameList = new ArrayList<BoardVO>();
		
		BoardVO target = null;
		target = new BoardVO();
		target.setBbsNm("블로그게시판#1");
		
		blogNameList.add(target);
		
		
		if(user != null) {
	    	model.addAttribute("sessionUniqId", user.getUniqId());
	    }
		
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("blogNameList", blogNameList);
		model.addAttribute("loginUserCnt", 1);
		
		model.addAttribute("preview", "true");
		
		// 안전한 경로 문자열로 조치
		tmplatCours = EgovWebUtil.filePathBlackList(tmplatCours);

		// 화이트 리스트 체크
		List<TemplateInfVO> templateWhiteList = egovTemplateManageService.selectTemplateWhiteList();
		LOGGER.debug("Template > WhiteList Count = {}",templateWhiteList.size());
		if ( tmplatCours == null ) tmplatCours = "";
		for(TemplateInfVO templateInfVO : templateWhiteList){
			LOGGER.debug("Template > whiteList TmplatCours = "+templateInfVO.getTmplatCours());
            if ( tmplatCours.equals(templateInfVO.getTmplatCours()) ) {
            	return tmplatCours;
            }
        }
		
		LOGGER.debug("Template > WhiteList mismatch! Please check Admin page!");
		return "egovframework/com/cmm/egovError";
	}
    
    /**
     * 최근 게시물 목록을 가져온다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/latestArticleListView.do")
    public String latestArticleListView(HttpServletRequest request,
    		@RequestParam(required=false, defaultValue="basic") String skinNm, @RequestParam(required=false) String[] bbsId, 
    		@RequestParam(required=false, defaultValue="5") String cntOfArticle, @RequestParam(required=false, defaultValue="FRST_REGIST_PNTTM") String ordColmn, 
    		@RequestParam(required=false, defaultValue="DESC") String ordWay, 
    		@RequestParam(required=false, defaultValue="N") String pageUse, @RequestParam(required=false, defaultValue="10") String pageGroupNum, 
    		@RequestParam(required=false, defaultValue="10") String pageArticleNum, @RequestParam(required=false, defaultValue="1") String pageNum,
    		@RequestParam(required=false, defaultValue="") String cateName, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	int totalArticles = 0;
    	
    	paramMap.put("skinNm", skinNm);
    	paramMap.put("cntOfArticle", cntOfArticle);
    	paramMap.put("ordColmn", ordColmn);
    	paramMap.put("ordWay", ordWay);
    	if(!cateName.equals("")) paramMap.put("cateName", cateName);
    	
    	if(bbsId == null || bbsId.length == 0 || bbsId.length == 1) {
	    	paramMap.put("bbsId", bbsId[0]);
	    	totalArticles = egovArticleService.latestArticleListViewCnt(paramMap);
    	} else {
    		paramMap.put("bbsId", bbsId);
	    	totalArticles = egovArticleService.latestMultiArticleListViewCnt(paramMap);
    	}
    	paramMap.put("totalArticles", String.valueOf(totalArticles));
    	
    	//페이징처리
    	if(pageUse.equals("Y")) {
    		paramMap.put("pageUse", pageUse);
    		int pageGN = Integer.parseInt(pageGroupNum);
    		int pageAN = Integer.parseInt(pageArticleNum);
    		int pageTotal = (totalArticles%pageAN>0) ? (int) Math.floor(totalArticles/pageAN)+1 : (int) Math.floor(totalArticles/pageAN);
    		int pageN = Integer.parseInt(pageNum);
    		if(pageN > pageTotal) pageN = pageTotal;
    		if(pageN < 1) pageN = 1;
    		if(pageTotal < 1) pageTotal = 1;
    		
    		int pageGS = (int) Math.floor((pageN-1)/pageGN)*pageGN+1;
    		int pageGE = (pageGS+pageGN-1>pageTotal) ? pageTotal : pageGS+pageGN-1;
    		
    		paramMap.put("pageGroupNum", pageGroupNum);
    		paramMap.put("pageArticleNum", pageArticleNum);
    		paramMap.put("startArticle", String.valueOf(pageAN*(pageN-1)));
    		paramMap.put("pageNum", pageNum);
    		paramMap.put("pageGroupStart", String.valueOf(pageGS));
    		paramMap.put("pageGroupEnd", String.valueOf(pageGE));
    		paramMap.put("pageTotal", String.valueOf(pageTotal));
    	}
		
    	List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
    	
    	if(bbsId == null || bbsId.length == 0 || bbsId.length == 1) {
	    	paramMap.put("bbsId", bbsId[0]);
	    	resultList = egovArticleService.latestArticleListView(paramMap);

	    	try {
		    	BoardMasterVO vo = new BoardMasterVO();
				vo.setBbsId((String) paramMap.get("bbsId"));
				if(user != null) vo.setUniqId(user.getUniqId());
				else vo.setUniqId("");
			
				BoardMasterVO master = egovBBSMasterService.selectBBSMasterInf(vo);
				model.addAttribute("boardMaster", master);
			} catch(Exception e) { e.printStackTrace(); }
    	} else {
    		paramMap.put("bbsId", bbsId);
	    	resultList = egovArticleService.latestMultiArticleListView(paramMap);
    	}
    	model.addAttribute("resultList", resultList);
    	model.addAttribute("paramMap", paramMap);
    	
    	//템플릿 파일 유무를 검사한다.
		String isDir = request.getServletContext().getRealPath("/WEB-INF/jsp/template/_latest/"+skinNm+".jsp");
		File f = new File(isDir);
		if(f.exists()) return "template/_latest/"+skinNm;
		else return "error/noTmpltErrorPage";
	}
    
    
    private int[] getMaxAcList(String[] ac1, String[] ac2, String[] ac3, String[] ac4, String[] ac5, 
    		String[] ac6, String[] ac7, String[] ac8, String[] ac9, String[] ac10, 
    		String[] ac11, String[] ac12, String[] ac13, String[] ac14, String[] ac15, 
    		String[] ac16, String[] ac17, String[] ac18, String[] ac19, String[] ac20) {
    	
    	// 추가칼럼 갯수 구하기
    	int acNLength[] = new int[] {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    	if(!ac1[0].equals("")) acNLength[0] = ac1.length;
    	if(!ac2[0].equals("")) acNLength[1] = ac2.length;
    	if(!ac3[0].equals("")) acNLength[2] = ac3.length;
    	if(!ac4[0].equals("")) acNLength[3] = ac4.length;
    	if(!ac5[0].equals("")) acNLength[4] = ac5.length;
    	if(!ac6[0].equals("")) acNLength[5] = ac6.length;
    	if(!ac7[0].equals("")) acNLength[6] = ac7.length;
    	if(!ac8[0].equals("")) acNLength[7] = ac8.length;
    	if(!ac9[0].equals("")) acNLength[8] = ac9.length;
    	if(!ac10[0].equals("")) acNLength[9] = ac10.length;
    	if(!ac11[0].equals("")) acNLength[10] = ac11.length;
    	if(!ac12[0].equals("")) acNLength[11] = ac12.length;
    	if(!ac13[0].equals("")) acNLength[12] = ac13.length;
    	if(!ac14[0].equals("")) acNLength[13] = ac14.length;
    	if(!ac15[0].equals("")) acNLength[14] = ac15.length;
    	if(!ac16[0].equals("")) acNLength[15] = ac16.length;
    	if(!ac17[0].equals("")) acNLength[16] = ac17.length;
    	if(!ac18[0].equals("")) acNLength[17] = ac18.length;
    	if(!ac19[0].equals("")) acNLength[18] = ac19.length;
    	if(!ac20[0].equals("")) acNLength[19] = ac20.length;
    	
    	// 추가칼럼 최대갯수 구하기
    	int acMaxSize = acNLength[0];
         
        for(int i=0 ; i<acNLength.length ; i++){
            if(acNLength[i] >= acMaxSize){
            	acMaxSize = acNLength[i];
            }
        }
        
        //최대갯수는 배열 마지막값에 넣는다.
        acNLength[20] = acMaxSize;
        
        return acNLength;
    }
        
    
}
