package egovframework.com.sec.phcf.web;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovComIndexService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sec.drm.service.DeptAuthorVO;
import egovframework.com.sec.drm.service.EgovDeptAuthorService;
import egovframework.com.sec.gmt.service.EgovGroupManageService;
import egovframework.com.sec.gmt.service.GroupManageVO;
import egovframework.com.sec.phcf.service.AuthManage;
import egovframework.com.sec.phcf.service.AuthManageVO;
import egovframework.com.sec.phcf.service.EgovPhcfAuthorService;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.util.JsonUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;

/**
 * 부서권한에 관한 controller 클래스를 정의한다.
 * @author 휴비즈 대외사업부
 * @since 2019.12.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2019.12.17  윤병훈          최초 생성
 *
 * </pre>
 */

@Controller
@SessionAttributes(types=SessionVO.class)
public class EgovPhcfAuthorController {
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "egovDeptAuthorService")
    private EgovDeptAuthorService egovDeptAuthorService;
    
    @Resource(name = "egovGroupManageService")
    private EgovGroupManageService egovGroupManageService;
    
    @Resource(name = "egovAuthorManageService")
    private EgovAuthorManageService egovAuthorManageService;
    
    @Resource(name="EgovComIndexService")
	private EgovComIndexService egovComIndexService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Resource(name="egovPhcfAuthorService")
    private EgovPhcfAuthorService egovPhcfAuthorService;
    
    @Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
    
    private static Logger logger = Logger.getLogger(EgovPhcfAuthorController.class);
    /**
	 * 부서별 할당된 권한목록 조회
	 * 
	 * @param deptAuthorVO DeptAuthorVO
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @IncludedInfo(name="문화재단권한관리", listUrl="/sec/phcf/EgovPhcfAuthorList.do", order = 110,gid = 20)
    @RequestMapping(value="/sec/phcf/EgovPhcfAuthorList.do")
	public String EgovPhcfAuthorList(@ModelAttribute("authManageVO") AuthManageVO authManageVO, ModelMap model) throws Exception {

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(authManageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(authManageVO.getPageUnit());
		paginationInfo.setPageSize(authManageVO.getPageSize());
		
		authManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		authManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		authManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		authManageVO.setPhcfAuthorList(egovPhcfAuthorService.selectAllEgovPhcfAuthList(authManageVO));
        model.addAttribute("authorManageList", authManageVO.getPhcfAuthorList());
        
        int totCnt = egovPhcfAuthorService.selectEgovPhcfAuthListCnt(authManageVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        return "egovframework/com/sec/phcf/EgovPhcfAuthorList";
	}
    
    @RequestMapping(value="/sec/phcf/listView.do")
	public String phcfAuthorList(ModelMap model, @RequestParam(required=false, defaultValue="cms") String page) throws Exception {
    	
    	model.addAttribute("page", page);
    	
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	// 문화재단 사이트 코드를 가져온다
		voComCode.setCodeId("PHC009");
		model.addAttribute("pageList", JsonUtil.getJsonArrayFromVOList( cmmUseService.selectCmmCodeDetail(voComCode) ));
		
		//조직(부서)목록 가져오기
		DeptAuthorVO deptAuthorVO = new DeptAuthorVO();
		deptAuthorVO.setSearchCondition(null);
		deptAuthorVO.setDeptList(egovDeptAuthorService.selectDeptList(deptAuthorVO));
        model.addAttribute("deptList", JsonUtil.getJsonArrayFromVOList(deptAuthorVO.getDeptList()));
        
        //그룹목록 가져오기
        GroupManageVO groupManageVO = new GroupManageVO();
        groupManageVO.setSearchCondition(null);
        // 그룹전체 가져오기
        groupManageVO.setFirstIndex(-1);
        groupManageVO.setRecordCountPerPage(-1);
        groupManageVO.setGroupManageList(egovGroupManageService.selectGroupList(groupManageVO));
        model.addAttribute("groupList", JsonUtil.getJsonArrayFromVOList(groupManageVO.getGroupManageList()));
        
        //메뉴목록 가져오기
        List<HashMap<String, Object>> tempMap = egovComIndexService.selectMenuInfoList(page);
        List<HashMap<String, Object>> menuMap = new ArrayList<HashMap<String, Object>>();
        for(HashMap<String, Object> mmap : tempMap) {
        	if(!mmap.get("menuNm").toString().equals("No Menu Mathed") && !mmap.get("link").toString().equals("#")) {
        		menuMap.add(mmap);
        	}
        }
        for(HashMap<String, Object> mmap : menuMap) {
        	String temp = mmap.get("menuNm").toString();
        	mmap.remove("menuNm");
        	mmap.put("menuNm", mmap.get("pageNm")+" >> "+temp);
        }
        menuMap.sort(new Comparator<HashMap<String, Object>>() {
			@Override
			public int compare(HashMap<String, Object> arg0, HashMap<String, Object> arg1) {
				String priority0 = arg0.get("pageNm").toString();
				String priority1 = arg1.get("pageNm").toString();
				
				return priority0.compareTo(priority1);
			}
		});
		model.addAttribute("menuList", JsonUtil.getJsonArrayFromList(menuMap));
    	
		return "egovframework/com/sec/phcf/phcfAuthorList";
	}
    
    @RequestMapping(value="/sec/phcf/getEgovPhcfAuthorList.do")
	public String getEgovPhcfAuthorList(@ModelAttribute("authManageVO") AuthManageVO authManageVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	authManageVO.setInsId(user.getUniqId());
		authManageVO.setPhcfAuthorList(egovPhcfAuthorService.selectAllEgovPhcfAuthList(authManageVO));
        model.addAttribute("value", authManageVO.getPhcfAuthorList());
        
        int totCnt = egovPhcfAuthorService.selectEgovPhcfAuthListCnt(authManageVO);
        model.addAttribute("totCnt", totCnt);
        
        return "jsonView";
	}
    
    @RequestMapping(value="/sec/phcf/insertEgovPhcfAuthorList.do")
	public String insertEgovPhcfAuthorList(@ModelAttribute("authManageVO") AuthManageVO authManageVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	authManageVO.setInsId(user.getUniqId());
    	
    	egovPhcfAuthorService.insertEgovPhcfAuthList(authManageVO);
        
        model.addAttribute("msg", "success");
        
        return "jsonView";
	}
    
    @RequestMapping(value="/sec/phcf/updateEgovPhcfAuthorList.do")
	public String updateEgovPhcfAuthorList(@ModelAttribute("authManageVO") AuthManageVO authManageVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	authManageVO.setUptId(user.getUniqId());
    	
    	egovPhcfAuthorService.updateEgovPhcfAuthList(authManageVO);
        
        model.addAttribute("msg", "success");
        
        return "jsonView";
	}
    
    @RequestMapping(value="/sec/phcf/deleteEgovPhcfAuthorList.do")
	public String deleteEgovPhcfAuthorList(@ModelAttribute("authManageVO") AuthManageVO authManageVO, ModelMap model) throws Exception {

    	egovPhcfAuthorService.deleteEgovPhcfAuthList(authManageVO);
        
        model.addAttribute("msg", "success");
        
        return "jsonView";
	}
    
    @RequestMapping(value="/sec/phcf/EgovPhcfAuthorBanGoToUrl.do")
	public ModelAndView EgovPhcfAuthorBanGoToUrl(ModelMap model) throws Exception {
    	return CommonMethod.generalAlertThrowing("", "_content", "접근 권한이 없습니다!");
	}
}
