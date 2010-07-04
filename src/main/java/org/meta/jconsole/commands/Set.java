package org.meta.jconsole.commands;

import org.meta.jconsole.Console;
import org.meta.jconsole.ConsoleCommand;
import org.meta.jconsole.StringUtil;

/**
 *
 * @author mark
 */
public class Set extends ConsoleCommand {
	private static final String NAME = "set";
	private static final String DESC = "allows to change properties that affect behaviour of other commands /n or list properties with the pattern";
	private static final String HELP = "set [property [value|?]] \n set [property*] : for pattern listing";
	/**
	 *
	 */
	public Set() {
		super(NAME, DESC, HELP);
	}
	/**
	 *
	 * @param args
	 */
	@Override
	public void execute(String[] args) {
		if (args.length == 0) {
			System.getProperties().list( Console.out );
		}
		else if (args.length == 1) {
			StringUtil.subList(System.getProperties() , args[0]).list(Console.out);
		}
		else if (args.length == 2) {
			if( "?".equals( args[1] ) ) {
				m_sim.displayMessage( args[0] + "=" + System.getProperty( args[0] ) );
			}
			else { System.setProperty( args[0], args[1] );
				m_sim.displayMessage(args[0] + " = " + System.getProperty(args[0]));
			}
		}
	}
	/**
	 *
	 */
	@Override
	protected void initialize() {
	}
}