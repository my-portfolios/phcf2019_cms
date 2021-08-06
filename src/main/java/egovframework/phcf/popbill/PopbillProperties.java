package egovframework.phcf.popbill;

import egovframework.com.cmm.service.EgovProperties;

public class PopbillProperties {
	public static final String POPBILL_CORP_NUM = EgovProperties.getProperty("Popbill.CorpNum");
	public static final String POPBILL_USER_ID = EgovProperties.getProperty("Popbill.UserID");
	public static final String POPBILL_LINK_ID = EgovProperties.getProperty("Popbill.LinkID");
	public static final String POPBILL_SECRET_KEY = EgovProperties.getProperty("Popbill.SecretKey");
	public static final String POPBILL_SENDER_NUM = EgovProperties.getProperty("Popbill.senderNum");
	public static final String POPBILL_SENDER_NAME = "포항문화재단";
	public static final boolean POPBILL_IS_TEST = Boolean.valueOf(EgovProperties.getProperty("Popbill.IsTest"));
	public static final boolean POPBILL_IS_IP_RESTRICT_ONOFF = Boolean.valueOf(EgovProperties.getProperty("Popbill.IsIPRestrictOnOff"));
	public static final boolean POPBILL_USE_STATIC_IP = Boolean.valueOf(EgovProperties.getProperty("Popbill.UseStaticIP"));
	public static final boolean POPBILL_USE_LOCAL_TIME_YN = Boolean.valueOf(EgovProperties.getProperty("Popbill.UseLocalTimeYN"));
	public static final String POPBILL_NOTICE_RESERVE_TIME = "103000"; // "HHmmss"
	
	
	public static final String SMS_SINGLE = "SMS_S";
	public static final String SMS_MULTI = "SMS_M";
	public static final String LMS_SINGLE = "LMS_S";
	public static final String LMS_MULTI = "LMS_M";
	public static final String MMS_SINGLE = "MMS_S";
	public static final String MMS_MULTI = "MMS_M";
	

}
