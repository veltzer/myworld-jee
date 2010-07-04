package org.meta.counter;

import junit.framework.TestCase;

/**
 *
 * @author mark
 */
public class BufferCounterTest extends TestCase {
	/**
	 *
	 */
	public void testZero() {
		//Counter c=new BufferCounter();
		Counter c=new IntCounter();
		c.zero();
		for(int i=0;i<10000;i++) {
			c.print(System.out);
			System.out.println();
			c.add();
		}
	}
}
