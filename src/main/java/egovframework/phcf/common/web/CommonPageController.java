package egovframework.phcf.common.web;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 공통 Controller
 * @author	김량래
 * @since	2020-01-21
 **/

@Controller
public class CommonPageController {
	
	@RequestMapping(value = "/common/imageCropper.do")
	public String egovPopupImageCropper(ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		
		return "egovframework/phcf/common/imageCropper";
	}
	
	@RequestMapping(value="/phcf/menuRefresh.do")
	public String menuRefresh() {
		return "egovframework/phcf/common/menuRefresh";
	}
	
}
