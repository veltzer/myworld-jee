package org.meta.swingconsole;

import java.io.IOException;
import java.net.InetSocketAddress;
//import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
//import org.meta.wrappers.java.nio.channels.SelectorUtil;

/**
 *
 * @author mark
 */
public class SelectorThread {

	// Creates a non-blocking socket channel for the specified host name and port.
	// connect() is called on the new channel before it is returned.
	/**
	 *
	 * @param hostName
	 * @param port
	 * @return
	 */
	public static SocketChannel createSocketChannel(String hostName, int port) {
		try {
			// Create a non-blocking socket channel
			SocketChannel sChannel = SocketChannel.open();
			sChannel.configureBlocking(false);
			// Send a connection request to the server; this method is non-blocking
			sChannel.connect(new InetSocketAddress(hostName, port));
			return sChannel;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 *
	 * @param args
	 */
	/*
	static public void main(String[] args) {
		Selector selector=SelectorUtil.open();
		SocketChannel sChannel1=createSocketChannel("localhost",80);
		SocketChannel sChannel2=createSocketChannel("localhost",80);
		// Register the channel with selector, listening for all events
		//sChannel1.register(selector,sChannel1.validOps());
		//sChannel2.register(selector,sChannel1.validOps());
	}
	 */
}