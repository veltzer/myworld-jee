package org.meta.wrappers.java.lang;

/**
 * This is a wrapper for the basic java thread class which does not throw
 * exceptions for fun.
 *
 * @author mark
 */
public class Thread extends java.lang.Thread {
	/**
	 *
	 * @param r
	 */
	public Thread(Runnable r) {
		super(r);
	}
	/**
	 * join is final so I had to call mine myJoin...
	 */
	public void myJoin() {
		try {
			join();
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * This is a better sleep method which throws runtime exception.
	 * @param millis
	 */
	public static void sleep(long millis) {
		try {
			java.lang.Thread.sleep(millis);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 * Another sleep function with two long arguments
	 *
	 * @param millis
	 * @param nanos
	 */
	public static void sleep(long millis, int nanos) {
		try {
			java.lang.Thread.sleep(millis,nanos);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
}