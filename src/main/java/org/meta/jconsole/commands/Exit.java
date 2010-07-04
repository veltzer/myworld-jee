package org.meta.jconsole.commands;

import org.meta.jconsole.ConsoleCommand;

/**
 *
 * @author mark
 */
public class Exit extends ConsoleCommand {
	private static final String DESC = "used to exit the console, same as quit";
	private static final String HELP = "- exit";
	private static final String NAME = "exit";

	/**
	 *
	 */
	public Exit() {
		super(NAME, DESC, HELP);
	}

	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		super.getCommand("quit").execute(null);
	}

	/**
	 *
	 * @throws java.lang.Exception
	 */
	@Override
	public void initialize() throws Exception {}
}