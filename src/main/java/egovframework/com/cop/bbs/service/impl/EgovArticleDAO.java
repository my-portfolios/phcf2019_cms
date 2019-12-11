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
	
	public List<BoardAddedColmnsVO> selectArticleAddedColmnsDetail(BoardVO boardVO){
		return (List<BoardAddedColmnsVO>) list("BBSArticle.selectArticleAddedColmnsDetail", boardVO);
	}

	public BoardVO selectArticleDetail(BoardVO boardVO) {
		BoardVO map = selectOne("BBSArticle.selectArticleDetail", boardVO);
		
		return map;
	}
	
	public void replyArticle(Board board) {
		insert("BBSArticle.replyArticle", board);
	}

	public void insertArticle(BoardAddedColmnsVO board) {
		if(board.getOrd() == -1) insert("BBSArticle.insertArticle", board);
		else insert("BBSArticle.insertArticleAddedColmns", board); //추가칼럼이 있을때 추가칼럼을 등록한다.
	}

	public void updateArticle(BoardAddedColmnsVO board) {
		if(board.getOrd() == -1) update("BBSArticle.updateArticle", board);	
		else update("BBSArticle.updateArticleAddedColmns", board); //추가칼럼이 있을때 추가칼럼을 업데이트한다.
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
