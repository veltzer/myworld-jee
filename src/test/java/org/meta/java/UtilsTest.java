package org.meta.java;

import org.meta.util.Utils;
import junit.framework.TestCase;

/**
 *
 * @author mark
 */
public class UtilsTest extends TestCase {
	/**
	 * 
	 */
	public void testReplaceSuffix() {
		System.out.println("replaceSuffix");
		String orig = "this.is.an.example.orig";
		String suffix = ".new";
		String expResult = "this.is.an.example.new";
		String result = Utils.replaceSuffix(orig, suffix);
		System.out.println("result is "+result);
		assertEquals(expResult, result);
	}
	public void testCommonPrefix() {
		String a="this is a long sentance";
		String b="this is a short one";
		String result=Utils.commonPrefix(a, b);
		String expResult="this is a ";
		assertEquals(expResult, result);
	}
}