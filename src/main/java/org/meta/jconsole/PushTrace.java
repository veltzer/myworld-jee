package org.meta.jconsole;

/**
 * @author nzjuneja
 */
public class PushTrace {
	private static java.lang.Throwable t;

	/**
	 *
	 * @param msg
	 * @param to
	 * @throws org.meta.jconsole.CommandFailedException
	 */
	public static void push(String msg, java.lang.Throwable to) throws CommandFailedException {
		t = to;
		throw new CommandFailedException(msg + to.getMessage());
	}

	/**
	 *
	 * @param to
	 */
	public static void push(java.lang.Throwable to) {
		t = to;
	}

	/**
	 *
	 */
	public static void pop() {
		if (t != null) {
			t.printStackTrace();
		} else {
			Thread.dumpStack();
		}
		t = null;
		Console.out.println(" DONE");
	}
}