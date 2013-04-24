package com.cisco.logdata;

import android.provider.BaseColumns;

public final class LogContract {
	private LogContract() {};
	
	public static final String DB_NAME = "log.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "messages";
	
	class Columns {
		public static final String ID = BaseColumns._ID;
		public static final String PRIORITY = "log_priority";
		public static final String TAG = "log_tag";
		public static final String TEXT = "log_text";
	}
}
