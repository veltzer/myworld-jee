package org.meta.richfunction.impl;

import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a very basic echo command.
 * @author mark
 */
public class Help extends RichFunction {

	/**
	 *
	 */
	public Help() {
		super();
		addName("help");
		setDescription("show help in the console");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("help", false, false, false);
		p.println("Help is not implemented yet...");
		p.fini();
	}
}