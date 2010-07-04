package org.meta.jconsole.old;

import org.meta.wrappers.java.io.InputStream;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class is a runnable(thread) that reads from a stream and passed
 * the data to a synchronized queue that can be handled by another thread
 * to do whatever.
 *
 * @author mark
 */
public class StreamHandler implements Runnable {
	private InputStream i;
	private JTextArea ijt;
	private JScrollPane p;

	/**
	 *
	 * @param ii
	 * @param iijt
	 * @param ip
	 */
	public StreamHandler(java.io.InputStream ii,JTextArea iijt,JScrollPane ip) {
		i=new InputStream(ii);
		ijt=iijt;
		p=ip;
	}

	@Override
	public void run() {
		while(true) {
			final String s=i.readString();
			ijt.append(s);
			/*
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					@Override
					public void run() {
						ijt.append(s);
					}
				});
			} catch (InterruptedException ex) {
				Logger.getLogger(StreamHandler.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InvocationTargetException ex) {
				Logger.getLogger(StreamHandler.class.getName()).log(Level.SEVERE, null, ex);
			}
			 */
		}
	}
}