package org.meta.jconsole.commands;

import java.util.Iterator;
import java.util.List;
import org.meta.jconsole.Console;
import org.meta.jconsole.ConsoleCommand;

/**
 *
 * @author mark
 */
public class Help extends ConsoleCommand {
	private static final String DESC = "used to provide help information";
	private static final String HELP = "- help [command-name optional]";
	private static final String NAME = "help";

	/**
	 *
	 */
	public Help() {
		super(NAME, DESC, HELP);
	}

	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		if ((args == null) || (args.length == 0)) {
			Console.out.println("The following commands are installed:");

			List l = super.getAllCommandsList();

			for (Iterator i = l.iterator(); i.hasNext(); ) {
				ConsoleCommand cmd = (ConsoleCommand) i.next();

				Console.out.println(cmd.getName() + "\t- " + cmd.getShortDescription());
			}

			Console.out.println("");

			return;
		}

		ConsoleCommand cmd = super.getCommand(args[0]);

		Console.out.println("Help topic for - " + cmd.getName());
		Console.out.println(cmd.getHelp());
		Console.out.println("");
	}
	/**
	 *
	 */
	@Override
	public void initialize() {}
}