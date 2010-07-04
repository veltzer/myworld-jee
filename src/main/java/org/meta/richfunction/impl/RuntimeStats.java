package org.meta.richfunction.impl;

import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * Print report on number of processors, memory, gc and more
 * @author mark
 */
public class RuntimeStats extends RichFunction {

	/**
	 *
	 */
	public RuntimeStats() {
		super();
		addName("runtimestats");
		setDescription("report runtime statistics");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("runtimestats", false, false, false);
		Runtime r=Runtime.getRuntime();
		System.out.println("processors "+r.availableProcessors());
		System.out.println("free memory "+r.freeMemory());
		System.out.println("max memory "+r.maxMemory());
		System.out.println("total memory "+r.totalMemory());
		p.fini();
	}
}