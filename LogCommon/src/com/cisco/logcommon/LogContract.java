package com.cisco.logcommon;

import android.net.Uri;
import android.provider.BaseColumns;

public final class LogContract {
	private LogContract() {};
	
	public static final String DB_NAME = "log.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "messages";
	
	public static final String AUTHORITY = "com.cisco.provider.log";
	public static final String PATH = TABLE;
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+PATH);
	public static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/vnd.cisco.log.message";
	public static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/vnd.cisco.log.message";
	
	public class Columns {
		public static final String ID = BaseColumns._ID;
		public static final String PRIORITY = "log_priority";
		public static final String TAG = "log_tag";
		public static final String TEXT = "log_text";
	}
}
