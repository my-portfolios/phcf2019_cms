package egovframework.com.cop.bbs.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardAddedColmnsVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;

@Repository("EgovArticleDAO")
public class EgovArticleDAO extends EgovComAbstractDAO {

	public List<?> selectArticleList(BoardVO boardVO) {
		return list("BBSArticle.selectArticleList", boardVO);
	}

	public int selectArticleListCnt(BoardVO boardVO) {
		return (Integer)selectOne("BBSArticle.selectArticleListCnt", boardVO);
	}

	public int selectMaxInqireCo(BoardVO boardVO) {
		return (Integer)selectOne("BBSArticle.selectMaxInqireCo", boardVO);
	}

	public void updateInqireCo(BoardVO boardVO) {
		update("BBSArticle.updateInqireCo", boardVO);
	}

	public BoardAddedColmnsVO selectArticleDetail(BoardVO boardVO) {
		BoardVO map = selectOne("BBSArticle.selectArticleDetail", boardVO);
		
		BoardAddedColmnsVO mapAc = new BoardAddedColmnsVO();
		
		// 추가칼럼이 있을 경우 추가칼럼도 가져온다.
		if(map.getAcYn().equals("Y")) {
			mapAc = selectOne("BBSArticle.selectArticleAddedColmns", boardVO);
			mapAc.setNttSj(map.getNttSj());
			mapAc.setNtcrId(map.getNtcrId());
			mapAc.setNtcrNm(map.getNtcrNm());
			mapAc.setNttNo(map.getNttNo());
			mapAc.setNttCn(map.getNttCn());
			mapAc.setPassword(map.getPassword());
			mapAc.setFrstRegisterId(map.getFrstRegisterId());
			mapAc.setFrstRegisterNm(map.getFrstRegisterNm());
			mapAc.setFrstRegisterPnttm(map.getFrstRegisterPnttm());
			mapAc.setNtceBgnde(map.getNtceBgnde());
			mapAc.setNtceEndde(map.getNtceEndde());
			mapAc.setInqireCo(map.getInqireCo());
			mapAc.setRecordCountPerPage(map.getRecordCountPerPage());
			mapAc.setUseAt(map.getUseAt());
			mapAc.setAtchFileId(map.getAtchFileId());
			mapAc.setBbsId(map.getBbsId());
			mapAc.setNttId(map.getNttId());
			mapAc.setSjBoldAt(map.getSjBoldAt());
			mapAc.setNoticeAt(map.getNoticeAt());
			mapAc.setSecretAt(map.getSecretAt());
			mapAc.setParnts(map.getParnts());
			mapAc.setReplyAt(map.getReplyAt());
			mapAc.setReplyLc(map.getReplyLc());
			mapAc.setSortOrdr(map.getSortOrdr());
			mapAc.setBbsTyCode(map.getBbsTyCode());
			mapAc.setReplyPosblAt(map.getReplyPosblAt());
			mapAc.setFileAtchPosblAt(map.getFileAtchPosblAt());
			mapAc.setPosblAtchFileNumber(map.getPosblAtchFileNumber());
			mapAc.setBbsNm(map.getBbsNm());
			mapAc.setAcYn(map.getAcYn());
		}
		
		return mapAc;
	}
	
	public void replyArticle(Board board) {
		insert("BBSArticle.replyArticle", board);
	}

	public void insertArticle(BoardAddedColmnsVO board) {
		insert("BBSArticle.insertArticle", board);
		
		//추가칼럼이 있을때 추가칼럼을 등록한다.
	    if(board.getAcYn().equals("Y")) insert("BBSArticle.insertArticleAddedColmns", board);
	}
	
	public void insertArticleAddedColmns(BoardAddedColmnsVO vo) {
		insert("BBSArticle.insertArticleAddedColmns", vo);
	}

	public void updateArticle(BoardAddedColmnsVO board) {
		update("BBSArticle.updateArticle", board);	
		
		//추가칼럼이 있을때 추가칼럼을 업데이트한다.
		if(board.getAcYn().equals("Y")) update("BBSArticle.updateArticleAddedColmns", board);
	}

	public void deleteArticle(BoardVO board) {
		update("BBSArticle.deleteArticle", board);
		
		//추가칼럼 있을시 삭제한다.
		if(board.getAcYn().equals("Y")) update("BBSArticle.deleteArticleAddedColmns", board);
	}

	public List<BoardVO> selectNoticeArticleList(BoardVO boardVO) {
		return (List<BoardVO>) list("BBSArticle.selectNoticeArticleList", boardVO);
	}
	
	public List<?> selectGuestArticleList(BoardVO vo) {
		return list("BBSArticle.selectGuestArticleList", vo);
	}

	public int selectGuestArticleListCnt(BoardVO vo) {
		return (Integer)selectOne("BBSArticle.selectGuestArticleListCnt", vo);
	}
	
	/*
	 * 블로그 관련
	 */
	public BoardVO selectArticleCnOne(BoardVO boardVO) {
		return (BoardVO) selectOne("BBSArticle.selectArticleCnOne", boardVO);
	}
	
	public List<BoardVO> selectBlogNmList(BoardVO boardVO) {
		return (List<BoardVO>) list("BBSArticle.selectBlogNmList", boardVO);
	}
	
	public List<?> selectBlogListManager(BoardVO vo) {
		return list("BBSArticle.selectBlogListManager", vo);
	}
	
	public int selectBlogListManagerCnt(BoardVO vo) {
		return (Integer)selectOne("BBSArticle.selectBlogListManagerCnt", vo);
	}
	
	public List<BoardVO> selectArticleDetailDefault(BoardVO boardVO) {
		return (List<BoardVO>) list("BBSArticle.selectArticleDetailDefault", boardVO);
	}
	
	public int selectArticleDetailDefaultCnt(BoardVO boardVO) {
		return (Integer)selectOne("BBSArticle.selectArticleDetailDefaultCnt", boardVO);
	}
	
	public List<BoardVO> selectArticleDetailCn(BoardVO boardVO) {
		return (List<BoardVO>) list("BBSArticle.selectArticleDetailCn", boardVO);
	}
	
	public int selectLoginUser(BoardVO boardVO) {
		return (Integer)selectOne("BBSArticle.selectLoginUser", boardVO);
	}
	

}
