package com.framework.logger;

import org.apache.log4j.Logger;

public class LoggerUtil {
	static Logger logger = Logger.getLogger(LoggerUtil.class.getName());

	public static void logInfo(String msg) {
		logger.info(msg);
	}

	public static void logError(String msg) {
		logger.error(msg);
	}
}
