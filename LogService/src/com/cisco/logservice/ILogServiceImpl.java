package com.cisco.logservice;

import android.os.RemoteException;
import com.cisco.logcommon.ILogService;

public class ILogServiceImpl extends ILogService.Stub {

	@Override
	public void log(int priority, String tag, String text) throws RemoteException {
		LogLib.logN(priority, tag, text);
	}

}
