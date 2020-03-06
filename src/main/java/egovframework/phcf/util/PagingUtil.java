package egovframework.phcf.util;

import java.util.HashMap;

import com.sun.star.lang.NullPointerException;

import egovframework.phcf.common.service.ParamMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.apache.commons.lang3.StringUtils;

public class PagingUtil {
	/**
	 * <b>Page Navigation</b></p>
	 * @param paramMap
	 * @param siteGubun	프론트 : F, 관리자 : A
	 * @return
	 */
	public static String printPageNavi(ParamMap paramMap, String siteGubun) {
		siteGubun = StringUtils.defaultString(siteGubun, "F");
		int page_no = paramMap.getInt(paramMap.getString("page_no_name","page_no"), 1); // 현재 페이지 번호
		int total_article = (int) paramMap.getDouble("total_article", 0); // 총 게시물 수
		int page_size = paramMap.getInt(paramMap.getString("page_size_name","page_size"), 10); // 한 페이지당 보여줄 목록 수
		int block_size = paramMap.getInt("block_size", 10); // 페이징 블럭 수

		return pageNavi(page_no, total_article, page_size, block_size, siteGubun, "D");
	}

	/**
	 * <b>Page Navigation</b></p>
	 * @param paramMap
	 * @param siteGubun	프론트 : F, 관리자 : A
	 * @param pageType  기본 : D, 서브 : S
	 * @return
	 */
	public static String printPageNavi(ParamMap paramMap, String siteGubun, String pageType) {
		siteGubun = StringUtils.defaultString(siteGubun, "F");
		pageType = StringUtils.defaultString(pageType, "D");
		int page_no = paramMap.getInt(paramMap.getString("page_no_name","page_no"), 1); // 현재 페이지 번호
		int total_article = (int) paramMap.getDouble("total_article", 0); // 총 게시물 수
		int page_size = paramMap.getInt(paramMap.getString("page_size_name","page_size"), 10); // 한 페이지당 보여줄 목록 수
		int block_size = paramMap.getInt("block_size", 10); // 페이징 블럭 수

		return pageNavi(page_no, total_article, page_size, block_size, siteGubun, pageType);
	}
	
	/**
	 * <b>Page Navigation</b></p>
	 * @param pageNo		현재 페이지 번호
	 * @param totCnt		총 게시물 수
	 * @param pageSize		한 페이당 보여줄 목록 수
	 * @param blockSize		페이징 블럭 수
	 * @param siteGubun		사이트 구분
	 * @param pageType		페이징 타입
	 * @return
	 */
	private static String pageNavi(int pageNo, int totCnt, int pageSize, int blockSize, String siteGubun, String pageType) {
		if("D".equalsIgnoreCase(pageType)) {
			return pageAdminDefNavi(pageNo, totCnt, pageSize, blockSize);
		} else {
			return pageAdminSubNavi(pageNo, totCnt, pageSize, blockSize);
		}
	}
		
	/**
	 * 관리자 기본 페이징
	 * @param pageNo
	 * @param totCnt
	 * @param pageSize
	 * @param blockSize
	 * @return
	 */
	private static String pageAdminDefNavi(int pageNo, int totCnt, int pageSize, int blockSize) {
		/*
		<div class='button btn_middle displayinline floatleft borderradiusrightnone borderrightnone'>prev</div>
		<ul>
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone on'>1</li><!--현재페이지에서 class='on' -->
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>2</li>
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>3</li>
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>4</li>
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>5</li>
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>6</li>
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>7</li>
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>8</li>
			<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>9...</li>
		</ul>
		<div class='button btn_middle displayinline floatleft borderradiusleftnone'>next</div>
		<div class='clear'></div>
		 */
		int totPageCnt = (totCnt / pageSize) + (totCnt % pageSize > 0 ? 1 : 0);
		int totBlockCnt = (totPageCnt / blockSize) + (totPageCnt % blockSize > 0 ? 1 : 0);
		int blockNo = (pageNo / blockSize) + (pageNo % blockSize > 0 ? 1 : 0);
		int startPageNo = (blockNo - 1) * blockSize + 1;
		int endPageNo = blockNo * blockSize;
	
		if (endPageNo > totPageCnt)
			endPageNo = totPageCnt;
	
		int prevBlockPageNo = (blockNo - 1) * blockSize;
		int nextBlockPageNo = blockNo * blockSize + 1;
	
		StringBuffer strHTML = new StringBuffer();
		
//		if (totPageCnt > 1) {
//			strHTML.append("<a href='#' onclick='fn_page(1); return false;'><img src='" + prev10Img + "' alt='처음페이지' /></a>\n ");
//		}
	
		if (blockNo > 1) {
			strHTML.append("<div class='button btn_middle displayinline floatleft borderradiusrightnone borderrightnone' onclick='fn_page(" + prevBlockPageNo + "); return false;'>prev</div>\n");
		} else {
			strHTML.append("<div class='button btn_middle displayinline floatleft borderradiusrightnone borderrightnone'>prev</div>\n");
		}
		
		strHTML.append("<ul>\n");
		String dot="";
		for (int i = startPageNo; i <= endPageNo; i++) {
			dot = (i==endPageNo && blockNo < totBlockCnt ?"...":"");
			if (i == pageNo) {
				strHTML.append("<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone on'>"+i+dot+"</li>\n");
			}else{								
				strHTML.append("<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone' onclick='fn_page(" + i + "); return false;'>" + i+dot + "</li>\n");
			}
		}
		
		if (totCnt == 0) {
			strHTML.append("<li class='button btn_middle displayinline floatleft borderradiusnone borderrightnone'>1</li>\n");
		}
	
		strHTML.append("</ul>\n");
	
		if (blockNo < totBlockCnt) {
			strHTML.append("<div class='button btn_middle displayinline floatleft borderradiusleftnone' onclick='fn_page(" + nextBlockPageNo + "); return false;'>next</div>\n");
		} else {
			strHTML.append("<div class='button btn_middle displayinline floatleft borderradiusleftnone'>next</div>\n");
		}
		strHTML.append("<div class='clear'></div>\n");
	
//		if (totPageCnt > 1) {
//			strHTML.append("<a href='#' onclick='fn_page(" + totPageCnt + "); return false;'><img src='" + next10Img + "' alt='마지막페이지'></a>\n");
//		}		
		
		return strHTML.toString();
	}
	
	/**
	 * 관리자 서브 페이징
	 * @param pageNo
	 * @param totCnt
	 * @param pageSize
	 * @param blockSize
	 * @return
	 */
	private static String pageAdminSubNavi(int pageNo, int totCnt, int pageSize, int blockSize) {
		return null;
	}
	
	// paramMap에 pageIndex 와 pageSize가 있다면 쿼리 LIMIT 구문의 OFFSET 값을 계산해 돌려준다.
	public static HashMap<String, Object> addFirstRecordIndex(HashMap<String, Object> paramMap) throws Exception {
		if( paramMap.get("pageIndex") == null || paramMap.get("pageSize") == null) {
			throw new NullPointerException("pageIndex 또는 pageSize 값이 존재 하지 않습니다.");
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
        paginationInfo.setCurrentPageNo(Integer.valueOf( String.valueOf( paramMap.get("pageIndex") ) ));
        paginationInfo.setRecordCountPerPage(Integer.valueOf( String.valueOf( paramMap.get("pageSize") ) ));
        paginationInfo.setPageSize(Integer.valueOf( String.valueOf( paramMap.get("pageSize") ) ));
        
        paramMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
        
        return paramMap;
	}
	
}
