package meta.utils;

import java.io.EOFException;

import org.gnu.readline.Readline;
import org.gnu.readline.ReadlineLibrary;

public abstract class Shell {
	private boolean load_readline = false;

	private String appname = Config.getConfig().getString("myapp");

	private String prompt = "myapp> ";

	public Shell() {
		if (load_readline) {
			try {
				Readline.load(ReadlineLibrary.GnuReadline);
			} catch (UnsatisfiedLinkError ignore_me) {
				System.err
						.println("couldn't load readline lib. Using simple stdin.");
			}
		}

		Readline.initReadline(appname);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Readline.cleanup();
			}
		});
	}

	public abstract void process(String cmd, String[] args);

	public final void run() {

		while (true) {
			try {
				String line = Readline.readline(prompt);
				if (line == null) {
					System.out.println("no input");
				} else {
					String[] args = line.split(" ");
					process(args[0], args);
				}
				// System.out.println("line is " + line);
			} catch (EOFException e) {
				System.out.println("EOF has occured");
				break;
			} catch (Exception e) {
				System.out.println("Exception has occured");
			}
		}
		Readline.cleanup(); // see note above about addShutdownHook

	}

	public static void main(String[] args) {
		/* This demos how to use the Shell object */
		Shell shell = new Shell() {
			@Override
			public void process(String cmd, String[] args) {
				System.out.println(args);
			}
		};
		shell.run();
	}

}