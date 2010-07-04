package org.meta.jconsole.old;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import org.meta.wrappers.java.io.PipedOutputStream;
import org.meta.wrappers.java.io.PipedInputStream;
import org.meta.wrappers.java.lang.Thread;

/*
 * Q: how is a console to be implemented?
 * There are many ways to implement a console:
 * - TextArea with a Scroller on top (all widgets).
 * - TextArea with a vertical scrollbar where you handle all changing of content
 * within the TextArea yourself.
 * - No text area and print everything to the screen yourself with a scroll bar.
 *
 * The lower you go the more you have to implement but the faster and more
 * efficient your implementation becomes (you do not hold strings but rather
 * memory buffers, etc...
 *
 * So which design should we choose? At the begining start wit the top (easiest
 * to implement and slowest) and as need arises go down one step at a time.
 *
 * Q: how to make sure that writing to stdout and stderr will go to my console?
 * Two ways to do that:
 * - using regular java pipes (as in the current implemention).
 * - using Channels and selectors and so you would only need one thead to handle
 * both stdout and stderr.
 *
 * Sometime in the future we need to switch to this implementation.

 * Q: Must we handle stdout and stderr or could we drop the whole thing
 * altogether?
 *
 * R: Yes we could. Here is an idea: create streams that throw exceptions
 * whenever people write to them and replace stdout and stderr with those
 * streams right at the begining. This way you make sure that nobody writes
 * to them.
 *
 * But what if you run a subprocess and someone writes to it anyway? Well - you
 * need to handle communication with the subprocess anyway so redirect it's
 * output, connect it to a pipe, to a channel, whatever.
 * A channel in this case seems to be the best way since we want to be able
 * to monitor many subprocesses from the same console.
 */

/**
 *
 * @author mark
 */
public class Console extends WindowAdapter implements WindowListener,ActionListener
{
	private JFrame frame;
	private JTextArea textArea;
	private Thread reader_out;
	private Thread reader_err;
	private PipedInputStream pin_out;
	private PipedInputStream pin_err;

	/**
	 *
	 */
	public Console()
	{
		// create all components and add them
		frame=new JFrame("Java Console");
		//Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		//Dimension frameSize=new Dimension((int)(screenSize.width/2),(int)(screenSize.height/2));
		//int x=(int)(frameSize.width/2);
		//int y=(int)(frameSize.height/2);
		//frame.setBounds(x,y,frameSize.width,frameSize.height);

		textArea=new JTextArea();
		/**
		 * TODO: fixme hardcoded values
		 */
		textArea.setRows(25);
		textArea.setColumns(80);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		/**
		 * TODO: I need a bigger font size
		 */
		//textArea.setFont(new Font());
		textArea.setEditable(false);
		textArea.append("hello world");

		frame.getContentPane().setLayout(new BorderLayout());
		JScrollPane p=new JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		p.setMinimumSize(textArea.getPreferredScrollableViewportSize());
		p.setWheelScrollingEnabled(true);
		frame.getContentPane().add(p,BorderLayout.CENTER);
		frame.addWindowListener(this);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

		pin_out=new PipedInputStream();
		pin_err=new PipedInputStream();
		PipedOutputStream pout=new PipedOutputStream();
		PipedOutputStream perr=new PipedOutputStream();
		pout.connect(pin_out);
		perr.connect(pin_err);
		/**
		 * TODO: save the old out and err
		 */
		System.setOut(new PrintStream(pout,true));
		System.setErr(new PrintStream(perr,true));

		// Starting two seperate threads to read from the PipedInputStreams
		reader_out=new Thread(new StreamHandler(pin_out,textArea,p));
		//reader_out.setDaemon(true);
		reader_out.start();

		reader_err=new Thread(new StreamHandler(pin_err,textArea,p));
		//reader_err.setDaemon(true);
		reader_err.start();

		// testing part
		// you may omit this part for your application
		System.out.println("Hello World 2");
		System.out.println("All fonts available to Graphic2D:\n");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames=ge.getAvailableFontFamilyNames();
		for(int n=0;n<fontNames.length;n++) {
			System.out.println(fontNames[n]);
			System.err.println(fontNames[n]);
			//Thread.sleep(1000);
		}
	}

	@Override
	public synchronized void windowClosed(WindowEvent evt)
	{
		reader_out.interrupt();
		reader_err.interrupt();
		reader_out.myJoin();
		reader_err.myJoin();
		pin_err.close();
		pin_out.close();
		System.exit(0);
	}

	@Override
	public synchronized void windowClosing(WindowEvent evt)
	{
		//frame.setVisible(false); // default behaviour of JFrame
		frame.dispose();
	}

	/**
	 *
	 * @param arg
	 */
	public static void main(String[] arg)
	{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Console(); // create console with no reference
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}