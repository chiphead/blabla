package chiphead.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncUtil {
	public static String MD5(String before) {
		if (before == null) {
			return "";
		}
		String after = "";

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}

		sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
		try {
			after = baseEncoder.encode(md5.digest(before.getBytes("utf-8")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return after;
	}
}
