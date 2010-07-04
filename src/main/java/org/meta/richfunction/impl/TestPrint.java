package org.meta.richfunction.impl;

import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a function made for testing purposes
 * It runs many prints which are long...
 * @author mark
 */
public class TestPrint extends RichFunction {

	/**
	 *
	 */
	public TestPrint() {
		super();
		addName("testprint");
		setDescription("test printing via System.out");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("testprint", false, false, false);
		for(int i=0;i<10000;i++) {
			System.out.println("this is very very very very very very very very very very very veyr very very very very very very long message"+i);
		}
		p.fini();
	}
}