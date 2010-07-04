package meta.utils.fs;

import java.io.File;
import meta.utils.ds.filters.IFilter;

/**
 * This is a filter which only accepts regular files
 * @author Mark Veltzer
 */
public class FilterFile implements IFilter<File> {

	/**
	 * This method only accepts regular files
	 * @param e
	 * @return
	 */
	@Override
	public boolean accept(File e) {
		return e.isFile();
	}

}
