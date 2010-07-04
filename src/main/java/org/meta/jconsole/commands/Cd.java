package org.meta.jconsole.commands;

import java.io.File;
import org.meta.jconsole.ConsoleCommand;

/*
* used to change the console directory
*/
/**
 *
 * @author mark
 */
public class Cd extends ConsoleCommand {
	private static final String DESC = "used to change the console directory.";
	private static final String HELP = "allows to change the current console directory";
	private static final String NAME = "cd";
	/**
	 *
	 */
	public Cd() {
		super(NAME, DESC, HELP);
	}
	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		if(args.length==0) {
			return;
		}
		File lsFile=super.getFile(args[0]);
		if (lsFile!=null) {
			setCurrentDir(lsFile.getAbsolutePath());
			return;
		}
		// in case user is giving the arg like "cd .." or
		File currDir=super.getCurrentDir();
		File newDir=new File(currDir.getPath() + System.getProperty("file.separator") + args[0]);
		if (!((newDir.exists()) && (newDir.isDirectory()))) {
			newDir = new File(args[0]);
			if (!((newDir.exists()) && (newDir.isDirectory()))) {
				super.throwCmdResult("Directory not found: " + newDir.getAbsolutePath());
			}
		}
		try {
			setCurrentDir(newDir.getCanonicalPath());
		} catch(Exception e) {
			e.printStackTrace();
			super.throwCmdFailed("change dir failed: " + newDir.getAbsolutePath(), e);
		}
	}
	/**
	 *
	 * @throws java.lang.Exception
	 */
	@Override
	protected void initialize() throws Exception {}
}
