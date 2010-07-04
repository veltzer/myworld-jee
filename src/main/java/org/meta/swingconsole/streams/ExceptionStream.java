package org.meta.swingconsole.streams;

import java.io.PrintStream;

/**
 * This class accepts a JTextArea and takes care of printing anything
 * that goes to stdout or stderr to it...
 * @author mark
 */
public class ExceptionStream extends PrintStream implements Thread.UncaughtExceptionHandler {
	/**
	 * Saved output stream for restoration
	 */
	protected PrintStream saveOut;
	/**
	 * Saved error sream for restoration
	 */
	protected PrintStream saveErr;

	/**
	 * Default constructor
	 */
	public ExceptionStream() {
		super(System.out);
	}
	/**
	 * Replace the standard input and output with this exception stream
	 */
	public void takeOver() {
		saveOut=System.out;
		saveErr=System.err;
		System.setOut(this);
		System.setErr(this);
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	/**
	 *
	 */
	public void Release() {
		System.setOut(saveOut);
		System.setErr(saveErr);
	}
	@Override
	public void println(Object o) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void println(String s) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void println() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void print(Object o) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void print(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		e.printStackTrace(saveErr);
	}
}