package org.meta.jconsole;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author Niraj Juneja
 */
public class ConsoleSystemException extends java.lang.RuntimeException {
	private Throwable m_embeddedException;

	/** Creates new ConsoleSystemException */
	public ConsoleSystemException() {
		super();
	}

	/**
	 *
	 * @param str
	 */
	public ConsoleSystemException(String str) {
		super(str);
	}

	/**
	 *
	 * @param str
	 * @param t
	 */
	public ConsoleSystemException(String str, Throwable t) {
		super(str);
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


//~ Formatted by Jindent --- http://www.jindent.com
