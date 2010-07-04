package org.meta.exceptions;

/**
 * This is an exception when a container does not find an object with the right name.
 * @author mark
 */
public class NoSuchValue extends RuntimeException {

	@Override
	public String toString() {
		return super.toString()+", key is "+o;
	}
	/**
	 * This is the object which is missing
	 */
	protected Object o;
	/**
	 *
	 * @param s
	 * @param o
	 */
	public NoSuchValue(String s,Object o) {
		super(s);
		this.o=o;
	}
}
