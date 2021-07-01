package egovframework.com.cop.bbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.fdl.cmmn.exception.FdlException;

public interface EgovArticleService {

	Map<String, Object> selectArticleList(BoardVO boardVO);

	BoardVO selectArticleDetail(BoardVO boardVO);
	
	List<BoardAddedColmnsVO> selectArticleAddedColmnsDetail(BoardVO boardVO);
	
	void insertArticle(BoardAddedColmnsVO board) throws FdlException;

	void updateArticle(BoardAddedColmnsVO board);

	void deleteArticle(BoardVO board) throws Exception;

	List<BoardVO> selectNoticeArticleList(BoardVO boardVO);
	
	Map<String, Object> selectGuestArticleList(BoardVO vo);
	
	List<HashMap<String, Object>> latestArticleListView(HashMap<String, Object> boardVO);
	
	List<HashMap<String, Object>> latestMultiArticleListView(HashMap<String, Object> boardVO);
	
	/*
	 * 블로그 관련
	 */
	BoardVO selectArticleCnOne(BoardVO boardVO);
	
	List<BoardVO> selectBlogNmList(BoardVO boardVO);
	
	Map<String, Object> selectBlogListManager(BoardVO boardVO);
	
	List<BoardVO> selectArticleDetailDefault(BoardVO boardVO);
	
	int selectArticleDetailDefaultCnt(BoardVO boardVO);
	
	List<BoardVO> selectArticleDetailCn(BoardVO boardVO);
	
	int selectLoginUser(BoardVO boardVO);

	int latestArticleListViewCnt(HashMap<String, Object> vo);

	int latestMultiArticleListViewCnt(HashMap<String, Object> vo);
	
	 // category name에 따라 article 선택 
	Map<String, Object> selectArticleListByCateName(BoardVO boardVO);
}
