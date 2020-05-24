package egovframework.phcf.util;

import com.ksign.securedb.api.SDBCrypto;

public class EncryptUtil {
	// 단방향 암호화 (비밀번호) 
	public static String DigestSha256(String text) throws Exception
	{
		
		String digData = "";
		
		if (text == null || "".equals(text) || "null".equals(text)) {
			return "";
		}
		
		try {
			digData = SDBCrypto.DigestSha256(text);
		} catch (Exception e) {
		}
		
		return digData;
	}
}
