package com.cisco.logdata;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
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
		switch (MATCHER.match(uri)) {
		case MESSAGE_DIR:
			return LogContract.MIME_TYPE_DIR;
		case MESSAGE_ITEM:
			return LogContract.MIME_TYPE_ITEM;
		default:
			throw new IllegalArgumentException("Invalid URI: " + uri);
		}
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

		// Notify of change
		getContext().getContentResolver().notifyChange(ret, null);
		return ret;
	}

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

		// Notify of change
		getContext().getContentResolver().notifyChange(uri, null);
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

		// Notify of change
		getContext().getContentResolver().notifyChange(uri, null);
		return rows;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(LogContract.TABLE);

		switch (MATCHER.match(uri)) {
		case MESSAGE_DIR:
			break;
		case MESSAGE_ITEM:
			builder.appendWhere(LogContract.Columns.ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Invalid URI: " + uri);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = builder.query(db, projection, selection, selectionArgs,
				null, null, sortOrder);

		Log.d(TAG,
				"query with coursor length: "
						+ ((cursor != null) ? cursor.getCount() : "null"));

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

}
