package egovframework.com.cop.ems.service;

/**
 * 메일 솔루션과 연동해서 이용해서 메일을 보내는 서비스 클래스
 * @since 2011.09.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.09.09  서준식       최초 작성
 *
 *  </pre>
 */

public interface EgovSndngMailService {

	/**
	 * 메일을 발송한다
	 * @param vo SndngMailVO
	 * @return boolean
	 * @exception Exception
	 */
	boolean sndngMail(SndngMailVO vo) throws Exception;
	
	/**
	 * 여러 메일을 한 번에 보낸다.
	 * 
	 * @param emailAddresses 수신 메일 주소 목록
	 * @param dividedSize 한 번에 보낼 메일 수
	 * @param sndngMailVO 
	 * @return boolean 메일 전송 성공/실패 여부
	 * @exception Exception 
	 */
	
	public boolean sendMultiMail(String[] emailAddresses, int dividedSize, SndngMailVO sndngMailVO) throws Exception;
}
