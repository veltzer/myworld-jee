package org.meta.autocomplete;

/**
 * This is an autocomplete factory. Always create autocomplete elements with
 * this one.
 * @author mark
 */
public class Factory {
	/**
	 * This is a static method to create autocomplete instances.
	 * The current implementation is to create trees.
	 * @return an empty AutoComplete instance that you can use
	 */
	public static AutoComplete createAutoComplete() {
		return new TrieTree();
	}
}
