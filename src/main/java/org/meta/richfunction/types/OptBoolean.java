package org.meta.richfunction.types;

import org.meta.richfunction.Option;

/**
 * This is a boolean paramter
 * @author mark
 */
public class OptBoolean implements Option<Boolean> {
	/**
	 *
	 */
	protected String name;
	/**
	 *
	 */
	protected String description;
	/**
	 *
	 */
	protected Boolean def;

	/**
	 *
	 * @return
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param s
	 */
	@Override
	public void setName(String s) {
		name=s;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @param s
	 */
	@Override
	public void setDescription(String s) {
		description=s;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public Boolean getDefault() {
		return def;
	}

	/**
	 *
	 * @param t
	 */
	@Override
	public void setDefault(Boolean t) {
		def=t;
	}

	/**
	 *
	 * @param s
	 * @return
	 */
	@Override
	public Boolean ValueFromString(String s) {
		if(s.equals("true")) {
			return Boolean.TRUE;
		} else {
			if(s.equals("false")) {
				return Boolean.FALSE;
			} else {
				throw new RuntimeException("what kind of boolean is "+s);
			}
		}
	}

	/**
	 *
	 * @param val
	 * @return
	 */
	@Override
	public String StringFromValue(Boolean val) {
		if(val.booleanValue()==true) {
			return "true";
		} else {
			return "false";
		}
	}
}