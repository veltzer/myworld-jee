package org.meta.jconsole;

import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Niraj Juneja
 */
public class StringUtil {
	/**
	 *
	 * @param prop
	 * @param pattern
	 * @return
	 */
	public static Properties subList(Properties prop, String pattern) {
		Properties aprop = new Properties();
		Enumeration enumi = prop.propertyNames();

		while (enumi.hasMoreElements()) {
			String str = (String) enumi.nextElement();

			if (str.startsWith(pattern)) {
				aprop.put(str, prop.getProperty(str));
			}
		}

		return aprop;
	}
}