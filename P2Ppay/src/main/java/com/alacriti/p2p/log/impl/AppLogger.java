package com.alacriti.p2p.log.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.alacriti.p2p.log.IAppLogger;
import com.alacriti.p2p.util.StringUtil;

public class AppLogger implements IAppLogger {
	private final String FQCN = AppLogger.class.getName() + ".";

	public Logger m_Logger = null;

	public AppLogger(Class logUserClass) {
		init(logUserClass);
	}
	
	protected Logger getLogger(Class logUserClass) {
		return Logger.getLogger(logUserClass);
	}

	public void init(Class logUserClass) {
		m_Logger = getLogger(logUserClass);
	}

	public void logTrace(Object message) {
		if (m_Logger.isTraceEnabled()) {
			m_Logger.log(FQCN, Level.TRACE, message, null);
		}
	}

	public void logTrace(Object message, Throwable throwable) {
		if (m_Logger.isTraceEnabled()) {
			m_Logger.log(FQCN, Level.TRACE, message, throwable);
		}
	}

	public void logDebug(Object message) {
		if (m_Logger.isDebugEnabled()) {
			m_Logger.log(FQCN, Level.DEBUG, message, null);
		}

	}

	public void debugPrintCurrentMethodName() {
		String message = "In " + Thread.currentThread().getStackTrace()[2].getMethodName();
		if (m_Logger.isDebugEnabled()) {
			m_Logger.log(FQCN, Level.INFO, message, null);
		}

	}

	public void errorPrintCurrentMethodName() {
		String message = "In " + Thread.currentThread().getStackTrace()[2].getMethodName();
		if (m_Logger.isDebugEnabled()) {
			m_Logger.log(FQCN, Level.ERROR, message, null);
		}

	}

	public void errorPrintWithCurrentMethodName(Object... messages) {
		String message = "In " + Thread.currentThread().getStackTrace()[2].getMethodName() + "::"
				+ StringUtil.appendStrings(messages);
		if (m_Logger.isDebugEnabled()) {
			m_Logger.log(FQCN, Level.ERROR, message, null);
		}

	}

	public void logDebug(Object... messages) {
		if (m_Logger.isDebugEnabled()) {
			m_Logger.log(FQCN, Level.DEBUG, StringUtil.appendStrings(messages), null);
		}
	}

	protected String appendMessages(Object... messages) {
		return StringUtil.appendStrings(messages);

	}

	public void logDebug(Object message, Throwable throwable) {
		if (m_Logger.isDebugEnabled()) {
			m_Logger.log(FQCN, Level.DEBUG, message, throwable);
		}
	}

	public void logError(Object message) {
		if (m_Logger.isEnabledFor(Level.ERROR)) {
			m_Logger.log(FQCN, Level.ERROR, message, null);
		}
	}

	public void logError(Object... messages) {
		if (m_Logger.isDebugEnabled()) {
			m_Logger.log(FQCN, Level.ERROR, StringUtil.appendStrings(messages), null);
		}
	}

	public void logError(Object message, Throwable throwable) {
		if (m_Logger.isEnabledFor(Level.ERROR)) {
			m_Logger.log(FQCN, Level.ERROR, message, throwable);
		}
	}

	public void logInfo(Object message) {
		if (m_Logger.isInfoEnabled()) {
			m_Logger.log(FQCN, Level.INFO, message, null);
		}
	}

	public void logInfo(Object message, Throwable throwable) {
		if (m_Logger.isInfoEnabled()) {
			m_Logger.log(FQCN, Level.INFO, message, throwable);
		}
	}

	public void logWarn(Object message) {
		if (m_Logger.isEnabledFor(Level.WARN)) {
			m_Logger.log(FQCN, Level.WARN, message, null);
		}
	}

	public void logWarn(Object message, Throwable throwable) {
		if (m_Logger.isEnabledFor(Level.WARN)) {
			m_Logger.log(FQCN, Level.WARN, message, throwable);
		}
	}

	public boolean isDebugEnabled() {
		return m_Logger.isDebugEnabled();
	}

	public boolean isInfoEnabled() {
		return m_Logger.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return m_Logger.isTraceEnabled();
	}

}
