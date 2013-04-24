package com.cisco.logservice;

import android.os.RemoteException;
import com.cisco.logcommon.ILogService;
import com.cisco.logcommon.LogMessage;

public class ILogServiceImpl extends ILogService.Stub {

	@Override
	public void logJ(int priority, String tag, String text) throws RemoteException {
		LogLib.logJ(priority, tag, text);		
	}

	@Override
	public void logN(int priority, String tag, String text) throws RemoteException {
		LogLib.logN(priority, tag, text);		
	}

	@Override
	public void log(LogMessage msg) throws RemoteException {
		LogLib.logJ(msg.getLevel(), msg.getTag(), msg.getText());
	}

}
