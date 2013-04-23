package com.cisco.fibclientlocalservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText input;
	private TextView output;
	private FibService fibService;
	private IFibServiceImpl fibServiceImpl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);
		
		bindService( new Intent(this, FibService.class), new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName name, IBinder binder) {
				fibServiceImpl = (IFibServiceImpl)binder;
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				fibServiceImpl = null;
			}
			
		}, Context.BIND_AUTO_CREATE);
	}

	/** Called when the button gets clicked. */
	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		Log.d("FibClientLocalService", "onClick called");

		try {
			// Java
			long start = System.currentTimeMillis();
			long resultJ = fibServiceImpl.fibJ(n);
			long timeJ = System.currentTimeMillis() - start;
			output.append(String.format("\n fibJ(%d) = %d (%d ms)", n, resultJ,
					timeJ));

			// Native
			start = System.currentTimeMillis();
			long resultN = fibServiceImpl.fibN(n);
			long timeN = System.currentTimeMillis() - start;
			output.append(String.format("\n fibN(%d) = %d (%d ms)", n, resultN,
					timeN));
		} catch (Exception e) {
			Log.e("FibNative", "Died", e);
			e.printStackTrace();
		}
	}

}
