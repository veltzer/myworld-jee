package org.meta.examples;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import org.jfugue.Pattern;
import org.jfugue.Player;

/**
 *
 * @author mark
 */
public class PlayMidi {

	public static void printInfo() {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for(MidiDevice.Info x: infos) {
			System.out.println("device "+x.getName()+","+x.getDescription()+","+x.getVendor()+","+x.getVersion()+","+x.getClass().getCanonicalName());
			//System.out.println("\t"+x.getClass());
			try {
				MidiDevice md=MidiSystem.getMidiDevice(x);
				for(Receiver y: md.getReceivers()) {
					System.out.println(y.toString());
				}
				//Transmitter t=md.getTransmitter();
				//System.out.println(t.toString());
			} catch (MidiUnavailableException ex) {
				throw new RuntimeException("cant get midi device");
			}
		}
	}
	public static MidiDevice getDevice(String devName) {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for(MidiDevice.Info x: infos) {
			if(x.getName().equals(devName)) {
				try {
					MidiDevice md=MidiSystem.getMidiDevice(x);
					if(md==null) {
						throw new RuntimeException("Cant get device");
					}
					return md;
				} catch (MidiUnavailableException ex) {
					throw new RuntimeException("cant get midi device");
				}
			}
		}
		throw new RuntimeException("didnt find device by name");
	}
	public static Sequencer getSeq(String sqname,String outputDev) {
		try {
			Sequencer sm_sequencer=(Sequencer)getDevice(sqname);
			sm_sequencer.open();
			MidiDevice outputDevice=getDevice(outputDev);
			outputDevice.open();
			Receiver midiReceiver =outputDevice.getReceiver();
			Transmitter midiTransmitter = sm_sequencer.getTransmitter();
			midiTransmitter.setReceiver(midiReceiver);
			return sm_sequencer;
		} catch (MidiUnavailableException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void playMusic(Sequencer s) {
		// "Frere Jacques"
		Pattern pattern1 = new Pattern("C5q D5q E5q C5q");

		// "Dormez-vous?"
		Pattern pattern2 = new Pattern("E5q F5q G5h");

		// "Sonnez les matines"
		Pattern pattern3 = new Pattern("G5i A5i G5i F5i E5q C5q");

		// "Ding ding dong"
		Pattern pattern4 = new Pattern("C5q G4q C5h");

		// Put all of the patters together to form the song
		Pattern song = new Pattern();
		song.add(pattern1, 2); // Adds 'pattern1' to 'song' twice
		song.add(pattern2, 2); // Adds 'pattern2' to 'song' twice
		song.add(pattern3, 2); // Adds 'pattern3' to 'song' twice
		song.add(pattern4, 2); // Adds 'pattern4' to 'song' twice

		// Play the song!
		Player player = new Player(s);
		player.play(song);
		//player.play("C5q");
	}
	public static void main(String[] args) {
		printInfo();
		//Sequencer s=getSeq("Java Sound Synthesizer","foo");
		//Sequencer s=getSeq("ALSA MIDI port (128:0)","ALSA MIDI port (128:0)");
		//Sequencer s=getSeq("Tritonus ALSA Sequencer","ALSA MIDI port (128:0)");
		//Sequencer s=getSeq("Gervill","ALSA MIDI port (128:0)");
		//Sequencer s=getSeq("Tritonus Java Sequencer","ALSA MIDI port (128:0)");
		//Sequencer s=getSeq("Real Time Sequencer","ALSA MIDI port (128:0)");
		Sequencer s=getSeq("Real Time Sequencer","Java Sound Synthesizer");
		//Sequencer s=getSeq("Tritonus fluidsynth Synthesizer","ALSA MIDI port (128:0)");
		playMusic(s);
		s.close();
	}
}