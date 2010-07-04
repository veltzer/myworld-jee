package org.meta.richfunction.impl;

import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a function made for testing purposes
 * It emulates a piece of code that throws an exception and forgets to report on
 * completion.
 * @author mark
 */
public class TestException extends RichFunction {

	/**
	 *
	 */
	public TestException() {
		super();
		setDescription("throw an exception");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("testswingui", true, true, true);
		// The if statement is to fool the compiler
		// so he wont bark at the fini statement 
		// afterwards as being unreachable...
		if(true) {
			throw new RuntimeException("This is an exception");
		}
		p.fini();
	}
}