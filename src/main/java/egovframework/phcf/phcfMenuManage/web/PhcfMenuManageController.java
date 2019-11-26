package egovframework.phcf.phcfMenuManage.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import egovframework.phcf.phcfMenuManage.service.PhcfMenuManageService;

@Controller
public class PhcfMenuManageController {
	@Resource(name="PhcfMenuManageService")
	private PhcfMenuManageService service;
	
	@RequestMapping("/phcfMenuManage/selectMenuManageList.do")
	public String selectMenuManageList(ModelMap model, @RequestParam HashMap<String,String> paramMap) throws Exception{
		List<HashMap<String, String>> menuInfo = service.selectMenuInfoList(paramMap);
		System.out.println("testing\n"+menuInfo);
		model.addAttribute("menuInfo",menuInfo);
		
		return "egovframework/phcf/phcfMenuManage/view";
	}
	
	@RequestMapping("/phcfMenuManage/insertRegMenu.do")
	public String insertRegMenu(ModelMap model, @RequestParam HashMap<String,String> paramMap) throws Exception{
		
		if(paramMap.containsKey("PAGE")) {
			service.insertRegMenu(paramMap);
			return "redirect:/phcfMenuManage/insertRegMenu.do";
		}
		return "egovframework/phcf/phcfMenuManage/insert";
	}
	
	@RequestMapping("/phcfMenuManage/updateRegMenu.do")
	public String updateRegMenu(ModelMap model, @RequestParam HashMap<String,String> paramMap) throws Exception{
		HashMap<String, String> hashMap = service.selectMenuInfoDetail(paramMap);
		
		if(paramMap.containsKey("PAGE")) {
			service.updateRegMenu(paramMap);
			return "redirect:/phcfMenuManage/updateRegMenu.do";
		}
		
		model.addAttribute("hashMap", hashMap);
		return "egovframework/phcf/phcfMenuManage/insert";
	}
}
