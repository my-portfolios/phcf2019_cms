package egovframework.com.sec.phcf.service;

import egovframework.com.cmm.ComDefaultVO;

public class AuthManage extends ComDefaultVO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 문화재단 권한 관리
	 */
	private String seq;
	
	private String authNm;
	
	private String page;
	
	private String orgnztId;
	
	private String orgnztNm;
	
	private String groupId;
	
	private String acceptLink;
	
	private String banLink;
	
	private String authPriority;
	
	private String useYn;
	
	private String insId;
	
	private String insDt;
	
	private String uptId;
	
	private String uptDt;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getAuthNm() {
		return authNm;
	}

	public void setAuthNm(String authNm) {
		this.authNm = authNm;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getOrgnztId() {
		return orgnztId;
	}

	public void setOrgnztId(String orgnztId) {
		this.orgnztId = orgnztId;
	}

	public String getOrgnztNm() {
		return orgnztNm;
	}

	public void setOrgnztNm(String orgnztNm) {
		this.orgnztNm = orgnztNm;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAcceptLink() {
		return acceptLink;
	}

	public void setAcceptLink(String acceptLink) {
		this.acceptLink = acceptLink;
	}

	public String getBanLink() {
		return banLink;
	}

	public void setBanLink(String banLink) {
		this.banLink = banLink;
	}

	public String getAuthPriority() {
		return authPriority;
	}

	public void setAuthPriority(String authPriority) {
		this.authPriority = authPriority;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getInsDt() {
		return insDt;
	}

	public void setInsDt(String insDt) {
		this.insDt = insDt;
	}

	public String getUptId() {
		return uptId;
	}

	public void setUptId(String uptId) {
		this.uptId = uptId;
	}

	public String getUptDt() {
		return uptDt;
	}

	public void setUptDt(String uptDt) {
		this.uptDt = uptDt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
