package org.meta.swingconsole;

import org.meta.richfunction.History;
import javax.swing.text.BadLocationException;
import gnu.trove.TIntHashSet;
import org.meta.richfunction.Commands;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JTextArea;
import org.meta.autocomplete.AutoCompleteResult;
import org.meta.progress.IProgressEventProcessor;
import org.meta.progress.ProgressEvent;

/**
 * TODO: handle scrolling (do my own method of append which also scrolls to the end and use it all over).
 * - enable to run regular commands from the command line and see the output
 * on the console.
 * - enable to run all the commands in the jconsole package.
 * - choose better fixed font.
 * - use StringBuffer of constant length to store the text and stop using the
 * text widget itself and the java string to display stuff. Instead, create
 * your own widget with it's own scroll bar.
 * @author mark
 */
public class TextArea extends JTextArea implements MouseListener, KeyListener, FocusListener,IProgressEventProcessor {
	/**
	 * A set of keys we are willing to be printed
	 */
	protected TIntHashSet printedKeySet;
	/**
	 * Are we in command mode or history mode ?
	 */
	protected boolean in_cmd;
	/**
	 * The current typed command
	 */
	protected String cmd;
	/**
	 * The prompt that we show to the user
	 */
	protected String prompt;
	/**
	 * All the commands that we can execute
	 */
	protected Commands cmds;
	/*
	 * Are we waiting for a command to finish ?
	 */
	protected boolean waiting;
	/*
	 * History of all commands
	 */
	protected History history;
	/**
	 * Current entry in history (if we are in history mode
	 */
	protected int hist_index;
	/**
	 *
	 */
	public TextArea() {
		super();
		// TODO: remove hardcoded values
		setRows(25);
		setColumns(80);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		// TODO: I need a bigger font size
		// this next line does not work
		//setFont(getFont().deriveFont(40));
		setEditable(false);
		addMouseListener(this);
		addKeyListener(this);
		// this is to enable us to get the tab keys
		setFocusTraversalKeysEnabled(false);
		cmd="";
		in_cmd=true;
		prompt="cmd> ";
		append(prompt);
		cmds=new Commands();
		waiting=false;
		history=new History();
		hist_index=-1;

		enableEvents(AWTProgressEvent.EVENT_ID);
		initHandle();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked "+e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("mousePressed "+e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("mouseReleased "+e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("mouseEntered "+e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("mouseExited "+e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("keyTyped "+e);
	}
	protected void erase(int size) {
		try {
			getDocument().remove(getDocument().getLength()-size,size);
		} catch (BadLocationException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			final char c=e.getKeyChar();
			append(new String(""+c));
			String[] args;
			String line;
			if(in_cmd) {
				line=cmd;
				history.add(cmd);
			} else {
				line=history.get(hist_index);
			}
			args=line.split("\\s");
			// this will not throw an exception (functions
			// are executed in the thread pool...
			final Component comp=this;
			try {
				IProgressEventProcessor pep=new IProgressEventProcessor() {

					@Override
					public void processProgressEvent(ProgressEvent pe) {
						EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
						eventQueue.postEvent(new AWTProgressEvent(comp,pe));
					}
				};
				cmds.execute(new ComponentProgress(pep,this),args,line);
				// we are now waiting for the thread to die...
				waiting=true;
			}
			catch (Exception ex) {
				append("Problem running command ["+ex+"]\n" + prompt);
				cmd="";
			}
			return;
		}
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
			if(cmd.length()>0) {
				cmd=cmd.substring(0,cmd.length()-1);
				erase(1);
			}
			return;
		}
		//System.out.println("key code is "+e.getKeyCode());
		if(e.getKeyCode()==KeyEvent.VK_TAB) {
			e.consume();
			// TODO: this needs to be done in a different thread
			// since it takes time
			// handle autocomplete
			AutoCompleteResult acr=cmds.ac.findCompletionsResult(cmd);
			List<String> suggestions=acr.getList();
			// if there are no suggestions then beep
			if(suggestions.size()==0) {
				//TODO: erase the command back to where it has completions
				// and show them.
				getToolkit().beep();
				return;
			}
			// if there is only one suggestion then write it.
			if(suggestions.size()==1) {
				String suggestion=suggestions.get(0);
				String app=suggestion.substring(cmd.length());
				append(app+" ");
				cmd+=app+" ";
				return;
			}
			// if there is more than 1 suggestion then show them
			if(suggestions.size()>1) {
				String print="\n";
				for(String x:suggestions) {
					print+=x+"\n";
				}
				cmd=acr.prefix;
				print+=prompt;
				print+=cmd;
				append(print);
				return;
			}
		}
		// debug
		if(e.getKeyCode()==KeyEvent.VK_ALT) {
			append("\nhistory listing\n");
			String print="";
			for(String x:history) {
				print+=x+"\n";
			}
			print+=prompt;
			print+=cmd;
			append(print);
		}
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			if(!history.isEmpty()) {
				if(in_cmd && cmd.length()>0) {
					history.add(cmd);
					erase(cmd.length());
					in_cmd=false;
				}
				if(in_cmd) {
					hist_index=history.getLastIndex();
				} else {
					if(hist_index==0) {
						return;
					}
					hist_index--;
				}
				in_cmd=false;
				append(history.get(hist_index));
			}
			return;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			return;
		}
		// handling regular keys
		int keycode=e.getKeyCode();
		if(canPrint(keycode)) {
			e.consume();
			char c=e.getKeyChar();
			if(!in_cmd) {
				cmd=history.get(hist_index);
				in_cmd=true;
			}
			append(new String(""+c));
			cmd+=c;
			return;
		}
		// don't do anything - we don't know how to handle that character...
		//System.out.println("keyPressed "+e.getKeyCode()+" "+KeyEvent.getKeyText(keycode));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("keyReleased "+e);
	}

	/**
	 * new Swing makes this method obsolete
	 *
	 * @param e
	 */
	/*
	@Override
	public boolean isManagingFocus() {
		return true;
	}
	 */

	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("focusGained "+e);
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("focusLost "+e);
	}

	/**
	 * This method initialized a set of all keys that we allow to be
	 * printed.
	 */
	private void initHandle() {
		printedKeySet=new TIntHashSet();
		for(int i=KeyEvent.VK_0;i<=KeyEvent.VK_9;i++) {
			printedKeySet.add(i);
		}
		for(int i=KeyEvent.VK_A;i<=KeyEvent.VK_Z;i++) {
			printedKeySet.add(i);
		}
		printedKeySet.add(KeyEvent.VK_SPACE);
		printedKeySet.add(KeyEvent.VK_PERIOD);
		printedKeySet.add(KeyEvent.VK_COLON);
		printedKeySet.add(KeyEvent.VK_SEMICOLON);
		printedKeySet.add(KeyEvent.VK_SLASH);
		printedKeySet.add(KeyEvent.VK_SUBTRACT);
		printedKeySet.add(KeyEvent.VK_MINUS);
		printedKeySet.add(KeyEvent.VK_COMMA);
		printedKeySet.add(KeyEvent.VK_AMPERSAND);
		printedKeySet.add(KeyEvent.VK_ASTERISK);
		printedKeySet.add(KeyEvent.VK_BACK_QUOTE);
		printedKeySet.add(KeyEvent.VK_BACK_SLASH);
		printedKeySet.add(KeyEvent.VK_AT);
		printedKeySet.add(KeyEvent.VK_BRACELEFT);
		printedKeySet.add(KeyEvent.VK_BRACERIGHT);
		printedKeySet.add(KeyEvent.VK_CLOSE_BRACKET);
		printedKeySet.add(KeyEvent.VK_OPEN_BRACKET);
		printedKeySet.add(KeyEvent.VK_DECIMAL);
		printedKeySet.add(KeyEvent.VK_DIVIDE);
		printedKeySet.add(KeyEvent.VK_DOLLAR);
		printedKeySet.add(KeyEvent.VK_EQUALS);
		printedKeySet.add(KeyEvent.VK_EXCLAMATION_MARK);
	}

	private boolean canPrint(int keycode) {
		return printedKeySet.contains(keycode);
	}
	@Override
	protected void processEvent(AWTEvent event) {
		if(event instanceof AWTProgressEvent) {
			//currently we are not actually doing anything with the
			// data in the event itself...
			AWTProgressEvent ape=(AWTProgressEvent)event;
			processProgressEvent(ape.getPe());
			return;
		}
		// other events go to the system default process event handler
		super.processEvent(event);
	}
	@Override
	public void processProgressEvent(ProgressEvent pe) {
		if(pe.getType()==ProgressEvent.Type.FINI) {
			waiting=false;
			append(prompt);
			cmd="";
		}
		if(pe.getType()==ProgressEvent.Type.PRINT) {
			append(pe.getMessage());
		}
	}
}