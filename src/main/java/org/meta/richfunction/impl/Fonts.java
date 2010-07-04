package org.meta.richfunction.impl;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;
import say.swing.JFontChooser;

/**
 * This command lists all fonts on the current system
 * @author mark
 */
public class Fonts extends RichFunction {

	/**
	 *
	 */
	public Fonts() {
		super();
		addName("fonts");
		setDescription("list all system fonts");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		/*
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String envfonts[] = gEnv.getAvailableFontFamilyNames();
		for(String x:envfonts) {
			System.out.println(x);
		}
		Font[] fonts=gEnv.getAllFonts();
		for(Font f:fonts) {
			System.out.println(f.getName());
			System.out.println(f.getPSName());
			System.out.println(f.getFamily());
			System.out.println(f.getFontName());
		}
		*/
		JFontChooser fontChooser = new JFontChooser();
		int result = fontChooser.showDialog(null);
		if (result == JFontChooser.OK_OPTION) {
			Font font = fontChooser.getSelectedFont();
			System.out.println("Selected Font : " + font);
		}
		/*
		Font f=JFontChooser.showDialog(null,null);
		System.out.println("font selected is "+f.getFontName());
		*/
		//JFontChooser jf=new JFontChooser();
		//jf.setVisible(true);
	}
}