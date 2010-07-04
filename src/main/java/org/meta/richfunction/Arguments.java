package org.meta.richfunction;

import java.util.ArrayList;
import java.util.List;
import org.meta.jconsole.StringUtil;
import org.meta.wrappers.java.util.LinkedHashMap;

/**
 * This is the class which holds all the arguments for a specific function
 * @author mark
 */
public class Arguments extends LinkedHashMap<String, Object> {
	/**
	 * This is the entire line string
	 */
	protected String line;
	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}
	public void setLine(String iLine) {
		line=iLine;
	}
	public String getArgString() {
		return line.substring(line.indexOf(" ")+1);
	}
	/**
	 * this is the name of the function executed
	 */
	protected String name;

	/**
	 * @return The name of the function activated
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the function
	 * @param name the name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * These are the free argument supplied to the function
	 */
	protected List<String> freeArgs;

	/**
	 *
	 */
	public Arguments() {
		super();
		freeArgs=new ArrayList<String>();
	}
	/**
	 *
	 * @param s
	 */
	public void addFreeArg(String s) {
		freeArgs.add(s);
	}
	/**
	 *
	 * @return
	 */
	public List<String> getFreeArgs() {
		return freeArgs;
	}
	/**
	 *
	 * @param s
	 * @return
	 */
	public Boolean getBoolean(String s) {
		return (Boolean)get(s);
	}
	/**
	 *
	 * @param s
	 * @return
	 */
	public String getString(String s) {
		return (String)get(s);
	}
	/**
	 *
	 * @param s
	 * @return
	 */
	public Integer getInteger(String s) {
		return (Integer)get(s);
	}
	/**
	 *
	 * @param s
	 * @return
	 */
	public Float getFloat(String s) {
		return (Float)get(s);
	}
}