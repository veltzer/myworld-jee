package org.meta.wrappers.java.io;

import java.io.IOException;

/**
 * This is an InputStream decorator which takes care of catching exceptions
 * from the read method.
 *
 * @author mark
 */
public class InputStream extends java.io.InputStream {

	/**
	 *
	 */
	protected java.io.InputStream s;

	/**
	 *
	 * @param is
	 */
	public InputStream(java.io.InputStream is) {
		s=is;
	}

	@Override
	public int read() {
		try {
			return s.read();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public int read(byte[] b) {
		try {
			return s.read(b);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public int read(byte[] b, int off, int len) {
		try {
			return s.read(b, off, len);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 *
	 * @return
	 */
	public String readString() {
		byte[] b=new byte[1024];
		int input=read(b);
		return new String(b,0,input);
	}
}