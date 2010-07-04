package org.meta.swingconsole;

import java.awt.AWTEvent;
import java.awt.Component;
import org.meta.progress.ProgressEvent;

/**
 * This is an event sent to the UI thread about a thread finishing...
 * @author mark
 */
public class AWTProgressEvent extends AWTEvent {

	/**
	 * Event id for the AWT framework
	 */
	public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX+1;

	/**
	 * actual event delivered
	 */
	private ProgressEvent pe;

	/**
	 *
	 * @param c
	 * @param type
	 */
	public AWTProgressEvent(Component c,ProgressEvent ipe) {
		super(c,EVENT_ID);
		pe=ipe;
	}

	public ProgressEvent getPe() {
		return pe;
	}
}