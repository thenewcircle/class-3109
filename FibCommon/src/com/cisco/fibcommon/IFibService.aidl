package com.cisco.fibcommon;

import com.cisco.fibcommon.FibRequest;

interface IFibService {
	long fibJ(long n);
	long fibN(long n);
	long fib(in FibRequest request);
}