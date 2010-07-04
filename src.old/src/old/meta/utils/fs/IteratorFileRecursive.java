package meta.utils.fs;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import meta.utils.ds.iterators.IteratorMultiple;

/**
 * Iterates over all non-directory files contained in some subdirectory of the
 * current one.
 *
 * TODO:
 * 1. add mindepth and maxdepth options to limit depth.
 * 2. get more features from find.
 *
 * @author Mark Veltzer
 */
public class IteratorFileRecursive extends IteratorMultiple<File> {

	/**
	 * Minimum depth to return results at 
	 */
	int mindepth=0;
	/**
	 * Maximum depth to return results at
	 */
	int maxdepth=Integer.MAX_VALUE;
	/**
	 * Add a file name to the iterator
	 * @param str The file name to be added
	 */
	public void add(String... strings) {
		List<File> lf=Utils.StringsToFiles(strings);
		Iterator ilf=lf.iterator();
		addFirst(ilf);
	}
	/**
	 * Add a file to the iterator
	 * @param f The file to be added.
	 */
	public void add(File... files) {
		List<File> lf=Arrays.asList(files);
		Iterator ilf=lf.iterator();
		addFirst(ilf);
	}

	/**
	 * Basic constructor. Supply a collection of files to boot.
	 * @param c The collection of files
	 */
	public IteratorFileRecursive(File f) {
		super();
		Stack<File> s=new Stack<File>();
		s.add(f);
		Iterator<File> it=s.iterator();
		add(it);
	}

	public IteratorFileRecursive(String s) {
		this(new File(s));
	}

	private void doRecurse(File f) {
		if(f.isDirectory() && f.canRead()) {
			List<File> lf=Arrays.asList(f.listFiles());
			Iterator ilf=lf.iterator();
			addFirst(ilf);
		}
	}
	/**
	 * The next method. It does what the basic method does but in addition
	 * when ever it sees a directory that it can read it will also add an
	 * iterator with it's content to the files that are yet to be scanned.
	 * @return The next file
	 */
	@Override
	public File next() {
		File f=super.next();
		doRecurse(f);
		return f;
	}

	/**
	 * A simple test method
	 * @param str
	 */
	public static void main(String[] str) {
		final IteratorFileRecursive itr=new IteratorFileRecursive(new File("/tmp"));
		for(File f:itr) {
			System.out.println(f.getName());
		}
	}

}