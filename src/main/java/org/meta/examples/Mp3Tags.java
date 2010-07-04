/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.meta.examples;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.generic.AudioFileReader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
/*
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.filename.FilenameTag;
import org.farng.mp3.lyrics3.AbstractLyrics3;
import org.farng.mp3.id3.AbstractID3v1;
import org.farng.mp3.id3.AbstractID3v2;
*/
/**
 *
 * @author mark
 */
public class Mp3Tags {


	static public void main(String[] args) throws TagException {
		String filename="/home/mark/links/topics_archive/audio/music/by_name/Queen/(1973) Queen/01 - Keep Yourself Alive.mp3";
		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.OFF);
		AudioFile f;
		try {
			AudioFileIO.logger.setLevel(Level.OFF);
			AudioFileReader.logger.setLevel(Level.OFF);
			org.jaudiotagger.tag.id3.ID3v24Frame.logger.setLevel(Level.OFF);
			org.jaudiotagger.tag.datatype.NumberFixedLength.logger.setLevel(Level.OFF);

			f = AudioFileIO.read(new File(filename));
		} catch (CannotReadException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (TagException ex) {
			throw new RuntimeException(ex);
		} catch (ReadOnlyFileException ex) {
			throw new RuntimeException(ex);
		} catch (InvalidAudioFrameException ex) {
			throw new RuntimeException(ex);
		}
		Tag tag = f.getTag();
		Iterator<TagField> itr=tag.getFields();
		while(itr.hasNext()) {
			TagField tf=itr.next();
		}
		System.out.println(tag.getFirst(FieldKey.ARTIST));
	}
}