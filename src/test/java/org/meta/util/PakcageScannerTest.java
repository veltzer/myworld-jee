package org.meta.util;

import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author mark
 */
public class PakcageScannerTest extends TestCase {
    
	/**
	 * Test of getClasses method, of class PakcageScanner.
	 */
	public void testIt() throws Exception {
		Set<Class<?>> list = PackageScanner.getClasses("org.meta.richfunction.impl",true);
		for(Class<?> x:list) {
			System.out.println(x.getCanonicalName());
		}
	 }
}