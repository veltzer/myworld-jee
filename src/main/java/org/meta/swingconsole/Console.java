package org.meta.swingconsole;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.meta.swingconsole.streams.EventStream;

/*
 * Q: how is a console to be implemented?
 * There are many ways to implement a console:
 * Swing:
 * - TextArea with a Scroller on top (all widgets).
 * - TextArea with a vertical scrollbar where you handle all changing of content
 * within the TextArea yourself.
 * - No text area and print everything to the screen yourself with a scroll bar.
 * The lower you go the more you have to implement but the faster and more
 * efficient your implementation becomes (you do not hold strings but rather
 * memory buffers, etc...
 *
 * Java standard io:
 * - implement terminal capabilities in java and then write a curses on top of
 * that.
 * - just use System.out and System.err and have less features.
 * Curses integration:
 * - Use jna to integrate with curses.
 * - Use jni to integrate with curses.
 *
 * So which design should we choose? At the begining start wit the top (easiest
 * to implement and slowest) and as need arises go down one step at a time.
 *
 * Q: how to make sure that writing to stdout and stderr will go to my console?
 * Two ways to do that:
 * - using java pipes and create a thread for each pipe that takes whatever
 * it can get and uses swing API to put it to the screen.
 * - using Channels and selectors and so you would only need one thead to handle
 * both stdout and stderr (and many other processes).
 * - Do not use the swing API to print directly to the screen but rather send
 * a message to the AWK event thread to print it out.
 * - A combination of the last two (best).

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
public class Console
{
	private JFrame frame;
	private TextArea textArea;

	/**
	 *
	 */
	public Console()
	{
		frame=new JFrame("Java Console");
		textArea=new TextArea();

		// Do direct append when writing to out or err
		//TextAreaStream cs=new TextAreaStream(textArea);
		//cs.takeOver();

		// Raise exception on each access to out or err
		//ExceptionStream cs=new ExceptionStream();
		//cs.takeOver();

		// Send messages when writing to out or err
		EventStream cs=new EventStream(textArea);
		cs.takeOver();


		frame.getContentPane().setLayout(new BorderLayout());
		JScrollPane p=new JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		p.setMinimumSize(textArea.getPreferredScrollableViewportSize());
		p.setWheelScrollingEnabled(true);
		frame.getContentPane().add(p,BorderLayout.CENTER);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
}