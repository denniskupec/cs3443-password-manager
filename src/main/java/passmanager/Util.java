package passmanager;

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

}
