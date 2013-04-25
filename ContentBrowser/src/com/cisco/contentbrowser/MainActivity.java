package com.cisco.contentbrowser;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.cisco.logcommon.LogContract;

public class MainActivity extends Activity {
	private static final Uri URI = LogContract.CONTENT_URI;
	private static final String[] FROM = { LogContract.Columns.TAG,
			LogContract.Columns.TEXT };
	private static final int[] TO = { android.R.id.text1, android.R.id.text2 };
	private static final int LOADER_ID = 42;
	private ListView listView;
	private EditText input;
	private Cursor cursor;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		input = (EditText) findViewById(R.id.input);
		listView = (ListView) findViewById(R.id.list);

		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, FROM, TO, 0);

		listView.setAdapter(adapter);
		getLoaderManager().initLoader(LOADER_ID, null, loaderCallback);
	}

	LoaderManager.LoaderCallbacks<Cursor> loaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			if (id != LOADER_ID)
				return null;
			String selection = (args == null) ? null : args
					.getString("selection");
			String[] selectionArgs = (args == null) ? null : args
					.getStringArray("selectionArgs");
			Log.d("ContentBrowser", "onCreateLoader selection: "+selection);
			return new CursorLoader(MainActivity.this, URI, null, selection,
					selectionArgs, null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			adapter.swapCursor(cursor);
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			adapter.swapCursor(null);
		}
	};

	/** Bound to the go button. */
	public void onSearch(View v) {
		String selection;
		String[] selectionArgs;

		String query = input.getText().toString();
		if (TextUtils.isEmpty(query)) {
			selection = null;
			selectionArgs = null;
		} else {
			selection = " (" + LogContract.Columns.TAG + " like ?) or ("
					+ LogContract.Columns.TEXT + " like ?)";
			selectionArgs = new String[2];
			selectionArgs[0] = "%" + query + "%";
			selectionArgs[1] = "%" + query + "%";
		}

		Log.d("ContentBrowser", "onSearch with selection: " + selection);
		Bundle bundle = new Bundle();
		bundle.putString("selection", selection);
		bundle.putStringArray("selectionArgs", selectionArgs);
		getLoaderManager().restartLoader(LOADER_ID, bundle, loaderCallback);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
