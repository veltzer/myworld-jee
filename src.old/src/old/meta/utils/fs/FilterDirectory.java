package meta.utils.fs;

import java.io.File;
import meta.utils.ds.filters.IFilter;

/**
 * This is a filter which only accepts directories
 * @author Mark Veltzer
 */
public class FilterDirectory implements IFilter<File> {

	/**
	 * This method only accepts directories
	 * @param e
	 * @return
	 */
	@Override
	public boolean accept(File e) {
		return e.isDirectory();
	}

}
