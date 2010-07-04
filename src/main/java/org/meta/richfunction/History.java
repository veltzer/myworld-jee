package org.meta.richfunction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A history class holding all command line history...
 * TODO: add persistance to file
 * TODO: add persistance to database
 * TODO: add limit to the size of the history
 * TODO: add completion according to history
 * TODO: should this be really here ? It has nothing todo with rich functions!
 * @author mark
 */
public class History extends ArrayList<String> {
	/**
	 * All the commands that were ever typed..
	 * this is held <em>in addition</em> to the array
	 * list...
	 */
	protected Set<String> allStrings;

	public History() {
		allStrings=new HashSet<String>();
	}

	@Override
	public boolean add(String entry) {
		allStrings.add(entry);
		return super.add(entry);
	}
	public int getLastIndex() {
		return size()-1;
	}
}