package com.cisco.fibclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cisco.fibcommon.IFibService;

public class MainActivity extends Activity {
	private EditText input;
	private TextView output;
	private static IFibService fibService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);
	}

	private static final Intent FIB_SERVICE_ACTION = new Intent(
			"com.cisco.fibservice.action.FIB_SERVICE");

	private static final ServiceConnection CONNECTION = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			fibService = IFibService.Stub.asInterface(binder);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			fibService = null;
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		// Bind to the service
		bindService(FIB_SERVICE_ACTION, CONNECTION, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Unbind from the service
		unbindService(CONNECTION);
	}

	/** Called when the button gets clicked. */
	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		try {
			// Java
			long start = System.currentTimeMillis();
			long resultJ = fibService.fibJ(n);
			long timeJ = System.currentTimeMillis() - start;
			output.append(String.format("\n fibJ(%d) = %d (%d ms)", n, resultJ,
					timeJ));

			// Native
			start = System.currentTimeMillis();
			long resultN = fibService.fibN(n);
			long timeN = System.currentTimeMillis() - start;
			output.append(String.format("\n fibN(%d) = %d (%d ms)", n, resultN,
					timeN));
		} catch (RemoteException re) {
			Log.e("FibNative", "Died", re);
			re.printStackTrace();
		} catch (Exception e) {
			Log.e("FibNative", "Died", e);
			e.printStackTrace();
		}
	}

}
