package org.meta.richfunction.impl;

import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a very basic echo command.
 * @author mark
 */
public class Vecho extends RichFunction {

	/**
	 *
	 */
	public Vecho() {
		super();
		addName("vecho");
		setDescription("verbatim echo stuff to the console");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("echo", false, false, false);
		p.print(args.getArgString());
		p.println();
		p.fini();
	}
}