package org.meta.issues;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.meta.progress.Progress;
import org.meta.util.Utils;

/**
 * This is a singleton class to handle issues. Currently it just records them
 * in a list.
 * 
 * Future ideas:
 * - Spawn threads via a thread pool to handle the issues.
 * - Connect issue handlers with the issues to do it.
 * - Do progress report on handling of these issues.
 * more along these lines...
 * @author mark
 */
public class IssueHandler {
	private Set<IIssue> set;
	private IssueHandler() {
		set=new LinkedHashSet<IIssue>();
	}
	static private IssueHandler theInstance=null;
	/**
	 * TODO: this synchronized thing is no good.
	 * @return
	 */
	static public synchronized IssueHandler getInstance() {
		if(theInstance==null) {
			theInstance=new IssueHandler();
		}
		return theInstance;
	}

	/**
	 * @return the list
	 */
	public Set<IIssue> getList() {
		return set;
	}
	/**
	 *
	 * @param i
	 */
	public void add(IIssue i) {
		set.add(i);
	}
	/**
	 *
	 *
	 * @param p
	 */
	public void handleAll(Progress p) {
		p.startl("fixing problems", set.size());
		List<IIssue> doneList=new ArrayList<IIssue>();
		for(IIssue x:set) {
			try {
				x.handle();
				doneList.add(x);
			}
			catch(RuntimeException ex) {
				ex.printStackTrace();
			}
			p.workl();
		}
		p.endl();
		// This is a very fast loop removing issues that were dealt with
		set.removeAll(doneList);
	}

	/**
	 *
	 * @param issues_file
	 */
	public void printAll(File outFile) {
		PrintStream s=Utils.openPrintStream(outFile);
		for(IIssue x:set) {
			s.println(x);
		}
		s.close();
	}
}