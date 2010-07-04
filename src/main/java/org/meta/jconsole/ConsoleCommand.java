package org.meta.jconsole;

import java.io.*;
import java.util.*;

/**
 * @author Mark Veltzer
 */
public abstract class ConsoleCommand {
	private static final File DEF_FILE=new File(System.getProperty("user.home") + File.separator + "temp-sim.xml");
	static File s_showFile;
	private String m_help;
	private String m_name;
	private String m_shortDescription;
	/**
	 *
	 */
	protected JConsole m_sim;

	/** Creates new ConsoleCommand
	 * @param commandName
	 * @param shortDesc
	 * @param commandHelp
	 */
	public ConsoleCommand(String commandName, String shortDesc, String commandHelp) {
		m_name=commandName;
		m_shortDescription=shortDesc;
		m_help=commandHelp;
	}
	/**
	 *
	 * @return
	 */
	protected final File getTempSimFile() {
		return DEF_FILE;
	}
	/**
	* @param in the InputStream, i.e. the source, closes the passed input stream
	* @param fn where to output the file (numbers are ok)
	*/
	protected final void fileForShow(InputStream in, String fn) {
	File f=(((fn == null) || fn.equals(""))
						? DEF_FILE
						: getFile(fn));
		boolean fout = "true".equals(System.getProperty("file.echo"));

		try {
			FileOutputStream out = new FileOutputStream(f);
			int			b=0;

			while ((b = in.read()) != -1) {
				if (fout) {
					Console.out.write(b);
				}

				out.write(b);
			}

			out.flush();
			out.close();
			Console.out.flush();
			in.close();
			s_showFile = f;
		} catch (Exception exp) {
			System.err.println("FAILED TO WRITE TO OUTPUT FILE " + f.getAbsolutePath());
			exp.printStackTrace();
		}
	}

	/**
	 *
	 * @param fn
	 */
	protected final void fileForShow(String fn) {
		if ((fn == null) || fn.equals("")) {
			return;
		}

		s_showFile = getFile(fn);
	}

	/**
	 *
	 * @param fn
	 */
	protected final void fileForShow(File fn) {
		if (!fn.exists() ||!fn.isFile()) {
			return;
		}

		s_showFile = fn;
	}

	/**
	 *
	 * @return
	 */
	protected final File getShowFile() {
		return s_showFile;
	}

	void setConsole(JConsole sim) {
		m_sim = sim;
	}

	/**
	 *
	 * @return
	 */
	public final String getName() {
		return m_name;
	}

	/**
	 *
	 * @return
	 */
	public final String getShortDescription() {
		return m_shortDescription;
	}

	/**
	 *
	 * @return
	 */
	public final String getHelp() {
		return m_help;
	}

	/**
	 * @return a read only list
	 */
	protected final List getAllCommandsList() {
		return m_sim.getAllCommandsList();
	}

	/** @return a read-only map. */
	protected final Map getAllCommandsMap() {
		return m_sim.getAllCommandsMap();
	}

	/**
	 *
	 * @param cmdName
	 * @return
	 */
	protected final ConsoleCommand getCommand(String cmdName) {
		return m_sim.getCommand(cmdName);
	}

	/**
	 *
	 * @return
	 */
	protected final String readUserInputLine() {
		return m_sim.getUserInput();
	}

	/**
	 *
	 * @param m
	 * @throws org.meta.jconsole.CommandResultException
	 */
	protected final void throwCmdResult(String m) throws CommandResultException {
		throw new CommandResultException(m);
	}

	/**
	 *
	 * @param m
	 * @param code
	 * @throws org.meta.jconsole.CommandResultException
	 */
	protected final void throwCmdResult(String m, int code) throws CommandResultException {
		throw new CommandResultException(m, code);
	}

	/**
	 *
	 * @param m
	 * @param code
	 * @param res
	 * @throws org.meta.jconsole.CommandResultException
	 */
	protected final void throwCmdResult(String m, int code, Object res) throws CommandResultException {
		throw new CommandResultException(m, code, res);
	}

	/**
	 *
	 * @param m
	 * @throws org.meta.jconsole.CommandFailedException
	 */
	protected final void throwCmdFailed(String m) throws CommandFailedException {
		throw new CommandFailedException(m);
	}

	/**
	 *
	 * @param m
	 * @param e
	 * @throws org.meta.jconsole.CommandFailedException
	 */
	protected final void throwCmdFailed(String m, Exception e) throws CommandFailedException {
		throw new CommandFailedException(m, e);
	}

	/**
	 *
	 * @param args
	 */
	public abstract void execute(String[] args);

	/**
	 *
	 * @throws java.lang.Exception
	 */
	protected abstract void initialize() throws Exception;

	/**
	 *
	 * @return
	 */
	protected final File getCurrentDir() {
		return new File(m_sim.getCurrentDir());
	}

	/**
	 *
	 * @param path
	 */
	protected final void setCurrentDir(String path) {
		m_sim.setCurrentDir(path);
	}

	/**
	 *
	 * @return
	 */
	protected Map getShareMap() {
		return null;
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	public Object getSharedObject(Object key) {
		Map m = getShareMap();	// the sub-class getShareMap() will be called (if implemented)

		if (m != null) {
			return m.get(key);
		}

		return null;
	}

	/**
	 *
	 * @param aKey
	 * @return
	 */
	protected final File getFile(String aKey) {
		return m_sim.getFile(aKey);
	}

	/**
	 *
	 * @return
	 */
	public List getCommandHistory() {
		return m_sim.getCommandHistory();
	}

	/**
	 *
	 * @param str
	 */
	protected final void displayPrompt(String str) {
		m_sim.displayPrompt(str);
	}

	/**
	 *
	 * @param str
	 */
	protected final void displayMessage(String str) {
		m_sim.displayMessage(str);
	}

	/**
	 *
	 * @return
	 */
	public boolean getVerbose() {
		return m_sim.getVerbose();
	}

	/**
	 *
	 * @param name
	 * @return
	 */
	protected boolean isScript(String name) {
		return m_sim.isScript(name);
	}

	/**
	 *
	 * @param arg1
	 * @return
	 */
	protected String[] args(String arg1) {
		return new String[] { arg1 };
	}

	/**
	 *
	 * @param arg1
	 * @param args2
	 * @return
	 */
	protected String[] args(String arg1, String args2) {
		return new String[] { arg1, args2 };
	}

	/**
	 *
	 * @param arg1
	 * @param args2
	 * @param args3
	 * @return
	 */
	protected String[] args(String arg1, String args2, String args3) {
		return new String[] { arg1, args2, args3 };
	}

	/**
	 *
	 * @param line
	 */
	protected void processLine(String line) {
		m_sim.processLine(line);
	}
}