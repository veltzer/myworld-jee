package org.meta.counter;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * This is a counter implemented as an nio Buffer. Increasing it is very quick
 * and it can print itself very fast to standard output.
 * @author mark
 */
public class BufferCounter implements Counter {
	static final boolean direct=true;

	/**
	 *
	 */
	protected ByteBuffer buf;
	/**
	 *
	 */
	protected CharBuffer buffer;
	/**
	 *
	 */
	protected int size;

	/**
	 *
	 * @param isize
	 */
	public BufferCounter(int isize) {
		size=isize;
		if(direct==true) {
			buf=ByteBuffer.allocateDirect(size*4);
		} else {
			buf=ByteBuffer.allocate(size*4);
		}
		buffer=buf.asCharBuffer();
	}
	/**
	 *
	 */
	public BufferCounter() {
		this(6);
	}

	/**
	 *
	 */
	@Override
	public void zero() {
		buffer.rewind();
		for(int x=0;x<size;x++) {
			buffer.put('0');
		}
	}

	/**
	 *
	 */
	@Override
	public void add() {
		boolean done=false;
		int pos=size-1;
		while(pos>=0 && !done) {
			char c=buffer.get(pos);
			if(c=='9') {
				buffer.put(pos,'0');
				pos--;
			} else {
				c++;
				buffer.put(pos,c);
				done=true;
			}
		}
	}

	/**
	 *
	 * @param s
	 */
	@Override
	public void print(PrintStream s) {
		//s.write(buffer.array());
		buffer.rewind();
		for(int x=0;x<size;x++) {
			s.write(buffer.get());
		}
	}
}