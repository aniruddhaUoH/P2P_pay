package com.alacriti.p2p.util;

import com.alacriti.p2p.log.impl.AppLogger;

public class LogUtil {
	public static AppLogger getLogger(Class<?> clazz) {
		return new AppLogger(clazz);
	}

	public static String getCurrentMethodName() {
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}

}
