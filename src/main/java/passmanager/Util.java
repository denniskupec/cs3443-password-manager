package passmanager;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.io.File;
import java.io.IOException;
>>>>>>> develop
=======
import java.io.File;
import java.io.IOException;
>>>>>>> develop
=======
import java.io.File;
import java.io.IOException;
>>>>>>> develop
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javafx.scene.Node;
<<<<<<< HEAD
=======
import javafx.scene.control.TextInputControl;
>>>>>>> develop

/**
 * Utility functions for convenience.
 */
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
	
	/**
	 * Configuration and database files are stored in the user home directory.
	 * (e.g. ~/.config/CS3443-passmanager)
	 * @return Path - Absolute path to the configuration directory
	 */
	public static Path getStoragePath() {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		return Paths.get(System.getProperty("user.home"), ".config", "CS3442-passmanager");
=======
=======
>>>>>>> develop
=======
>>>>>>> develop
		Path p = Paths.get(Paths.get(System.getProperty("user.home"), ".config", "CS3442-passmanager").toString());
		File pf = new File(p.toString());
		
		if (pf.mkdirs()) {
			Util.getLogger(Util.class).info("Created data directories.");
		}
			
		return p;
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
=======
>>>>>>> develop
	}
	
	/**
	 * Returns the path to a specific file in the storage directory.
	 * @param String - name of the file
	 * @return Path - Absolute path to the file in the configuration directory.
	 */
	public static Path getStoragePath(String filename)  {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		return Paths.get(Util.getStoragePath().toString(), filename);
=======
=======
>>>>>>> develop
=======
>>>>>>> develop
		Path p = Paths.get(Util.getStoragePath().toString(), filename);
		File pf = new File(p.toString());
		
		try {
			pf.createNewFile();
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
			
		return p;
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
	}

	/**
	 * Get the path of a resource file. 
	 * @param String name 		name of the resource file requested
	 * @return Path - Absolute path to application resource(s)
	 * @throws URISyntaxException
	 */
	public static Path getResourcePath(String name) {
		try {
			return Paths.get( App.class.getResource(name).toURI() );
		} 
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Formats a Date object into a readable string.
	 * @param Date date
	 * @return String
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		return sdf.format(date);
	}
<<<<<<< HEAD
=======
	
	/**
	 * Formats a Date object into a readable string, with optional specific format argument.
	 * @param Date date
	 * @param String formatString
	 * @return String
	 */
	public static String formatDate(Date date, String formatString) {
		if (formatString.isEmpty()) {
			return Util.formatDate(date);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		return sdf.format(date);
	}
	
	/**
	 * Takes a variable number of Node arguments, and sets their disabled status.
	 * @param boolean status
	 * @param Node nodes
	 */
	public static void setDisabled(boolean status, Node ...nodes) {
		for (Node n : nodes) {
			n.setDisable(status);
		}
	}
	
	/**
	 * Takes a variable number of Node arguments, and sets their visibility status.
	 * @param boolean status
	 * @param Node nodes
	 */
	public static void setVisible(boolean status, Node ...nodes) {
		for (Node n : nodes) {
			n.setVisible(status);
		}
	}
>>>>>>> develop
=======
	}
>>>>>>> develop

	/**
	 * Get the path of a resource file. 
	 * @param String name 		name of the resource file requested
	 * @return Path - Absolute path to application resource(s)
	 * @throws URISyntaxException
	 */
	public static Path getResourcePath(String name) {
		try {
			return Paths.get( App.class.getResource(name).toURI() );
		} 
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Formats a Date object into a readable string.
	 * @param Date date
	 * @return String
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		return sdf.format(date);
	}
	
	/**
	 * Formats a Date object into a readable string, with optional specific format argument.
	 * @param Date date
	 * @param String formatString
	 * @return String
	 */
	public static String formatDate(Date date, String formatString) {
		if (formatString.isEmpty()) {
			return Util.formatDate(date);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		return sdf.format(date);
	}
	
	/**
	 * Takes a variable number of Node arguments, and sets their disabled status.
	 * @param boolean status
	 * @param Node... nodes
	 */
	public static void setDisabled(boolean status, Node...nodes) {
		for (Node n : nodes) {
			n.setDisable(status);
		}
	}
	
	/**
	 * Takes a variable number of Node arguments, and sets their visibility status.
	 * @param boolean status
	 * @param Node... nodes
	 */
	public static void setVisible(boolean status, Node...nodes) {
		for (Node n : nodes) {
			n.setVisible(status);
		}
	}
	
	/**
	 * Takes a variable number of TextInputControl arguments, and clears their contents.
	 * @param TextInputControl... nodes
	 */
	public static void clearText(TextInputControl...nodes) {
		for (TextInputControl n : nodes) {
			n.clear();
		}
	}
}
