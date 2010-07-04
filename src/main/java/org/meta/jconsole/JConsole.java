package org.meta.jconsole;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import org.meta.jconsole.commands.*;

/**
 *
 * @author mark
 */
public class JConsole implements Runnable {

	private static final String[] EMPTY_ARR = new String[0];
	private static final File DEF_FILE = new File(SystemProperties.get("user.home") + File.separator + "temp-sim.xml");
	private static String VERSION = SystemProperties.get("jconsole.version");


	private static final Class[] CMD_CLASS_LIST = new Class[] {
		org.meta.jconsole.commands.Help.class, // must be the first one
		org.meta.jconsole.commands.Exit.class,
		org.meta.jconsole.commands.Quit.class,
		org.meta.jconsole.commands.History.class,
		org.meta.jconsole.commands.Pwd.class,
		org.meta.jconsole.commands.Set.class,
		org.meta.jconsole.commands.Rmdir.class,
		org.meta.jconsole.commands.Ls.class ,
		org.meta.jconsole.commands.Cd.class ,
		org.meta.jconsole.commands.Cp.class ,
		org.meta.jconsole.commands.Cls.class,
		org.meta.jconsole.commands.Ptrace.class
	};
	private ConsoleCommand _helpCommand;
	private static JConsole s_instance = null;

	private List m_cmdList;
	private Map m_cmdMap;
	private String m_startCommand;
	private String[] m_startArgs;
	private BufferedReader m_stdinReader;

	/* if set to true, the verbose output will be displayed on the screen */
	private boolean m_verbose = false;

	/* hold the ConsoleHome */
	private String m_consoleHome = null;

		/* holds the current dir */
	private String m_currDir = null;

		/* keeps the alias for the commands defined in the property file*/
	private HashMap<String,String[]> m_aliases = new HashMap<String,String[]>();

		/* keeps the history of commands */
	private List<HistoryEntry> m_histryRWList = new ArrayList<HistoryEntry>();
	private List<HistoryEntry> m_histryROList = null;

	private int m_NoofCmdHist = 0;

	/**
	 *
	 * @param args
	 */
	public static void main(String [] args) {
		JConsole con = JConsole.instance();
		con.m_startCommand = (args.length > 0 ? args[0] : null);
		con.m_startArgs = EMPTY_ARR;
		if (args.length > 0) {
			con.m_startArgs = new String[args.length - 1];
			System.arraycopy(args, 1, con.m_startArgs, 0, args.length-1);
		}

		Thread t = new Thread( con, "JConsole" );
		t.start();
		try {
			t.join();
		} catch( InterruptedException e) {}
	}



	@Override
	 public void run() {
		try {
			start();
		} catch(Exception e) {
			Console.err.println("");
			e.printStackTrace();
			Console.err.println("Press Enter key...");
			try {
				new BufferedReader(new InputStreamReader(Console.in)).readLine();
			} catch(Exception e2) {
			}
		}
	}


	/*
	 * To make the class singlton.
	 */
	/**
	 *
	 * @return
	 */
	public static JConsole instance() {
		if (s_instance == null) {
			try {
				s_instance = new JConsole();
			} catch (Exception e) {
				throw new ConsoleSystemException("Could not load JConsole." , e);
			}
		}
		return s_instance;
	}

	private static String getVersion()
	{
	 return SystemProperties.get("jconsole.version");
	}

	/** Creates new JConsole */
	private JConsole() throws Exception{
				Console.out.println("Java Console " + getVersion() + " starting...");
		Console.out.println("");
				String rootHome = SystemProperties.getConsoleHome();
		m_consoleHome = "." ;
		m_verbose = "true".equals(System.getProperty("verbose"));
		try {
			m_NoofCmdHist = Integer.parseInt( System.getProperty("commandhist") );
		} catch (Exception e) {
			m_NoofCmdHist = 50; // default
		}

		File f = new File(m_consoleHome);
		if (! ( (f.exists()) && (f.isDirectory()) ) ){
			m_consoleHome = ".";
		}
		this.setCurrentDir(m_consoleHome);

		m_histryROList = Collections.unmodifiableList(m_histryRWList);

		m_stdinReader = new BufferedReader(new InputStreamReader(System.in));

		loadAlias();
		loadCommands();
		initializeCommands();

	}


	private void addCommand(Map<String,ConsoleCommand> m, List<ConsoleCommand> l, ConsoleCommand cmd) throws Exception {
		String name = cmd.getName();
				if (m.get(name) != null) {
			ConsoleCommand old = (ConsoleCommand) m.get(name);
			throw new Exception("Cannot load 2 commands with same name, " + old.getClass().getName() + ", " + cmd.getClass().getName());
		}

				cmd.setConsole(this);
		m.put(name, cmd);
		l.add(cmd);

	}

	private void loadInternalCommands(Map<String,ConsoleCommand> m, List<ConsoleCommand> l) throws Exception {
		//create commands
		for (int i = 0; i < CMD_CLASS_LIST.length; i++) {
			ConsoleCommand cmd = (ConsoleCommand) CMD_CLASS_LIST[i].newInstance();
			addCommand(m, l, cmd);
		}
		addCommand(m, l, new AliasCommand());
		addCommand(m, l, new HomeCommand());
	}

	private void loadExternalCommands(Map<String,ConsoleCommand> m, List<ConsoleCommand> l ) throws Exception {
		String prefix = "jconsole.command.";
		int prelen = prefix.length();

		Properties p = System.getProperties();
		for (Iterator i = p.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Map.Entry) i.next();
			String key = (String)e.getKey();
			String value = (String)e.getValue();
			if (key.startsWith(prefix) && key.length() > prelen) {
				try {
					Class cls = Class.forName(value);
					ConsoleCommand cmd = (ConsoleCommand) cls.newInstance();
					addCommand(m, l, cmd );
				}
				catch (Exception exp) {
					System.err.println("failed to load external command [" + value + "].");
					exp.printStackTrace();
					System.err.println("");
					System.err.println("");
				}
			}
		}
	}

	private void loadCommands() throws Exception {

		List<ConsoleCommand> l = new ArrayList<ConsoleCommand>();
		Map<String,ConsoleCommand> m = new HashMap<String,ConsoleCommand>();
		loadInternalCommands(m,l);
		loadExternalCommands( m, l);
		m_cmdList = Collections.unmodifiableList(l);
		m_cmdMap = Collections.unmodifiableMap(m);

		_helpCommand = (ConsoleCommand) m_cmdMap.get("help");

		if (_helpCommand == null) {
			throw new Exception("help command was not be loaded.");
		}
	}


	/*
	 * Loads the command aliases from the property file
	 */
	private void loadAlias() {
		Enumeration propNames = System.getProperties().propertyNames();
		while ( propNames.hasMoreElements() ) {
			String pName = (String)propNames.nextElement();
			if ((pName.startsWith("alias.")) && (pName.length() > 6)) {
				String val = System.getProperty(pName);
				m_aliases.put(pName.substring(6), tokenizeLine(val));
			}
		}
	}

	/*
	 * To initialize all the commands
	 */
	private void initializeCommands() throws Exception {
		for (Iterator i = m_cmdList.iterator(); i.hasNext();) {
			ConsoleCommand cmd = (ConsoleCommand) i.next();
			cmd.initialize();
		}
	}

	/*
	 * Command Console Prompt
	 */
	private void start() throws Exception {
		Console.out.println("");
		Console.out.println("Type <help> for command list.");
		Console.out.println("Type <help [command]> for help on specific commands.");
		Console.out.println("");
		if (m_startCommand != null) {
			ConsoleCommand cmd = getCommand(m_startCommand);
			if (cmd != null) {
				Console.out.println("Running Command " + m_startCommand);
				cmd.execute(m_startArgs);
			}
		}
		while (true) {
			displayPrompt(SystemProperties.get(SystemProperties.PROMPT));
			String line = this.getUserInput();
			processLine(line);
		}
	}


	/**
	 *
	 * @param pstr
	 */
	public final void displayPrompt(String pstr) {
		boolean printPath = "true".equals(System.getProperty("jconsole.prompt.path"));
		String pathPrint = (printPath ? m_currDir : "");
		Console.out.print("[" + pstr + " - " + pathPrint + "]> ");
		Console.out.flush();
	}

	/**
	 *
	 * @param str
	 */
	public final void displayMessage(String str) {
		Console.out.println(str);
		Console.out.flush();
	}

	/**
	 *
	 * @return
	 */
	public final String getUserInput() {
		try {
			return m_stdinReader.readLine();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe.getMessage());
		}
	}

	/**
	 *
	 * @param aKey
	 * @return
	 */
	public final File getFile(String aKey) {
		File result = null;
		Ls ls = (Ls)getCommand("ls");
		try {
			Integer aInt = new Integer(aKey);
			result = (File)ls.getSharedObject(aInt);
		} catch (NumberFormatException e) {
			File newDir = new File(getCurrentDir(), aKey );
			if ( newDir.exists() ) {
				result = newDir;
			}
			else {
				newDir = new File( aKey ); // needed when aKey is an absolute path
				if ( newDir.exists() ) {
					result = newDir;
				}
			}
		} // catch

		return result;

	}

	// parsed tokens
	private String[] tokenizeLine(String line) {
		StringTokenizer t = new StringTokenizer(line);
		int totTokens = t.countTokens();
		if (totTokens == 0) {
			return EMPTY_ARR;
		}
		String[] arr = new String[totTokens];
		for (int i = 0; t.hasMoreTokens(); i++) {
			arr[i] = t.nextToken();
		}
		return arr;
	}

	private String[] processAlias(String line) {
		String[] arr = tokenizeLine(line);
		if (arr.length == 0) {
			return EMPTY_ARR;
		}
		String[] aliasVal = m_aliases.get(arr[0]);
		if (aliasVal == null) {
			return arr;
		}

		String[] rtn = new String[aliasVal.length + arr.length - 1];
		System.arraycopy(aliasVal, 0, rtn, 0, aliasVal.length);
		System.arraycopy(arr, 1, rtn, aliasVal.length, arr.length - 1);
		return rtn;
	}

	/*
	 * Processes the command prompt line
	 */
	/**
	 *
	 * @param line
	 */
	public void processLine(String line) {
		line = line.trim();
		if( line.startsWith("#") ) return;
		if( line.equals("") ) return;

		String[] parr = processAlias(line);
		if (parr.length == 0) {
			return;
		}

		String cmdRun = parr[0];
		String[] arr = new String[parr.length-1];
		System.arraycopy(parr, 1, arr, 0, arr.length);

		ConsoleCommand cmd = getCommand(cmdRun);

		if (line.startsWith("!") == false && cmd != null) {
			m_histryRWList.add( new HistoryEntry( line, cmd, arr ) );
		}

		if (cmd == null) {

			String fn = parr[0];

			File scr = new File( getCurrentDir(), fn);
			if( scr.exists() && isScript(scr.getName()) ) {
				executeCommand(getCommand("batch"), new String[]{fn} );
			}
			else if( new File( getCurrentDir(), fn+".jcon").exists() ) {
				executeCommand(getCommand("batch"), new String[]{fn+".jcon"} );
			}
			else {
				Console.out.println("Command or script " + cmdRun + " not found.");
				executeCommand(_helpCommand, EMPTY_ARR);
			}

			return;
		}

		executeCommand(cmd, arr);

		if (m_histryRWList.size() > m_NoofCmdHist ) {
			m_histryRWList.remove(0);
		}
	}

	private void executeCommand(ConsoleCommand cmd, String[] arr) {
		try {
			cmd.execute(arr);
		}
		catch (CommandFailedException ee) {
			System.err.println("Command " + cmd.getName() + " failed: " +ee.getMessage());
		}
		catch (RuntimeException exp) {
			exp.printStackTrace();
		}
	}

	List getAllCommandsList() {
		return m_cmdList;
	}

	Map getAllCommandsMap() {
		return m_cmdMap;
	}

	/**
	 *
	 * @param cmdName
	 * @return
	 */
	public ConsoleCommand getCommand(String cmdName) {
		return (ConsoleCommand) m_cmdMap.get(cmdName);
	}


	/*
	 * To get the current console dir
	 */
	String getCurrentDir() {
		return m_currDir;
	}

	/*
	 * To set the console current dir
	 */
	void setCurrentDir(String dir) {
		File f = new File(dir);
		if (f.exists() && f.isDirectory()) {
			try {m_currDir = f.getCanonicalPath(); } catch (IOException e) {}
		}
	}

	/*
	 * returns the ReadOnly-History list
	 */
	/**
	 *
	 * @return
	 */
	public List getCommandHistory() {
		return m_histryROList;
	}

	/**
	 *
	 * @return
	 */
	public boolean getVerbose() {
		return m_verbose;
	}


	boolean isScript( String name ) {
		int offset = name.length()-4;
		return name.startsWith( ".jcon", offset ) || name.startsWith( ".JCON", offset );
	}


	class AliasCommand extends ConsoleCommand {
		// the constructor
		public AliasCommand() {
			super("alias", "used to display the command aliases.", "- alias");
		}

		// the execute logic
		@Override
		public void execute(String[] args) throws CommandFailedException {
			if ((m_aliases == null) || (m_aliases.size() == 0)) {
				super.throwCmdFailed("No Aliases...");
			}
			if (args.length == 0) { // display aliases
				Iterator it = m_aliases.keySet().iterator();
				while (it.hasNext()) {
					String key = (String)it.next();
					String[] val = m_aliases.get(key);
					dispAlias(key, val);
				}
				return;
			}
			if (args.length == 1 ) { // display given alias
				String[] val = m_aliases.get(args[0]);
				if (val == null) {
					super.throwCmdFailed("Alias not found");
				}
				dispAlias(args[0], val);
				return;
			}

			String[] vals = new String[args.length -1];
			System.arraycopy(args, 1, vals, 0, vals.length);
			m_aliases.put(args[0], vals);
		}

		private void dispAlias(String key, String[] val) {
			StringBuffer sb = new StringBuffer();
			for (int x=0; x < val.length; x++) {
				if (! "".equals(val[x]) ) {
					sb.append(val[x] + " ");
				}
			}
			Console.out.println(key + "\t= " + sb.toString() );
		}

		@Override
		protected void initialize() throws Exception {
		}
		/*
		protected boolean needsMQ() {
			return false;
		}
		*/
	}

	class HomeCommand extends ConsoleCommand {
		private String PREFIX = "console.home.";
		private int PRELEN = PREFIX.length();

		private Map<String,String> m_alternateHomes = new HashMap<String,String>();

		public HomeCommand() {
			super("home", "cd to the console home dir.",
			"usage 1: \n console> home\n cd to console home dir.\n" +
			"usage 2: \n console> home -a\n Prints all defined homes.\n" +
			"usage 3: \n console> home alternate-home-str\n cd to alternate home.\n" +
			"usage 4: \n console> home alternate-home-str alternate-home-location\n defines alternate home.\n"
			);
		}

		private void printHomes() {
			Console.out.println("Console Home : " + m_consoleHome);
			for (Iterator i = m_alternateHomes.entrySet().iterator(); i.hasNext();) {
				Map.Entry e = (Map.Entry) i.next();
				Console.out.println("Console Home, " + e.getKey() + " : " + e.getValue());
			}
		}

		@Override
		public void execute(String[] args) throws CommandFailedException {
			if (args == null || args.length == 0) {
				this.setCurrentDir(m_consoleHome);
				return;
			}
			if (args.length > 0 && args[0].startsWith("-a")) {
				printHomes();
				return;
			}
			if (args.length == 1) {
				String path = m_alternateHomes.get(args[0]);
				if (path == null) {
					super.throwCmdFailed("Alternate home [" + args[0] + "] not defined.");
				}
				this.setCurrentDir(path);
				return;
			}

			// now args has atleast 2 args
			if (!addAlternateHome(args[0], args[1])) {
				super.throwCmdFailed("Directory [" + args[1] + "] either does not exist or cannot be read.");
			}

		}
/*
		protected boolean needsMQ() {
			return false;
		}
 */

		@Override
		protected void initialize() throws Exception {
			Properties p = System.getProperties();
			for (Enumeration e = p.keys(); e.hasMoreElements();) {
				String key = e.nextElement().toString();
				if (key.length() > PRELEN && key.startsWith(PREFIX)) {
					String alternateHomeKey = key.substring(PRELEN);
					addAlternateHome(alternateHomeKey, p.getProperty(key).trim());
				}
			}
		}

		private boolean addAlternateHome(String key, String value) {
			File f = new File(value);
			if (!f.exists() || !f.isDirectory() || !f.canRead()) {
				return false;
			}
			m_alternateHomes.put(key, f.getAbsolutePath());
			return true;
		}

	}
}