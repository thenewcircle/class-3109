package com.cisco.fibservice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;

import com.cisco.fibcommon.FibRequest;
import com.cisco.fibcommon.IFibListener;
import com.cisco.fibcommon.IFibService;

public class IFibServiceImpl extends IFibService.Stub {
	private Context context;

	public IFibServiceImpl(Context context) {
		super();
		this.context = context;
	}

	private static final int N_THRESHOLD = 35;

	/** Permission check. */
	private void checkPermissions(long n) {
		if (n > N_THRESHOLD
				&& context
						.checkCallingOrSelfPermission("com.cisco.permission.FIB_SERVICE_HIGH_NUMBERS") != PackageManager.PERMISSION_GRANTED) {
			throw new SecurityException("Not allowed for value of n: " + n);
		}
	}

	@Override
	public long fibJ(long n) throws RemoteException {
		checkPermissions(n);
		return FibLib.fibJ(n);
	}

	@Override
	public long fibN(long n) throws RemoteException {
		checkPermissions(n);
		return FibLib.fibN(n);
	}

	@Override
	public long fib(FibRequest request) throws RemoteException {
		if (request.getAlgorithm() == FibRequest.ALGORITHM_JAVA)
			return fibJ(request.getN());
		else if (request.getAlgorithm() == FibRequest.ALGORITHM_NATIVE)
			return fibN(request.getN());
		else
			return -1;
	}

	@Override
	public void asyncFib(FibRequest request, IFibListener listener)
			throws RemoteException {
		long n = fib(request);
		listener.onResponse(n);
	}

}
