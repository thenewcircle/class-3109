package com.cisco.logcommon;

interface ILogService {
	void log(int priority, String tag, String text);
}