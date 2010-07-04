package org.meta.wrappers.java.io;

import java.io.IOException;
import java.io.PipedInputStream;

/**
 * This is a wrapper class for PipedOutputStream which throws Runtime exceptions
 * @author mark
 */
public class PipedOutputStream extends java.io.PipedOutputStream {

	@Override
	public synchronized void connect(PipedInputStream snk) {
		try {
			super.connect(snk);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}