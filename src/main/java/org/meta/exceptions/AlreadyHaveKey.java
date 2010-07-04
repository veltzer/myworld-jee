package org.meta.exceptions;

/**
 * This is an exception to be used when a container is asked to store the same key twice.
 * @author mark
 */
public class AlreadyHaveKey extends RuntimeException {
	/**
	 *
	 */
	protected Object key;
	/**
	 *
	 */
	protected Object value;
	/**
	 *
	 * @param message
	 * @param key
	 * @param value
	 */
	public AlreadyHaveKey(String message,Object key, Object value) {
		super(message);
		this.key=key;
		this.value=value;
	}
}