package org.meta.richfunction;

import java.util.ArrayList;
import java.util.List;
import org.meta.autocomplete.AutoComplete;
import org.meta.autocomplete.Factory;
import org.meta.progress.Progress;
import org.meta.wrappers.java.util.LinkedHashMap;

/**
 * This is a collection of options to describe a particular function.
 *
 * It should be able to:
 * - describe the function it is destined to supply arguments for
 *   (name and description).
 * - describe whether free arguments are supported.
 * - provide auto-completions for the free arguments passed to it.
 * - validate the number and free arguments passed to it.
 * - provide help facilities on using it (with all it's options).
 * @author mark
 */
public abstract class RichFunction {

	/**
	 * This is a hash map for all of it's options
	 */
	protected LinkedHashMap<String,Option> opts;
	/**
	 * This is an auto completion object for it's parameters
	 */
	protected AutoComplete params;
	/**
	 * This is a list of names for this rich function
	 */
	protected List<String> names;
	/**
	 * This is this long functions description
	 */
	protected String description;

	/**
	 *
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 *
	 * @return
	 */
	public List<String> getNames() {
		return names;
	}

	/**
	 *
	 * @param name
	 */
	public void addName(String name) {
		names.add(name);
	}

	/**
	 *
	 */
	public RichFunction() {
		opts=new LinkedHashMap<String, Option>();
		params=Factory.createAutoComplete();
		names=new ArrayList<String>();
		addName(getClass().getCanonicalName());
	}
	/**
	 *
	 * @param o
	 */
	public void addOption(Option o) {
		opts.insert(o.getName(),o);
		params.add(o.getName());
	}
	/**
	 *
	 * @param name
	 * @return
	 */
	public Option getOption(String name) {
		return opts.get(name);
	}
	/**
	 * This is the method that RichFunction writers should implement.
	 * Notice that this method is not synchronized to allow the same
	 * function to be run by two different threads at the same time.
	 *
	 * This means that:
	 * 1. You should lock stuff yourself if you need it.
	 * 2. Per instance related data should be on the stack or in the heap,
	 * or maybe in a hash but not a single variable in the derived class.
	 * @param p
	 * @param args
	 */
	abstract public void execute(Progress p,Arguments args);

	/**
	 *
	 * @param p
	 * @param sargs
	 */
	public void execute(Progress p,String[] sargs,String line) {
		Arguments args=parse(sargs,line);
		execute(p,args);
	}

	/**
	 *
	 * @param sargs
	 * @return
	 */
	public Arguments parse(String[] sargs,String line) {
		// create a clean arguments object
		Arguments args=new Arguments();
		// fill it with defaults
		for(String x:opts.keySet()) {
			Option opt=opts.get(x);
			args.put(x,opt.getDefault());
		}
		// set the function name
		args.setName(sargs[0]);
		// run only from 1 (first argument is the function name)
		for(int i=1;i<sargs.length;i++) {
			String x=sargs[i];
			// is there any better way to do this ? (instead of split...)
			if(x.startsWith("-")) {
				int index=x.indexOf("=");
				String key=x.substring(1, index);
				String value=x.substring(index+1);
				Option option=getOption(key);
				Object o=option.ValueFromString(value);
				args.put(key,o);
			} else {
				args.addFreeArg(x);
			}
		}
		args.setLine(line);
		return args;
	}
}
