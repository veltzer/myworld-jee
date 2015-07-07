package org.meta.conf;

import java.io.File;

/**
 * Singleton pattern configuration for meta
 * @author mark
 */
public class Conf {

	static private Conf instance=null;

	static public Conf getInstance() {
		if(instance==null) {
			instance=new Conf();
		}
		return instance;
	}
	
	public String getString(ConfId id) {
		switch(id) {
			case CONF_TWITTER_PASSWORD: 
				return "";
			case CONF_TWITTER_USERID:
				return "veltzer";
			case CONF_TWITTER_USERAGENT:
				return "MyWorld";
			case CONF_TWITTER_SOURCE:
				return "MyWorld";
			case CONF_TWITTER_CONSUMER:
				return "";
			case CONF_TWITTER_CONSUMER_SECRET:
				return "";
			case CONF_TWITTER_TOKEN_FILE:
				return "~/.twitter.token";
		}
		// this is unreachable code...
		throw new RuntimeException("cannot find conf id "+id);
	}
	public File getFolder(ConfId id) {
		switch(id) {
			case CONF_MUSIC_FOLDER:
				return new File("/home/mark/links/topics_archive/audio/music");
			case CONF_MUSIC_ISSUES:
				return new File("music_issues.txt");
			case CONF_MUSIC_STATS:
				return new File("music_stats.txt");
		}
		throw new RuntimeException("cannot find conf id "+id);
	}
	public boolean getBoolean(ConfId id) {
		switch(id) {
			case CONF_MUSIC_FIX:
				return false;
			case CONF_MUSIC_PRINTSTATS:
				return true;
			case CONF_MUSIC_PRINTISSUES:
				return true;
		}
		throw new RuntimeException("cannot find conf id "+id);
	}

	/*
	 * This shows how you can read all the configuration options from a database
	 * or ini type file...
	 */

	public static void main(String [] args) {
		for(ConfId x: ConfId.values()) {
			System.out.println("x is "+x);
			System.out.println("x.ordinal() is "+x.ordinal());
			System.out.println("x.name() is "+x.name());
			System.out.println("x.toString() is "+x.toString());
		}
	}
}
