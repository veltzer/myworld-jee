package org.meta.wrappers.java.nio.channels;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 *
 * @author mark
 */
public class SelectorUtil {
	/**
	 *
	 * @return
	 */
	public static Selector open() {
		try {
			return Selector.open();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
