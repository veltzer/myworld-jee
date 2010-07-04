package org.meta.richfunction.impl;

import org.meta.richfunction.Arguments;
import org.meta.richfunction.types.OptBoolean;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is a very basic echo command.
 * @author mark
 */
public class Echo extends RichFunction {

	private static final String nameNewline="newline";
	private static final String nameSpace="space";
	
	/**
	 *
	 */
	public Echo() {
		super();
		addName("echo");
		addName("ec");
		setDescription("echo stuff to the console");
		OptBoolean oNewline=new OptBoolean();
		oNewline.setName(nameNewline);
		oNewline.setDescription("should I put a newline at the end");
		oNewline.setDefault(true);
		addOption(oNewline);
		OptBoolean oSpace=new OptBoolean();
		oSpace.setName(nameSpace);
		oSpace.setDescription("should I put a space between the arguments");
		oSpace.setDefault(true);
		addOption(oSpace);
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("echo", false, false, false);
		boolean bNewline=args.getBoolean(nameNewline);
		boolean bSpace=args.getBoolean(nameSpace);
		for(String x:args.getFreeArgs()) {
			p.print(x);
			if(bSpace) {
				p.print(" ");
			}
		}
		if(bNewline) {
			p.println();
		}
		p.fini();
	}
}