package org.meta.richfunction.impl;

import java.io.File;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a java version of ls.
 *
 * TODO: add many more flags
 * TODO: add paging support (how?)
 * TODO: add sorting
 * TODO: add formatting of output (formatter)
 * TODO: add coloring of output (curses)
 * @author mark
 */
public class Ls extends RichFunction {

	public Ls() {
		super();
		addName("ls");
		setDescription("list files or folders");
	}

	@Override
	public void execute(Progress p,Arguments args) {
		p.init("ls", false, false, false);
		File curDir=new File(".");
		for(File f: curDir.listFiles()) {
			p.println(f.getName());
		}
		p.fini();
	}
}