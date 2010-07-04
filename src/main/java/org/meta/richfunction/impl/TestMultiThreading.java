package org.meta.richfunction.impl;

import org.meta.util.Utils;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a function made for testing purposes
 * It test that RichFunctions can indeed spawn threads and that the child
 * threads can be long, do UI stuff, do multi processing and multi threading.
 * @author mark
 */
public class TestMultiThreading extends RichFunction {

	/**
	 *
	 */
	public TestMultiThreading() {
		super();
		setDescription("test whether multi threading works");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		for(int i=0;i<20;i++) {
			Utils.sleep(1000);
			System.out.println("i is "+i);
		}
	}
}