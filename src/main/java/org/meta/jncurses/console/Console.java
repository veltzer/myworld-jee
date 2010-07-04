package org.meta.jncurses.console;

import org.meta.progress.Progress;
import gnu.trove.TIntHashSet;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import org.meta.autocomplete.AutoCompleteResult;
import org.meta.jncurses.Jncurses;
import org.meta.jncurses.Ncurses;
import org.meta.progress.IProgressEventProcessor;
import org.meta.progress.ProgressEvent;
import org.meta.richfunction.Commands;
import org.meta.richfunction.History;

/**
 *
 * @author mark
 */
public class Console implements IProgressEventProcessor {
	protected String prompt;
	protected Commands cmds;
	protected TIntHashSet printedKeySet;
	protected boolean in_cmd;
	protected History history;
	protected StringBuilder cmd;
	protected int hist_index;
	protected boolean waiting;

	StringBuilder sb;
	Formatter formatter;
	public Console() {
		sb = new StringBuilder();
		formatter = new Formatter(sb, Locale.US);
		prompt="cmd> ";
		print(prompt);
		cmds=new Commands();
		in_cmd=true;
		history=new History();
		cmd=new StringBuilder(100);
		hist_index=-1;

		printedKeySet=new TIntHashSet();
		for(int i='0';i<='9';i++) {
			printedKeySet.add(i);
		}
		for(int i='A';i<='Z';i++) {
			printedKeySet.add(i);
		}
		for(int i='a';i<='z';i++) {
			printedKeySet.add(i);
		}
		printedKeySet.add(' ');
		printedKeySet.add('.');
		printedKeySet.add(':');
		printedKeySet.add(';');
		printedKeySet.add('/');
		printedKeySet.add('-');
		printedKeySet.add(',');
		printedKeySet.add('&');
		printedKeySet.add('*');
		printedKeySet.add('`');
		printedKeySet.add('\\');
		printedKeySet.add('@');
		printedKeySet.add('(');
		printedKeySet.add(')');
		printedKeySet.add('[');
		printedKeySet.add(']');
		printedKeySet.add('.');
		printedKeySet.add('%');
		printedKeySet.add('$');
		printedKeySet.add('=');
		printedKeySet.add('!');
		printedKeySet.add('_');

		waiting=false;
	}
	public void finish() {
	}
	public void append(String s) {
		Ncurses.getInstance().print(s);
	}
	public void print(String s) {
		Ncurses.getInstance().print(s);
	}
	public void println(String s) {
		Ncurses.getInstance().println(s);
	}
	protected void showChar(char c) {
		Ncurses.getInstance().showChar(c);
	}
	protected void eraseChar() {
		Ncurses.getInstance().eraseChar();
	}
	protected void show(String s) {
		Ncurses.getInstance().print(s);
		Ncurses.getInstance().refresh();
	}
	protected void downOneLine() {
	}
	protected void editCmd() {
		if(in_cmd==false) {
			cmd.setLength(0);
			cmd.append(history.get(hist_index));
			in_cmd=true;
		}
	}
	public void main() {
		int x;
		boolean over=false;
		while(!over) {
			x=Ncurses.getInstance().getch();
			char c=(char)x;
			//print("character is "+x);
			if(c==Jncurses.KEY_ENTER) {
				show("\n\r");
				String[] args;
				String line;
				if(in_cmd) {
					if(cmd.length()==0) {
						append(prompt);
						continue;
					}
					line=cmd.toString();
					history.add(cmd.toString());
				} else {
					line=history.get(hist_index);
				}
				args=line.split("\\s");
				// this will not throw an exception (functions
				// are executed in the thread pool...
				try {
					cmds.execute(new Progress(this),args,line);
					// we are now waiting for the thread to die...
					Ncurses.getInstance().hide_cursor();
					waiting=true;
				}
				catch (Exception ex) {
					print("Problem running command ["+ex+"]\n" + prompt);
					cmd.setLength(0);
				}
			}
			if(c==Jncurses.KEY_ESCAPE) {
				println("got escape, quiting");
				over=true;
			}
			if(c==Jncurses.KEY_BACKSPACE) {
				editCmd();
				if(cmd.length()>0) {
					eraseChar();
					//showChar('b');
					cmd.setLength(cmd.length()-1);
				}
			}
			if(c==Jncurses.KEY_TAB) {
				// TODO: this needs to be done in a different thread
				// since it takes time
				// handle autocomplete
				AutoCompleteResult acr=cmds.ac.findCompletionsResult(cmd.toString());
				List<String> suggestions=acr.getList();
				// if there are no suggestions then beep
				if(suggestions.size()==0) {
					//TODO: erase the command back to where it has completions
					// and show them.
					Ncurses.getInstance().flash();
					continue;
				}
				// if there is only one suggestion then write it.
				if(suggestions.size()==1) {
					String suggestion=suggestions.get(0);
					String app=suggestion.substring(cmd.length());
					append(app+" ");
					cmd.append(app+" ");
					continue;
				}
				// if there is more than 1 suggestion then show them
				if(suggestions.size()>1) {
					String print="\n";
					if(suggestions.size()<20) {
						for(String sug:suggestions) {
							print+=sug+"\n";
						}
					} else {
						print+="Too many options...\n";
					}
					cmd.setLength(0);
					cmd.append(acr.prefix);
					print+=prompt;
					print+=cmd;
					append(print);
					continue;
				}
			}
			if(printedKeySet.contains(c)) {
				editCmd();
				showChar(c);
				cmd.append(c);
			}
		}
		// shutdown must come before finish since some of the threads
		// may be still printing to the screen...
		cmds.shutdown(false,true);
	}
	public static void main(String[] arg) {
		boolean haveEx=false;
		RuntimeException e=null;
		Ncurses.load();
		Ncurses.getInstance().init();
		try {
			Console c=new Console();
			c.main();
		}
		catch (RuntimeException ex) {
			haveEx=true;
			e=ex;
		}
		finally {
			Ncurses.getInstance().finish();
			if(haveEx) {
				System.out.println("got exception ["+e+"]");
			}
		}
	}

	@Override
	public void processProgressEvent(ProgressEvent pe) {
		if(pe.getType()==ProgressEvent.Type.INIT) {
			show("starting task ["+pe.getMessage()+"]\n");
		}
		if(pe.getType()==ProgressEvent.Type.STARTL) {
			sb.setLength(0);
			formatter.format("starting loop [%s]\n[000000/%06d] [--DIDNT START--]",
				pe.getMessage(),
				pe.getProgress().getUnits());
			show(formatter.toString());
			//show("starting loop ["+pe.getMessage()+"]\n[------/------] [DIDNT START]");
		}
		if(pe.getType()==ProgressEvent.Type.WORK) {
			sb.setLength(0);
			final int msgLen=80;
			String msg;
			if(pe.getMessage().length()<msgLen) {
				msg=pe.getMessage();
			} else {
				msg=pe.getMessage().substring(0,msgLen);
			}
			formatter.format("\r[%06d/%06d] [%-80s]",
				pe.getProgress().getWork(),
				pe.getProgress().getUnits(),
				msg
			);
			show(sb.toString());
		}
		if(pe.getType()==ProgressEvent.Type.ENDL) {
			//downOneLine();
			show("\nfinishing loop ["+pe.getMessage()+"]\n");
		}
		if(pe.getType()==ProgressEvent.Type.FINI) {
			show("finished task ["+pe.getMessage()+"]\n"+prompt);
			Ncurses.getInstance().show_cursor();
			waiting=false;
			cmd.setLength(0);
		}
		if(pe.getType()==ProgressEvent.Type.PRINT) {
			show(pe.getMessage());
		}
		if(pe.getType()==ProgressEvent.Type.PRINTLN) {
			show(pe.getMessage()+"\n\r");
		}
	}
}