package egovframework.phcf.busking;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;


public class BuskingGroupVO extends ComDefaultVO {
	private String seq;
	private String mberId;
	private String regDate;
	private String approveYN;
	private String teamName;
	private String headName;
	private String phone;
	private String genre;
	private String area;
	private String personnel;
	private String mbers;
	private String profile;
	private String equipment;
	private String snsLink;
	private String snsVideo;
	private String tFile;
	
	private List<BuskingGroupVO> buskingGroupList;
	
	private int pageOffset;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getMberId() {
		return mberId;
	}

	public void setMberId(String mberId) {
		this.mberId = mberId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getApproveYN() {
		return approveYN;
	}

	public void setApproveYN(String approveYN) {
		this.approveYN = approveYN;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public String getMbers() {
		return mbers;
	}

	public void setMbers(String mbers) {
		this.mbers = mbers;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getSnsLink() {
		return snsLink;
	}

	public void setSnsLink(String snsLink) {
		this.snsLink = snsLink;
	}

	public String getSnsVideo() {
		return snsVideo;
	}

	public void setSnsVideo(String snsVideo) {
		this.snsVideo = snsVideo;
	}

	public String gettFile() {
		return tFile;
	}

	public void settFile(String tFile) {
		this.tFile = tFile;
	}


	public List<BuskingGroupVO> getBuskingGroupList() {
		return buskingGroupList;
	}

	public void setBuskingGroupList(List<BuskingGroupVO> buskingGroupList) {
		this.buskingGroupList = buskingGroupList;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	
	
	
}
