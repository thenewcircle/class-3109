/*
 * FibNative.c
 *
 *  Created on: Apr 22, 2013
 *      Author: marko
 */
#include "com_cisco_fibnative_FibLib.h"

/* Pure C implementation */
long fib(long n) {
	if(n==0) return 0;
	if(n==1) return 1;
	return fib(n-1)+fib(n-2);
}

/* JNI Wrapper */
JNIEXPORT jlong JNICALL Java_com_cisco_fibnative_FibLib_fibN
  (JNIEnv *env, jclass clazz, jlong n) {
	return fib(n);
}



