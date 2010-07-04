package org.meta.richfunction.impl;

import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * Print a report on all currently running java threads
 * @author mark
 */
public class Threads extends RichFunction {

	/**
	 *
	 */
	public Threads() {
		super();
		addName("threads");
		setDescription("report on all java threads");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("threads", false, false, false);
		// Find the root thread group, maybe we can cache this ?
		ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
		root.list();
		p.fini();
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	public void execute2(Progress p,Arguments args) {
		p.init("threads", false, false, false);
		// Find the root thread group, maybe we can cache this ?
		ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
		while (root.getParent() != null) {
			root = root.getParent();
		}
		// Visit each thread group
		visit(root, 0);
	}
	/**
	 * This method recursively visits all thread groups under `group'.
	 *
	 * @param group
	 * @param level
	 */
	public static void visit(ThreadGroup group, int level) {
		// Get threads in `group'
		int numThreads = group.activeCount();
		// why the 2? it's because the number of threads may change
		// between the previous line and the next one
		Thread[] threads = new Thread[numThreads*2];
		numThreads = group.enumerate(threads, false);
		// Enumerate each thread in `group'
		for (int i=0; i<numThreads; i++) {
			// Get thread
			Thread thread = threads[i];
			System.out.println(thread.getId());
			System.out.println(thread.getName());
		}
		// Get thread subgroups of `group'
		int numGroups = group.activeGroupCount();
		ThreadGroup[] groups = new ThreadGroup[numGroups*2];
		numGroups = group.enumerate(groups, false);
		// Recursively visit each subgroup
		for (int i=0; i<numGroups; i++) {
			visit(groups[i], level+1);
		}
	}
}