package com.cisco.fibclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cisco.fibcommon.FibManager;
import com.cisco.fibcommon.FibRequest;

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

	/** Called when the button gets clicked. */
	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		try {
			// Java
			long start = System.currentTimeMillis();
			long resultJ = fibManager.fib( new FibRequest(FibRequest.ALGORITHM_JAVA,n) );
			long timeJ = System.currentTimeMillis() - start;
			output.append(String.format("\n fibJ(%d) = %d (%d ms)", n, resultJ,
					timeJ));

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

}
