package egovframework.phcf.support.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.utl.cas.service.EgovSessionCookieUtil;
import egovframework.phcf.common.service.ParamMap;
import egovframework.phcf.nicepay.service.NicepayService;
import egovframework.phcf.support.service.SupportService;
import egovframework.phcf.util.JsonUtil;
import egovframework.phcf.util.PagingUtil;
import egovframework.phcf.util.PropertiesUtil;

@SuppressWarnings("deprecation")
@Controller
public class SupportController {
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(SupportController.class);
   
   @Resource(name="EgovCmmUseService")
   private EgovCmmUseService cmmUseService;
   
   @Resource(name = "SupportService")
   SupportService supportService;
   
   @Resource(name = "NicepayService")
   NicepayService nicepayService;
   
   @IncludedInfo(name="후원신청 관리", order=10000, gid=100)
   @RequestMapping(value="/cms/support/listView.do")
   public String support(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {

      // 회원종류를 가져온다.
      ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
       voComCode.setCodeId("PHC004");
       model.addAttribute("userTp", JsonUtil.getJsonArrayFromVOList( cmmUseService.selectCmmCodeDetail(voComCode)) );
      
      // 개인회원 등급 리스트를 가져온다.
      model.addAttribute("gradeList", JsonUtil.getJsonArrayFromList( supportService.selectGradeCodeList("PHC003")) );
      
      // 후원방식 코드 리스트를 가져온다.
      voComCode.setCodeId("PHC005");
      model.addAttribute("spMhTp", JsonUtil.getJsonArrayFromVOList( cmmUseService.selectCmmCodeDetail(voComCode) ));
      
      // 결제방식 코드 리스트를 가져온다.
      voComCode.setCodeId("PHC006");
      model.addAttribute("scPriceTp", JsonUtil.getJsonArrayFromVOList( cmmUseService.selectCmmCodeDetail(voComCode) ));
      
      return "egovframework/phcf/support/list";
   }
   
   @RequestMapping(value="/cms/support/getCmsSupportList.do")
   public String getCmsSupportList(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {
      
      System.out.println("== getCmsSupportList paramMap : " + paramMap);
      
      // 리스트 및 total count 값 적용..
      List<HashMap<String, Object>> cmsSupportList = supportService.selectCmsSupportList(paramMap);
      model.addAttribute("value", cmsSupportList);
      // 전체 건수
      int totalCnt = supportService.selectCmsSupportCnt(paramMap);
      model.addAttribute("totCnt", totalCnt);
      
      return "jsonView";
   }
   
   @RequestMapping(value="/cms/support/updateCmsSupportItem.do")
   public String updateCmsSupportItem(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {
      
      System.out.println("== updateCmsSupportItem paramMap : " + paramMap);
      
      supportService.updateCmsSupportItem(paramMap);
      
      model.addAttribute("msg", "success");
      
      return "jsonView";
   }
   
   @RequestMapping(value="/cms/support/deleteCmsSupportItem.do")
   public String deleteCmsSupportItem(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {
      
      System.out.println("== deleteCmsSupportItem paramMap : " + paramMap);
      
      supportService.deleteCmsSupportItem(paramMap);
      
      model.addAttribute("msg", "success");
      
      return "jsonView";
   }
   
   @RequestMapping(value="/cms/support/add.do")
   public String addCmsSupportItem(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {
      
	   return "egovframework/phcf/support/add";
   }
   
   	
   /**
	 * 후원회원 정보 삭제 기능 후원 이력 및 기업일 경우 로고 정보, 정기 후원 발생 이력 정보 까지 모두 삭제 처리함.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/delete_detailInfo.do", method=RequestMethod.POST)
	public ModelAndView delete_detailInfo(HttpServletRequest request) throws Exception {
		ParamMap paramMap = parseRequestCleanXss(request);
		System.out.println("== delete_detailInfo paramMap : " + paramMap);
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		try {
			supportService.deleteDetailInfo(paramMap);
			mav.addObject("state", true);
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject("state", false);
		}
		
		return mav;
	}
	
	/**
	 * 후원관리 상세 페이지 수정 기능
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/update_detailInfo.do")
	public ModelAndView updateDetailInfo(HttpServletRequest request) throws Exception {
		
		ParamMap paramMap = parseRequestCleanXss(request);
		System.out.println("== updateDetailInfo paramMap : " + paramMap);

		supportService.updateDetailinfo(paramMap);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", true);
		
		return mav;
	}
	
	/**
	 * 후원관리 상세화면 보기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/supportDetailView.do", method=RequestMethod.POST)
	public ModelAndView supportDetailView(HttpServletRequest request) throws Exception {
		
		ParamMap paramMap = parseRequestCleanXss(request);

		ModelAndView mav = new ModelAndView("admin/support/list_edit");

		mav.addObject("supportDetail", supportService.getSupportDetailInfo( paramMap.getString("sp_id") ));
		
		return mav;
	}
	
	@RequestMapping(value="/cms/support/status_delete")
	public ModelAndView statusDelete(@RequestParam(value="cms_id") String cms_id) throws Exception {
		
		supportService.deleteStatus(cms_id);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", true);
		return mav;
	}
	
	@RequestMapping(value="/cms/support/status_modify.do")
	public ModelAndView statusModify(@RequestParam(value="cms_id") String cms_id
														, @RequestParam(value="use_yn") String use_yn) throws Exception {
		
		supportService.updateStatusModify(cms_id, use_yn);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", true);
		return mav;
	}
	
	
	/**
	 * CMS전송현황 상세 보기 화면
	 * 1. View, update 기능 제공
	 * 2, Insert는 Quartz 스케줄러에 의해서 생성 된다..
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/status_edit.do", method=RequestMethod.POST)
	public ModelAndView statusEdit(HttpServletRequest request) throws Exception {
		
		ParamMap paramMap = parseRequestCleanXss(request);
		
		ModelAndView mav = new ModelAndView("admin/support/status_edit");
		mav.addObject("statusDetailInfo", supportService.getStatusDetailInfo(paramMap));
		
		return mav;
	}
	
	/**
	 * 관리자 메시지 수정 Ajax
	 * @param sp_id	: 컬럼 순번
	 * @param admin_msg : 변경 메시지
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/updateAdminMsg.do")
	public ModelAndView updateAdminMsg(@RequestParam(value="sp_id") String sp_id,
														@RequestParam(value="admin_msg") String admin_msg) throws Exception {
		
		supportService.updateAdminMsg(sp_id, admin_msg);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", true);
		
		return mav;
	}
	
	@RequestMapping(value="/cms/support/compChkProc.do")
	public ModelAndView cmsSupportCompChkProc(
			@RequestParam(value="userTp") String userTp,
			@RequestParam(value="compNm") String compNm,
			@RequestParam(value="compNumber") String compNumber) throws Exception {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		// 기업회원 명과 사업자번호를 이용해 tb_support_log 테이블을 조회해 온다.
		// 조회해온 결과 내 담당자 이름 전화번호 이메일 정보를 돌려준다.
		List<ParamMap> compLogList = supportService.getCompLogList(userTp, compNm, compNumber);
		
		ParamMap compLogoInfo = supportService.getCompLogoInfo(userTp, compNm, compNumber);
		
		if(compLogList.size() != 1) {
			mav.addObject("state", false);
		} else {
			mav.addObject("state", true);
			mav.addObject("compInfo", compLogList.get(0));	// 데이터는 무조건 1개만 날라 올 것이다...
			mav.addObject("scgInfo", compLogoInfo);	// 마지막 저장된 Logo 정보를 돌려준다.
		}
		
		return mav;
	}
	
	public int getFileIndex(List<ParamMap> fileList, String inputName) {
		// 저장된 이미지가 날라 올때 Logo 파일 및 CMS이체 동의서 둘다 날라 오는 경우가 있다.
		// 이중에서 이미지 로고 파일을 가져 와야 된다.
		int index = -1;
		for(int i = 0; i < fileList.size(); i++) {
			
			ParamMap file = fileList.get(i);
			if( !String.valueOf( file.get("input_name") ).equalsIgnoreCase(inputName) ) { continue; }
			
			index = i;
			break;
		}
		
		return index;
	}
	
	/**
	 * 썸내일 등록 메소드...
	 * @param fileList
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void upsertSupportCgInfo(List<ParamMap> fileList, ParamMap paramMap) throws Exception {
		
		int logoIndex = getFileIndex(fileList, "attach_logo");
		
		if(null != fileList && fileList.size() > 0 && logoIndex != -1) {
			// 파일 등록은 하나만 처리 한다..
			ParamMap fileTemp = (ParamMap)fileList.get(0);
			
			// 이미지 파일 등록
			paramMap.put("img_file_name", fileTemp.getString("org_file_name"));
			paramMap.put("img_file_path", fileTemp.getString("upload_file_name"));
			
			// 썸네일 변경 및 파일 등록..
			paramMap.put("img_thum_v_name", fileTemp.getString("thum_view_file_name"));
			paramMap.put("img_thum_l_name", fileTemp.getString("thum_list_file_name"));
			
			// 기업 및 단체 Logo 등록
			supportService.upsertSupportCgInfo(paramMap);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cms/support/compCmsInsertProc.do")
	public String compCmsInsertProc(MultipartHttpServletRequest request) throws Exception {
		
		// CMS 이체 동의서 이미지 파일이 필요 하다..
		// 이미지 파일 저장 디렉토리
		File baseUploadDir = new File(PropertiesUtil.getValue("upload_root"));
		if(!baseUploadDir.exists()) {
			throw new NullPointerException("이미지 저장 Root 경로가 존재 하지 않습니다.");
		}
		
		// 복사 대상 디렉토리
		File theBillBaseDir = new File(PropertiesUtil.getValue("theBillHome"));
		if(!theBillBaseDir.exists()) {
			throw new NullPointerException("TheBill 모듈 저장 Root 경로가 존재 하지 않습니다.");
		}
		
		ParamMap paramMap = parseRequestCleanXss(request);
		
		// tb_support_log 테이블에 기본 정보 저장.
		String order_num = nicepayService.getOrderNumber(); 	// 주문번호를 생성한다.
		paramMap.put("order_num", order_num);
		
		// 현재 세션에서 user_id 항목을 가져와 추가해 준다..
		LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");
		paramMap.put("ins_id", vo.getId());
		
		// 메인사이트 및 Logo 이미지 등록...(이미지파일 & 썸네일 등록..)
		// 이미지 저장 할려고 굳이 트랜젝션 처리 까지 할 필요는 없다...
		upsertSupportCgInfo((List<ParamMap>)paramMap.get("fileList"), paramMap);
		
		// MultipartHttpServletRequest를 통해 이미 이미지(CMS이체 동의서)는 서버에 저장 되어 있을것이다.
		// 해당 이미지 파일을 가져와 복사해서 
		// 서버에 저장된 물리 경로를 이용해 해당 파일을 가져와야 된다.
		List<ParamMap> fileList = (List<ParamMap>)paramMap.get("fileList");
		int fileAgreeindex = getFileIndex( fileList, "attach_argree" );
		ParamMap fileMap = fileList.get(fileAgreeindex);	// 등록된 파일은 무조건 1개이어야 한다.
		
		// 등록할 파일을 가져온다.
		// 원본이미지는 용량 관계로 등록 할 수가 없다..
		// 썸네일용으로 등록된 이미지를 등록 해야 된다..
		String uploadFileName = String.valueOf( fileMap.get("thum_view_file_name") );
		
		File imgDateDir = new File(baseUploadDir, uploadFileName.substring(0, 8) );
		if(!imgDateDir.exists()) {
			throw new NullPointerException("이미지 저장 Date 경로가 존재 하지 않습니다.");
		}
		
		File agreeImgFile = new File(imgDateDir, uploadFileName);
		if(!agreeImgFile.exists()) {
			throw new NullPointerException("전송할 CMS이체 동의서 파일이 존재 하지 않습니다.");
		}
		
		// 변경 대상 파일명 이용기관코드.회원ID.1(이미지).jpg
		String dealID = String.valueOf( PropertiesUtil.getValue("dealID") );
		
		// demon형 모듈에 send 할 수 있도록 파일명을 변환 하고
		// demon형 모듈 send 디렉토리에 저장 한다.
		FileCopyUtils.copy(agreeImgFile, new File(theBillBaseDir + File.separator + "data" + File.separator + "agree" + File.separator + "send"
																	, dealID + "." + String.valueOf( paramMap.get("acc_user_id") + ".1.jpg" )));
		
		// tb_support_log 테이블 저장 해야 된다.
		// tb_support_cms 테이블에 저장 해야 된다..
		supportService.compCmsInsertProc(paramMap);
		
		return "redirect:/cms/support/list.do";
	}
	
	/**
	 * 후언관리 기업&단체에 대한 정기후원 카드 결제에 대한 정보를 저장 관리 한다.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/cms/support/compCardInsertProc.do")
	public String compCardInsertProc(MultipartHttpServletRequest request) throws Exception {
		
		ParamMap paramMap = parseRequestCleanXss(request);
		
		// tb_support_log 테이블에 기본 정보 저장.
		String order_num = nicepayService.getOrderNumber(); 	// 주문번호를 생성한다.
		paramMap.put("order_num", order_num);
		
		// 현재 세션에서 user_id 항목을 가져와 추가해 준다..
		LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");
		paramMap.put("ins_id", vo.getId());
		
		// 메인사이트 및 Logo 이미지 등록...(이미지파일 & 썸네일 등록..)
		// 이미지 저장 할려고 굳이 트랜젝션 처리 까지 할 필요는 없다...
		upsertSupportCgInfo((List)paramMap.get("fileList"), paramMap);
		
		// tb_support_log 테이블 저장 해야 된다.
		// tb_support_cms 테이블에 저장 해야 된다..
		supportService.compCardInsertProc(paramMap);
		
		return "redirect:/cms/support/list.do";
	}
	
	/**
	 * 기업 및 단체 정보 insert 처리...
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/cms/support/compInsertProc.do")
	public String cmsSupportCompProc(MultipartHttpServletRequest request) throws Exception {
		
		ParamMap paramMap = parseRequestCleanXss(request);
		
		// 현재 세션에서 user_id 항목을 가져와 추가해 준다..
		LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");
		paramMap.put("ins_id", vo.getId());
		
		// 기업 및 단체의 결제방식이 무통장입금일 경우 DB 상에 정보만 등록 하면 된다..
		// 기본정보 및 입금액 입력..
		supportService.insertCompSupportLog(paramMap);
		
		// 메인사이트 및 Logo 이미지 등록...(이미지파일 & 썸네일 등록..)
		upsertSupportCgInfo((List)paramMap.get("fileList"), paramMap);
		
		
		return "redirect:/cms/support/list.do";
	}
	
	/**
	 * 이미 등록된 정기후원 내역(tb_support_cms 테이블 조회)이 있는지 결과를 돌려준다.
	 * @param accUserId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/checkAgreeUser.do")
	public ModelAndView checkAgreeUser(@RequestParam(value="accUserId") String accUserId) throws Exception {
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", nicepayService.checkAgreeUser( accUserId ));
		
		return mav;
	}
	
	/**
	 * 개인 후원 CMS 등록 Controller
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cms/support/userInsertProc.do")
	public String cmsSupportUserProc(MultipartHttpServletRequest request) throws Exception {
		
		// 이미지 파일 저장 디렉토리
		File baseUploadDir = new File(PropertiesUtil.getValue("upload_root"));
		if(!baseUploadDir.exists()) {
			throw new NullPointerException("이미지 저장 Root 경로가 존재 하지 않습니다.");
		}
		
		// 복사 대상 디렉토리
		File theBillBaseDir = new File(PropertiesUtil.getValue("theBillHome"));
		if(!theBillBaseDir.exists()) {
			throw new NullPointerException("TheBill 모듈 저장 Root 경로가 존재 하지 않습니다.");
		}
		
		ParamMap paramMap = parseRequestCleanXss(request);
		
		// 현재 세션에서 user_id 항목을 가져와 추가해 준다..
		LoginVO vo = (LoginVO) EgovSessionCookieUtil.getSessionAttribute(request, "loginVO");
		paramMap.put("ins_id", vo.getId());
		
		// tb_support_log 테이블에 기본 정보 저장.
		String order_num = nicepayService.getOrderNumber(); 	// 주문번호를 생성한다.
		paramMap.put("order_num", order_num);
		
		supportService.insertUserSupportLog(paramMap);
		
		// CMS동의 인증서는 화면에서 web api를 통해 미리 전송한다.(이거 안된다고~~~~!!!!!!!)
		// CMS동의서 전달은 Demon형 모듈을 이용해 처리 해야 된다...
		// MultipartHttpServletRequest를 통해 이미 이미지는 서버에 저장 되어 있을것이다.
		// 해당 이미지 파일을 가져와 복사해서 
		// 서버에 저장된 물리 경로를 이용해 해당 파일을 가져와야 된다.
		List<ParamMap> fileList = (List<ParamMap>)paramMap.get("fileList");
		int fileAgreeindex = getFileIndex( fileList, "attach_argree" );
		ParamMap fileMap = fileList.get(fileAgreeindex);	// 등록된 파일은 무조건 1개이어야 한다.
		
		// 등록할 파일을 가져온다.
		// 원본이미지는 용량 관계로 등록 할 수가 없다..
		// 썸네일용으로 등록된 이미지를 등록 해야 된다..
		String uploadFileName = String.valueOf( fileMap.get("thum_view_file_name") );
		
		File imgDateDir = new File(baseUploadDir, uploadFileName.substring(0, 8) );
		if(!imgDateDir.exists()) {
			throw new NullPointerException("이미지 저장 Date 경로가 존재 하지 않습니다.");
		}
		
		File agreeImgFile = new File(imgDateDir, uploadFileName);
		if(!agreeImgFile.exists()) {
			throw new NullPointerException("전송할 CMS이체 동의서 파일이 존재 하지 않습니다.");
		}
		
		// 변경 대상 파일명 이용기관코드.회원ID.1(이미지).jpg
		String dealID = String.valueOf( PropertiesUtil.getValue("dealID") );
		
		// demon형 모듈에 send 할 수 있도록 파일명을 변환 하고
		// demon형 모듈 send 디렉토리에 저장 한다.
		FileCopyUtils.copy(agreeImgFile, new File(theBillBaseDir + File.separator + "data" + File.separator + "agree" + File.separator + "send"
																	, dealID + "." + String.valueOf( paramMap.get("acc_user_id") + ".1.jpg" )));
		
		// tb_support_cms 테이블에 등록 현황을 저장한다.
		nicepayService.insertUserCmsInfo(paramMap);
		
		// demon형 모듈은 5분 단위로 전송 처리 되며
		// cronQuartz를 통해 전송 완료 여부를 체크 하여
		// tb_support_cms 테이블에 agree_send_yn 컬럼을 변경 처리 한다.
		// 회원 등록 및 출금신청 등록인 스케줄러에 의해 자동으로 등록 된다.
		// CmsUserCreateCronQuartz & CmsPayCreateCronQuartz
		
		return "redirect:/cms/support/list.do";
	}
	
	/**
	 * 등록회원 조회.
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/userChkProc.do")
	public ModelAndView cmsUserChkProc(@RequestParam(value="userId")String userId) throws Exception {
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		// 사용자 체크
		// 카운트 값을 하지 말고.. 그냥 데이터를 조회해 오자...
		// 어짜피 등록된 회원이라면.. 이름 전화번호 이메일 정도는 있을 것이다...
//		int userCnt = supportService.cmsUserChkCnt(userId);
//		if(userCnt != 1) {
//			mav.addObject("userId", userId);
//			mav.addObject("state", false);
//		}else {
//			mav.addObject("state", true);
//		}	
		
		List<ParamMap> userInfo = supportService.cmsGetUserInfo(userId);
		
		if(userInfo.size() != 1) {
			mav.addObject("userId", userId);
			mav.addObject("state", false);
		} else {
			mav.addObject("state", true);
			mav.addObject("userInfo", userInfo.get(0));
		}
		
		return mav;
	}
	
	@RequestMapping(value="/cms/support/updateGrade.do")
	public ModelAndView updateGrade(@RequestParam(value="user_id") String user_id
														, @RequestParam(value="grade") String grade) throws Exception {
		
		// 회원 등급 정보를 변경 처리한다.
		supportService.updateGrade(user_id, grade);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", true);
		
		return mav;
	}
	
	/**
	 * 회원 등록 취소 처리 로직
	 * cms_id를 입력 받아 del_target_yn을 'Y'로 업데이트 처리한다..
	 * 스케줄링 처리시 해당 값을 이용해 회원등록 취소를 진행 한다.
	 * @param cms_id : 변경 대상 seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/status_del.do")
	public ModelAndView statusDelAjax(@RequestParam(value="cms_id") String cms_id
													,@RequestParam(value="del_target_yn") String del_target_yn) throws Exception {
		
		supportService.updateTargetYn(cms_id, del_target_yn);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("state", true);
		
		return mav;
	}
	
	/**
	 * CMS전송현황
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cms/support/status.do")
	public ModelAndView status(HttpServletRequest request) throws Exception {
		ParamMap paramMap = parseRequestCleanXss(request);
		
		// PageSize 처리를 위해.. 넘겨 받은 값을 저장 한다..
		// 아래 Limit 용 쿼리를 처리 하기 위해 page_no 값이 변경 되기 때문이다...
		String pageNoStr = String.valueOf( paramMap.get("page_no") );
		if(pageNoStr == null || pageNoStr.equalsIgnoreCase("null") || pageNoStr.length() < 1) {
			// pageNo 값이 없을 경우 기본 1값을 셋팅한다.
			pageNoStr = "1";
		}
		
		ModelAndView mav = new ModelAndView("egovframework/phcf/support/status");
		
		setPageSize10(paramMap);
		
		System.out.print("paramMapparamMapparamMapparamMapparamMapparamMap"+paramMap);
		
		//ModelAndView에 Object를 추가하여 CmsSupport.xml의 'getCmsStatusList'와 맵핑
		//'getCmsStatusList'는 Limit이 걸린 쿼리이므로 전체 건수가 나오는 Object를 따로 추가해야 하나?
		mav.addObject("cmsStatus", supportService.getCmsStatusList(paramMap));

		// Paging 처리를 위해 입력 받은 값을 다시 셋팅해주어야 한다.
		paramMap.put("page_no", (pageNoStr == null || pageNoStr.equalsIgnoreCase("null"))? "1" : pageNoStr);
		mav.addObject("paging", PagingUtil.printPageNavi(paramMap, "A"));
		
		mav.addObject("paramMap", paramMap);
		
		return mav;
	}
	
	/**
	 * 이것도 안되겠네..........
	 * Header에 Api-key 를 담는 순간 request method가 options로 바뀐다.
	 * TheBill 에서 Access-control-method 접속 허용이 안 풀려 있는듯 하여...
	 * cross-domain에 걸려 버린다.... 줴길....... 
	 * 
	 * 
	 * 출금이체 동의서 이미지 파일 크로스도메인 upload 처리..
	 * 이미지 전송을 하고자 하는 경우 TheBill 사이트에서 제공 하는 weblogic 처리를 진행 하여야 한다.
	 * 타 사이트로 이미지를 전송 하고 결과를 json으로 받아야 되는데
	 * 이는 크로스도메인에 걸린다..
	 * 이에 따라 아래와 같이 서버단에서 이미지를 전송 하고 결과를 받아 다시 json으로 
	 * 리턴 하는 방식을 사용 하였다.
	 * @param request
	 * @param response
	 * @param agreetype
	 * @param fileext
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cms/support/insertAgreeImg.do")
	public ModelAndView InsertinsertAgreeImg(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 이미지 파일 저장 디렉토리
		File baseUploadDir = new File(PropertiesUtil.getValue("upload_root"));
		if(!baseUploadDir.exists()) {
			throw new NullPointerException("이미지 저장 Root 경로가 존재 하지 않습니다.");
		}
		
		// multipart로 넘어 온 파일 정보가 parseRequestCleanXss 메소드를 통해 서버에 저장 되게 된다.
		ParamMap paramMap = parseRequestCleanXss(request);
		
		// 서버에 저장된 물리 경로를 이용해 해당 파일을 가져와야 된다.
		@SuppressWarnings("unchecked")
		List<ParamMap> fileList = (List<ParamMap>)paramMap.get("fileList");
		int fileAgreeindex = getFileIndex( fileList, "attach_argree" );
		ParamMap fileMap = fileList.get(fileAgreeindex);	// 등록된 파일은 무조건 1개이어야 한다.
		
		// 등록할 파일을 가져온다.
		// 원본이미지는 용량 관계로 등록 할 수가 없다..
		// 썸네일용으로 등록된 이미지를 등록 해야 된다..
		String uploadFileName = String.valueOf( fileMap.get("thum_view_file_name") );
		
		File imgDateDir = new File(baseUploadDir, uploadFileName.substring(0, 8) );
		if(!imgDateDir.exists()) {
			throw new NullPointerException("이미지 저장 Date 경로가 존재 하지 않습니다.");
		}
		
		File agreeImgFile = new File(imgDateDir, uploadFileName);
		if(!agreeImgFile.exists()) {
			throw new NullPointerException("전송할 CMS이체 동의서 파일이 존재 하지 않습니다.");
		}
		
		String dealID = String.valueOf( PropertiesUtil.getValue("dealID") );
		String apiKey = String.valueOf( PropertiesUtil.getValue("apiKey") );
		
		response.setContentType("application/json; charset=utf-8");
		
		@SuppressWarnings({ "resource" })
		HttpClient httpclient = new DefaultHttpClient();
		
		String url = "https://rest.thebill.co.kr:4435/thebill/test/retailers/" + dealID + "/members/" + String.valueOf(paramMap.get("accUserId")) + "/agree";
//		String url = "https://rest.thebill.co.kr:4435/thebill/test/retailers/30000000/members/abcd1234/agree";
		System.out.println("== access url : " + url);
		
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Api-key", apiKey);
		httppost.setHeader("Content-Type", "multipart/form-data");
		httppost.setHeader("Accept", "application/json");
		
		HttpEntity entityData = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addBinaryBody("filename", agreeImgFile, ContentType.DEFAULT_BINARY, agreeImgFile.getName())
				.addTextBody("agreetype", String.valueOf(paramMap.get("agreetype")) )
				.addTextBody("fileext", String.valueOf( paramMap.get("fileext") ))
				.build();
		
		httppost.setEntity(entityData);
		
		HttpResponse httpResponse = httpclient.execute(httppost);
		HttpEntity entity = httpResponse.getEntity();
		
		System.out.println("== httpResponse info : " + EntityUtils.toString(entity, "UTF-8"));
		
		
		return null;
		
//		ModelAndView mav = new ModelAndView("jsonView");
//		mav.addObject("state", true);
//		
//		return mav;
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
	
	@SuppressWarnings("unchecked")
	protected void setPageSize10(ParamMap paramMap) {
		paramMap.put(paramMap.getString("page_size_name","page_size"), paramMap.getString(paramMap.getString("page_size_name","page_size"), "10")); 	// 한페이지당 보여줄 목록 수
		paramMap.put(paramMap.getString("page_no_name","page_no"), paramMap.getString(paramMap.getString("page_no_name","page_no"), "1")); 		// 현재 페이지 번호
		paramMap.put("block_size", paramMap.getString("block_size", "10")); // 페이징 블럭 수
	}
	
}