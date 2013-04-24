package com.cisco.fibcommon;

import com.cisco.fibcommon.FibRequest;
import com.cisco.fibcommon.IFibListener;

interface IFibService {
	long fibJ(long n);
	long fibN(long n);
	long fib(in FibRequest request);
	oneway void asyncFib(in FibRequest request, in IFibListener listener );
}