package egovframework.com.uat.uia.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 DAO 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.08.26  서준식          EsntlId를 이용한 로그인 추가
 *  2017.07.21  장동한 			로그인인증제한 작업
 *  </pre>
 */
@Repository("loginDAO")
public class LoginDAO extends EgovComAbstractDAO {

    /**
     * 2011.08.26
	 * EsntlId를 이용한 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionLoginByEsntlId(LoginVO vo) throws Exception {
    	return (LoginVO)selectOne("LoginUsr.ssoLoginByEsntlId", vo);
    }

	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionLogin(LoginVO vo) throws Exception {
    	return (LoginVO)selectOne("LoginUsr.actionLogin", vo);
    }
    
    public LoginVO cookieLogin(HashMap<String, Object> paramMap) throws Exception {
    	return (LoginVO)selectOne("LoginUsr.cookieLogin", paramMap);
    }
    
    // 자동로그인 체크한 경우에 사용자 테이블에 세션과 유효시간을 저장하기 위한 메서드
    public void keepLogin(String uid, String sessionId, Date next){
         
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("sessionId", sessionId);
        map.put("next", next);
         
        // Mapper.xml로 데이터를 전달할 때 한 객체밖에 전달 못함으로 map으로 묶어서 보내줌 단... 주의할 점은
        // Mapper.xml 안에서 #{} 이 안에 지정한 이름이랑 같아야함.. 자동으로 매핑될 수 있도록
        // 아래가 수행되면서, 사용자 테이블에 세션id와 유효시간이 저장됨
        update("LoginUsr.keepLogin",map);
         
    }

    /**
	 * 인증서 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionCrtfctLogin(LoginVO vo) throws Exception {

    	return (LoginVO)selectOne("LoginUsr.actionCrtfctLogin", vo);
    }

    /**
	 * 아이디를 찾는다.
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO searchId(LoginVO vo) throws Exception {

    	return (LoginVO)selectOne("LoginUsr.searchId", vo);
    }

    /**
	 * 비밀번호를 찾는다.
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO searchPassword(LoginVO vo) throws Exception {

    	return (LoginVO)selectOne("LoginUsr.searchPassword", vo);
    }

    /**
	 * 변경된 비밀번호를 저장한다.
	 * @param vo LoginVO
	 * @exception Exception
	 */
    public void updatePassword(LoginVO vo) throws Exception {
    	update("LoginUsr.updatePassword", vo);
    }
    
    
    /**
	 * 로그인인증제한 내역을 조회한다.
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
	public Map<?,?> selectLoginIncorrect(LoginVO vo) throws Exception {
    	return (Map<?,?>)selectOne("LoginUsr.selectLoginIncorrect", vo);
    }

    /**
	 * 로그인인증제한 내역을 업데이트 한다.
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public void updateLoginIncorrect(Map<?,?> map) throws Exception {
    	update("LoginUsr.updateLoginIncorrect"+map.get("USER_SE"), map);
    }

}
