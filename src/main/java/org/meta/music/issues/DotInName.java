package org.meta.music.issues;

import java.io.File;
import org.meta.util.Utils;

/**
 * This is an issue of a file having a bad permissions
 * @author mark
 */
public class DotInName extends Base {

	/**
	 *
	 */
	@Override
	public void handle() {
		super.handle();
		String name=file.getName();
		int last=name.lastIndexOf(".");
		String base=name.substring(0,last);
		String ext=name.substring(last);
		String newbase=base.replace(".","_");
		String newname=newbase+ext;
		Utils.renameRel(file.getAbsolutePath(),newname);
	}
	/**
	 *
	 * @param ifile
	 * @param idepth
	 */
	public DotInName(File ifile, int idepth) {
		super(ifile, idepth);
	}
}