package org.meta.jconsole.commands;

import org.meta.jconsole.Console;
import org.meta.jconsole.ConsoleCommand;

/**
 *
 * @author mark
 */
public class Pwd extends ConsoleCommand {
	private static final String DESC = "used to print the current directory.";
	private static final String HELP = "- pwd";
	private static final String NAME = "pwd";

	/**
	 *
	 */
	public Pwd() {
		super(NAME, DESC, HELP);
	}

	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		Console.out.println(super.getCurrentDir().getAbsolutePath());
	}

	/**
	 *
	 */
	@Override
	protected void initialize() {}

	/**
	 *
	 * @return
	 */
	protected boolean needsMQ() {
		return false;
	}
}
