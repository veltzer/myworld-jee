package org.meta.jconsole.commands;

import java.net.InetAddress;
import org.meta.jconsole.CommandFailedException;
import org.meta.jconsole.Console;
import org.meta.jconsole.ConsoleCommand;

/**
 * @author nzjuneja
 */
public class Ping extends ConsoleCommand {
	private static final String DESC = "The network ping";
	private static final String HELP = "ping [ip / Host]";
	private static final String NAME = "ping";

	/** Creates new echoCommand */
	public Ping() {
		super(NAME, DESC, HELP);
	}

	/**
	 *
	 */
	@Override
	protected void initialize() {}

	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		InetAddress ad = null;

		try {
			if (args.length > 0) {
				ad = InetAddress.getByName(args[0]);
			} else {
				ad = InetAddress.getByName(null);
			}
		} catch (Exception ex) {
			throw new CommandFailedException("Unable to ping " + args[0], ex);
		}

		Console.out.println("HOST = " + ad.getHostName() + " IP= " + ad.getHostAddress());
	}
}