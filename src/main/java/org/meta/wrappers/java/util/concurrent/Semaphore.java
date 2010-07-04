package org.meta.wrappers.java.util.concurrent;

/**
 * A wrapper of the Semaphore class to use runtime exceptions
 * @author mark
 */
public class Semaphore extends java.util.concurrent.Semaphore {

	/**
	 *
	 * @param permits
	 */
	public Semaphore(int permits) {
		super(permits);
	}
	@Override
	public void acquire() {
		try {
			super.acquire();
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void acquire(int permits) {
		try {
			super.acquire(permits);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
}