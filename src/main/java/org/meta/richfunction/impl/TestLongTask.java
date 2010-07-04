package org.meta.richfunction.impl;

import org.meta.util.Utils;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a function made for testing purposes
 * It emulates a long running task which sometimes prints something to the
 * screen.
 * @author mark
 */
public class TestLongTask extends RichFunction {

	/**
	 *
	 */
	public TestLongTask() {
		super();
		addName("testlongtask");
		setDescription("do something for a long time");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("test long task", true, true, true);
		p.startl("starting work",20);
		for(int i=0;i<20;i++) {
			if(p.isCancelled()) {
				break;
			}
			/**
			 * TODO: this sleep will not break if the task is cancelled -
			 * fix that - sleep via the progress class and have the break
			 * interrupt the sleep
			 */
			Utils.sleep(1000);
			//System.out.println("i is "+i);
			p.waitForCont();
			p.workl("element "+i);
		}
		p.endl();
		p.fini();
	}
}