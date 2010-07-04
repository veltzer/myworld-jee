package org.meta.counter;

import java.io.PrintStream;

/**
 * This class implements a counter using a regular integer
 * @author mark
 */
public class IntCounter implements Counter {
	/**
	 *
	 */
	protected int val;

	/**
	 *
	 */
	@Override
	public void zero() {
		val=0;
	}

	/**
	 *
	 */
	@Override
	public void add() {
		val++;
	}

	/**
	 *
	 * @param s
	 */
	@Override
	public void print(PrintStream s) {
		s.print(val);
	}
}