package com.cisco.contentbrowser;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.cisco.logcommon.LogContract;

public class MainActivity extends Activity {
	private static final Uri URI = LogContract.CONTENT_URI;
	private static final String[] FROM = { LogContract.Columns.TAG,
			LogContract.Columns.TEXT };
	private static final int[] TO = { android.R.id.text1, android.R.id.text2 };
	private ListView listView;
	private Cursor cursor;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.list);

		cursor = getContentResolver().query(URI, null,
				null, null, null);

		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, FROM, TO, 0);
		
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
