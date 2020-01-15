package egovframework.phcf.util.web;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {
	
	/**
	 * 이미지 Cropper
	 * @param model
	 * @return cropper page
	 * @throws Exception
	 * @author 김량래
	 */
	@RequestMapping(value = "/editimage/imageCropper.do")
	public String egovPopupImageCropper(ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		
		return "egovframework/phcf/editimage/index";
	}
}
