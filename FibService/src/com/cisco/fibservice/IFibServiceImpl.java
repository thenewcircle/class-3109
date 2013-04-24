package com.cisco.fibservice;

import android.os.RemoteException;

import com.cisco.fibcommon.FibRequest;
import com.cisco.fibcommon.IFibService;

public class IFibServiceImpl extends IFibService.Stub {

	@Override
	public long fibJ(long n) throws RemoteException {
		return FibLib.fibJ(n);
	}

	@Override
	public long fibN(long n) throws RemoteException {
		return FibLib.fibN(n);
	}

	@Override
	public long fib(FibRequest request) throws RemoteException {
		if( request.getAlgorithm()==FibRequest.ALGORITHM_JAVA)
			return fibJ(request.getN());
		else if( request.getAlgorithm()==FibRequest.ALGORITHM_NATIVE)
			return fibN(request.getN());
		else
			return -1;
	}

}
