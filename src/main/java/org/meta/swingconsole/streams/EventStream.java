package org.meta.swingconsole.streams;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.PrintStream;
import org.meta.progress.ProgressEvent;
import org.meta.swingconsole.AWTProgressEvent;

/**
 * This class accepts a JTextArea and takes care of printing anything
 * that goes to stdout or stderr to it...
 * @author mark
 */
public class EventStream extends PrintStream {
	/**
	 * The component which is the target of the event (initialized in the
	 * constructor).
	 */
	protected Component c;
	/**
	 * The saved output stream
	 */
	protected PrintStream saveOut;
	/**
	 * The saved error stream
	 */
	protected PrintStream saveErr;

	/**
	 * Constructor with the component
	 *
	 * @param c
	 */
	public EventStream(Component c) {
		super(System.out);
		this.c=c;
	}
	/**
	 * Replace the standard output and error with this stream.
	 */
	public void takeOver() {
		saveOut=System.out;
		saveErr=System.err;
		System.setOut(this);
		System.setErr(this);
	}
	/**
	 * Return the original standrad output and error
	 */
	public void Release() {
		System.setOut(saveOut);
		System.setErr(saveErr);
	}
	@Override
	public void println(Object o) {
		print(o.toString()+"\n");
	}
	@Override
	public void println(String s) {
		print(s+"\n");
	}
	@Override
	public void println() {
		print("\n");
	}
	@Override
	public void print(Object o) {
		print(o.toString());
	}
	@Override
	public void print(String s) {
		EventQueue eventQueue=Toolkit.getDefaultToolkit().getSystemEventQueue();
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.PRINT,null);
		pe.setMessage(s);
		eventQueue.postEvent(new AWTProgressEvent(c,pe));
	}
}