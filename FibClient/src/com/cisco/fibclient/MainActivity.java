package com.cisco.fibclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cisco.fibcommon.FibManager;
import com.cisco.fibcommon.FibRequest;
import com.cisco.fibcommon.IFibListener;

public class MainActivity extends Activity {
	private EditText input;
	private TextView output;
	private FibManager fibManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);

		fibManager = new FibManager(this);
	}

	long start;

	/** Called when the button gets clicked. */
	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		try {
			// Java
			start = System.currentTimeMillis();
			fibManager.asyncFib( new FibRequest(FibRequest.ALGORITHM_JAVA,n), listener );

			// Native
			start = System.currentTimeMillis();
			long resultN = fibManager.fib( new FibRequest(FibRequest.ALGORITHM_NATIVE,n) );
			long timeN = System.currentTimeMillis() - start;
			output.append(String.format("\n fibN(%d) = %d (%d ms)", n, resultN,
					timeN));
		} catch (Exception e) {
			Log.e("FibNative", "Died", e);
			e.printStackTrace();
		}
	}
	
	private static final int WHAT = 42;
	// On the UI thread
	private final Handler handler = new Handler() {
		 @Override
		public void handleMessage(Message msg) {
			 if(msg.what != WHAT) return;
			 long time = System.currentTimeMillis() - start;
			 long result = (Long)msg.obj;
			 output.append(String.format("\n fibJ() = %d (%d ms)", result, time));
		}
	};

	// Executes on service binder thread - a non-UI thread
	private final IFibListener listener = new IFibListener.Stub() {

		@Override
		public void onResponse(long result) throws RemoteException {
			Message msg = handler.obtainMessage(WHAT);
			msg.obj = result;
			handler.sendMessage(msg);
		}
	};
}
