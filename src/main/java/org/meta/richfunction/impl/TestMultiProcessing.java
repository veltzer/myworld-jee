package org.meta.richfunction.impl;

import org.meta.util.Utils;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a function made for testing purposes
 * It checks that a RichFunction can "exec" another process and that the
 * processes input and output in handled correctly from within the console.
 * @author mark
 */
public class TestMultiProcessing extends RichFunction {

	/**
	 *
	 */
	public TestMultiProcessing() {
		super();
		setDescription("test if running a sub process works");
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