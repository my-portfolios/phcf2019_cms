package egovframework.com.sec.phcf.web;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.sec.drm.service.EgovDeptAuthorService;
import egovframework.com.sec.phcf.service.AuthManageVO;
import egovframework.com.sec.phcf.service.EgovPhcfAuthorService;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.phcf.util.JsonUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;

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
    
    @Resource(name = "egovAuthorManageService")
    private EgovAuthorManageService egovAuthorManageService;
    
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
	public String support(@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {
    	
    	ComDefaultCodeVO voComCode = new ComDefaultCodeVO();
    	// 문화재단 사이트 코드를 가져온다
		voComCode.setCodeId("PHC009");
		model.addAttribute("pageList", JsonUtil.getJsonArrayFromVOList( cmmUseService.selectCmmCodeDetail(voComCode) ));
    	
		return "egovframework/com/sec/phcf/phcfAuthorList";
	}
    
    @RequestMapping(value="/sec/phcf/getEgovPhcfAuthorList.do")
	public String getEgovPhcfAuthorList(@ModelAttribute("authManageVO") AuthManageVO authManageVO, ModelMap model) throws Exception {

		authManageVO.setPhcfAuthorList(egovPhcfAuthorService.selectAllEgovPhcfAuthList(authManageVO));
        model.addAttribute("value", authManageVO.getPhcfAuthorList());
        
        int totCnt = egovPhcfAuthorService.selectEgovPhcfAuthListCnt(authManageVO);
        model.addAttribute("totCnt", totCnt);
        
        return "jsonView";
	}
    
    @RequestMapping(value="/sec/phcf/EgovPhcfAuthorBanGoToUrl.do")
	public String EgovPhcfAuthorBanGoToUrl(ModelMap model) throws Exception {

    	
        
        return "error/noTmpltErrorPage";
	}
}
