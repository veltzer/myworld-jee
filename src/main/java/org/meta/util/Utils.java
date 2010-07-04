package org.meta.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * This is a static collection of methods to turn checked exceptions into
 * Runtime exceptions.
 * @author mark
 */
public class Utils {
	/**
	 * Wrapper for the getCannonicalPath function.
	 * @param f
	 * @return
	 */
	public static String getCannonicalPath(File f) {
		try {
			return f.getCanonicalPath();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 *
	 * @param c
	 * @param cmdarray
	 * @return
	 */
	public static String join(char c,String[] cmdarray) {
		StringBuilder sb=new StringBuilder();
		for(String x: cmdarray) {
			sb.append(x);
			sb.append(c);
		}
		return sb.toString();
	}
	/**
	 *
	 * @param cmdarray
	 * @return
	 */
	public static Process exec(String[] cmdarray) {
		try {
			System.out.println("executing "+join(',',cmdarray));
			return Runtime.getRuntime().exec(cmdarray);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 *
	 * @param cmdarray
	 */
	public static void execAndWait(String[] cmdarray) {
		Process p=exec(cmdarray);
		waitFor(p);
	}
	/**
	 *
	 * @param p
	 */
	public static void waitFor(Process p) {
		try {
			int res=p.waitFor();
			if(res!=0) {
				throw new RuntimeException("got error code "+res);
			}
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 *
	 * @param file
	 * @return
	 */
	public static PrintStream openPrintStream(String file) {
		try {
			PrintStream s=new PrintStream(new FileOutputStream(file));
			return s;
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	public static PrintStream openPrintStream(File outFile) {
		try {
			PrintStream s=new PrintStream(new FileOutputStream(outFile));
			return s;
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 *
	 * @param oldfile
	 * @param newfile
	 */
	public static void rename(File oldfile,File newfile) {
		boolean suc=oldfile.renameTo(newfile);
		if(suc==false) {
			throw new RuntimeException("cannot rename "+oldfile.getAbsolutePath()+" to "+newfile.getAbsolutePath());
		}
	}
	/**
	 *
	 * @param oldabspath
	 * @param newabspath
	 */
	public static void renameAbs(String oldabspath,String newabspath) {
		File oldfile=new File(oldabspath);
		File newfile=new File(newabspath);
		rename(oldfile,newfile);
	}
	/**
	 *
	 * @param oldabspath
	 * @param newrelpath
	 */
	public static void renameRel(String oldabspath,String newrelpath) {
		File oldfile=new File(oldabspath);
		String dir=oldfile.getParent();
		String newabspath=dir+File.separatorChar+newrelpath;
		File newfile=new File(newabspath);
		rename(oldfile,newfile);
	}
	/**
	 * Create a temp file with runtime exceptions
	 * @param subject
	 * @return
	 */
	public static File createTempFile(String subject) {
		try {
			File result = File.createTempFile("meta-" + subject, null);
			return result;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 *
	 * @param orig
	 * @param suffix
	 * @return
	 */
	public static String replaceSuffix(String orig,String suffix) {
		return orig.substring(0,orig.lastIndexOf('.'))+suffix;
	}
	public static String stripExtension(String fname) {
		return fname.substring(0,fname.lastIndexOf('.'));
	}
	/**
	 *
	 * @param f
	 */
	public static void delete(File f) {
		boolean val=f.delete();
		if(val==false) {
			throw new RuntimeException("could not delete file ["+getCannonicalPath(f)+"]");
		}
	}
	/**
	 * Find the common prefix of two strings
	 * @param a the first string
	 * @param b the second one
	 * @return
	 */
	public static String commonPrefix(String a,String b) {
		int min=Math.min(a.length(), b.length());
		int i=0;
		// make sure that the check for minimum preceeds any check
		// of the actual characters so as not to access an illegal
		// position within the string...
		while(i<min) {
			if(a.charAt(i)!=b.charAt(i)) {
				break;
			}
			i++;
		}
		return a.substring(0,i);
	}
	/**
	 * Sleep without having to catch exceptions
	 * @param millis time to sleep
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

}