package org.meta.wrappers.java.util;

import org.meta.exceptions.AlreadyHaveKey;
import org.meta.exceptions.NoSuchValue;

/**
 * This is a linked hash map which adds a method which inserts and makes sure
 * that the value is not there...
 * @param <K>
 * @param <V>
 * @author mark
 */
public class LinkedHashMap<K,V> extends java.util.LinkedHashMap<K,V> {

	@Override
	//@SuppressWarnings("element-type-mismatch")
	public V get(Object key) {
		if(containsKey(key)) {
			return super.get(key);
		} else {
			throw new NoSuchValue("linkedhashmap",key);
		}
	}

	/**
	 * Insert a new value into the hashmap. If the value exists this
	 * will throw a runtime exception.
	 * @param key
	 * @param value
	 * @return
	 */
	public V insert(K key, V value) {
		if(containsKey(key)) {
			throw new AlreadyHaveKey("hashmap insert",key,value);
		}
		return super.put(key, value);
	}
}