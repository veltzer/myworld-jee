package org.meta.richfunction.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;
import org.meta.util.Utils;

/**
 * This class is in charge of executing real external executables.
 * @author mark
 */
public class Exec extends RichFunction {
	
	/**
	 * A private hash map of executables and their full path
	 */
	protected Map<String,String> execmap;

	/**
	 *
	 */
	public Exec() {
		super();
		execmap=new HashMap<String,String>();
		String path=System.getenv("PATH");
		String[] dirs=path.split(":");
		for(String dir: dirs) {
			File d=new File(dir);
			if(d.isDirectory()) {
				File[] files=d.listFiles();
				for(File f:files) {
					if(f.canExecute()) {
						String name=f.getName();
						if(!execmap.containsKey(name)) {
							execmap.put("exec:"+name, f.getPath());
						}
					}
				}
			}
		}
		for(String x: execmap.keySet()) {
			addName(x);
		}
		setDescription("execute external executables");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("exec", false, false, false);
		List<String> list=new ArrayList<String>();
		list.add(execmap.get(args.getName()));
		list.addAll(args.getFreeArgs());
		String[] aargs=list.toArray(new String[0]);
		// debug
		for(String x:aargs) {
			System.out.println(x);
		}
		Utils.execAndWait(aargs);
		p.fini();
	}
}