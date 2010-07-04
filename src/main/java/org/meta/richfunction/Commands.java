package org.meta.richfunction;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.meta.autocomplete.AutoComplete;
import org.meta.autocomplete.Factory;
import org.meta.progress.Progress;
import org.meta.wrappers.java.util.LinkedHashMap;
import org.meta.util.PackageScanner;

/**
 * This is a container for all the commands that the console knows how to perform.
 * It knows how to execute a command, access the commands for autocompletion,
 * supply the list of all commandsm and more...
 *
 * This class knows how to do multi threading since running rich functions involves
 * multi-threading issues. It stores a thread pool and uses it for running the
 * tasks. Each task is given an id so it could be uniquely identified and thus
 * cancelled, paused etc...
 *
 * TODO: get ridd of all the imports of all commands and find them on the fly somehow...
 *
 * @author mark
 */
public class Commands {
	/**
	 * This is the thread pool that is used to run the commands
	 */
	ExecutorService es;
	/**
	 * This is an id counter to assign sequential ids to the commands
	 * executed.
	 */
	protected int currId;
	/**
	 * This is a hash map of all the available RichFunctions and their names
	 */
	protected LinkedHashMap<String,RichFunction> commands;
	/**
	 * This is an auto complete class to help auto completing the function
	 * names (not their parameters - that is the job of each individual function).
	 */
	public AutoComplete ac;

	/**
	 * A constructor for this class.
	 * It initializes the hash map, the auto complete object,
	 * and the thread pool.
	 */
	public Commands() {
		commands=new LinkedHashMap<String, RichFunction>();
		ac=Factory.createAutoComplete();
		fillCommands("org.meta");
		currId=0;
		es=Executors.newCachedThreadPool();
	}
	/**
	 * Add a rich function to the set of known commands.
	 * This adds the function both to the hash and to the auto complete
	 * structure.
	 * @param cmd
	 */
	protected void addCommand(RichFunction cmd) {
		for(String x: cmd.getNames()) {
			commands.insert(x,cmd);
			ac.add(x);
		}
	}
	/**
	 * This method adds all commands to the current object.
	 * This is a pretty bad habit since it will have a line per command.
	 * In the future this should be replaced with a more sophisticated
	 * version which scans the class path for classes implementing the
	 * ConsoleCommand interface and trying to use them...
	 */
	private void fillCommands(String pkg) {
		// commands from nconsole
		Set<Class<?>> list = PackageScanner.getClasses(pkg,true);
		for(Class<?> x:list) {
			if(x.getSuperclass()==org.meta.richfunction.RichFunction.class) {
				try {
					RichFunction cmd =(RichFunction) x.newInstance();
					addCommand(cmd);
				} catch (InstantiationException ex) {
					throw new RuntimeException(ex);
				} catch (IllegalAccessException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}
	/**
	 * This is the most important function. Supply a set of arguments.
	 * First is the name of the command to execute.
	 * The command will execute in it's own thread via the thread pool.
	 * @param p 
	 * @param sargs
	 */
	public void execute(final Progress p,final String[] sargs,final String line) {
		final RichFunction rf=commands.get(sargs[0]);
		Runnable r=new Runnable() {
			@Override
			public void run() {
				// TODO: notify (listener pattern) that a task
				// has started...
				rf.execute(p,sargs,line);
				// TODO: notify (listener pattern) that a task
				// has finished...
			}
		};
		// execute this runnable via an executor service
		es.execute(r);
	}

	/**
	 * This function must be called to make sure that this object does not leave
	 * any lingering threads which will prevent the clean termination of your program
	 * TODO: try to see if there is a way to make this automatic...
	 */
	public void shutdown(boolean aggressive,boolean wait) {
		if(aggressive==true) {
			es.shutdownNow();
		} else {
			es.shutdown();
		}
		if(wait==true) {
			try {
				es.awaitTermination(10000000, TimeUnit.SECONDS);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}