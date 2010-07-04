package org.meta.jconsole.commands;

import java.io.File;
import org.meta.jconsole.ConsoleCommand;

/**
 *
 * @author mark
 */
public class Mkdir extends ConsoleCommand {
	private static final String DESC = "used to create a new directory.";
	private static final String HELP = "mkdir new-dir-name";
	private static final String NAME = "mkdir";
	/**
	 *
	 */
	public Mkdir() {
		super(NAME, DESC, HELP);
	}
	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		int count = (args == null)
					? 0
					: args.length;

		if (count == 0) {
			super.throwCmdFailed(HELP);
		}

		String name=args[0];
		File newDir=new File(super.getCurrentDir(), name);

		if (!newDir.mkdir()) {
			super.throwCmdFailed("mkdir failed");
		}
	}

	/**
	 *
	 * @throws java.lang.Exception
	 */
	@Override
	protected void initialize() throws Exception {}
}