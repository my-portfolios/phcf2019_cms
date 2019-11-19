package egovframework.com.cop.bbs.service;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 게시물 관리를 위한 VO 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------      --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2009.06.29  한성곤		2단계 기능 추가 (댓글관리, 만족도조사)
 *
 * </pre>
 */
@SuppressWarnings("serial")
public class BoardVO extends Board implements Serializable {

    /** 검색시작일 */
    private String searchBgnDe = "";
    
    /** 검색조건 */
    private String searchCnd = "";
    
    /** 검색종료일 */
    private String searchEndDe = "";
    
    /** 검색단어 */
    private String searchWrd = "";
    
    /** 정렬순서(DESC,ASC) */
    private long sortOrdr = 0L;

    /** 검색사용여부 */
    private String searchUseYn = "";

    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지갯수 */
    private int pageUnit = 10;

    /** 페이지사이즈 */
    private int pageSize = 10;

    /** 첫페이지 인덱스 */
    private int firstIndex = 1;

    /** 마지막페이지 인덱스 */
    private int lastIndex = 1;

    /** 페이지당 레코드 개수 */
    private int recordCountPerPage = 10;

    /** 레코드 번호 */
    private int rowNo = 0;

    /** 최초 등록자명 */
    private String frstRegisterNm = "";

    /** 최종 수정자명 */
    private String lastUpdusrNm = "";

    /** 유효여부 */
    private String isExpired = "N";

    /** 상위 정렬 순서 */
    private String parntsSortOrdr = "";

    /** 상위 답변 위치 */
    private String parntsReplyLc = "";

    /** 게시판 유형코드 */
    private String bbsTyCode = "";
    
    /** 게시판 속성코드 */
    private String bbsAttrbCode = "";

    /** 게시판 명 */
    private String bbsNm = "";
    
    /** 추가칼럼여부 */
    private String acYn = "";
    
    /** 추가칼럼1정의 */
    private String ac1Nm = "";
    
    /** 추가칼럼2정의 */
    private String ac2Nm = "";
    
    /** 추가칼럼3정의 */
    private String ac3Nm = "";
    
    /** 추가칼럼4정의 */
    private String ac4Nm = "";
    
    /** 추가칼럼5정의 */
    private String ac5Nm = "";
    
    /** 추가칼럼6정의 */
    private String ac6Nm = "";
    
    /** 추가칼럼7정의 */
    private String ac7Nm = "";
    
    /** 추가칼럼8정의 */
    private String ac8Nm = "";
    
    /** 추가칼럼9정의 */
    private String ac9Nm = "";
    
    /** 추가칼럼10정의 */
    private String ac10Nm = "";
    
    /** 추가칼럼11정의 */
    private String ac11Nm = "";
    
    /** 추가칼럼12정의 */
    private String ac12Nm = "";
    
    /** 추가칼럼13정의 */
    private String ac13Nm = "";
    
    /** 추가칼럼14정의 */
    private String ac14Nm = "";
    
    /** 추가칼럼15정의 */
    private String ac15Nm = "";
    
    /** 추가칼럼16정의 */
    private String ac16Nm = "";
    
    /** 추가칼럼17정의 */
    private String ac17Nm = "";
    
    /** 추가칼럼18정의 */
    private String ac18Nm = "";
    
    /** 추가칼럼19정의 */
    private String ac19Nm = "";
    
    /** 추가칼럼20정의 */
    private String ac20Nm = "";

    /** 파일첨부가능여부 */
    private String fileAtchPosblAt = "";
    
    /** 첨부가능파일숫자 */
    private int posblAtchFileNumber = 0;
    
    /** 답장가능여부 */
    private String replyPosblAt = "";
    
    /** 조회 수 증가 여부 */
    private boolean plusCount = false;
    
    /** 익명등록 여부 */
    private String anonymousAt = "";
    
    /** 하위 페이지 인덱스 (댓글 및 만족도 조사 여부 확인용) */
    private String subPageIndex = "";

    /** 게시글 댓글갯수 */
    private String commentCo = "";
    
    /**
     * searchBgnDe attribute를 리턴한다.
     * 
     * @return the searchBgnDe
     */
    public String getSearchBgnDe() {
	return searchBgnDe;
    }

    /**
     * searchBgnDe attribute 값을 설정한다.
     * 
     * @param searchBgnDe
     *            the searchBgnDe to set
     */
    public void setSearchBgnDe(String searchBgnDe) {
	this.searchBgnDe = searchBgnDe;
    }

    /**
     * searchCnd attribute를 리턴한다.
     * 
     * @return the searchCnd
     */
    public String getSearchCnd() {
	return searchCnd;
    }

    /**
     * searchCnd attribute 값을 설정한다.
     * 
     * @param searchCnd
     *            the searchCnd to set
     */
    public void setSearchCnd(String searchCnd) {
	this.searchCnd = searchCnd;
    }

    /**
     * searchEndDe attribute를 리턴한다.
     * 
     * @return the searchEndDe
     */
    public String getSearchEndDe() {
	return searchEndDe;
    }

    /**
     * searchEndDe attribute 값을 설정한다.
     * 
     * @param searchEndDe
     *            the searchEndDe to set
     */
    public void setSearchEndDe(String searchEndDe) {
	this.searchEndDe = searchEndDe;
    }

    /**
     * searchWrd attribute를 리턴한다.
     * 
     * @return the searchWrd
     */
    public String getSearchWrd() {
	return searchWrd;
    }

    /**
     * searchWrd attribute 값을 설정한다.
     * 
     * @param searchWrd
     *            the searchWrd to set
     */
    public void setSearchWrd(String searchWrd) {
	this.searchWrd = searchWrd;
    }

    /**
     * sortOrdr attribute를 리턴한다.
     * 
     * @return the sortOrdr
     */
    public long getSortOrdr() {
	return sortOrdr;
    }

    /**
     * sortOrdr attribute 값을 설정한다.
     * 
     * @param sortOrdr
     *            the sortOrdr to set
     */
    public void setSortOrdr(long sortOrdr) {
	this.sortOrdr = sortOrdr;
    }

    /**
     * searchUseYn attribute를 리턴한다.
     * 
     * @return the searchUseYn
     */
    public String getSearchUseYn() {
	return searchUseYn;
    }

    /**
     * searchUseYn attribute 값을 설정한다.
     * 
     * @param searchUseYn
     *            the searchUseYn to set
     */
    public void setSearchUseYn(String searchUseYn) {
	this.searchUseYn = searchUseYn;
    }

    /**
     * pageIndex attribute를 리턴한다.
     * 
     * @return the pageIndex
     */
    public int getPageIndex() {
	return pageIndex;
    }

    /**
     * pageIndex attribute 값을 설정한다.
     * 
     * @param pageIndex
     *            the pageIndex to set
     */
    public void setPageIndex(int pageIndex) {
	this.pageIndex = pageIndex;
    }

    /**
     * pageUnit attribute를 리턴한다.
     * 
     * @return the pageUnit
     */
    public int getPageUnit() {
	return pageUnit;
    }

    /**
     * pageUnit attribute 값을 설정한다.
     * 
     * @param pageUnit
     *            the pageUnit to set
     */
    public void setPageUnit(int pageUnit) {
	this.pageUnit = pageUnit;
    }

    /**
     * pageSize attribute를 리턴한다.
     * 
     * @return the pageSize
     */
    public int getPageSize() {
	return pageSize;
    }

    /**
     * pageSize attribute 값을 설정한다.
     * 
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
    }

    /**
     * firstIndex attribute를 리턴한다.
     * 
     * @return the firstIndex
     */
    public int getFirstIndex() {
	return firstIndex;
    }

    /**
     * firstIndex attribute 값을 설정한다.
     * 
     * @param firstIndex
     *            the firstIndex to set
     */
    public void setFirstIndex(int firstIndex) {
	this.firstIndex = firstIndex;
    }

    /**
     * lastIndex attribute를 리턴한다.
     * 
     * @return the lastIndex
     */
    public int getLastIndex() {
	return lastIndex;
    }

    /**
     * lastIndex attribute 값을 설정한다.
     * 
     * @param lastIndex
     *            the lastIndex to set
     */
    public void setLastIndex(int lastIndex) {
	this.lastIndex = lastIndex;
    }

    /**
     * recordCountPerPage attribute를 리턴한다.
     * 
     * @return the recordCountPerPage
     */
    public int getRecordCountPerPage() {
	return recordCountPerPage;
    }

    /**
     * recordCountPerPage attribute 값을 설정한다.
     * 
     * @param recordCountPerPage
     *            the recordCountPerPage to set
     */
    public void setRecordCountPerPage(int recordCountPerPage) {
	this.recordCountPerPage = recordCountPerPage;
    }

    /**
     * rowNo attribute를 리턴한다.
     * 
     * @return the rowNo
     */
    public int getRowNo() {
	return rowNo;
    }

    /**
     * rowNo attribute 값을 설정한다.
     * 
     * @param rowNo
     *            the rowNo to set
     */
    public void setRowNo(int rowNo) {
	this.rowNo = rowNo;
    }

    /**
     * frstRegisterNm attribute를 리턴한다.
     * 
     * @return the frstRegisterNm
     */
    public String getFrstRegisterNm() {
	return frstRegisterNm;
    }

    /**
     * frstRegisterNm attribute 값을 설정한다.
     * 
     * @param frstRegisterNm
     *            the frstRegisterNm to set
     */
    public void setFrstRegisterNm(String frstRegisterNm) {
	this.frstRegisterNm = frstRegisterNm;
    }

    /**
     * lastUpdusrNm attribute를 리턴한다.
     * 
     * @return the lastUpdusrNm
     */
    public String getLastUpdusrNm() {
	return lastUpdusrNm;
    }

    /**
     * lastUpdusrNm attribute 값을 설정한다.
     * 
     * @param lastUpdusrNm
     *            the lastUpdusrNm to set
     */
    public void setLastUpdusrNm(String lastUpdusrNm) {
	this.lastUpdusrNm = lastUpdusrNm;
    }

    /**
     * isExpired attribute를 리턴한다.
     * 
     * @return the isExpired
     */
    public String getIsExpired() {
	return isExpired;
    }

    /**
     * isExpired attribute 값을 설정한다.
     * 
     * @param isExpired
     *            the isExpired to set
     */
    public void setIsExpired(String isExpired) {
	this.isExpired = isExpired;
    }

    /**
     * parntsSortOrdr attribute를 리턴한다.
     * 
     * @return the parntsSortOrdr
     */
    public String getParntsSortOrdr() {
	return parntsSortOrdr;
    }

    /**
     * parntsSortOrdr attribute 값을 설정한다.
     * 
     * @param parntsSortOrdr
     *            the parntsSortOrdr to set
     */
    public void setParntsSortOrdr(String parntsSortOrdr) {
	this.parntsSortOrdr = parntsSortOrdr;
    }

    /**
     * parntsReplyLc attribute를 리턴한다.
     * 
     * @return the parntsReplyLc
     */
    public String getParntsReplyLc() {
	return parntsReplyLc;
    }

    /**
     * parntsReplyLc attribute 값을 설정한다.
     * 
     * @param parntsReplyLc
     *            the parntsReplyLc to set
     */
    public void setParntsReplyLc(String parntsReplyLc) {
	this.parntsReplyLc = parntsReplyLc;
    }

    /**
     * bbsTyCode attribute를 리턴한다.
     * 
     * @return the bbsTyCode
     */
    public String getBbsTyCode() {
	return bbsTyCode;
    }

    /**
     * bbsTyCode attribute 값을 설정한다.
     * 
     * @param bbsTyCode
     *            the bbsTyCode to set
     */
    public void setBbsTyCode(String bbsTyCode) {
	this.bbsTyCode = bbsTyCode;
    }

    /**
     * bbsAttrbCode attribute를 리턴한다.
     * 
     * @return the bbsAttrbCode
     */
    public String getBbsAttrbCode() {
	return bbsAttrbCode;
    }

    /**
     * bbsAttrbCode attribute 값을 설정한다.
     * 
     * @param bbsAttrbCode
     *            the bbsAttrbCode to set
     */
    public void setBbsAttrbCode(String bbsAttrbCode) {
	this.bbsAttrbCode = bbsAttrbCode;
    }

    /**
     * bbsNm attribute를 리턴한다.
     * 
     * @return the bbsNm
     */
    public String getBbsNm() {
	return bbsNm;
    }

    /**
     * bbsNm attribute 값을 설정한다.
     * 
     * @param bbsNm
     *            the bbsNm to set
     */
    public void setBbsNm(String bbsNm) {
	this.bbsNm = bbsNm;
    }
    

    public String getAcYn() {
		return acYn;
	}

	public void setAcYn(String acYn) {
		this.acYn = acYn;
	}

	public String getAc1Nm() {
		return ac1Nm;
	}

	public void setAc1Nm(String ac1Nm) {
		this.ac1Nm = ac1Nm;
	}

	public String getAc2Nm() {
		return ac2Nm;
	}

	public void setAc2Nm(String ac2Nm) {
		this.ac2Nm = ac2Nm;
	}

	public String getAc3Nm() {
		return ac3Nm;
	}

	public void setAc3Nm(String ac3Nm) {
		this.ac3Nm = ac3Nm;
	}

	public String getAc4Nm() {
		return ac4Nm;
	}

	public void setAc4Nm(String ac4Nm) {
		this.ac4Nm = ac4Nm;
	}

	public String getAc5Nm() {
		return ac5Nm;
	}

	public void setAc5Nm(String ac5Nm) {
		this.ac5Nm = ac5Nm;
	}

	public String getAc6Nm() {
		return ac6Nm;
	}

	public void setAc6Nm(String ac6Nm) {
		this.ac6Nm = ac6Nm;
	}

	public String getAc7Nm() {
		return ac7Nm;
	}

	public void setAc7Nm(String ac7Nm) {
		this.ac7Nm = ac7Nm;
	}

	public String getAc8Nm() {
		return ac8Nm;
	}

	public void setAc8Nm(String ac8Nm) {
		this.ac8Nm = ac8Nm;
	}

	public String getAc9Nm() {
		return ac9Nm;
	}

	public void setAc9Nm(String ac9Nm) {
		this.ac9Nm = ac9Nm;
	}

	public String getAc10Nm() {
		return ac10Nm;
	}

	public void setAc10Nm(String ac10Nm) {
		this.ac10Nm = ac10Nm;
	}

	public String getAc11Nm() {
		return ac11Nm;
	}

	public void setAc11Nm(String ac11Nm) {
		this.ac11Nm = ac11Nm;
	}

	public String getAc12Nm() {
		return ac12Nm;
	}

	public void setAc12Nm(String ac12Nm) {
		this.ac12Nm = ac12Nm;
	}

	public String getAc13Nm() {
		return ac13Nm;
	}

	public void setAc13Nm(String ac13Nm) {
		this.ac13Nm = ac13Nm;
	}

	public String getAc14Nm() {
		return ac14Nm;
	}

	public void setAc14Nm(String ac14Nm) {
		this.ac14Nm = ac14Nm;
	}

	public String getAc15Nm() {
		return ac15Nm;
	}

	public void setAc15Nm(String ac15Nm) {
		this.ac15Nm = ac15Nm;
	}

	public String getAc16Nm() {
		return ac16Nm;
	}

	public void setAc16Nm(String ac16Nm) {
		this.ac16Nm = ac16Nm;
	}

	public String getAc17Nm() {
		return ac17Nm;
	}

	public void setAc17Nm(String ac17Nm) {
		this.ac17Nm = ac17Nm;
	}

	public String getAc18Nm() {
		return ac18Nm;
	}

	public void setAc18Nm(String ac18Nm) {
		this.ac18Nm = ac18Nm;
	}

	public String getAc19Nm() {
		return ac19Nm;
	}

	public void setAc19Nm(String ac19Nm) {
		this.ac19Nm = ac19Nm;
	}

	public String getAc20Nm() {
		return ac20Nm;
	}

	public void setAc20Nm(String ac20Nm) {
		this.ac20Nm = ac20Nm;
	}

	/**
     * fileAtchPosblAt attribute를 리턴한다.
     * 
     * @return the fileAtchPosblAt
     */
    public String getFileAtchPosblAt() {
	return fileAtchPosblAt;
    }

    /**
     * fileAtchPosblAt attribute 값을 설정한다.
     * 
     * @param fileAtchPosblAt
     *            the fileAtchPosblAt to set
     */
    public void setFileAtchPosblAt(String fileAtchPosblAt) {
	this.fileAtchPosblAt = fileAtchPosblAt;
    }

    /**
     * posblAtchFileNumber attribute를 리턴한다.
     * 
     * @return the posblAtchFileNumber
     */
    public int getPosblAtchFileNumber() {
	return posblAtchFileNumber;
    }

    /**
     * posblAtchFileNumber attribute 값을 설정한다.
     * 
     * @param posblAtchFileNumber
     *            the posblAtchFileNumber to set
     */
    public void setPosblAtchFileNumber(int posblAtchFileNumber) {
	this.posblAtchFileNumber = posblAtchFileNumber;
    }

    /**
     * replyPosblAt attribute를 리턴한다.
     * 
     * @return the replyPosblAt
     */
    public String getReplyPosblAt() {
	return replyPosblAt;
    }

    /**
     * replyPosblAt attribute 값을 설정한다.
     * 
     * @param replyPosblAt
     *            the replyPosblAt to set
     */
    public void setReplyPosblAt(String replyPosblAt) {
	this.replyPosblAt = replyPosblAt;
    }

    /**
     * plusCount attribute를 리턴한다.
     * @return the plusCount
     */
    public boolean isPlusCount() {
        return plusCount;
    }

    /**
     * plusCount attribute 값을 설정한다.
     * @param plusCount the plusCount to set
     */
    public void setPlusCount(boolean plusCount) {
        this.plusCount = plusCount;
    }

    /**
     * subPageIndex attribute를 리턴한다.
     * @return the subPageIndex
     */
    public String getSubPageIndex() {
        return subPageIndex;
    }

    /**
     * subPageIndex attribute 값을 설정한다.
     * @param subPageIndex the subPageIndex to set
     */
    public void setSubPageIndex(String subPageIndex) {
        this.subPageIndex = subPageIndex;
    }

    /**
     * anonymousAt attribute를 리턴한다.
     * @return the anonymousAt
     */
    public String getAnonymousAt() {
        return anonymousAt;
    }

    /**
     * anonymousAt attribute 값을 설정한다.
     * @param anonymousAt the anonymousAt to set
     */
    public void setAnonymousAt(String anonymousAt) {
        this.anonymousAt = anonymousAt;
    }
    
    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }
    
    /**
     * commentCo attribute를 리턴한다.
     * @return the commentCo
     */
    public String getCommentCo() {
        return commentCo;
    }

    
    /**
     * commentCo attribute 값을 설정한다.
     * @param commentCo the commentCo to set
     */
    public void setCommentCo(String commentCo) {
        this.commentCo = commentCo;
    }
    
}
