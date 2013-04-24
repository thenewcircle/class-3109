package com.cisco.logcommon;

import com.cisco.logcommon.LogMessage;

interface ILogService {
	void logN(int priority, String tag, String text);
	void logJ(int priority, String tag, String text);
	void log( in LogMessage message );
}