package org.meta.jconsole.commands;

import org.meta.jconsole.Console;
import org.meta.jconsole.ConsoleCommand;

/**
 *
 * @author mark
 */
public class Quit extends ConsoleCommand {
	private static final String DESC = "used to exit the console, same as exit";
	private static final String HELP = "- quit";
	private static final String NAME = "quit";

	/**
	 *
	 */
	public Quit() {
		super(NAME, DESC, HELP);
	}

	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		Console.out.println("Roots Console done.");
		System.exit(0);
	}

	/**
	 *
	 */
	@Override
	public void initialize() {}
}
