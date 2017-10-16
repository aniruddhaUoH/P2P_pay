package com.alacriti.p2p.log;

import java.io.Serializable;

public interface IAppLogger extends Serializable {
	public void init(Class logUserClass);

	public void logTrace(Object message);

	public void logTrace(Object message, Throwable throwable);

	public void logDebug(Object message);

	public void debugPrintCurrentMethodName();

	public void errorPrintCurrentMethodName();

	public void errorPrintWithCurrentMethodName(Object... messages);

	public void logDebug(Object... message);

	public void logDebug(Object message, Throwable throwable);

	public void logError(Object message);

	public void logError(Object... message);

	public void logError(Object message, Throwable throwable);

	public void logInfo(Object message);

	public void logInfo(Object message, Throwable throwable);

	public void logWarn(Object message);

	public void logWarn(Object message, Throwable throwable);

	public boolean isDebugEnabled();

	public boolean isInfoEnabled();

	public boolean isTraceEnabled();
}
