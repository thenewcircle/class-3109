package com.cisco.fibcommon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class FibManager {
	private Context context;
	private static IFibService fibService;

	public FibManager(Context context) {
		super();
		this.context = context;
		// Bind to the service
		context.bindService(FIB_SERVICE_ACTION, CONNECTION, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void finalize() {
		// Unbind from the service
		context.unbindService(CONNECTION);
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

	
	// --- Proxy Calls ---
	
	public long fibJ(long n) {
		if(fibService==null) return -1;
		try {
			return fibService.fibJ(n);
		} catch (RemoteException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public long fibN(long n) {
		if(fibService==null) return -1;
		try {
			return fibService.fibN(n);
		} catch (RemoteException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public long fib(FibRequest request) {
		if(fibService==null) return -1;
		try {
			return fibService.fib(request);
		} catch (RemoteException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void asyncFib(FibRequest request, IFibListener listener) {
		if(fibService==null) return;
		try {
			fibService.asyncFib(request, listener);
		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		}
	}
}
