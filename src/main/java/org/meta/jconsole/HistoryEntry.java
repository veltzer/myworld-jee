package org.meta.jconsole;

/**
 *
 * @author mark
 */
public class HistoryEntry {
	/**
	 *
	 */
	protected String m_cmdString;
	/**
	 *
	 */
	protected ConsoleCommand m_cmd;
	/**
	 *
	 */
	protected String[] m_args;

	/**
	 *
	 * @param aCmdString
	 * @param aCmd
	 * @param args
	 */
	public HistoryEntry(String aCmdString, ConsoleCommand aCmd, String args[]) {
		m_cmdString=aCmdString;
		m_cmd=aCmd;
		m_args=args;
	}
	/**
	 *
	 * @return
	 */
	public ConsoleCommand getCommand() {
		return m_cmd;
	}
	/**
	 *
	 * @return
	 */
	public String[] getArgs() {
		return m_args;
	}
	@Override
	public String toString() {
		return m_cmdString;
	}
}
