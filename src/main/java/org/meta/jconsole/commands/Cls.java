package org.meta.jconsole.commands;

import org.meta.jconsole.Console;
import org.meta.jconsole.ConsoleCommand;

/*
* used to clear the screen
*/
/**
 *
 * @author mark
 */
public class Cls extends ConsoleCommand {
	private static final String DESC = "used to clear the screen.";
	private static final String HELP = "- cls";
	private static final String NAME = "cls";

	/**
	 *
	 */
	public Cls() {
		super(NAME, DESC, HELP);
	}
	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		for (int x = 0; x < 50; x++) {
		Console.out.println("");
		}
	}

	/**
	 *
	 */
	@Override
	protected void initialize() {}
}
