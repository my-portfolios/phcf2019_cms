package egovframework.phcf.statistic.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.sts.com.StatsVO;
import egovframework.phcf.statistic.service.StatisticService;
import egovframework.phcf.statistic.web.StatisticVO;

/**
 * 통계 집계 결과 관련 서비스 구현 클래스
 * @author	권혜진
 * @since	2019-09-26
 * */

@Service("StatisticService")
public class StatisticServiceImpl implements StatisticService {

	@Resource(name="statisticDAO")
	private StatisticDAO dao;
	
	
	@Override
	public List<StatisticVO> selectMonthlyReport() throws Exception {
		return dao.selectMonthlyReport();
	}


	@Override
	public List<HashMap<String, Object>> selectRcntBbsList() throws Exception {
		return dao.selectRcntBbsList();
	}


	@Override
	public List<HashMap<String, Object>> selectPopulMenuList() throws Exception {
		return dao.selectPopulMenuList();
	}


	@Override
	public String selectConnectCnt() throws Exception {
		return dao.selectConnectCnt();
	}

}
