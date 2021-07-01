package egovframework.phcf.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;

/**
 * 공통 Controller
 * @author	김량래
 * @since	2020-01-21
 **/

@Controller
public class CommonPageController {
	
	/** EgovFileMngUtil */
	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	/** EgovFileMngService */
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	
	@RequestMapping(value = "/common/imageCropper.do")
	public String egovPopupImageCropper(ModelMap model, @RequestParam HashMap<String, String> paramMap) throws Exception {
		
		return "egovframework/phcf/common/imageCropper";
	}
	
	@RequestMapping(value="/phcf/menuRefresh.do")
	public String menuRefresh() {
		return "egovframework/phcf/common/menuRefresh";
	}
	
	/*
	 * 네이버 스마트 에디터 업로드 이미지
	 */
	@RequestMapping(value = "/common/uploadImage.do")
	public void uploadImage(final MultipartHttpServletRequest request, HttpServletResponse response)  {
		
		try {
			String callback = request.getParameter("callback");
	        String callback_func = "?callback_func="+request.getParameter("callback_func");
	        String return_url = "";
	        
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("text/html;charset=UTF-8");
	        
			List<FileVO> _result = null;
			String _atchFileId = "";
			String _streFileNm = "";
			final Map<String, MultipartFile> files = request.getFileMap();
			if(!files.isEmpty()){
				_result = fileUtil.parseFileInf(files, "NSE_", 0, "", ""); 
				_atchFileId = fileMngService.insertFileInfs(_result);  //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
				FileVO fileVo = fileMngService.selectFileInf(_result.get(0));
				_streFileNm = fileVo.getStreFileNm();
			}
			
			//file의 이름을 알아오고 그 파일의 URL를 알아낸다. 
			System.out.println("streFileNm==="+_streFileNm);
			System.out.println("atchFileId==="+_atchFileId);
			
			return_url += "&bNewLine=true";
	        return_url += "&sFileName=" + _streFileNm;
	        return_url += "&sFileURL=/upload/" + _streFileNm;
	        
	        response.sendRedirect(callback + callback_func + return_url);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}
