package org.meta.wrappers.java.io;

import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * This is a wrapper class for PipedOutputStream which throws Runtime exceptions
 * @author mark
 */
public class PipedInputStream extends java.io.PipedInputStream {

	@Override
	public void close() {
		try {
			super.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void connect(PipedOutputStream src) {
		try {
			super.connect(src);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}