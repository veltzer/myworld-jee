package org.meta.autocomplete;

import java.util.ArrayList;
import java.util.List;

/**
 * This is just a wrapper to put all results together.
 * @author mark
 */
public class AutoCompleteResult {

	/**
	 *
	 */
	public List<String> list;
	/**
	 *
	 */
	public String prefix;

	/**
	 *
	 * @return
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 *
	 * @return
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 *
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 *
	 */
	public AutoCompleteResult() {
		list=new ArrayList<String>();
	}
}