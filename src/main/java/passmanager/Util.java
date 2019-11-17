package passmanager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class Util {

	/**
	 * Convenient way to get a new or existing logger
	 * @param 	rc			runtime class associated with the logger
	 * @return	Logger
	 */
	public static Logger getLogger(Class<?> rc) {
		return Logger.getLogger(rc.getName());
	}
	
	/**
	 * Calculates a sha256 sum of the given bytes. Returns null on failure.
	 * @param input			array of bytes
	 * @return byte[]
	 */
	public static byte[] sha256(byte[] input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(input);
			
			return md.digest();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Useful for testing.
	 * Converts a byte array to a string of hexadecimal digits.
	 * @param bytes
	 * @return String
	 */
	public static String byte2hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%x", b));
		}
		
		return sb.toString();
	}

}
