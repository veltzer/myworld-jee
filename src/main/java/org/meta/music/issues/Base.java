package org.meta.music.issues;

import java.io.File;
import org.meta.issues.IIssue;
import org.meta.util.Utils;

/**
 * Base class for all music related issues
 * @author mark
 */
public abstract class Base implements IIssue {

	/**
	 *
	 */
	protected File file;
	/**
	 *
	 */
	protected int depth;
	/**
	 *
	 * @param ifile
	 * @param idepth
	 */
	public Base(File ifile,int idepth) {
		file=ifile;
		depth=idepth;
	}
	/**
	 *
	 */
	@Override
	public void handle() {
	}
	@Override
	public String toString() {
		return getClass().getName() + "," + Utils.getCannonicalPath(file) + "," +depth;
	}
}