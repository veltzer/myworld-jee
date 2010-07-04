package meta.utils.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A set of file system related utilities in static functions
 * @author Mark Veltzer
 */
public class Utils {

	/**
	 * Convert an array of strings to a list of files
	 * @param strings
	 * @return The list of files as an ArrayList
	 */
	static List<File> StringsToFiles(String[] strings) {
		List<File> lf=new ArrayList<File>();
		for(String s:strings) {
			lf.add(new File(s));
		}
		return lf;
	}
}