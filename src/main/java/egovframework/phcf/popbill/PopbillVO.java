package egovframework.phcf.popbill;

public class PopbillVO {
	private String userId = PopbillProperties.POPBILL_USER_ID;
	private String corpNum = PopbillProperties.POPBILL_CORP_NUM;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCorpNum() {
		return corpNum;
	}
	public void setCorpNum(String corpNum) {
		this.corpNum = corpNum;
	}
	
	@Override
	public String toString() {
		return "PopbillVO [userId=" + userId + ", corpNum=" + corpNum + "]";
	}
}
