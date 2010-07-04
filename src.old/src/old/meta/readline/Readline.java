package meta.readline;

import jcurses.system.CharColor;
import jcurses.system.InputChar;

/* TODO:
 * - find the current position from the toolkit - and if that is not possible then
 * - just do the clear strategy we are using here.
 * - enable to change colors in mid way
 * - print out user responses
 * - have a history so that user can go back. Store that in a database once in a while.
 * 	(create a table for that).
 * - scan the path variable and complete command line stuff - can we use bash completion ?
 * - enable prompt with substitution via some templating library
 * - enable to show the substitution via the cmd line
 * - make this class a singleton.
 * - have a set of escape characters that if pressed will cause the system to shut down.
 * - enable to set the color (background and foreground) from the console.
 **/

public class Readline {
	static final int KEY_ENTER = 10;
	static final int KEY_ESC = 27;
	private int xpos;
	private int ypos;
	private int width;
	private int height;
	private short background = jcurses.system.CharColor.BLACK;
	private short foreground = jcurses.system.CharColor.WHITE;
	private CharColor mycolor = new CharColor(background, foreground);
	private static Readline instance = null;

	public int getHeight() {
		return height;
	}

	static public synchronized Readline getInstance() {
		if (instance == null) {
			instance = new Readline();
		}
		return instance;
	}

	private Readline() {
		jcurses.system.Toolkit.init();
		clear();
		// register a shutdown hook in case we get interrupted in mid stream
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				jcurses.system.Toolkit.shutdown();
			}
		});
	}

	public void clear() {
		jcurses.system.Toolkit.startPainting();
		jcurses.system.Toolkit.clearScreen(mycolor);
		jcurses.system.Toolkit.endPainting();
		xpos = 0;
		ypos = 0;
		width = jcurses.system.Toolkit.getScreenWidth();
		height = jcurses.system.Toolkit.getScreenHeight();
	}

	public void startPainting() {
		jcurses.system.Toolkit.startPainting();
	}

	public void endPaintng() {
		jcurses.system.Toolkit.endPainting();
	}

	public void print(StringBuffer s, int from, int to) {
		jcurses.system.Toolkit.printString(s.substring(from, to), xpos, ypos,
				mycolor);
	}

	public void write(String s) {
		int length = s.length();
		StringBuffer sb = new StringBuffer(s);

		int index = 0;
		while (xpos + length - index > width) {
			int cur_len = width - xpos;
			print(sb, index, index += cur_len);
			xpos = 0;
			ypos++;
		}
		// TODO - maybe y needs to move to and maybe scroll is in order too
		print(sb, index, length);
		xpos += s.length();
	}

	public InputChar read() {
		InputChar ic = jcurses.system.Toolkit.readCharacter();
		return ic;
	}

	public void up() {
		ypos--;
		// TODO: check that y is not less than 0
	}

	public void enter() {
		ypos++;
		xpos = 0;
		// TODO: handle scroll down if y reaches limit
	}

	public void backspace() {
		if (xpos == 0) {
			ypos--;
			// TODO: goto the end of the previous line
		} else {
			xpos--;
			// erase character if backspace means erase
		}
	}

	public static void main(String[] args) {
		Readline rl = Readline.getInstance();

		boolean over = false;
		while (!over) {
			InputChar c = rl.read();
			if (c.getCode() == KEY_ENTER) {
				rl.enter();
				continue;
			}
			if (c.getCode() == KEY_ESC) {
				over = true;
				continue;
			}
			if (c.getCode() == InputChar.KEY_UP) {
				rl.up();
				continue;
			}
			if (c.getCode() == InputChar.KEY_BACKSPACE) {
				rl.backspace();
			}
			if (!c.isSpecialCode()) {
				rl.write(c.toString());
				// rl.write(Integer.toString(c.hashCode()));
			}
		}
	}
}
