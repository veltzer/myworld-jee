package org.meta.music.issues;

import java.io.File;

/**
 * This is an issue of a file having a bad extension
 * @author mark
 */
public class BadExtension extends Base {
	/**
	 *
	 */
	protected String mime;

	@Override
	public String toString() {
		return super.toString()+","+mime;
	}
	/**
	 *
	 * @param ifile
	 * @param idepth
	 * @param imime
	 */
	public BadExtension(File ifile,int idepth,String imime) {
		super(ifile,idepth);
		mime=imime;
	}
}