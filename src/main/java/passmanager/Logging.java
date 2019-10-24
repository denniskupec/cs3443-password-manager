package passmanager;

import java.util.logging.Logger;

public interface Logging {
	
	/**
	 * Returns an instance of Logger to be used.
	 * @return Logger
	 */
	default public Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}

}
