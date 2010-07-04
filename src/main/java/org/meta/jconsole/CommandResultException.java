package org.meta.jconsole;

/**
 *
 * @author mark
 */
public class CommandResultException extends CommandFailedException {
	private int m_code;
	private Object m_result;

	/**
	 *
	 * @param m
	 */
	public CommandResultException(String m) {
		this(m, 0, null);
	}

	/**
	 *
	 * @param m
	 * @param c
	 */
	public CommandResultException(String m, int c) {
		this(m, c, null);
	}

	/**
	 *
	 * @param m
	 * @param res
	 */
	public CommandResultException(String m, Object res) {
		this(m, 0, res);
	}
	/**
	 *
	 * @param m
	 * @param code
	 * @param res
	 */
	public CommandResultException(String m, int code, Object res) {
		super(m);
		m_code=code;
		m_result=res;
	}
}