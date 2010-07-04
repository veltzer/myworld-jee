package meta.utils.fs;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import meta.utils.ds.filters.IFilter;

/**
 * A filter which filters files according to a regexp on their parent names
 * @author Mark Veltzer
 */
public class FilterParentRegexp implements IFilter<File> {
	/**
	 * The regexp compiled pattern
	 */
	private Pattern pattern;

	/**
	 * A constructor from a pre prepared compiled regular expression
	 * @param p The pattern to use
	 */
	public FilterParentRegexp(Pattern p) {
		pattern=p;
	}
	/**
	 * A constructor from a string regular expression
	 * @param s The string to use
	 */
	public FilterParentRegexp(String s) {
		pattern=Pattern.compile(s);
	}

	@Override
	public boolean accept(File e) {
		Matcher m=pattern.matcher(e.getParent());
		return m.matches();
	}
}