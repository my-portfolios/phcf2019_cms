package egovframework.phcf.performance;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.popbill.api.MessageService;


import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.BoardAddedColmnsVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.ems.service.EgovMultiPartEmail;
import egovframework.com.cop.ems.service.EgovSndngMailRegistService;
import egovframework.com.cop.ems.service.EgovSndngMailService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.performance.service.PerformanceService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class PerformanceController {
	@Resource(name="PerformanceService")
	PerformanceService service;
	
	@Resource(name="EgovArticleService")
	EgovArticleService egovArticleService;
	
	/** sndngMailRegistService */
	@Resource(name = "sndngMailRegistService")
	private EgovSndngMailRegistService sndngMailRegistService;
	
	/** EgovFileMngUtil */
	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	/** EgovFileMngService */
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	/** mberManageService */
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;
	
	/** popbill message service*/
	@Autowired
    private MessageService messageService;
	
	@Resource(name = "egovMultiPartEmail")
	private EgovMultiPartEmail egovMultiPartEmail;
	
	@Resource(name = "egovSndngMailService")
	private EgovSndngMailService egovSndngMailService;
	
	
	private String emailRegex = EgovProperties.getProperty("emailAddress.Regex.javaString");
	
	
	@RequestMapping(value = "/performance/list.do")
	public ModelAndView performanceList(@RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/performance/list");

		return mav;
	}
	
	@RequestMapping(value = "/performance/selectPerformanceApplierListJson.do")
	public ModelAndView selectPerformanceApplierListJson(@RequestParam HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		Object pageIndex = paramMap.get("pageIndex");
		Object pageSize = paramMap.get("pageSize");
		int pageOffset  = 0;
		if(pageIndex != null && pageSize != null) {
			pageOffset = (Integer.parseInt(pageIndex.toString())-1) * Integer.parseInt(pageSize.toString());
			paramMap.put("pageOffset", pageOffset);
		}
		
		List<HashMap<String, Object>> performanceAndApllierList = new ArrayList<>();
		List<HashMap<String, Object>> performanceApplierList = service.selectPerformanceApplierList(paramMap);
		int performanceAndApllierListCnt = service.selectPerformanceApplierListCnt(paramMap);
		
		for(HashMap<String, Object> applier : performanceApplierList) {
			BoardVO boardVO = new BoardVO();
			boardVO.setBbsId(applier.get("BBS_ID").toString());
			boardVO.setNttId(Long.parseLong(applier.get("NTT_ID").toString()));
			
			boardVO = egovArticleService.selectArticleDetail(boardVO);
			List<BoardAddedColmnsVO> addedColmnList = egovArticleService.selectArticleAddedColmnsDetail(boardVO);
			
			if(boardVO == null || addedColmnList == null) { continue; }
			HashMap<String, Object> applyInfo = new HashMap<>();
			applyInfo.putAll(applier);
			List<HashMap<String, Object>> applyVisitorList = service.selectAppliedVisitorPerformanceList(applyInfo);
			String visitorInfo = "";
			for(HashMap<String, Object> visitor : applyVisitorList) {
				visitorInfo += visitor.get("PERSON_NAME").toString() + " " + visitor.get("PERSON_PHONE").toString() + "<br/>";
			}
			
			applyInfo.put("visitorInfo", visitorInfo);
			applyInfo.put("subject", boardVO.getNttSj());
			applyInfo.put("author", boardVO.getNtcrNm());
			applyInfo.put("category", boardVO.getCateName());
			
			for(BoardAddedColmnsVO addedColmn : addedColmnList) {
				if(addedColmn.getOrd() == 0) {
					applyInfo.put("cost", addedColmn.getAc5());
					applyInfo.put("place", addedColmn.getAc7());
				}
			}
			
			performanceAndApllierList.add(applyInfo);
		}
		
		mav.addObject("performanceAndApllierList", performanceAndApllierList);
		mav.addObject("performanceAndApllierListCnt", performanceAndApllierListCnt);
		return mav;
	}
	
	@RequestMapping(value = "/performance/updatePerformanceApplierItem.do", method=RequestMethod.POST)
	public ModelAndView updateReservationMaster(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mav = new ModelAndView("jsonView");
		String result = "success";
		try {
			service.updatePerformanceApplierItem(paramMap);
		}
		catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}
		
		mav.addObject("result", result);
		return mav;
	}
	
	
	/* 
	 * 2021.06.24 ?????????
	 * ?????? ?????? ??????
	 *  ?????? ?????? ??????
	 *  -> ????????? ?????? ??????
	 * 	-> ?????? ?????? ??? ??????
	 *  -> ?????? ??????
	 * */
	
	@RequestMapping(value = "/performance/articleInfos.do")
	public String articleInfos(@ModelAttribute("boardVO") BoardVO boardVO, ModelMap model) throws Exception {
//		// ????????? ???????????? ?????? ????????????
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
//			return "redirect:/egovDevIndex.jsp";
//			return "/EgovContent.do";
			return "index";		
		}
		
		String cateName = "??????";
		String[] cateNames = {"??????", "??????", "??????"};
		
		/** EgovPropertyService */
		boardVO.setPageUnit(propertiesService.getInt("pageUnit"));
		boardVO.setPageSize(propertiesService.getInt("pageSize"));
		
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
		
		//boardVO ??????
		/*boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		boardVO.setCateName(cateName);*/
		
		//HashMap ??????
		HashMap<String, Object> boardMap = new HashMap<>();
		boardMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
		boardMap.put("lastIndex", paginationInfo.getLastRecordIndex());
		boardMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
		boardMap.put("cateList", cateNames);
		boardMap.put("searchCnd", boardVO.getSearchCnd());
		boardMap.put("searchWrd", boardVO.getSearchWrd());
		
		// performance ??????
		Map<String, Object> articleListMap = egovArticleService.selectArticleListByCateNames(boardMap);
//		Map<String, Object> articleListMap = egovArticleService.selectArticleListByCateName(boardVO);
		List<BoardVO> articleList = (List<BoardVO>)articleListMap.get("resultList");
		model.addAttribute("resultList", articleList);
		
		int totCnt = Integer.parseInt((String)articleListMap.get("resultCnt"));
		paginationInfo.setTotalRecordCount(totCnt);
		
		model.addAttribute("paginationInfo", paginationInfo);

		//???????????? ??????????????? ????????????????????? ??????
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("PHC017");
		
		List<CmmnDetailCode> performanceCode = cmmUseService.selectCmmCodeDetail(vo);
		
		model.addAttribute("performanceCode", performanceCode);
		
		return "egovframework/phcf/performance/performance_list";
	}
	
	//?????? ?????? ?????? ???????????? ??? ??????
	
	@RequestMapping(value = "/performance/writeMail.do", method=RequestMethod.POST)
	public ModelAndView writeMail(/*@ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO,*/ HttpServletRequest request, @RequestParam("selectedId") String nttId) throws Exception {
//	public ModelAndView sendMail(final MultipartHttpServletRequest multiRequest, @RequestParam("selectedId") String nttId, @ModelAttribute("boardVO") BoardVO boardVO, ModelMap model, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/performance/mailRegist");
		
		BoardVO searchBoardVO = new BoardVO();
		SndngMailVO sndngMailVO = new SndngMailVO();
		String sjPrefix = "[??????????????????] ";
		
		String link = "N";
		/*if (sndngMailVO != null && sndngMailVO.getLink() != null && !sndngMailVO.getLink().equals("")) {
			link = sndngMailVO.getLink();
		}*/
		String bbsId = request.getParameter("bbsId");
		
		searchBoardVO.setBbsId(bbsId);
		searchBoardVO.setNttId(Long.valueOf(nttId));
		
		// article ??????
		BoardVO article = egovArticleService.selectArticleDetail(searchBoardVO);
		
//		sndngMailVO.setSj(paramMap.get("mailSubject").toString());
		
		

		// ?????? ?????? ??????
		sndngMailVO.setSj(sjPrefix + article.getNttSj());
		System.out.println("nttSJ==="+sndngMailVO.getSj());
		//????????? ??????
		sndngMailVO.setDsptchPerson("phcf01");
		// ????????? ??????
//		sndngMailVO.setEmailCn(article.getNttCn());
		
		// ???????????? ID
		if(article.getAtchFileId() != null && article.getAtchFileId() != "") {
			sndngMailVO.setAtchFileId(article.getAtchFileId());
		
		}
		System.out.println("===??????");
		System.out.println(sndngMailVO.getSj());
		System.out.println("===emailCn");
		System.out.println(sndngMailVO.getEmailCn());
		System.out.println("===dsptchPerson");
		System.out.println(sndngMailVO.getDsptchPerson());
		System.out.println("===recptnPerson");
		System.out.println(sndngMailVO.getRecptnPerson());
		
		mav.addObject("resultInfo", sndngMailVO);
		mav.addObject("bbsId", bbsId);
		mav.addObject("nttId", nttId);
		
		System.out.println("in /performance/writeMail.do sndngMailVO file==="+sndngMailVO.getAtchFileId());
		

		return mav;
		
	}
	
	/**
	 * Copy From EgovSndngMailRegistController.java
	 * ??????????????? ????????????
	 * @param multiRequest MultipartHttpServletRequest
	 * @param sndngMailVO SndngMailVO
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value = "/performance/insertSndngMail.do", method=RequestMethod.POST)
	public String insertSndngMail(final MultipartHttpServletRequest multiRequest, @ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO, ModelMap model, HttpServletRequest request)
			throws Exception {
		
		/*
		 * ????????? ?????? ??? ???????????? ??????
		 * ?????????, ?????????, ?????? ??????, ?????? ??????, ?????? ??????
		 * 
		 */
		String link = "N";
		if (sndngMailVO != null && sndngMailVO.getLink() != null && !sndngMailVO.getLink().equals("")) {
			link = sndngMailVO.getLink();
		}

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
   	 	// KISA ??????????????? ?????? (2018-12-10, ?????????)
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
        
        System.out.println("===checked_membership");
		System.out.println(request.getParameter("checked_membership"));
		System.out.println("===checked_normal");
		System.out.println(request.getParameter("checked_normal"));
		
		// ?????? ??????, ?????? ????????? ????????? ?????? ????????? ?????? ??????
		List<MberManageVO> mberManageList;
		HashMap<String, Object> searchMap = new HashMap<>();
		CommonMethod commonMethod = new CommonMethod();
		
		if(request.getParameter("checked_membership") != null &&
				request.getParameter("checked_normal") != null) {
			searchMap.put("toMembership", true);
			searchMap.put("toNormal", true);
			System.out.println("==?????????, ???????????? ??????");
		}
		// ?????? ????????? ????????? ?????? ??????
		else if(request.getParameter("checked_membership") != null) {
			searchMap.put("toMembership", true);
			searchMap.put("toNormal", false);
			System.out.println("==???????????? ??????");
		}
		// ?????? ????????? ????????? ?????? ??????
		else {
			searchMap.put("toMembership", false);
			searchMap.put("toNormal", true);
			System.out.println("==???????????? ??????");
		}
		System.out.println("searchMap==" + searchMap);
		// ?????? ?????? ??????
		mberManageList = mberManageService.selectMberListForSndngMail(searchMap);
		
		
		
        System.out.println("in /performance/insertSndngMail.do, sndngMailVO===" + sndngMailVO);
        System.out.println("in /performance/insertSndngMail.do, sndngMailVO file===" + sndngMailVO.getAtchFileId());
        System.out.println("in /performance/insertSndngMail.do, multiRequest.getFileMap()" + multiRequest.getFileMap());
        
		List<FileVO> _result = new ArrayList<FileVO>();
		String _atchFileId = "";
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		if(!files.isEmpty()) {
			_result = fileUtil.parseFileInf(files, "MSG_", 0, "", "");
			_atchFileId = fileMngService.insertFileInfs(_result); //????????? ?????????????????? ????????? ???????????? ID??? ????????????.

		}
		System.out.println("_result===" + _result);
		System.out.println("_atchFileId2===" +_atchFileId);
		String orignlFileList = ""; //?????? ????????? ?????? ??? ???????????? ?????? ????????? ??? ????????????.
		
		for (int i = 0; i < _result.size(); i++) {
			FileVO fileVO = _result.get(i);
			orignlFileList = fileVO.getOrignlFileNm();
		}
		if (sndngMailVO != null) {
			sndngMailVO.setDsptchPerson(user.getId());
		}
		else {
			sndngMailVO  = new SndngMailVO();
			sndngMailVO.setDsptchPerson(user.getId());
		}
		//?????? ?????? ??????
		sndngMailVO = attachFile(sndngMailVO, _atchFileId, orignlFileList);
		
		
		// ?????? ?????? ??????
		sndngMailVO.setSj(sndngMailVO.getSj());
		
		// ?????? ?????? ??????
		BoardVO boardSearchVO = new BoardVO();
		String bbsId = request.getParameter("bbsId");
		String nttId = request.getParameter("nttId");
		boardSearchVO.setBbsId(bbsId);
		boardSearchVO.setNttId(Long.valueOf(nttId));
		BoardVO article = egovArticleService.selectArticleDetail(boardSearchVO);
		
		String articleUrl = EgovProperties.getProperty("Globals.place_url") + "/cop/bbs/selectArticleDetail.do?" 
				+ "bbsId=" + bbsId + "&nttId=" + nttId;
		String emailTemplate = commonMethod.getFileContent("egovframework/mail/email_performance.html");
		String emailCn = emailTemplate.replace("#emailContent#", sndngMailVO.getEmailCn())
				.replace("#articleTitle#", article.getNttSj())
				.replace("#articleLink#", articleUrl);
		
		// article poster img ??????
		String posterImgChildPath = "IMG/" + bbsId + "_"+ nttId + ".png";
		String posterImgFullPath = EgovProperties.getProperty("Globals.fileStorePath") + posterImgChildPath;
		File posterImgFile = new File(posterImgFullPath); 
		if(posterImgFile.exists()) {
			emailCn = emailCn.replace("#posterSrc#", "/upload/" + posterImgChildPath);
		} else {
			emailCn = emailCn.replace("#posterSrc#", "");
		}
		emailCn = CommonMethod.localImgSrcToGlobal(emailCn);
		
		sndngMailVO.setEmailCn(emailCn);
		
		
		System.out.println("email Sj==" + sndngMailVO.getSj());
		System.out.println("in /performance/insertSndngMail.do, emailCn===" + sndngMailVO.getEmailCn());
		System.out.println("sndngMailVO file==="+sndngMailVO.getAtchFileId());
		System.out.println("DsptchPerson==" + sndngMailVO.getDsptchPerson());
		System.out.println("OrignlFileNm==" + sndngMailVO.getOrignlFileNm());
		
		System.out.println("emialRegex===" + emailRegex);
////////////////************************* ???????????? ????????? ?????? ????????? ??????
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		
		int j = 0;
		for(MberManageVO mber : mberManageList) {
			String e_adres = mber.getMberEmailAdres();
			if(!e_adres.equals("") && e_adres != null && /*EgovFormatCheckUtil.checkFormatMail(e_adres)*/
					Pattern.matches(emailRegex, e_adres)) {
				System.out.println("email==" + e_adres + 
						" \nsendMailYn==" + mber.getSendMailYn() + " \nmbershipType==" + mber.getMembershipType());
				
				// ?????? ?????? ??? ??????
//				sndngMailVO.setRecptnPerson(e_adres);
//				Callable<Boolean> callableSingleMail = sndngMailRegistService.sendSingleMailCallable(sndngMailVO);
//				Future<Boolean> resultFuture = executorService.submit(callableSingleMail);
//				boolean result = resultFuture.get();
				
//				boolean result = sndngMailRegistService.insertSndngMail(sndngMailVO);
				
//				if(result) 
					System.out.println("?????? ??????=="+e_adres);
				j++;
			}
		}
		System.out.println("total mail original target==" + mberManageList.size());
		System.out.println("total mail target=="+j);
		
		
		
		// ???????????? ?????? ??????
		List<String> mailAddresses =  new ArrayList<>();
		mailAddresses.add("hkimkm1@hubizict.com");
//		mailAddresses.add("hkkyoungmin@gmail.com");
		
		for(String address : mailAddresses) {
			sndngMailVO.setRecptnPerson(address);
			// ?????? ?????? ??? ??????
//			boolean result = sndngMailRegistService.insertSndngMail(sndngMailVO);			
		}

		// ?????? ?????? ?????????
		String[] toEmails = new String[49];
		for(int i = 0; i < toEmails.length; i++) {
			toEmails[i] = "hkimkm1@hubizict.com";
//			boolean result = sndngMailRegistService.insertSndngMail(sndngMailVO);
		}

		SndngMailVO sndngMailVOForAsync = new SndngMailVO();
		sndngMailVOForAsync.setEmailCn(sndngMailVO.getEmailCn());
		sndngMailVOForAsync.setSj(sndngMailVO.getSj());
		sndngMailVOForAsync.setRecptnPerson(sndngMailVO.getRecptnPerson());
		
		
		Callable<String> callableMultiMail = sendMultiMailCallable(toEmails, sndngMailVOForAsync);
		
		for(int i = 0; i < 150; i ++) {
			Callable<String> callableSingleMail = sendSingleMailCallable(sndngMailVOForAsync, executorService);
//			Callable<Boolean> callableSingleMail = sndngMailRegistService.sendSingleMailCallable(sndngMailVO);
			executorService.submit(callableSingleMail);
		}
//		es.submit(callableMultiMail);
		executorService.shutdown();
		
////////////////************************* ???????????? ????????? ?????? ????????? ??????
		boolean result = true;
		// ?????? ????????? ????????? ?????? ?????? ????????? ????????? ??? ????????? ??? ??????. => boolean arr??? ???????????? ?????????????
		if (result) {
			return "redirect:/performance/articleInfos.do";
			
		} else {
			return "egovframework/com/cmm/error/egovError";
		}
	}
	
	@RequestMapping(value = "/performance/writeMessage.do", method=RequestMethod.POST)
	public ModelAndView writeMessage(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, @RequestParam("selectedId") String nttId) {
		ModelAndView mav = new ModelAndView("egovframework/phcf/performance/writeMessage");
//		ModelAndView mav = new ModelAndView("egovframework/com/cop/sms/EgovSmsInfoRegist");
		String sjPrefix = "[??????????????????] ";
		System.out.println("sms paramMap====" + paramMap);
		System.out.println("sms nttId===" + nttId);
		System.out.println("request parameter selectedId==" + request.getParameter("selectedId"));
		
		String bbsId = request.getParameter("bbsId");
		
		BoardVO searchBoardVO = new BoardVO();
		
		searchBoardVO.setBbsId(bbsId);
		searchBoardVO.setNttId(Long.valueOf(nttId));
		
		// article ??????
		BoardVO article = egovArticleService.selectArticleDetail(searchBoardVO);
		
		return mav;
	}
	
	@RequestMapping(value = "/performance/test.do")
	public String test(ModelMap model, HttpServletRequest request)
			throws Exception{
		/*FileVO fileVO = new FileVO();
		fileVO.setAtchFileId("FILE_000000000016312");
		fileVO.setFileSn("0");
		FileVO fvo = fileMngService.selectFileInf(fileVO);

		File uFile = new File(fvo.getFileStreCours(), fvo.getStreFileNm());

		System.out.println("file path: " + uFile.getPath());
		System.out.println("file stored cours: " + fvo.getFileStreCours());
		
		HtmlEmail email = new HtmlEmail();
		File img = new File("C:/eGovFrameDev-3.8.0-64bit/workspace/upload/NSE_202106300503482360");
		String cid = email.embed(uFile);
		System.out.println("cid===" + cid);*/
		
		
		return "redirect:/performance/articleInfos.do";
		
	}
	
	private String strDecode(String originalString, String originalCharSet, String toCharSet) throws UnsupportedEncodingException {
		String decoded = new String(originalString.getBytes(originalCharSet), toCharSet);
		return decoded;
	}
	
	private SndngMailVO attachFile(SndngMailVO sndngMailVO, String attachedFileId, String originalFileNm) throws Exception {
		//?????? ?????? ??????
				/*if (sndngMailVO != null) { // ??????????????? ?????? ?????? ?????? ?????????.
					FileVO fileVo = new FileVO();
					sndngMailVO.setDsptchPerson(dispatchId);
					//?????? ????????? ?????? ???
					if(attachedFileId == null || attachedFileId.equals("")) {
						// ???????????? ?????? ????????? ?????? ???
						if (sndngMailVO.getAtchFileId() == null || sndngMailVO.getAtchFileId().equals("")) {
							sndngMailVO.setAtchFileId(attachedFileId);
							// ?????? ????????? ?????? ??????
							sndngMailVO.setOrignlFileNm(strDecode(originalFileNm, "UTF-8", "8859_1"));
//							sndngMailVO.setOrignlFileNm(orignlFileList);
						}
						// ???????????? ?????? ????????? ?????? ???
						else {
							fileVo.setAtchFileId(sndngMailVO.getAtchFileId());
							fileVo.setFileSn("0");
							fileVo = fileMngService.selectFileInf(fileVo);
							
							// ?????? ????????? ?????? ??????
							sndngMailVO.setOrignlFileNm(strDecode(fileVo.getOrignlFileNm(), "UTF-8", "8859_1"));
//							sndngMailVO.setOrignlFileNm(fileVo.getOrignlFileNm());
						}
					}
					//?????? ????????? ?????? ???
					else {
						sndngMailVO.setAtchFileId(attachedFileId);
						// ?????? ????????? ?????? ??????
						sndngMailVO.setOrignlFileNm(strDecode(originalFileNm, "UTF-8", "8859_1"));
//						sndngMailVO.setOrignlFileNm(orignlFileList);
					}
				}*/
				
				// ???????????? ?????? ??????????????? ????????? ???????????? ????????? ????????? ?????? ??????
				if (sndngMailVO != null) {
					sndngMailVO.setAtchFileId(attachedFileId);
//					sndngMailVO.setDsptchPerson(dispatchId);
					sndngMailVO.setOrignlFileNm(strDecode(originalFileNm, "UTF-8", "8859_1"));
				}
				return sndngMailVO;
	}
	
	Callable<String> sendMultiMailCallable(String[] toAddresses, SndngMailVO sndngMailVOForAsync) {
		Callable<String> callableMultiMail = new Callable<String>() {

			@Override
			public String call() throws Exception {
				try {
					egovSndngMailService.sendMultiMail(toAddresses, 10, sndngMailVOForAsync);
				} catch (Exception e) {
					System.out.println("thread error!");
					e.printStackTrace();
					System.out.println(e.getMessage());
					return "fail";
				}
				return "success";
			}
			
		};
		return callableMultiMail;
	}
	
	Callable<String> sendSingleMailCallable(SndngMailVO sndngMailVO, ExecutorService es) {
		Callable<String> callableSingleMail = new Callable<String>() {
			@Override
			public String call() throws Exception {
				try {
//					mailThread.sendMultiMail();
					egovSndngMailService.sndngMail(sndngMailVO);
					
					// ????????? ?????? ??????
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) es;
                     
                    int poolSize = threadPoolExecutor.getPoolSize();//????????? ??? ????????? ??????
                    String threadName = Thread.currentThread().getName();//????????? ?????? ?????? ?????? ????????? ?????? ??????
                     
                    System.out.println("[??? ????????? ??????:" + poolSize + "] ?????? ????????? ??????: "+threadName);
                     
				} catch (Exception e) {
					System.out.println("thread error!");
					e.printStackTrace();
					System.out.println(e.getMessage());
					return "fail";
				}
				return "success";
			}
		};
		
		return callableSingleMail;
	}
	
}
