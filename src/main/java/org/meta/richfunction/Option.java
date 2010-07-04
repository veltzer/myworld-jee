/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.meta.richfunction;

/**
 * This is a single type of option
 *
 * Each option should be able to tell:
 * 1. It's name 
 * 2. It's description.
 * 3. It's default value (if any).
 * 4. Is it mandatory ?
 * 5. An auto completion function for it's value
 * 6. Validation function for the value.
 *
 * The value is not to be stored here
 *
 * @param <T>
 * @author mark
 */
public interface Option<T> {
	/**
	 *
	 * @return
	 */
	public String getName();
	/**
	 *
	 * @param s
	 */
	public void setName(String s);
	/**
	 *
	 * @return
	 */
	public String getDescription();
	/**
	 *
	 * @param s
	 */
	public void setDescription(String s);
	/**
	 *
	 * @return
	 */
	public T getDefault();
	/**
	 *
	 * @param t
	 */
	public void setDefault(T t);
	/**
	 *
	 * @param s
	 * @return
	 */
	public T ValueFromString(String s);
	/**
	 *
	 * @param val
	 * @return
	 */
	public String StringFromValue(T val);
}