package egovframework.phcf.arttour.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArttourController {
	
	@RequestMapping(value="/artTor/list.do")
	public ModelAndView list(HttpServletRequest request, ModelMap model, @RequestParam HashMap<String, String> paramMap) {
		ModelAndView mav = new ModelAndView("egovframework/phcf/artTor/list"); 
		
		return mav;
	}
	
}
