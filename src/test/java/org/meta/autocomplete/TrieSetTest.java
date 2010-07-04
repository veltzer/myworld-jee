package org.meta.autocomplete;

import junit.framework.TestCase;

/**
 *
 * @author mark
 */
public class TrieSetTest extends TestCase {
    
	/**
	 * Test of findCompletionsResult method, of class TrieSet.
	 */
	public void testFindCompletionsResult() {
		System.out.println("findCompletionsResult");
		String prefix = "t";
		TrieSet instance1 = new TrieSet();
		instance1.add("truck");
		instance1.add("tree");
		instance1.add("banana");
		AutoCompleteResult result1 = instance1.findCompletionsResult(prefix);
		assertEquals(result1.prefix, "tr");
		TrieTree instance2 = new TrieTree();
		instance2.add("truck");
		instance2.add("tree");
		instance2.add("banana");
		AutoCompleteResult result2 = instance2.findCompletionsResult(prefix);
		assertEquals(result2.prefix, "tr");
	}

}