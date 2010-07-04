package org.meta.jconsole.commands;

import java.io.File;
import org.meta.jconsole.ConsoleCommand;

/**
 *
 * @author mark
 */
public class Rmdir extends ConsoleCommand {
	private static final String DESC = "used to remove a directory.";
	private static final String HELP = "rmdir dir-name";
	private static final String NAME = "rmdir";

	/**
	 *
	 */
	public Rmdir() {
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
		File theDir=new File(super.getCurrentDir(), name);

		// if the directory doesn't exist do nothing.
		if (!theDir.exists()) {
			return;
		}

		// check if a given source is a file or a dir.
		if (!theDir.isDirectory()) {
			super.throwCmdFailed("rmdir failed. '" + name + " is not a Directory.");
		}

		// remember the absolute path.
		String absPath = theDir.getAbsolutePath();

		// empty the content of the directory
		removeContent(theDir);

		// try to remove. if no succees throw an exception.
		if (!(new File(absPath)).delete()) {
			super.throwCmdFailed("rmdir failed. Unable to remove " + absPath);
		}
	}

	/**
	 * Recursively walks through a directory tree and removes its content
	 * @param File - directory to remove a content from
	 */
	private void removeContent(File file) {
		String absPath = file.getAbsolutePath();

		if (file.isDirectory()) {
			String[] dirItem = file.list();

			for (int i = 0; i < dirItem.length; i++) {
				File tmpFile = new File(absPath, dirItem[i]);

				if (tmpFile.isFile()) {
					if (!tmpFile.delete()) {
						super.throwCmdFailed("rmdir failed. Unable to remove " + tmpFile.getName());
					}
				} else {
					removeContent(tmpFile);

					if (!tmpFile.delete()) {
						super.throwCmdFailed("rmdir failed. Unable to remove " + tmpFile.getName());
					}
				}
			}
		}
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