package egovframework.phcf.performance;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

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
import egovframework.com.cop.ems.service.EgovSndngMailRegistService;
import egovframework.com.cop.ems.service.SndngMail;
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
	 * 2021.06.24 김경민
	 * 메일 발송 메뉴
	 *  발송 메뉴 입장
	 *  -> 발송할 항목 선택
	 * 	-> 메일 작성 뷰 입장
	 *  -> 메일 전송
	 * */
	
	@RequestMapping(value = "/performance/sendInfo.do")
	public String sendInfo(@ModelAttribute("boardVO") BoardVO boardVO, ModelMap model) throws Exception {
//		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
//			return "redirect:/egovDevIndex.jsp";
//			return "/EgovContent.do";
			return "index";		
		}
		
		String cateName = "공연";
		/** EgovPropertyService */
		boardVO.setPageUnit(propertiesService.getInt("pageUnit"));
		boardVO.setPageSize(propertiesService.getInt("pageSize"));
		
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		boardVO.setCateName(cateName);
		
		// performance 조회
		Map<String, Object> articleListMap = egovArticleService.selectArticleListByCateName(boardVO);
		List<BoardVO> articleList = (List<BoardVO>)articleListMap.get("resultList");
		model.addAttribute("resultList", articleList);
		
		int totCnt = Integer.parseInt((String)articleListMap.get("resultCnt"));
		paginationInfo.setTotalRecordCount(totCnt);
		
		model.addAttribute("paginationInfo", paginationInfo);

		//기회전시 카테고리를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("PHC017");
		
		List<CmmnDetailCode> performanceCode = cmmUseService.selectCmmCodeDetail(vo);
		
		model.addAttribute("performanceCode", performanceCode);
		
		return "egovframework/phcf/performance/performance_list";
	}
	
	//메일 작성 폼에 들어가기 전 처리
	
	@RequestMapping(value = "/performance/writeMail.do")
	public ModelAndView sendMail(/*@ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO,*/ HttpServletRequest request, @RequestParam("selectedId") String nttId) throws Exception {
//	public ModelAndView sendMail(final MultipartHttpServletRequest multiRequest, @RequestParam("selectedId") String nttId, @ModelAttribute("boardVO") BoardVO boardVO, ModelMap model, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("egovframework/phcf/performance/mailRegist");
		
		BoardVO searchBoardVO = new BoardVO();
		SndngMailVO sndngMailVO = new SndngMailVO();
		String sjPrefix = "[포항문화재단] ";
		
		String link = "N";
		/*if (sndngMailVO != null && sndngMailVO.getLink() != null && !sndngMailVO.getLink().equals("")) {
			link = sndngMailVO.getLink();
		}*/
		String bbsId = request.getParameter("bbsId");
		
		searchBoardVO.setBbsId(bbsId);
		searchBoardVO.setNttId(Long.valueOf(nttId));
		
		// article 선택
		BoardVO article = egovArticleService.selectArticleDetail(searchBoardVO);
		
//		sndngMailVO.setSj(paramMap.get("mailSubject").toString());
		
		

		// 메일 제목 설정
		sndngMailVO.setSj(sjPrefix + article.getNttSj());
		System.out.println("nttSJ==="+sndngMailVO.getSj());
		//발신자 설정
		sndngMailVO.setDsptchPerson("phcf01");
		// 이메일 내용
//		sndngMailVO.setEmailCn(article.getNttCn());
		
		// 첨부파일 ID
		if(article.getAtchFileId() != null && article.getAtchFileId() != "") {
			sndngMailVO.setAtchFileId(article.getAtchFileId());
		
		}
		System.out.println("===제목");
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
	 * 발송메일을 등록한다
	 * @param multiRequest MultipartHttpServletRequest
	 * @param sndngMailVO SndngMailVO
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value = "/performance/insertSndngMail.do")
	public String insertSndngMail(final MultipartHttpServletRequest multiRequest, @ModelAttribute("sndngMailVO") SndngMailVO sndngMailVO, ModelMap model, HttpServletRequest request)
			throws Exception {
		
		/*
		 * 이메일 보낼 시 정해야할 부분
		 * 발신자, 수신자, 메일 제목, 메일 내용, 첨부 파일
		 * 
		 */
		String link = "N";
		if (sndngMailVO != null && sndngMailVO.getLink() != null && !sndngMailVO.getLink().equals("")) {
			link = sndngMailVO.getLink();
		}

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
        
        System.out.println("===checked_membership");
		System.out.println(request.getParameter("checked_membership"));
		System.out.println("===checked_normal");
		System.out.println(request.getParameter("checked_normal"));
		
		// 유료 회원, 무료 회원의 이메일 목록 추출을 위한 작업
		List<MberManageVO> mberManageList;
		HashMap<String, Object> searchMap = new HashMap<>();
		CommonMethod commonMethod = new CommonMethod();
		
		if(request.getParameter("checked_membership") != null &&
				request.getParameter("checked_normal") != null) {
			searchMap.put("toMembership", "Y");
			searchMap.put("toNormal", "Y");
			System.out.println("==멤버십, 일반회원 선택");
		}
		// 유료 회원의 이메일 목록 추출
		else if(request.getParameter("checked_membership") != null) {
			searchMap.put("toMembership", "Y");
			searchMap.put("toNormal", "N");
			System.out.println("==맴버십만 선택");
		}
		// 무료 회원의 이메일 목록 추출
		else {
			searchMap.put("toMembership", "N");
			searchMap.put("toNormal", "Y");
			System.out.println("==일반회원 선택");
		}
		System.out.println("searchMap==" + searchMap);
		// 회원 목록 추출
		mberManageList = mberManageService.selectMberListForSndngMail(searchMap);
		
		
		
        System.out.println("in /performance/insertSndngMail.do, sndngMailVO===" + sndngMailVO);
        System.out.println("in /performance/insertSndngMail.do, sndngMailVO file===" + sndngMailVO.getAtchFileId());
        System.out.println("in /performance/insertSndngMail.do, multiRequest.getFileMap()" + multiRequest.getFileMap());
        
		List<FileVO> _result = new ArrayList<FileVO>();
		String _atchFileId = "";
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		if(!files.isEmpty()) {
			_result = fileUtil.parseFileInf(files, "MSG_", 0, "", "");
			_atchFileId = fileMngService.insertFileInfs(_result); //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.

		}
		
		System.out.println("_atchFileId2===" +_atchFileId);
		String orignlFileList = ""; //첨부 파일이 최대 한 개이므로 파일 이름이 한 가지이다.
		
		for (int i = 0; i < _result.size(); i++) {
			FileVO fileVO = _result.get(i);
			orignlFileList = fileVO.getOrignlFileNm();
		}
		
		sndngMailVO.setDsptchPerson(user.getId());
		//첨부 파일 처리
		sndngMailVO = attachFile(sndngMailVO, _atchFileId, orignlFileList);
		//첨부 파일 처리
		/*if (sndngMailVO != null) { // 첨부파일이 있든 없든 항상 실행됨.
			FileVO fileVo = new FileVO();
			//첨부 파일이 없을 때
			sndngMailVO.setDsptchPerson(user.getId());
			if(_atchFileId == null || _atchFileId.equals("")) {
				// 게시물의 원래 파일이 없을 때
				if (sndngMailVO.getAtchFileId() == null || sndngMailVO.getAtchFileId().equals("")) {
					sndngMailVO.setAtchFileId(_atchFileId);
					// 한긒 파일명 깨짐 방지
					sndngMailVO.setOrignlFileNm(strDecode(orignlFileList, "UTF-8", "8859_1"));
//					sndngMailVO.setOrignlFileNm(orignlFileList);
				}
				// 게시물의 원래 파일이 있을 때
				else {
					fileVo.setAtchFileId(sndngMailVO.getAtchFileId());
					fileVo.setFileSn("0");
					fileVo = fileMngService.selectFileInf(fileVo);
					
					// 한긒 파일명 깨짐 방지
					sndngMailVO.setOrignlFileNm(strDecode(fileVo.getOrignlFileNm(), "UTF-8", "8859_1"));
//					sndngMailVO.setOrignlFileNm(fileVo.getOrignlFileNm());
				}
			}
			//첨부 파일이 있을 때
			else {
				sndngMailVO.setAtchFileId(_atchFileId);
				// 한긒 파일명 깨짐 방지
				sndngMailVO.setOrignlFileNm(strDecode(orignlFileList, "UTF-8", "8859_1"));
//				sndngMailVO.setOrignlFileNm(orignlFileList);
			}
		}*/
		
		// 자동으로 파일 첨부해주는 기능을 없애려면 하려면 이것을 주석 해제하고 위의 코드를 없애면 된다.
		/*if (sndngMailVO != null) {
			sndngMailVO.setAtchFileId(_atchFileId);
			sndngMailVO.setDsptchPerson(user.getId());
			sndngMailVO.setOrignlFileNm(orignlFileList);
		}*/
		
		// 메일 제목 설정
		sndngMailVO.setSj(sndngMailVO.getSj());
		
		// 메일 내용 설정
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
		
		// article poster img 얻기
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
		////////////////************************* 실서버에 적용할 때는 수정할 부분
		
		
		int j = 0;
		for(MberManageVO mber : mberManageList) {
			String e_adres = mber.getMberEmailAdres();
			if(!e_adres.equals("") && e_adres != null && /*EgovFormatCheckUtil.checkFormatMail(e_adres)*/
					Pattern.matches(emailRegex, e_adres)) {
				System.out.println("email==" + e_adres + 
						" \nsendMailYn==" + mber.getSendMailYn() + " \nmbershipType==" + mber.getMembershipType());
				
				// 메일 등록 및 발송
//				sndngMailVO.setRecptnPerson(e_adres);
//				boolean result = sndngMailRegistService.insertSndngMail(sndngMailVO);
//				if(result) 
					System.out.println("전송 완료=="+e_adres);
				j++;
			}
		}
		System.out.println("total mail original target==" + mberManageList.size());
		System.out.println("total mail target=="+j);
		
		
		
		// 테스트할 때의 코드
		List<String> mailAddresses =  new ArrayList<>();
		mailAddresses.add("hkimkm1@hubizict.com");
//		mailAddresses.add("hkkyoungmin@gmail.com");
		for(String address : mailAddresses) {
			sndngMailVO.setRecptnPerson(address);
			// 메일 등록 및 발송
			boolean result = sndngMailRegistService.insertSndngMail(sndngMailVO);			
		}
		////////////*******************************************
		boolean result = true;
		// 여러 명에게 메일을 보낼 경우 결과를 일일이 다 확인할 수 없다. => boolean arr를 만들어서 반영할까?
		if (result) {
			return "redirect:/performance/sendInfo.do";
			
		} else {
			return "egovframework/com/cmm/error/egovError";
		}
	}
	
	
	
	@RequestMapping(value = "/performance/test.do")
	public String test(ModelMap model, HttpServletRequest request)
			throws Exception{
		FileVO fileVO = new FileVO();
		fileVO.setAtchFileId("FILE_000000000016312");
		fileVO.setFileSn("0");
		FileVO fvo = fileMngService.selectFileInf(fileVO);

		File uFile = new File(fvo.getFileStreCours(), fvo.getStreFileNm());

		System.out.println("file path: " + uFile.getPath());
		System.out.println("file stored cours: " + fvo.getFileStreCours());
		
		HtmlEmail email = new HtmlEmail();
		File img = new File("C:/eGovFrameDev-3.8.0-64bit/workspace/upload/NSE_202106300503482360");
		String cid = email.embed(uFile);
		System.out.println("cid===" + cid);
		
		return "redirect:/performance/sendInfo.do";
		
	}
	
	
	private String strDecode(String originalString, String originalCharSet, String toCharSet) throws UnsupportedEncodingException {
		String decoded = new String(originalString.getBytes(originalCharSet), toCharSet);
		return decoded;
	}
	
	private SndngMailVO attachFile(SndngMailVO sndngMailVO, String attachedFileId, String originalFileNm) throws Exception {
		//첨부 파일 처리
				/*if (sndngMailVO != null) { // 첨부파일이 있든 없든 항상 실행됨.
					FileVO fileVo = new FileVO();
					sndngMailVO.setDsptchPerson(dispatchId);
					//첨부 파일이 없을 때
					if(attachedFileId == null || attachedFileId.equals("")) {
						// 게시물의 원래 파일이 없을 때
						if (sndngMailVO.getAtchFileId() == null || sndngMailVO.getAtchFileId().equals("")) {
							sndngMailVO.setAtchFileId(attachedFileId);
							// 한긒 파일명 깨짐 방지
							sndngMailVO.setOrignlFileNm(strDecode(originalFileNm, "UTF-8", "8859_1"));
//							sndngMailVO.setOrignlFileNm(orignlFileList);
						}
						// 게시물의 원래 파일이 있을 때
						else {
							fileVo.setAtchFileId(sndngMailVO.getAtchFileId());
							fileVo.setFileSn("0");
							fileVo = fileMngService.selectFileInf(fileVo);
							
							// 한긒 파일명 깨짐 방지
							sndngMailVO.setOrignlFileNm(strDecode(fileVo.getOrignlFileNm(), "UTF-8", "8859_1"));
//							sndngMailVO.setOrignlFileNm(fileVo.getOrignlFileNm());
						}
					}
					//첨부 파일이 있을 때
					else {
						sndngMailVO.setAtchFileId(attachedFileId);
						// 한긒 파일명 깨짐 방지
						sndngMailVO.setOrignlFileNm(strDecode(originalFileNm, "UTF-8", "8859_1"));
//						sndngMailVO.setOrignlFileNm(orignlFileList);
					}
				}*/
				
				// 자동으로 파일 첨부해주는 기능을 없애려면 하려면 이것을 주석 해제하고 위의 코드를 없애면 된다.
				if (sndngMailVO != null) {
					sndngMailVO.setAtchFileId(attachedFileId);
//					sndngMailVO.setDsptchPerson(dispatchId);
					sndngMailVO.setOrignlFileNm(strDecode(originalFileNm, "UTF-8", "8859_1"));
				}
				
				return sndngMailVO;
		
	}
	
	
	
	
}
