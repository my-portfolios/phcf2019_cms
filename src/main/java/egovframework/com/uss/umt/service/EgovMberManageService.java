package egovframework.com.uss.umt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 일반회원관리에 관한 인터페이스클래스를 정의한다.
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2017.07.21  장동한 			로그인인증제한 작업
 *
 * </pre>
 */
public interface EgovMberManageService {

	/**
	 * 사용자의 기본정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param mberManageVO 일반회원 등록정보
	 * @return 등록결과
	 * @throws Exception
	 */
	public String insertMber(MberManageVO mberManageVO) throws Exception;

	/**
	 * 기 등록된 사용자 중 검색조건에 맞는 일반회원의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param mberId 상세조회대상 일반회원아이디
	 * @return mberManageVO 일반회원상세정보
	 * @throws Exception
	 */
	public MberManageVO selectMber(String mberId) throws Exception;

	/**
	 * 기 등록된 회원 중 검색조건에 맞는 회원들의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param userSearchVO 검색조건
	 * @return List<MberManageVO> 일반회원목록정보
	 * @throws Exception
	 */
	public List<MberManageVO> selectMberList(UserDefaultVO userSearchVO) throws Exception;

    /**
     * 일반회원 총 갯수를 조회한다.
     * @param userSearchVO 검색조건
     * @return 일반회원총갯수(int)
     * @throws Exception
     */
    public int selectMberListTotCnt(UserManageVO userSearchVO) throws Exception;

	/**
	 * 화면에 조회된 일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param mberManageVO 일반회원수정정보
	 * @throws Exception
	 */
	public void updateMber(MberManageVO mberManageVO) throws Exception;

	/**
	 * 화면에 조회된 사용자의 정보를 데이터베이스에서 삭제
	 * @param checkedIdForDel 삭제대상 일반회원아이디
	 * @throws Exception
	 */
	public void deleteMber(String checkedIdForDel) throws Exception;

	/**
	 * 일반회원 약관확인
	 * @param stplatId 일반회원약관아이디
	 * @return 일반회원약관정보(List)
	 * @throws Exception
	 */
	public List<?> selectStplat(String stplatId)  throws Exception;

	/**
	 * 일반회원암호수정
	 * @param mberManageVO 일반회원수정정보(비밀번호)
	 * @throws Exception
	 */
	public void updatePassword(MberManageVO mberManageVO) throws Exception;

	/**
	 * 일반회원이 비밀번호를 기억하지 못할 때 비밀번호를 찾을 수 있도록 함
	 * @param passVO 일반회원암호 조회조건정보
	 * @return mberManageVO 일반회원암호정보
	 * @throws Exception
	 */
	public MberManageVO selectPassword(MberManageVO passVO) throws Exception;

	/**
	 * 로그인인증제한 해제 
	 * @param mberManageVO 일반회원정보
	 * @return void
	 * @throws Exception
	 */
	public void updateLockIncorrect(MberManageVO mberManageVO) throws Exception;
	

	public MberManageVO selectMberWithId(String mberId);
	
	/* 휴면 회원 전환 대상자 조회 */
	public List<Map<String, Object>> selectDormantReserveMemberList(Map<String, Object> paramMap) throws Exception;
	
	/* 휴면 회원 전환 대상자 입력 */
	public void insertDormantReserveMember(MberManageVO mberManageVO) throws Exception;
	
	/* 휴면 회원 전환 대상자 삭제 */
	public void deleteDormantReserveMemberList(Map<String, Object> paramMap) throws Exception;
		
	/* 휴면 회원 조회 */
	public List<Map<String, Object>> selectDormantMberList(Map<String, Object> paramMap) throws Exception;
		
	/* 휴면회원 전환 (휴면회원 삽입) */
	public void transferDormantMber(MberManageVO mberManageVO) throws Exception;
		
	/* 휴면회원 전환 (회원 정보 삭제) */
	public void updateMberToDormant(MberManageVO mberManageVO) throws Exception;
	
	/* 로그인하지 않은 회원 조회 */
	public List<Map<String, Object>> selectNotLoggedMberList(Map<String, Object> paramMap) throws Exception;

	public int selectDormantMberCnt(HashMap<String, Object> paramMap) throws Exception;
	
	/* 특정 membershipType을 제외하고 나머지  회원들의 멤버십 관련 정보 조회 */
	public List<MberManageVO> selectMberListExcept(String merbershipType) throws Exception;
	
	/* 특정 membershipType 회원들의 멤버십 관련 정보 조회 */
	public List<MberManageVO> selectMberListSpecificMembership(String merbershipType) throws Exception;
	
	/* 회원 만료일이 지난 회원을 무료 회원으로 전환 */
	public void updateMberTypeAfterExpire(MberManageVO mberManageVO) throws Exception;
	
	/* 멤버십 만료 전까지 x일이 남은 회원들의 목록 조회*/
	public List<MberManageVO> selectMberNearExpireList(Map<String, Object> paramMap) throws Exception;
	
	/* 멤버십 타입에 따른 회원 메일 주소 목록*/
	public List<MberManageVO> selectMberListForSndngMail(Map<String, Object> paramMap) throws Exception;
}