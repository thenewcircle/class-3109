package com.cisco.contentbrowser.test;

import junit.framework.Assert;
import android.content.ContentValues;
import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cisco.contentbrowser.MainActivity;
import com.cisco.contentbrowser.R;
import com.cisco.logcommon.LogContract;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity activity;
	private EditText input;
	private Button buttonGo;
	private ListView listView;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.activity = (MainActivity) getActivity();
		this.input = (EditText) activity.findViewById( R.id.input);
		this.buttonGo = (Button) activity.findViewById(R.id.buttonGo);
		this.listView = (ListView) activity.findViewById(R.id.list);
	}

	public void testViewsExist() {
		Assert.assertNotNull(activity);
		Assert.assertNotNull(buttonGo);
		Assert.assertNotNull(input);
		Assert.assertNotNull(listView);
	}
	
	public void testAddingLogMessages() throws Exception {
		// How many do we have to begin with?
		int countPre = listView.getCount();
		
		// Log three new messages
		ContentValues values = new ContentValues();
		values.put(LogContract.Columns.TAG, "TESTER");
		values.put(LogContract.Columns.TEXT, "One");
		activity.getContentResolver().insert(LogContract.CONTENT_URI, values);
		super.getInstrumentation().waitForIdleSync();

		values.put(LogContract.Columns.TEXT, "Two");
		activity.getContentResolver().insert(LogContract.CONTENT_URI, values);
		super.getInstrumentation().waitForIdleSync();

		values.put(LogContract.Columns.TEXT, "Three");
		activity.getContentResolver().insert(LogContract.CONTENT_URI, values);
		super.getInstrumentation().waitForIdleSync();

		// Restart the activity
		this.activity.finish();
		this.setUp();
		
		// Assert we have three more than we had before
		int countPost = listView.getCount();
		Assert.assertEquals(countPre+3, countPost);
	}
	
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

}
