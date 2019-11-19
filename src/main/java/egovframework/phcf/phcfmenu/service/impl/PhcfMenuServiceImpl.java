package egovframework.phcf.phcfmenu.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.phcfmenu.service.PhcfMenuService;


@Service("PhcfMenuService")
public class PhcfMenuServiceImpl implements PhcfMenuService {

	@Resource(name="phcfMenuDAO")
	private PhcfMenuDAO dao;
	
	public List<HashMap<String, Object>> selectMenuList() throws Exception {
		return dao.selectMenuList();
	}

}
