package org.meta.jconsole.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import org.meta.jconsole.*;

/**
 * used to copy a file
 */
public class Cp extends ConsoleCommand {
	private static final String DESC = "used to copy a file.";
	private static final String HELP = "cp source destination";
	private static final String NAME = "cp";

	/**
	 *
	 */
	public Cp() {
		super(NAME, DESC, HELP);
	}

	/**
	 *
	 * @throws java.lang.Exception
	 */
	@Override
	protected void initialize() throws Exception {}

	/**
	 *
	 * @param args
	 * @throws org.meta.jconsole.CommandFailedException
	 */
	@Override
	public void execute(String[] args) throws CommandFailedException {
		int count = (args == null)
					? 0
					: args.length;

		if (count != 2) {
			super.throwCmdFailed(HELP);
		}

		/**
		 * These are the cases
		 * 1 file dir
		 * 2 file dir/file
		 * file file
		 * 3. * dir
		 */
		String srcFileName=args[0];
		String destFileName=args[1];
		File srcFile=getFile(srcFileName);	 // new File (super.getCurrentDir(), srcFileName);
		File destFile=getFile(destFileName);	// new File (destFileName);

		// destFile = new File( getCurrentDir(), destFileName );

		/**
		 * destFile cases
		 * 1 new relative dir -> null
		 * 2 new relative dir/file -> null
		 * 3 exist relative dir -> File
		 * 4 exist relative dir.file -> File
		 * 5-8 with absolute path
		 */
		if ((destFile != null) && destFile.exists() && destFile.isDirectory()) {

			// Do nothing: it is an absolute path
		} else {

			// It is: a new relative file or directory
			destFile = new File(getCurrentDir(), destFileName);

			// Another case: it is a new absolute directory
		}

		try {

			// if src name is a mask then filter current dir
			// and copy a filtered result.
			if (srcFileName.indexOf("*") != -1) {
				if (!destFile.exists()) {
					m_sim.processLine("mkdir " + destFile);
				}

				String[] filteredSrc = scanDir(srcFileName, super.getCurrentDir());

				for (int i = 0; i < filteredSrc.length; i++) {
					File tmpSrc = new File(super.getCurrentDir(), filteredSrc[i]);

					copyFile(tmpSrc, destFile);
				}
			} else {
				copyFile(srcFile, destFile);
			}
		} catch (IOException io) {
			super.throwCmdFailed("CP Command failed", io);
		}
	}

	/**
	 * This method returs an array of file names that matches to
	 * a specific mask from a given dir.
	 *
	 * -==- Please note that only '*xxx' mask is implemented -==-
	 *
	 * @return String[]
	 */
	private String[] scanDir(final String mask, File path) {
	return path.list(new FilenameFilter() {
		@Override
		public boolean accept(File dir, String n) {
			return filterImpl(new File(n).getName());
		}
			private boolean filterImpl(String fileName) {
				if (mask.indexOf("*") != -1) {
					if (mask.startsWith("*")) {
						String val = mask.substring(1, mask.length());

						if (fileName.endsWith(val)) {
							return true;
						}
					}
				}

				return false;
			}
		});
	}

	/**
	 * Convienence method to copy a file from a source to a destination
	 *
	 * @throws IOException
	 */
	private void copyFile(File srcFile, File destFile) throws IOException {

		// ensure if distination is a dir that copy with the same name
		if (destFile.isDirectory()) {
			destFile = new File(destFile, srcFile.getName());
		}

		if (srcFile.equals(destFile)) {
			return;
		}

/*
		// ensure that parent dir of dest file exists!
		// not using getParentFile method to stay 1.1 compat
		File parent = new File(destFile.getParent());
		if (!parent.exists())
		{
			parent.mkdirs();
		}
*/
		FileInputStream in=new FileInputStream(srcFile);
		FileOutputStream out=new FileOutputStream(destFile);
		byte[] buffer=new byte[8*1024];
		int count=0;

		do {

			// first iteration does write nothing.
			out.write(buffer, 0, count);
			count = in.read(buffer, 0, buffer.length);
		} while (count != -1);

		in.close();
		out.close();
	}
}