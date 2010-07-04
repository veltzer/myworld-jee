package org.meta.jconsole;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author mark
 */
public class CommandFailedException extends RuntimeException {
	private Throwable m_embeddedException;

	/**
	 *
	 */
	public CommandFailedException() {
		super();
	}
	/**
	 *
	 * @param m
	 */
	public CommandFailedException(String m) {
		super(m);
	}
	/**
	 *
	 * @param m
	 * @param t
	 */
	public CommandFailedException(String m, Throwable t) {
		super(m);
		m_embeddedException = t;
	}
	@Override
	public void printStackTrace() {
		if (m_embeddedException == null) {
			super.printStackTrace();
			return;
		}
		System.err.println(this.getClass().getName() + ": " + this.getMessage());
		m_embeddedException.printStackTrace();
	}
	@Override
	public void printStackTrace(PrintStream s) {
		if (m_embeddedException == null) {
			super.printStackTrace(s);
			return;
		}
		s.println(this.getClass().getName() + ": " + this.getMessage());
		m_embeddedException.printStackTrace(s);
	}
	@Override
	public void printStackTrace(PrintWriter s) {
		if (m_embeddedException == null) {
			super.printStackTrace(s);
			return;
		}
		s.println(this.getClass().getName() + ": " + this.getMessage());
		m_embeddedException.printStackTrace(s);
	}
}