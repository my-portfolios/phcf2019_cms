/**
 * 개요
 * - 배너에 대한 ServiceImpl 클래스를 정의한다.
 * 
 * 상세내용
 * - 배너에 대한 등록, 수정, 삭제, 조회, 반영확인 기능을 제공한다.
 * - 배너의 조회기능은 목록조회, 상세조회로 구분된다.
 * @author 이문준
 * @version 1.0
 * @created 03-8-2009 오후 2:07:12
 */

package egovframework.com.uss.ion.bnr.service.impl;

import java.io.File;
import java.util.List;

import egovframework.com.cmm.service.FileVO;
import egovframework.com.uss.ion.bnr.service.Banner;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.com.uss.ion.bnr.service.EgovBannerService;
import egovframework.phcf.hubizCommonMethod.CommonMethod;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("egovBannerService")
public class EgovBannerServiceImpl extends EgovAbstractServiceImpl implements EgovBannerService {
	
	/** logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovBannerServiceImpl.class);

	@Resource(name="bannerDAO")
    private BannerDAO bannerDAO;

	/**
	 * 배너를 관리하기 위해 등록된 배너목록을 조회한다.
	 * @param bannerVO - 배너 VO
	 * @return List - 배너 목록
	 */
	public List<BannerVO> selectBannerList(BannerVO bannerVO) throws Exception{
		return bannerDAO.selectBannerList(bannerVO);
	}

	/**
	 * 배너목록 총 갯수를 조회한다.
	 * @param bannerVO - 배너 VO
	 * @return int - 배너 카운트 수
	 */
	public int selectBannerListTotCnt(BannerVO bannerVO) throws Exception {
		return bannerDAO.selectBannerListTotCnt(bannerVO);
	}
	
	/**
	 * 등록된 배너의 상세정보를 조회한다.
	 * @param bannerVO - 배너 VO
	 * @return BannerVO - 배너 VO
	 */
	public BannerVO selectBanner(BannerVO bannerVO) throws Exception{
		return bannerDAO.selectBanner(bannerVO);
	}

	/**
	 * 배너정보를 신규로 등록한다.
	 * @param banner - 배너 model
	 */
	public BannerVO insertBanner(Banner banner, BannerVO bannerVO) throws Exception{
		CommonMethod.base64ImageDecoder(banner.getBannerImage(), "BANNER", banner.getBannerId());
		banner.setBannerImage("");
		
		bannerDAO.insertBanner(banner);
        bannerVO.setBannerId(banner.getBannerId());
        
        return selectBanner(bannerVO);
	}

	/**
	 * 기 등록된 배너정보를 수정한다.
	 * @param banner - 배너 model
	 */
	public void updateBanner(Banner banner, BannerVO bannerVO) throws Exception{
		if(banner.getBannerImage() != null) {
			CommonMethod.base64ImageDecoder(banner.getBannerImage(), "BANNER", banner.getBannerId());
		}
		banner.setBannerImage("");
		
		bannerDAO.updateBanner(banner);
	}

	/**
	 * 기 등록된 배너정보를 삭제한다.
	 * @param banner - 배너 model
	 */
	public void deleteBanner(Banner banner) throws Exception {
		CommonMethod.removeFile("BANNER",banner.getBannerId() + ".png");
        bannerDAO.deleteBanner(banner);
	}

	

	/**
	 * 배너가 특정화면에 반영된 결과를 조회한다.
	 * @param bannerVO - 배너 VO
	 * @return BannerVO - 배너 VO
	 */
	public List<BannerVO> selectBannerResult(BannerVO bannerVO) throws Exception{
		return bannerDAO.selectBannerResult(bannerVO);
	}

}