package org.meta.music;

import eu.medsea.util.MimeUtil;
import java.io.File;
import java.io.PrintStream;
import org.meta.issues.IssueHandler;
import org.meta.util.Utils;
import org.meta.music.issues.BadExtension;
import org.meta.music.issues.Depth;
import org.meta.music.issues.DotInName;
import org.meta.music.issues.NonMp3;
import org.meta.music.issues.Permission;
import org.meta.progress.Progress;
import org.meta.wrappers.org.apache.commons.io.DirectoryWalker;

/**
 * This is a class which implements a directory walker and checks my music
 * collection.
 * - check the extension of the files.
 * - check the mime info from magic.
 * - check the directory structure (right depth).
 * - check the name of the file (regexp).
 * - check the existance of id3 tags (as a unit).
 * - check the existance of artist, album, year, track number, track name
 *	and regexps on these things.
 * - check the correlation of ids tags and the file name
 *	Filename=Track+" "+TrackName+".mp3"
 * And more to come...
 *
 * This walker will also take care of the problems it finds in three ways:
 * - automatically fix the problem.
 * - interact with the user to fix the problem.
 * - things which are not fixed in the first two ways will be recorded
 * so that they may be fixed automatically and will not need to be
 * deduced again.
 *
 * TODO: add size of collection to the statistics
 *
 * @author mark
 */
public class Checker extends DirectoryWalker {


	/**
	 * This is a non default constructor and we must have it...
	 * @param p
	 */
	public Checker(Progress p) {
		super(p);
	}
	protected int artists;
	protected int albums;
	protected int tracks;

	protected boolean bcheckDepth=true;
	protected boolean bcheckPermission=true;
	protected boolean bcheckExtension=true;
	protected boolean bcheckMimeType=true;
	protected boolean bcheckDotInName=true;

	@Override
	protected void handleStart(File startDirectory) {
		super.handleStart(startDirectory);
		artists=0;
		albums=0;
		tracks=0;
	}
	/**
	 *
	 * @param directory
	 * @param depth
	 */
	@Override
	protected void handleDirectoryEnd(File directory, int depth) {
		super.handleDirectoryEnd(directory, depth);
		if(depth==3) {
			albums++;
		}
		if(depth==2) {
			artists++;
		}
	}

	/**
	 *
	 * @param file
	 * @param depth
	 */
	@Override
	protected void handleFile(File file,int depth) {
		super.handleFile(file,depth);
		tracks++;
		if(bcheckDepth) {
			checkDepth(file,depth);
		}
		if(bcheckPermission) {
			checkPermission(file,depth);
		}
		if(bcheckExtension) {
			checkExtension(file,depth);
		}
		if(bcheckMimeType) {
			checkMimeType(file,depth);
		}
		if(bcheckDotInName) {
			checkDotInName(file,depth);
		}
	}
	private void checkDepth(File file, int depth) {
		if(depth!=4) {
			IssueHandler.getInstance().add(new Depth(file,depth));
		}
	}
	private void checkPermission(File file, int depth) {
		if(file.canExecute()) {
			IssueHandler.getInstance().add(new Permission(file,depth));
		}
	}
	private void checkExtension(File file,int depth) {
		String mime=MimeUtil.getFirstMimeType(MimeUtil.getExtensionMimeTypes(file));
		if(!mime.equals("audio/mpeg")) {
			IssueHandler.getInstance().add(new BadExtension(file,depth,mime));
		}
	}
	private void checkMimeType(File file,int depth) {
		String mime=MimeUtil.getMagicMimeType(file);
		if(!mime.equals("audio/mpeg")) {
			IssueHandler.getInstance().add(new NonMp3(file,depth,mime));
		}
	}
	private void checkDotInName(File file, int depth) {
		String name=file.getName();
		int last=name.lastIndexOf(".");
		String base=name.substring(0,last);
		if(base.contains(".")) {
			IssueHandler.getInstance().add(new DotInName(file,depth));
		}
	}
	public void printStats(File stats_file) {
		PrintStream s=Utils.openPrintStream(stats_file);
		s.println("number of artists "+artists);
		s.println("number of albums "+albums);
		s.println("number of tracks "+tracks);
		s.close();
	}
}