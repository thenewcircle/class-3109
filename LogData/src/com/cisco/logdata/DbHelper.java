package com.cisco.logdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context) {
		super(context, LogContract.DB_NAME, null, LogContract.DB_VERSION);
	}

	/** Called when we don't have the db at all. */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format(
				"create table %s ( %s int primary key autoincrement, "
						+ "%s int, %s text, %s text)", LogContract.TABLE,
				LogContract.Columns.ID, LogContract.Columns.PRIORITY,
				LogContract.Columns.TAG, LogContract.Columns.TEXT);
		Log.d("DbHelper", "onCreate with sql: "+sql);
		db.execSQL(sql);
	}

	/** Called when the oldVersion of db != newVersion. */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// For development purposes, typically you'd do ALTER TABLE in here
		db.execSQL("drop table if exists "+LogContract.TABLE);
		this.onCreate(db);
	}

}
