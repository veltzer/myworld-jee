package org.meta.autocomplete;

import java.util.List;

/**
 * This is an abstract class for an autocomplete data structure
 *
 * @author mark
 */
public abstract class AutoComplete {
	/**
	 * Add a string to this auto complete instance.
	 * @param s the string to add
	 */
	public abstract void add(String s);
	/**
	 * A convenience method to add a list of strings to the trie:w
	 * @param list the list for which all elements would be added
	 */
	public void add(List<String> list) {
		for(String x: list) {
			add(x);
		}
	}
	/**
	 * This is the heavy lifter. Give it a prefix and it will return
	 * the list of available completions.
	 * @param prefix
	 * @return A list of completions for this prefix
	 */
	public abstract List<String> findCompletions(String prefix);
	/**
	 * This method only returns whether the prefix is matched or not
	 *
	 * @param prefix
	 * @return
	 */
	public abstract boolean matchPrefix(String prefix);
	/**
	 * This is the most complex method that returns the completions
	 * but also the prefix that they all share.
	 * example:
	 * if the structure has tree, train, truffle, triad, banana
	 * the "t" will return all tree staring words but also "tr".
	 *
	 * @param prefix
	 * @return
	 */
	public abstract AutoCompleteResult findCompletionsResult(String prefix);
}