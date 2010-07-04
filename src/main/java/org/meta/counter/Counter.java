package org.meta.counter;

/**
 * This is the interface for a counter which you can set to zero and add
 * to.
 */

import java.io.PrintStream;

/**
 *
 * @author mark
 */
public interface Counter {
	/**
	 *
	 */
	public void zero();
	/**
	 *
	 */
	public void add();
	/**
	 *
	 * @param s
	 */
	public void print(PrintStream s);
}