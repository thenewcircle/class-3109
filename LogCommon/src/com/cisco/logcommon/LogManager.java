package com.cisco.logcommon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class LogManager {
	public static final Intent FIB_SERVICE_ACTION = new Intent(
			"com.cisco.logservice.action.LOG_SERVICE");
	private Context context;
	private static ILogService logService;

	public LogManager(Context context) {
		super();
		this.context = context;
		
		context.bindService(FIB_SERVICE_ACTION, CONNECTION, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.unbindService(CONNECTION);
	}
	
	private static final ServiceConnection CONNECTION = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			logService = ILogService.Stub.asInterface(binder);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			logService = null;			
		}	
	};
	
	// --- Proxy Methods ---
	
	public void logJ(int priority, String tag, String text) {
		if(logService==null) return;
		try {
			logService.logJ(priority, tag, text);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public void logN(int priority, String tag, String text) {
		if(logService==null) return;
		try {
			logService.logN(priority, tag, text);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
