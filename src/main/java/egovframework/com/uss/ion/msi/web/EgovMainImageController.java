/**
 * 개요
 * - 메인화면이미지에 대한 controller 클래스를 정의한다.
 *
 * 상세내용
 * - 메인화면이미지에 대한 등록, 수정, 삭제, 조회, 반영확인 기능을 제공한다.
 * - 메인화면이미지의 조회기능은 목록조회, 상세조회로 구분된다.
 * @author 이문준
 * @version 1.0
 * @created 03-8-2009 오후 2:08:57
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2010.8.3	이문준          최초 생성
 *  2011.8.26	정진오			IncludedInfo annotation 추가
 *
 *  </pre>
 */

package egovframework.com.uss.ion.msi.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.msi.service.EgovMainImageService;
import egovframework.com.uss.ion.msi.service.MainImage;
import egovframework.com.uss.ion.msi.service.MainImageVO;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;


@Controller
public class EgovMainImageController {

	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    @Resource(name="EgovFileMngService")
    private EgovFileMngService fileMngService;

    @Resource(name="EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    /** Message ID Generation */
    @Resource(name="egovMainImageIdGnrService")
    private EgovIdGnrService egovMainImageIdGnrService;

    @Resource(name = "egovMainImageService")
    private EgovMainImageService egovMainImageService;

    @Autowired
	private DefaultBeanValidator beanValidator;

    /**
	 * 메인화면이미지 목록화면 이동
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/uss/ion/msi/selectMainImageListView.do")
    public String selectMainImageListView() throws Exception {

        return "egovframework/com/uss/ion/msi/EgovMainImageList";
    }

	/**
	 * 메인화면이미지정보를 관리하기 위해 등록된 메인화면이미지 목록을 조회한다.
	 * @param mainImageVO - 메인이미지 VO
	 * @return String - 리턴 Url
	 */
    @IncludedInfo(name="메인이미지관리", order = 770 ,gid = 50)
    @RequestMapping("/uss/ion/msi/selectMainImageList.do")
	public String selectMainImageList(@ModelAttribute("mainImageVO") MainImageVO mainImageVO,
                                       ModelMap model) throws Exception {

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(mainImageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(mainImageVO.getPageUnit());
		paginationInfo.setPageSize(mainImageVO.getPageSize());

		mainImageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		mainImageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		mainImageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		mainImageVO.setMainImageList(egovMainImageService.selectMainImageList(mainImageVO));

		model.addAttribute("mainImageList", mainImageVO.getMainImageList());

        int totCnt = egovMainImageService.selectLoginScrinImageListTotCnt(mainImageVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

        model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));

        return "egovframework/com/uss/ion/msi/EgovMainImageList";
	}

	/**
	 * 등록된 메인화면이미지의 상세정보를 조회한다.
	 * @param mainImageVO - 메인이미지 VO
	 * @return String - 리턴 Url
	 */
    @RequestMapping(value="/uss/ion/msi/getMainImage.do")
	public String selectMainImage(@RequestParam("imageId") String imageId,
                                  @ModelAttribute("mainImageVO") MainImageVO mainImageVO,
                                   ModelMap model) throws Exception {

    	mainImageVO.setImageId(imageId);
    	model.addAttribute("mainImage", egovMainImageService.selectMainImage(mainImageVO));
    	model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));

    	return "egovframework/com/uss/ion/msi/EgovMainImageUpdt";
	}

	/**
	 * 메인인화면이미지 등록 화면으로 이동한다.
	 * @return String - 리턴 Url
	 */
    @RequestMapping(value="/uss/ion/msi/addViewMainImage.do")
	public String insertViewMainImage(@ModelAttribute("mainImageVO") MainImageVO mainImageVO) throws Exception {
    	return "egovframework/com/uss/ion/msi/EgovMainImageRegist";
	}

	/**
	 * 메인화면이미지정보를 신규로 등록한다.
	 * @param mainImage - 메인이미지 model
	 * @return String - 리턴 Url
	 */
    @SuppressWarnings("unused")
	@RequestMapping(value="/uss/ion/msi/addMainImage.do")
	public String insertMainImage(final MultipartHttpServletRequest multiRequest,
			                      @ModelAttribute("mainImage") MainImage mainImage,
			                      @ModelAttribute("mainImageVO") MainImageVO mainImageVO,
			                      @RequestParam String popupImage,
			                      @RequestParam HashMap<String,String> paramMap,
			                       BindingResult bindingResult,
			                       ModelMap model) throws Exception {

    	beanValidator.validate(mainImage, bindingResult); //validation 수행

    	if (bindingResult.hasErrors()) {
    		model.addAttribute("mainImageVO", mainImageVO);
			return "egovframework/com/uss/ion/msi/EgovMainImageRegist";
		} else {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

	    	mainImage.setImageId(egovMainImageIdGnrService.getNextStringId());
	    	mainImage.setUserId(user.getId());
	    	
	    	if(popupImage != null) {
	    		CommonMethod.base64ImageDecoder(popupImage, "MAIN_IMG", mainImage.getImageId());
	    	}
	    	
	    	model.addAttribute("message", egovMessageSource.getMessage("success.common.insert"));
	    	model.addAttribute("mainImage", egovMainImageService.insertMainImage(mainImage, mainImageVO));

//			return "egovframework/com/uss/ion/msi/EgovMainImageUpdt";
			return "forward:/uss/ion/msi/selectMainImageList.do";

		}
	}

	/**
	 * 기 등록된 메인화면이미지정보를 수정한다.
	 * @param mainImage - 메인이미지 model
	 * @return String - 리턴 Url
	 */
    @SuppressWarnings("unused")
	@RequestMapping(value="/uss/ion/msi/updtMainImage.do")
	public String updateMainImage(final MultipartHttpServletRequest multiRequest,
                                  @ModelAttribute("mainImage") MainImage mainImage,
                                  @RequestParam String popupImage,
                                  @RequestParam HashMap<String,String> paramMap,
                                   BindingResult bindingResult,
                                   ModelMap model) throws Exception {

    	beanValidator.validate(mainImage, bindingResult); //validation 수행

    	if (bindingResult.hasErrors()) {
    		model.addAttribute("mainImageVO", mainImage);
			return "egovframework/com/uss/ion/msi/EgovMainImageUpdt";
		} else {
			if(popupImage != null) {
				CommonMethod.base64ImageDecoder(popupImage, "MAIN_IMG", mainImage.getImageId());
			}

	    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	    	mainImage.setUserId(user.getId());

	    	egovMainImageService.updateMainImage(mainImage);
//	    	return "forward:/uss/ion/msi/getMainImage.do";
	    	return "forward:/uss/ion/msi/selectMainImageList.do";
		}
    }

	/**
	 * 기 등록된 메인화면이미지정보를 삭제한다.
	 * @param mainImage - 메인이미지 model
	 * @return String - 리턴 Url
	 */
	@RequestMapping(value="/uss/ion/msi/removeMainImage.do")
	public String deleteMainImage(@RequestParam("imageId") String imageId,
                                  @ModelAttribute("mainImage") MainImage mainImage,
  			                       ModelMap model) throws Exception {

		mainImage.setImageId(imageId);
		egovMainImageService.deleteMainImage(mainImage);
    	model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		return "forward:/uss/ion/msi/selectMainImageList.do";
	}

	/**
	 * 기 등록된 메인화면이미지정보를 삭제한다.
	 * @param mainImage - 메인이미지 model
	 * @return String - 리턴 Url
	 */
	@RequestMapping(value="/uss/ion/msi/removeMainImageList.do")
	public String deleteMainImageList(@RequestParam("imageIds") String imageIds,
                                      @ModelAttribute("mainImage") MainImage mainImage,
                                       ModelMap model) throws Exception {

    	String [] strImageIds = imageIds.split(";");

    	for(int i=0; i<strImageIds.length;i++) {
    		mainImage.setImageId(strImageIds[i]);
    		egovMainImageService.deleteMainImage(mainImage);
    	}

    	model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
    	return "forward:/uss/ion/msi/selectMainImageList.do";
	}

	/**
	 * 기 등록된 메인화면이미지정보의 이미지파일을 삭제한다.
	 * @param mainImage - 메인이미지 model
	 * @return String - 리턴 Url
	 */
	public String deleteMainImageFile(MainImage mainImage) throws Exception {
		return "";
	}

	/**
	 * 메인화면이미지가 특정화면에 반영된 결과를 조회한다.
	 * @param mainImageVO - 메인이미지 VO
	 * @return String - 리턴 Url
	 */
	@IncludedInfo(name="메인이미지 반영결과보기", order = 771 ,gid = 50)
	@RequestMapping(value="/uss/ion/msi/getMainImageResult.do")
	public String selectMainImageResult(@ModelAttribute("mainImageVO") MainImageVO mainImageVO,
		                                 ModelMap model) throws Exception {

		List<MainImageVO> fileList = egovMainImageService.selectMainImageResult(mainImageVO);
		model.addAttribute("fileList", fileList);

		return "egovframework/com/uss/ion/msi/EgovMainImageView";
	}
}
