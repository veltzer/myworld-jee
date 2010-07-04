package org.meta.music.issues;

import java.io.File;
import org.meta.util.Utils;

/**
 * This is an issue for Non mp3 files. It simply records the file which is not
 * mp3.
 * @author mark
 */
public class NonMp3 extends Base {
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
	public NonMp3(File ifile,int idepth,String imime) {
		super(ifile,idepth);
		mime=imime;
	}
	/**
	 *
	 */
	@Override
	public void handle() {
		super.handle();
		/**
		 * TODO: pacpl does not return a correct error code when it
		 * fails.
		 * fix this by doing mimetype detection again after the end and
		 * throwing an exception if the mimetype is still wrong
		 * in any case maybe try again with --decoder mplayer which
		 * fixes some of the problems.
		 */

		/**
		 * TODO: pacpl has a problem with names with , in them - fix it
		 */
		String path=Utils.getCannonicalPath(file);
		String[] cmdarray={
			"pacpl",
			"--to",
			"mp3",
			"--overwrite",
			"--delete",
			path
		};
		System.out.println("mime is "+mime);
		Utils.execAndWait(cmdarray);
	}
}