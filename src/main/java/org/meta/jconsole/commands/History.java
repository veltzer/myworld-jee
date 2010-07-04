package org.meta.jconsole.commands;

import java.util.List;
import org.meta.jconsole.Console;
import org.meta.jconsole.ConsoleCommand;
import org.meta.jconsole.HistoryEntry;
import org.meta.jconsole.Utilities;

/**
 *
 * @author mark
 */
public class History extends ConsoleCommand {
	private static final String DESC = "used to display the command history.";
	private static final String HELP = "- hist [#]";
	private static final String NAME = "hist";

	/**
	 *
	 */
	public History() {
		super(NAME, DESC, HELP);
	}
	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		List cmdList = super.getCommandHistory();

		if ((cmdList == null) || (cmdList.size() == 0)) {
			Console.out.println("No History...");

			return;
		}

		int histCount = cmdList.size();

		if (args.length > 0) {
			try {
				histCount = Integer.parseInt(args[0]);
			} catch (Exception e) {
				super.throwCmdFailed("argument not a number: " + args[0], e);
			}
		}

		for (int i = (cmdList.size() - histCount) + 1; i <= cmdList.size(); i++) {
			Console.out.println(Utilities.Pad("!" + i, 5, Utilities.PAD_RIGHT) + (HistoryEntry) cmdList.get(i - 1));
		}
	}

	/**
	 *
	 * @throws java.lang.Exception
	 */
	@Override
	protected void initialize() throws Exception {}
}
