package org.meta.jconsole;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author nzjuneja
 */
public final class Console {
	/**
	 *
	 */
	public static PrintStream err = System.err;
	/**
	 *
	 */
	public static InputStream in = System.in;
	/**
	 *
	 */
	public static PrintStream out = System.out;

	/**
	 *
	 * @param stream
	 */
	public static void setErrorStream(PrintStream stream) {
		err = stream;
	}

	/**
	 *
	 * @param stream
	 */
	public static void setOutputStream(PrintStream stream) {
		out = stream;
	}

	/**
	 *
	 * @param stream
	 */
	public static void setInputStream(InputStream stream) {
		in = stream;
	}

	/**
	 *
	 * @param str
	 */
	public static void p(String str) {
		out.println(str);
	}

	/**
	 *
	 * @param str
	 */
	public static void pt(String str) {
		out.println("\t" + str);
	}
}