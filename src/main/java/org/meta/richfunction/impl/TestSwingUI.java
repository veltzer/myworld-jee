package org.meta.richfunction.impl;

import org.meta.util.Utils;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a function made for testing purposes
 * It tests whether or not and how can we do SwingUI stuff from these
 * Rich functions
 * @author mark
 */
public class TestSwingUI extends RichFunction {

	/**
	 *
	 */
	public TestSwingUI() {
		super();
		setDescription("test whether doing swing UI stuff works");
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