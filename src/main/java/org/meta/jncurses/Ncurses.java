package org.meta.jncurses;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * A single pattern
 * @author mark
 */
public class Ncurses {
	private static Jncurses curses=null;
	private static Ncurses instance=null;
	private static Pointer win=null;
	public static void load() {
		curses = (Jncurses)Native.loadLibrary("jnacurses", Jncurses.class);
		//curses = (Jncurses)Native.loadLibrary("ncurses", Jncurses.class);
		//System.out.println("INSTACE is "+INSTANCE);
	}
	public static Ncurses getInstance() {
		if(instance==null) {
			instance=new Ncurses();
		}
		return instance;
	}
	public void init() {
		win=curses.initscr();
		curses.cbreak();
		curses.noecho();
		curses.nonl();
	}
	public void finish() {
		//curses.refresh();
		//curses.nl();
		//curses.echo();
		//curses.nocbreak();
		//curses.clear();
		curses.endwin();
		//curses.refresh();
	}
	public void print(String s) {
		byte[] bytes=s.getBytes();
		for(int i=0;i<bytes.length;i++) {
			curses.addch((char) bytes[i]);
		}
		/*
		for(int i=0;i<s.length();i++) {
			curses.addch(s.charAt(i));
		}
		 */
	}
	public void println(String s) {
		print(s);
		curses.addch('\n');
	}
	public void showChar(char c) {
		curses.addch(c);
	}
	public int getch() {
		return curses.getch();
	}
	public void refresh() {
		curses.refresh();
	}
	public void hide_cursor() {
		curses.curs_set(0);
	}
	public void show_cursor() {
		curses.curs_set(1);
	}
	public void blink_cursor() {
		curses.curs_set(2);
	}
	public void eraseChar() {
		int x=getx();
		int y=gety();
		curses.mvdelch(y,x-1);
	}
	public int gety() {
		return curses.gety(win);
	}
	public int getx() {
		return curses.getx(win);
	}
	public void beep() {
		curses.beep();
	}
	public void flash() {
		curses.flash();
	}
}
