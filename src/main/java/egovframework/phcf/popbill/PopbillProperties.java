package egovframework.phcf.popbill;

import egovframework.com.cmm.service.EgovProperties;

public class PopbillProperties {
	public static String POPBILL_CORP_NUM = EgovProperties.getProperty("Popbill.CorpNum");
	public static String POPBILL_USER_ID = EgovProperties.getProperty("Popbill.UserID");
	public static String POPBILL_LINK_ID = EgovProperties.getProperty("Popbill.LinkID");
	public static String POPBILL_SECRET_KEY = EgovProperties.getProperty("Popbill.SecretKey");
	public static String POPBILL_SENDER_NUM = EgovProperties.getProperty("Popbill.senderNum");
	public static boolean POPBILL_IS_TEST = Boolean.valueOf(EgovProperties.getProperty("Popbill.IsTest"));
	public static boolean POPBILL_IS_IP_RESTRICT_ONOFF = Boolean.valueOf(EgovProperties.getProperty("Popbill.IsIPRestrictOnOff"));
	public static boolean POPBILL_USE_STATIC_IP = Boolean.valueOf(EgovProperties.getProperty("Popbill.UseStaticIP"));
	public static boolean POPBILL_USE_LOCAL_TIME_YN = Boolean.valueOf(EgovProperties.getProperty("Popbill.UseLocalTimeYN"));
	

}
