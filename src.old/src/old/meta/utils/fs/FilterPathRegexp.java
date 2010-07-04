package meta.utils.fs;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import meta.utils.ds.filters.IFilter;

/**
 * A filter which filters files according to a regexp on their path names
 * @author Mark Veltzer
 */
public class FilterPathRegexp implements IFilter<File> {
	/**
	 * The regexp compiled pattern
	 */
	private Pattern pattern;

	/**
	 * A constructor from a pre prepared compiled regular expression
	 * @param p The pattern to use
	 */
	public FilterPathRegexp(Pattern p) {
		pattern=p;
	}
	/**
	 * A constructor from a string regular expression
	 * @param s The string to use
	 */
	public FilterPathRegexp(String s) {
		pattern=Pattern.compile(s);
	}

	@Override
	public boolean accept(File e) {
		Matcher m=pattern.matcher(e.getPath());
		return m.matches();
	}
}