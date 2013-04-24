package com.cisco.logservice;

import android.os.RemoteException;
import com.cisco.logcommon.ILogService;

public class ILogServiceImpl extends ILogService.Stub {

	@Override
	public void logJ(int priority, String tag, String text) throws RemoteException {
		LogLib.logJ(priority, tag, text);		
	}

	@Override
	public void logN(int priority, String tag, String text) throws RemoteException {
		LogLib.logN(priority, tag, text);		
	}

}
