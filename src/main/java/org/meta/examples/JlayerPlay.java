package org.meta.examples;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.Player;
/**
 *
 * @author mark
 */
public class JlayerPlay {

	// play the MP3 file to the sound card
	static public void play(String filename) {
		Player player;
		try {
			FileInputStream fis=new FileInputStream(filename);
			BufferedInputStream bis=new BufferedInputStream(fis);
			player=new Player(bis);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		try {
			player.play();
		} catch (JavaLayerException ex) {
			throw new RuntimeException(ex);
		}
		player.close();
	}

	public static void main(String[] args) {
		JlayerPlay.play(args[0]);
	}

}