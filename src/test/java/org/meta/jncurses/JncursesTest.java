/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.meta.jncurses;

import junit.framework.TestCase;
import org.meta.util.Utils;

/**
 *
 * @author mark
 */
public class JncursesTest extends TestCase {


	public void testIt() {
		Ncurses.load();
		Ncurses.getInstance().init();
		Utils.sleep(5000);
		Ncurses.getInstance().finish();
	}
}