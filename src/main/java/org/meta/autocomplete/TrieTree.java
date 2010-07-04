package org.meta.autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.meta.util.Utils;

/**
 *
 * @author mark
 */
public class TrieTree extends AutoComplete {
	/**
	 *
	 */
	public class TrieNode {

		private Character character;
		private HashMap<Character,TrieNode> children;

		/**
		 *
		 * @param c
		 */
		public TrieNode(char c) {
			super();
			this.character = new Character(c);
			children = new HashMap<Character,TrieNode>();
		}

		/**
		 *
		 * @return
		 */
		public char getNodeValue() {
			return character.charValue();
		}

		/**
		 *
		 * @return
		 */
		public Collection<TrieNode> getChildren() {
			return children.values();
		}

		/**
		 *
		 * @return
		 */
		public Set<Character> getChildrenNodeValues() {
			return children.keySet();
		}

		/**
		 *
		 * @param c
		 */
		public void add(char c) {
			if (children.get(new Character(c)) == null) {
				// children does not contain c, add a TrieNode
				children.put(new Character(c), new TrieNode(c));
			}
		}

		/**
		 *
		 * @param c
		 * @return
		 */
		public TrieNode getChildNode(char c) {
			return children.get(new Character(c));
		}

		/**
		 *
		 * @param c
		 * @return
		 */
		public boolean contains(char c) {
			return (children.get(new Character(c)) != null);
		}

		@Override
		public int hashCode() {
			return character.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof TrieNode)) {
				return false;
			}
			TrieNode that = (TrieNode) obj;
			return (this.getNodeValue() == that.getNodeValue());
		}

		@Override
		public String toString() {
			return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		}
	}
	private TrieNode rootNode;
	/**
	 *
	 */
	public TrieTree() {
		super();
		rootNode = new TrieNode(' ');
	}
	/**
	 *
	 * @param phrase
	 */
	@Override
	public void add(String phrase) {
		loadRecursive(rootNode, phrase + "$");
	}

	private void loadRecursive(TrieNode node, String phrase) {
		if (StringUtils.isBlank(phrase)) {
			return;
		}
		char firstChar = phrase.charAt(0);
		node.add(firstChar);
		TrieNode childNode = node.getChildNode(firstChar);
		if (childNode != null) {
			loadRecursive(childNode, phrase.substring(1));
		}
	}
	/**
	 *
	 * @param prefix
	 * @return
	 */
	@Override
	public boolean matchPrefix(String prefix) {
		TrieNode matchedNode = matchPrefixRecursive(rootNode, prefix);
		return (matchedNode != null);
	}
	private TrieNode matchPrefixRecursive(TrieNode node, String prefix) {
		if (StringUtils.isBlank(prefix)) {
			return node;
		}
		char firstChar = prefix.charAt(0);
		TrieNode childNode = node.getChildNode(firstChar);
		if (childNode == null) {
			// no match at this char, exit
			return null;
		} else {
			// go deeper
			return matchPrefixRecursive(childNode, prefix.substring(1));
		}
	}
	/**
	 *
	 * @param prefix
	 * @return
	 */
	@Override
	public List<String> findCompletions(String prefix) {
		TrieNode matchedNode = matchPrefixRecursive(rootNode, prefix);
		List<String> completions = new ArrayList<String>();
		findCompletionsRecursive(matchedNode, prefix, completions);
		return completions;
	}
	private void findCompletionsRecursive(TrieNode node, String prefix, List<String> completions) {
		if (node == null) {
			// our prefix did not match anything, just return
			return;
		}
		if (node.getNodeValue() == '$') {
			// end reached, append prefix into completions list. Do not append
			// the trailing $, that is only to distinguish words like ann and anne
			// into separate branches of the tree.
			completions.add(prefix.substring(0, prefix.length() - 1));
			return;
		}
		Collection<TrieNode> childNodes = node.getChildren();
		for (TrieNode childNode : childNodes) {
			char childChar = childNode.getNodeValue();
			findCompletionsRecursive(childNode, prefix + childChar, completions);
		}
	}
	@Override
	public String toString() {
		return "Trie:" + rootNode.toString();
	}
	private void findCompletionsResultRecursive(TrieNode node, String prefix, AutoCompleteResult acr) {
		if (node == null) {
			// our prefix did not match anything, just return
			return;
		}
		if (node.getNodeValue() == '$') {
			// end reached, append prefix into completions list. Do not append
			// the trailing $, that is only to distinguish words like ann and anne
			// into separate branches of the tree.
			String toAdd=prefix.substring(0, prefix.length() - 1);
			acr.getList().add(toAdd);
			if(acr.prefix==null) {
				acr.prefix=toAdd;
			} else {
				acr.prefix=Utils.commonPrefix(acr.prefix, toAdd);
			}
			return;
		}
		Collection<TrieNode> childNodes = node.getChildren();
		for (TrieNode childNode : childNodes) {
			char childChar = childNode.getNodeValue();
			findCompletionsResultRecursive(childNode, prefix + childChar, acr);
		}
	}
	@Override
	public AutoCompleteResult findCompletionsResult(String prefix) {
		TrieNode matchedNode = matchPrefixRecursive(rootNode, prefix);
		AutoCompleteResult acr=new AutoCompleteResult();
		findCompletionsResultRecursive(matchedNode, prefix, acr);
		return acr;
	}
}