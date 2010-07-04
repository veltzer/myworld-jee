package org.meta.music;

import java.io.File;
import org.meta.conf.Conf;
import org.meta.conf.ConfId;
import org.meta.issues.IssueHandler;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This program will check the validity of my mp3 collection
 * @author Mark Veltzer
 */
public class Check extends RichFunction {
	static File pFolder;
	static File pStatsFile;
	static File pIssuesFile;
	static boolean pFix;
	static boolean pPrintStats;
	static boolean pPrintIssues;

	/**
	 *
	 */
	public Check() {
		super();
		addName("musiccheck");
		setDescription("check my music collection");
		pFolder=Conf.getInstance().getFolder(ConfId.CONF_MUSIC_FOLDER);
		pStatsFile=Conf.getInstance().getFolder(ConfId.CONF_MUSIC_STATS);
		pIssuesFile=Conf.getInstance().getFolder(ConfId.CONF_MUSIC_ISSUES);
		pFix=Conf.getInstance().getBoolean(ConfId.CONF_MUSIC_FIX);
		pPrintStats=Conf.getInstance().getBoolean(ConfId.CONF_MUSIC_PRINTSTATS);
		pPrintIssues=Conf.getInstance().getBoolean(ConfId.CONF_MUSIC_PRINTISSUES);

	}

	@Override
	public void execute(Progress p,Arguments args) {
		Checker w=new Checker(p);
		w.walk(pFolder);
		if(pPrintStats) {
			w.printStats(pStatsFile);
		}
		if(pFix) {
			IssueHandler.getInstance().handleAll(p);
		}
		if(pPrintIssues) {
			IssueHandler.getInstance().printAll(pIssuesFile);
		}
	}
}