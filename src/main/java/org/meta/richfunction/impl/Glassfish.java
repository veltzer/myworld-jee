package org.meta.richfunction.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Properties;
import org.glassfish.embed.EmbeddedException;
import org.glassfish.embed.EmbeddedInfo;
import org.glassfish.embed.ScatteredArchive;
import org.glassfish.embed.Server;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;

/**
 * This is the glassfish plugin to meta
 * @author mark
 */
public class Glassfish extends RichFunction {

	/**
	 * This is the one glassfish server that we hold
	 */
	protected Server server;
	/**
	 *
	 */
	public Glassfish() {
		super();
		addName("gfdeploy");
		addName("gfstop");
		addName("gfinfo");
		setDescription("deply and undeploy glassfish");
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("echo", false, false, false);
		if(args.getName().equals("gfdeploy")) {
			try {
				EmbeddedInfo info = new EmbeddedInfo();
				info.setServerName("server");
				info.setHttpPort(8080);
				info.setAdminHttpPort(8081);
				info.setJmxConnectorPort(8082);
				server = new Server(info);
				server.start();
				try {
					File myapp=new File("src/main/resources/myapp");
					File myinf=new File(myapp,"WEB-INF");
					File myweb=new File(myapp,"web");
					ScatteredArchive war=new ScatteredArchive(
						"myapp",
						myweb,
						new File(myinf,"web.xml"),
						Collections.singleton(new File("build/classes").toURI().toURL())
					);
					/*
					 * This is for debug
					Properties prop=new Properties() {

						@Override
						public String getProperty(String key) {
							System.out.println("getting key "+key);
							return super.getProperty(key);
						}
					};
					 */
					Properties prop=new Properties();
					prop.setProperty("contextroot","/");
					server.getDeployer().deploy(war,prop);
				} catch (MalformedURLException ex) {
					throw new RuntimeException(ex);
				}
			} catch (EmbeddedException ex) {
				throw new RuntimeException(ex);
			}
		}
		if(args.getName().equals("gfstop")) {
			try {
				server.stop();
			} catch (EmbeddedException ex) {
				throw new RuntimeException(ex);
			}
		}
		if(args.getName().equals("gfinfo")) {
			try {
				p.println(server);
				p.println(server.getFileSystem());
				p.println(server.getDeployer());
				p.println(server.getHabitat());
			} catch (EmbeddedException ex) {
				throw new RuntimeException(ex);
			}
		}
		p.fini();
	}
}