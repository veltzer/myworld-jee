package org.meta.jconsole.commands;

import org.meta.jconsole.ConsoleCommand;
import org.meta.jconsole.PushTrace;

/**
 * @author nzjuneja
 */
public class Ptrace extends ConsoleCommand{

	private static final String NAME = "ptrace";
	private static final String DESC = "used to print the last Error Stact trace";
	private static final String HELP = "- ptrace ";
	/** Creates new stop */
	public Ptrace() {
		 super(NAME, DESC, HELP);
	}

	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		PushTrace.pop();
	}

	/**
	 *
	 * @throws java.lang.Exception
	 */
	@Override
	protected void initialize() throws Exception {
	}
}