package com.cisco.contentbrowser;

import android.app.Activity;
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

		cursor = getContentResolver().query(URI, null, null, null, null);

		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, FROM, TO, 0);

		listView.setAdapter(adapter);
	}

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
			selectionArgs[0] = "%"+query+"%";
			selectionArgs[1] = "%"+query+"%";
		}

		Log.d("ContentBrowser", "onSearch with selection: " + selection);
		cursor = getContentResolver().query(URI, null, selection,
				selectionArgs, null);
		adapter.swapCursor(cursor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
