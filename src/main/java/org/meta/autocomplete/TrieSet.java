package org.meta.autocomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.meta.util.Utils;

/**
 *
 * @author mark
 */
public class TrieSet extends AutoComplete {

	private TreeSet<String> lines;

	/**
	 * An empty constructor
	 */
	public TrieSet() {
		lines=new TreeSet<String>();
	}

	/**
	 *
	 * @param line
	 */
	@Override
	public void add(String line) {
		lines.add(line);
	}

	/**
	 *
	 * @param prefix
	 * @return
	 */
	@Override
	public boolean matchPrefix(String prefix) {
		Set<String> tailSet = lines.tailSet(prefix);
		for (String tail:tailSet) {
			if (tail.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param prefix
	 * @return
	 */
	@Override
	public List<String> findCompletions(String prefix) {
		List<String> completions=new ArrayList<String>();
		Set<String> tailSet=lines.tailSet(prefix);
		for(String tail:tailSet) {
			if (tail.startsWith(prefix)) {
				completions.add(tail);
			} else {
				break;
			}
		}
		return completions;
	}

	@Override
	public AutoCompleteResult findCompletionsResult(String prefix) {
		AutoCompleteResult acr=new AutoCompleteResult();
		Set<String> tailSet=lines.tailSet(prefix);
		boolean seen=false;
		for(String tail:tailSet) {
			if (tail.startsWith(prefix)) {
				acr.list.add(tail);
				if(seen) {
					acr.prefix=Utils.commonPrefix(acr.prefix, tail);
				} else {
					seen=true;
					acr.prefix=tail;
				}
			} else {
				break;
			}
		}
		return acr;
	}
}