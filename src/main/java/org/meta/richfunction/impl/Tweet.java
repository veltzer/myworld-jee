package org.meta.richfunction.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.meta.conf.Conf;
import org.meta.conf.ConfId;
import org.meta.location.Current;
import org.meta.richfunction.Arguments;
import org.meta.richfunction.RichFunction;
import org.meta.progress.Progress;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * This is tweeting command via twitter
 * @author mark
 */
public class Tweet extends RichFunction {

	/**
	 * The twitter object we will be working with...
	 */
	private Twitter twitter;

	/**
	 *
	 */
	public Tweet() {
		super();
		addName("tweet");
		setDescription("tweet to twitter");
		twitter = new Twitter();
		twitter.setOAuthConsumer(Conf.getInstance().getString(ConfId.CONF_TWITTER_CONSUMER), Conf.getInstance().getString(ConfId.CONF_TWITTER_CONSUMER_SECRET));
		AccessToken at=loadAccessToken();
		twitter.setOAuthAccessToken(at);
		//twitter.setUserId(Conf.getInstance().getString(ConfId.CONF_TWITTER_USERID));
		//twitter.setPassword(Conf.getInstance().getString(ConfId.CONF_TWITTER_PASSWORD));
	}

	/**
	 *
	 * @param p
	 * @param args
	 */
	@Override
	public void execute(Progress p,Arguments args) {
		p.init("tweet", false, false, false);
		try {
			// check that the string is not too long...
			double latitude=Current.getInstance().getLatitude();
			double longitude=Current.getInstance().getLongitude();
			Status status=twitter.updateStatus(args.getArgString(),latitude,longitude);
			p.println("updated status to ["+status.getText()+"]");
			// store this status with it's id in the database
			//long id=status.getId();
		} catch (TwitterException ex) {
			throw new RuntimeException(ex);
		}
		p.fini();
	}

	public static void main(String args[]) {
		try {
			Twitter twitter = new Twitter();
			twitter.setOAuthConsumer(Conf.getInstance().getString(ConfId.CONF_TWITTER_CONSUMER), Conf.getInstance().getString(ConfId.CONF_TWITTER_CONSUMER_SECRET));
			RequestToken requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (null == accessToken) {
				System.out.println("Open the following URL and grant access to your account:");
				System.out.println(requestToken.getAuthorizationURL());
				System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
				String pin = br.readLine();
				try {
					if (pin.length() > 0) {
						accessToken = twitter.getOAuthAccessToken(requestToken, pin);
					} else {
						accessToken = requestToken.getAccessToken();
					}
				} catch (TwitterException te) {
					if (401 == te.getStatusCode()) {
						System.out.println("Unable to get the access token.");
					} else {
						te.printStackTrace();
					}
				}
			}
			//persist to the accessToken for future reference.
			storeAccessToken(accessToken);
			System.exit(0);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (TwitterException ex) {
			throw new RuntimeException(ex);
		}
	}
	private static void storeAccessToken(AccessToken at) {
		try {
			String filename = Conf.getInstance().getString(ConfId.CONF_TWITTER_TOKEN_FILE);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(at);
			oos.close();
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	private static AccessToken loadAccessToken() {
		AccessToken at=null;
		try {
			String filename = Conf.getInstance().getString(ConfId.CONF_TWITTER_TOKEN_FILE);
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
			at=(AccessToken)ois.readObject();
			ois.close();
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return at;
	}
}