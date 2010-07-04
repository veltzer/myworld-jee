/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.meta.jncurses;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 *
 * @author mark
 */
public interface Jncurses extends Library {

/** Simple example of JNA interface mapping and usage. */
    // This is the standard, stable way of mapping, which supports extensive
    // customization and mapping of Java to native types.

        Pointer initscr();

	int cbreak();
	int nocbreak();

	int noecho();
	int echo();

	int endwin();
	int erase();
	int clear();
	int refresh();
	int getch();
	int addch(char c);
	int delch();

	int nonl();
	int nl();
	int gety(Pointer win);
	int getx(Pointer win);
	int mvdelch(int y,int x);

	void beep();
	void flash();

	int curs_set(int visibility);

	final static char KEY_LF=10;
	final static char KEY_ENTER=13;
	final static char KEY_ESCAPE=27;
	final static char KEY_BACKSPACE=127;
	final static char KEY_TAB=9;
}