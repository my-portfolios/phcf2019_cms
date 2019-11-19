package egovframework.phcf.statistic.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.phcf.statistic.web.StatisticVO;

/**
 * 통계 집계 결과 관련 DAO 클래스
 * @author	권혜진
 * @since	2019-09-26
 * */

@Repository("statisticDAO")
public class StatisticDAO extends EgovComAbstractDAO {
	
	// 월별 접속자 통계
	public List<StatisticVO> selectMonthlyReport() {
		return selectList("StatisticDAO.selectMonthlyReport");
	}
	// 최근 게시물
	public List<HashMap<String, Object>> selectRcntBbsList() {
		return selectList("StatisticDAO.selectRcntBbsList");
	}
	// 인기메뉴목록
	public List<HashMap<String, Object>> selectPopulMenuList() {
		return selectList("StatisticDAO.selectPopulMenuList");
	}
	// 오늘 접속자 수 집계
	public String selectConnectCnt() {
		return selectOne("StatisticDAO.selectConnectCnt");
	}
}
