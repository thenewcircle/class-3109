package com.cisco.logcommon;

interface ILogService {
	void logN(int priority, String tag, String text);
	void logJ(int priority, String tag, String text);
}