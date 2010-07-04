package org.meta.examples;

/**
 * This example explores unicode usage, both in regular java and using my curses
 * module...
 * @author mark
 */

import org.meta.jncurses.Ncurses;

public class Unicode {

	static public void main(String[] args) {
		/*
		 * The next piece of code works and shows the unicode ok
		 * both inside development environments and on the cmdline...
		 */
		/*
		String s="מרק";
		System.out.println(s);
		*/
		String s="מרק";
		Ncurses.load();
		Ncurses.getInstance().init();
		Ncurses.getInstance().println(s);
		Ncurses.getInstance().refresh();
		Ncurses.getInstance().finish();
	}

}
