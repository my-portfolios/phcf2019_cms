package egovframework.com.sec.phcf.service;

import java.util.List;

public class AuthManageVO extends AuthManage {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 문화재단권한목록
	 */
	List <AuthManageVO> PhcfAuthorList;
	public List<AuthManageVO> getPhcfAuthorList() {
		return PhcfAuthorList;
	}
	public void setPhcfAuthorList(List<AuthManageVO> list) {
		PhcfAuthorList = list;
	}
}
