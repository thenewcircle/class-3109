package com.cisco.fibnative;

public class FibLib {
	
	/** Java version */
	public static long fibJ(long n) {
		assert(n>=0);
		if(n==0) return 0;
		if(n==1) return 1;
		return fibJ(n-1) + fibJ(n-2);
	}
	
	/** Native version */
	static {
		System.loadLibrary("FibNative");
	}
	public static native long fibN(long n);

}
