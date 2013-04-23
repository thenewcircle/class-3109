package com.cisco.fibclientlocalservice;

import android.os.Binder;
import android.util.Log;

public class IFibServiceImpl extends Binder {
	private static final String TAG = "IFibServiceImpl";

	public long fibJ(long n)  {
		Log.d(TAG, "fibJ called");
		return FibLib.fibJ(n);
	}

	public long fibN(long n) {
		Log.d(TAG, "fibN called");
		return FibLib.fibN(n);
	}
	
}
