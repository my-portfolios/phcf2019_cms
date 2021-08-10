package egovframework.phcf.ticketLink.service;

import egovframework.com.cmm.service.EgovProperties;

public class TicketLinkProperties {
	public static final int TKLINK_RESPONSE_JOIN_SUCCESS_CODE = 0;
	public static final int TKLINK_RESPONSE_ALREADY_JOIN_CODE = -10103;
	
	public static final int TKLINK_RESPONSE_MEMBER_DETAIL_SUCCESS_CODE = 0;
	public static final int TKLINK_RESPONSE_NO_USER_DETAIL_CODE = -10102;
	
	public static final int TKLINK_RESPONSE_MODIFY_SUCCESS_CODE = 0;
	
	public static final int TKLINK_RESPONSE_DEFAULT_CODE = -999; // -10101
	
	public static final String TKLINK_JOIN_URI = EgovProperties.getProperty("ticketlink.host") + EgovProperties.getProperty("ticketlink.join");
	public static final String TKLINK_MEMBER_DETAIL_URI = EgovProperties.getProperty("ticketlink.host") + EgovProperties.getProperty("ticketlink.detail");
	public static final String TKLINK_MEMBER_MODIFY_URI = EgovProperties.getProperty("ticketlink.host") + EgovProperties.getProperty("ticketlink.modify");
	public static final String TKLINK_PRODUCT_RESERVE_URI = EgovProperties.getProperty("ticketlink.facility.host") + EgovProperties.getProperty("ticketlink.facility.reserve");
	
	
	public static final String TKLINK_MEMBER_QUALIFICATION_CODE_FREE = EgovProperties.getProperty("ticketlink.memberQualificationCode.free");
	public static final String TKLINK_MEMBER_QUALIFICATION_CODE_MEMBERSHIP = EgovProperties.getProperty("ticketlink.memberQualificationCode.membership");
	
	public static final String TKLINK_PARTNER_NO = EgovProperties.getProperty("ticketlink.partnerNo");
	public static final String TKLINK_PARTNER_NO_ENCODED = EgovProperties.getProperty("ticketlink.partnerNo.encoded");
	public static final String TKLINK_PARTNER_NO_URL_ENCODED = EgovProperties.getProperty("ticketlink.partnerNo.urlencoded");
}
