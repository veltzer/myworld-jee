package org.meta.swingconsole.streams;

import java.io.PrintStream;
import javax.swing.JTextArea;

/**
 * This class accepts a JTextArea and takes care of printing anything
 * that goes to stdout or stderr to it...
 * @author mark
 */
public class TextAreaStream extends PrintStream {
	/**
	 *
	 */
	protected JTextArea text;
	/**
	 *
	 */
	protected PrintStream saveOut;
	/**
	 *
	 */
	protected PrintStream saveErr;

	/**
	 *
	 * @param text
	 */
	public TextAreaStream(JTextArea text) {
		super(System.out);
		this.text = text;
	}
	/**
	 *
	 */
	public void takeOver() {
		saveOut=System.out;
		saveErr=System.err;
		System.setOut(this);
		System.setErr(this);
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
		text.append(s);
	}
}