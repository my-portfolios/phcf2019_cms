package egovframework.phcf.util;

import java.util.HashMap;

import com.sun.star.lang.NullPointerException;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class PagingUtil {
	
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
