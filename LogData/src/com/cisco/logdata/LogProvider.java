package com.cisco.logdata;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.cisco.logcommon.LogContract;

public class LogProvider extends ContentProvider {
	private static final String TAG = "LogProvider";
	private DbHelper dbHelper;

	private static final int MESSAGE_DIR = 1;
	private static final int MESSAGE_ITEM = 2;
	private static final UriMatcher MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		MATCHER.addURI(LogContract.AUTHORITY, LogContract.PATH, MESSAGE_DIR);
		MATCHER.addURI(LogContract.AUTHORITY, LogContract.PATH + "/#",
				MESSAGE_ITEM);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return (dbHelper == null) ? false : true;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		// Check that we have a valid uri
		if (MATCHER.match(uri) != MESSAGE_DIR)
			throw new IllegalArgumentException("Invalid URI: " + uri);

		// Insert into DB
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		long id = db.insert(LogContract.TABLE, null, values);
		Uri ret = (id == -1) ? null : ContentUris.withAppendedId(uri, id);

		Log.d(TAG, "inserted uri: " + ret);
		return ret;
	}

	// update messages set message='updated!' where tag = 'Cisco';
	// selection: "where tag = ?"
	// args: Cisco

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String where;

		switch (MATCHER.match(uri)) {
		case MESSAGE_DIR:
			where = selection;
			break;
		case MESSAGE_ITEM:
			String id = uri.getLastPathSegment();
			where = String.format("where %s=%s", LogContract.Columns.ID, id);
			if (!TextUtils.isEmpty(selection)) {
				where = String.format("%s AND ( %s )", where, selection);
			}
		default:
			throw new IllegalArgumentException("Invalid URI: " + uri);
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rows = db.update(LogContract.TABLE, values, where, selectionArgs);

		return rows;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String where;

		switch (MATCHER.match(uri)) {
		case MESSAGE_DIR:
			where = selection;
			break;
		case MESSAGE_ITEM:
			String id = uri.getLastPathSegment();
			where = String.format("where %s=%s", LogContract.Columns.ID, id);
			if (!TextUtils.isEmpty(selection)) {
				where = String.format("%s AND ( %s )", where, selection);
			}
		default:
			throw new IllegalArgumentException("Invalid URI: " + uri);
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rows = db.delete(LogContract.TABLE, where, selectionArgs);

		return rows;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

}
