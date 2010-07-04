package org.meta.timing;

import junit.framework.TestCase;

/**
 * @author mark
 */
public class StopWatchTest extends TestCase {
	/**
	 *
	 */
	public void testIt() {
		StopWatch sw=new StopWatch();
		sw.start();
		for(long i=0;i<100000;i++) {
		}
		sw.stop();
		long res=sw.getElapsedTime();
		System.out.println("res is "+res);
		assertTrue("time should be positive",res>0);
	}
}