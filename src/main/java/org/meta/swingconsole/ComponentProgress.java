package org.meta.swingconsole;

import java.awt.Component;
import org.meta.progress.IProgressEventProcessor;
import org.meta.progress.Progress;

/**
 * A swing based progress indicator...
 * TODO: use a pool (I'm creating a multitue of small Event objects here...)
 * @author mark
 */
public class ComponentProgress extends Progress {

	/**
	 *
	 */
	protected Component c;

	/**
	 *
	 * @param c
	 */
	public ComponentProgress(IProgressEventProcessor ipep,Component ic) {
		super(ipep);
		c=ic;
	}

}