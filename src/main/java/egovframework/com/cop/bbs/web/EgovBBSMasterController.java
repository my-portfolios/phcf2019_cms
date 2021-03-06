package egovframework.com.cop.bbs.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovComponentChecker;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.cop.bbs.service.Blog;
import egovframework.com.cop.bbs.service.BlogUserVO;
import egovframework.com.cop.bbs.service.BlogVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/**
 * 게시판 속성관리를 위한 컨트롤러  클래스
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
 *   2009.3.12  이삼섭          최초 생성
 *   2009.06.26	한성곤		    2단계 기능 추가 (댓글관리, 만족도조사)
 *	 2011.07.21 안민정          커뮤니티 관련 메소드 분리 (->EgovBBSAttributeManageController)
 *	 2011.8.26	정진오			IncludedInfo annotation 추가
 *   2011.09.15 서준식           2단계 기능 추가 (댓글관리, 만족도조사) 적용방법 변경
 *   2016.06.13 김연호          표준프레임워크 v3.6 개선
 * </pre>
 */

@Controller
public class EgovBBSMasterController {

    @Resource(name = "EgovBBSMasterService")
    private EgovBBSMasterService egovBBSMasterService;

    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    
    @Resource(name = "egovBBSMstrIdGnrService")
    private EgovIdGnrService idgenServiceBbs;
    
    @Resource(name = "egovBlogIdGnrService")
    private EgovIdGnrService idgenServiceBlog;
    
    /** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
    


    @Autowired
    private DefaultBeanValidator beanValidator;

    //Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 신규 게시판 마스터 등록을 위한 등록페이지로 이동한다.
     * 
     * @param boardMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/insertBBSMasterView.do")
    public String insertBBSMasterView(HttpServletRequest request, @ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
		BoardMasterVO boardMaster = new BoardMasterVO();
		//공통코드(게시판유형)
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM101");
		List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("bbsTyCode", codeResult);
		model.addAttribute("boardMasterVO", boardMaster);
		
		//-------------------------------------------
		// 2019. 10. 31, 윤병훈
		// template 하위 폴더내 template 목록을 가져온다.
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		Map<String, ArrayList<String>> TmpltKdList = new HashMap<String, ArrayList<String>>();
		
		TmpltKdList = getTmpltDirList(request, "/WEB-INF/jsp/template/");
		
		ArrayList<String> TmpltNmList = new ArrayList<String>();
		TmpltKdList.forEach((item,value)->{
			TmpltNmList.add(item);
		});
		
		//JSONObject TmpltKdListJson = new JSONObject();
		//TmpltKdListJson.putAll(TmpltKdList);
		
		model.addAttribute("TmpltNmList", TmpltNmList);
        //model.addAttribute("TmpltKdList", TmpltKdListJson);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        
		//---------------------------------
		// 2011.09.15 : 2단계 기능 추가 반영 방법 변경
		//---------------------------------

		
		if(EgovComponentChecker.hasComponent("EgovArticleCommentService")){
			model.addAttribute("useComment", "true");
		}
		if(EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")){
			model.addAttribute("useSatisfaction", "true");
		}
		
		return "egovframework/com/cop/bbs/EgovBBSMasterRegist";
    }

    /**
     * 신규 게시판 마스터 정보를 등록한다.
     * 
     * @param boardMasterVO
     * @param boardMaster
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/insertBBSMaster.do")
    public String insertBBSMaster(HttpServletRequest request, @ModelAttribute("searchVO") BoardMasterVO boardMasterVO, @ModelAttribute("boardMaster") BoardMaster boardMaster,
	    BindingResult bindingResult, ModelMap model) throws Exception {
    	
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		beanValidator.validate(boardMaster, bindingResult);
		if (bindingResult.hasErrors()) {
		    ComDefaultCodeVO vo = new ComDefaultCodeVO();
		    
		    //게시판유형코드
		    vo.setCodeId("COM101");
		    List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
		    model.addAttribute("bbsTyCode", codeResult);
	
		    return "egovframework/com/cop/bbs/EgovBBSMasterRegist";
		}
		
		if (isAuthenticated) {
		    boardMaster.setFrstRegisterId(user.getUniqId());
		    
		    //게시판 유형을 통합게시판으로 기본 저장한다.
		    boardMaster.setBbsTyCode("BBST01");
		    
		    if(boardMasterVO.getBlogAt().equals("Y")){
		    	boardMaster.setBlogAt("Y");
		    }else{
		    	boardMaster.setBlogAt("N");
		    }
		    
		    //-------------------------------------------
			// 2019. 11. 6, 윤병훈
			// 게시판의 템플릿정보가 없을 때 기본값적용
			//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		    if(boardMaster.getTmplatId().equals("") || boardMaster.getTmplatId().equals(null)) {
		    	boardMaster.setTmplatId("basic");
		    }
		    
		    egovBBSMasterService.insertBBSMasterInf(boardMaster);
		}
		
		//-------------------------------------------
		// 2019. 11. 4, 윤병훈
		// 템플릿 추가칼럼 생성
		// 2019. 11. 11, 윤병훈
		// 
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		//String sql="";
		
		//파일 객체 생성
        //Path path = Paths.get("template/board/"+boardMaster.getTmplatId()+"/scheme.sql");
		/*String isDir = request.getServletContext().getRealPath("/WEB-INF/jsp/template/"+boardMaster.getTmplatId()+"/scheme.sql");
		File f = new File(isDir);
		if(f.exists()) {
			Path path = Paths.get(isDir);
			
			// 캐릭터셋 지정
	        Charset cs = StandardCharsets.UTF_8;
	        //파일 내용담을 리스트
	        List<String> list = new ArrayList<String>();
	        try{
	            list = Files.readAllLines(path,cs);
	        }catch(IOException e){
	            e.printStackTrace();
	        }
	        for(String readLine : list){
	            System.out.println(readLine);
	            sql += readLine+" ";
	        }
	        sql = sql.replaceAll(System.getProperty("line.separator"), " ");
	        sql = sql.replaceAll("#ID#", boardMaster.getTmplatId());
	        
	        egovBBSMasterService.createAddedTableInf(sql);
		}*/
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		if(boardMaster.getBlogAt().equals("Y")){
			return "forward:/cop/bbs/selectArticleBlogList.do";
		}else{
			return "forward:/cop/bbs/selectBBSMasterInfs.do";
		}
		
    }

    /**
     * 게시판 마스터 목록을 조회한다.
     * 
     * @param boardMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @IncludedInfo(name="게시판관리",order = 180 ,gid = 40)
    @RequestMapping("/cop/bbs/selectBBSMasterInfs.do")
    public String selectBBSMasterInfs(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
		boardMasterVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardMasterVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardMasterVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardMasterVO.getPageUnit());
		paginationInfo.setPageSize(boardMasterVO.getPageSize());
	
		boardMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = egovBBSMasterService.selectBBSMasterInfs(boardMasterVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
		System.out.print("paginationInfopaginationInfopaginationInfo"+paginationInfo.getTotalRecordCount());
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));	
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "egovframework/com/cop/bbs/EgovBBSMasterList";
    }
    
    /**
     * 블로그에 대한 목록을 조회한다.
     * 
     * @param blogVO
     * @param model
     * @return
     * @throws Exception
     */
    @IncludedInfo(name="블로그관리", order = 170 ,gid = 40)
    @RequestMapping("/cop/bbs/selectBlogList.do")
    public String selectBlogMasterList(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	 //KISA 보안취약점 조치 (2018-12-10, 신용호)
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
    	
		boardMasterVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardMasterVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardMasterVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardMasterVO.getPageUnit());
		paginationInfo.setPageSize(boardMasterVO.getPageSize());
	
		boardMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		boardMasterVO.setFrstRegisterId(user.getUniqId());
		
		Map<String, Object> map = egovBBSMasterService.selectBlogMasterInfs(boardMasterVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));	
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/cop/bbs/EgovBlogList";
    }
    
    /**
     * 블로그 등록을 위한 등록페이지로 이동한다.
     * 
     * @param blogVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/insertBlogMasterView.do")
    public String insertBlogMasterView(@ModelAttribute("searchVO") BlogVO blogVO, ModelMap model) throws Exception {
    	model.addAttribute("blogMasterVO", new BlogVO());
	return "egovframework/com/cop/bbs/EgovBlogRegist";
    }
    
    /**
     * 블로그 생성 유무를 판단한다.
     * 
     * @param blogVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectChkBloguser.do")
    public ModelAndView chkBlogUser(@ModelAttribute("searchVO") BlogVO blogVO, ModelMap model) throws Exception {
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
        	throw new IllegalAccessException("Login Required!");
        }
    	
    	model.addAttribute("blogMasterVO", new BlogVO());
    	
    	String userVal="";
    	blogVO.setFrstRegisterId(user.getUniqId());
    	userVal = egovBBSMasterService.checkBlogUser(blogVO);
    	
    	ModelAndView mav = new ModelAndView("jsonView");
    	mav.addObject("userChk", userVal);
    	return mav;
    }

    /**
     * 블로그 정보를 등록한다.
     * 
     * @param blogVO
     * @param blog
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/insertBlogMaster.do")
    public String insertBlogMaster(@ModelAttribute("searchVO") BlogVO blogVO, @ModelAttribute("blogMaster") Blog blog,
	    BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
        if(!isAuthenticated) { //KISA 보안약점 조치 (2018-12-10, 신용호)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		blogVO.setFrstRegisterId(user.getUniqId());
		BlogVO vo = egovBBSMasterService.checkBlogUser2(blogVO);
		
		if(vo != null) {
			model.addAttribute("blogMasterVO", new BlogVO());
			model.addAttribute("message", egovMessageSource.getMessage("comCopBlog.validate.blogUserCheck"));
			return "egovframework/com/cop/bbs/EgovBlogRegist";
		}
		
		beanValidator.validate(blog, bindingResult);
		
		if (bindingResult.hasErrors()) {
		    return "egovframework/com/cop/bbs/EgovBlogRegist";
		}
		
		String blogId = idgenServiceBlog.getNextStringId(); //블로그 아이디 채번
		String bbsId = idgenServiceBbs.getNextStringId(); //게시판 아이디 채번
		
		blog.setRegistSeCode("REGC02");
		blog.setFrstRegisterId(user.getUniqId());
		blog.setBbsId(bbsId);
		blog.setBlogId(blogId);
		blog.setBlogAt("Y");
		egovBBSMasterService.insertBlogMaster(blog);
		
		if (isAuthenticated) {
		    //블로그 개설자의 정보를 등록한다.
		    BlogUserVO blogUserVO = new BlogUserVO();
		    blogUserVO.setBlogId(blogId);
		    blogUserVO.setEmplyrId(user.getUniqId());
		    blogUserVO.setMngrAt("Y");
		    blogUserVO.setMberSttus("P");
		    blogUserVO.setUseAt("Y");
		    blogUserVO.setFrstRegisterId(user.getUniqId());
		    
		    egovBBSMasterService.insertBoardBlogUserRqst(blogUserVO);
		}
		return "forward:/cop/bbs/selectBlogList.do";
    }

    /**
     * 게시판 마스터 상세내용을 조회한다.
     * 
     * @param boardMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectBBSMasterDetail.do")
    public String selectBBSMasterDetail(@ModelAttribute("searchVO") BoardMasterVO searchVO, ModelMap model) throws Exception {
		BoardMasterVO vo = egovBBSMasterService.selectBBSMasterInf(searchVO);
		model.addAttribute("result", vo);
	
		//---------------------------------
		// 2011.09.15 : 2단계 기능 추가 반영 방법 변경
		//---------------------------------
		
		if(EgovComponentChecker.hasComponent("EgovArticleCommentService")){
			model.addAttribute("useComment", "true");
		}
		if(EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")){
			model.addAttribute("useSatisfaction", "true");
		}
		
		return "egovframework/com/cop/bbs/EgovBBSMasterDetail";
    }
    
    /**
     * 게시판 마스터정보를 수정하기 위한 전 처리
     * @param bbsId
     * @param searchVO
     * @param model
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/updateBBSMasterView.do")
    public String updateBBSMasterView(HttpServletRequest request, @RequestParam("bbsId") String bbsId ,
            @ModelAttribute("searchVO") BoardMaster searchVO, ModelMap model)
            throws Exception {


        BoardMasterVO boardMasterVO = new BoardMasterVO();

        
        //게시판유형코드
        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        vo.setCodeId("COM101");
        List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
        model.addAttribute("bbsTyCode", codeResult);
        
        // Primary Key 값 세팅
        boardMasterVO.setBbsId(bbsId);
        boardMasterVO = egovBBSMasterService.selectBBSMasterInf(boardMasterVO);
        
        //----------------------------
		// 카테고리 리스트 가져오기
		//----------------------------
		if(boardMasterVO.getCateUse().equals("Y")) {
			ArrayList<String> cateNames = new ArrayList<String>();
			String[] cateNamesTmp = boardMasterVO.getCateList().split("\\|");
			for(String cmp : cateNamesTmp) {
				cateNames.add(cmp);
			}
			boardMasterVO.setCateNames(cateNames);
		}

        model.addAttribute("boardMasterVO", boardMasterVO);
        

		//---------------------------------
		// 2011.09.15 : 2단계 기능 추가 반영 방법 변경
		//---------------------------------
		
		if(EgovComponentChecker.hasComponent("EgovArticleCommentService")){
			model.addAttribute("useComment", "true");
		}
		if(EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")){
			model.addAttribute("useSatisfaction", "true");
		}
        
        return "egovframework/com/cop/bbs/EgovBBSMasterUpdt";
    }
    

    /**
     * 게시판 마스터 정보를 수정한다.
     * 
     * @param boardMasterVO
     * @param boardMaster
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/updateBBSMaster.do")
    public String updateBBSMaster(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, @ModelAttribute("boardMaster") BoardMaster boardMaster,
	    BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		beanValidator.validate(boardMaster, bindingResult);
		if (bindingResult.hasErrors()) {
		    BoardMasterVO vo = egovBBSMasterService.selectBBSMasterInf(boardMasterVO);
	
		    model.addAttribute("result", vo);
	
		    ComDefaultCodeVO comVo = new ComDefaultCodeVO();
	        comVo.setCodeId("COM101");
	        List<?> codeResult = cmmUseService.selectCmmCodeDetail(comVo);
	        model.addAttribute("bbsTyCode", codeResult);
		    
		    return "egovframework/com/cop/bbs/EgovBBSMasterUpdt";
		}
	
		if (isAuthenticated) {
		    boardMaster.setLastUpdusrId(user.getUniqId());
		    
		    egovBBSMasterService.updateBBSMasterInf(boardMaster);
		}
	
		return "forward:/cop/bbs/selectBBSMasterInfs.do";
    }

    /**
     * 게시판 마스터 정보를 삭제한다.
     * 
     * @param boardMasterVO
     * @param boardMaster
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/deleteBBSMaster.do")
    public String deleteBBSMaster(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, @ModelAttribute("boardMaster") BoardMaster boardMaster
	    ) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	if (isAuthenticated) {
	    boardMaster.setLastUpdusrId(user.getUniqId());
	    egovBBSMasterService.deleteBBSMasterInf(boardMaster);
	}
	// status.setComplete();
	return "forward:/cop/bbs/selectBBSMasterInfs.do";
    }
    
    /**
     * 포트릿을 위한 블로그 목록 정보를 조회한다.
     * 
     * @param blogVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectBlogListPortlet.do")
    public String selectBlogListPortlet(@ModelAttribute("searchVO") BlogVO blogVO, ModelMap model) throws Exception {
	List<BlogVO> result = egovBBSMasterService.selectBlogListPortlet(blogVO);
	
	model.addAttribute("resultList", result);

	return "egovframework/com/cop/bbs/EgovBlogListPortlet";
    }
    
    /**
     * 포트릿을 위한 게시판 목록 정보를 조회한다.
     * 
     * @param blogVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/selectBBSListPortlet.do")
    public String selectBBSListPortlet(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
    	List<BoardMasterVO> result = egovBBSMasterService.selectBBSListPortlet(boardMasterVO);
    	
    	model.addAttribute("resultList", result);
    	
    	return "egovframework/com/cop/bbs/EgovBBSListPortlet";
    }
    
    //-------------------------------------------
	// 2019. 10. 31, 윤병훈
	// 템플릿 폴더 목록을 가져온다.
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public HashMap<String, ArrayList<String>> getTmpltDirList(HttpServletRequest request, String source) throws IOException{
    	//template 하위 폴더위 template 목록을 가져온다.
		String isDir = request.getServletContext().getRealPath(source);
		HashMap<String, ArrayList<String>> TmpltKdList = new HashMap<String, ArrayList<String>>();
		ArrayList<String> TmpltList = new ArrayList<String>();
		
		// 하위의 모든 디렉토리
		TmpltList=subDirList(isDir);
		TmpltList.forEach(item->{
			String subDir = isDir+File.separator+item+File.separator; 
			//템플릿 유효성 평가
			if(validateTemplate(subDir)) {
				try {
					TmpltKdList.put(item, subDirList(subDir));
				} catch (IOException e) {e.printStackTrace();}
			}
		});
		
		return TmpltKdList;
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    //-------------------------------------------
  	// 2019. 10. 31, 윤병훈
  	// 하위폴더의 목록을 불러온다.
  	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public ArrayList<String> subDirList(String source) throws IOException{
		File dir = new File(source);
		
		if(!dir.exists()) { 
			return null; 
		}
		
		File[] fileList = dir.listFiles(); 
		
		ArrayList<String> folderList = new ArrayList<String>();
		
		for(int i=0 ; i < fileList.length ; i++){
			File file = fileList[i]; 
			if(file.isDirectory() && !file.getName().equals("System Volume Information") && !file.getName().equals("$RECYCLE.BIN") && !file.getName().matches("_.*")){
				System.out.println(file.getName());
				folderList.add(file.getName());
			}
		}
		
		return folderList;
	}
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    //-------------------------------------------
  	// 2019. 10. 31, 윤병훈
  	// 템플릿의 유효성평가
  	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public Boolean validateTemplate(String source) {
		return true;
	}
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
