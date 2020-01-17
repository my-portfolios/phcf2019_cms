package egovframework.com.sec.phcf.service;

import java.util.List;

/**
 * @Class Name : EgovPhcfAuthorService.java
 * @Description : 문화재단 권한관리에 대한 서비스 인터페이스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 * 2019. 12. 27.     윤병훈    최초생성
 *
 * @author 휴비즈 대외사업부
 * @since 2019. 12. 27.
 * @version
 * @see
 *
 */
public interface EgovPhcfAuthorService {
	List<AuthManageVO> selectEgovPhcfAuthList(AuthManageVO map) throws Exception;
	
	List<AuthManageVO> selectAllEgovPhcfAuthList(AuthManageVO map) throws Exception;
	
	void insertEgovPhcfAuthList(AuthManageVO map) throws Exception;
	
	void updateEgovPhcfAuthList(AuthManageVO map) throws Exception;
	
	void deleteEgovPhcfAuthList(AuthManageVO map) throws Exception;
	
	int selectEgovPhcfAuthListCnt(AuthManageVO map) throws Exception;
}
