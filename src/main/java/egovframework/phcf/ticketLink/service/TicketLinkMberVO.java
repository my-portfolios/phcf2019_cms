package egovframework.phcf.ticketLink.service;

public class TicketLinkMberVO {
	private int tlId;
	
	private String memberId;
	
	private String partnerNo;
	
	/** 회원 종류 코드(회원 등급 구분)*/
	private String mberQualCode;
	
	/** 회원 멤버십 번호 (최대 16자리) - 포항문화재단 DB ESNTL_ID 칼럼 사용*/
	private String membershipNo;
	
	private String memberName;
	
	private String cellPhoneNo;
	
	private String birthday;
	
	private String email;
	
	private String gender;

	public int getTlId() {
		return tlId;
	}

	public void setTlId(int tlId) {
		this.tlId = tlId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public String getMberQualCode() {
		return mberQualCode;
	}

	public void setMberQualCode(String mberQualCode) {
		this.mberQualCode = mberQualCode;
	}

	public String getMembershipNo() {
		return membershipNo;
	}

	public void setMembershipNo(String membershipNo) {
		this.membershipNo = membershipNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCellPhoneNo() {
		return cellPhoneNo;
	}

	public void setCellPhoneNo(String cellPhoneNo) {
		this.cellPhoneNo = cellPhoneNo;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "TicketLinkMberVO [tlId=" + tlId + ", memberId=" + memberId + ", partnerNo=" + partnerNo
				+ ", mberQualCode=" + mberQualCode + ", membershipNo=" + membershipNo + ", memberName=" + memberName
				+ ", cellPhoneNo=" + cellPhoneNo + ", birthday=" + birthday + ", email=" + email + ", gender=" + gender
				+ "]";
	}

	
	
	
	
}
