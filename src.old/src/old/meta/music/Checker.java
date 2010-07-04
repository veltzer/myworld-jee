package meta.music;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import meta.utils.ds.filters.FilterAnd;
import meta.utils.ds.iterators.IteratorWithFilter;
import meta.utils.fs.FilterFile;
import meta.utils.fs.FilterNameRegexp;
import meta.utils.fs.IteratorFileRecursive;
import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.ID3Tag;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;

/**
 * This application validates that my music collection is in order.
 *
 * It should check
 * - That all files have the right suffix (out of a set of given suffixes)
 * - Each file resides in a directory of the right depth
 * - The tags in the file match the directory in which the file resides.
 * - The file name reflects the meta data of the file using a consistent naming
 * scheme.
 * - Each file has embedded the image of the album from which it is taken.
 *
 * Future:
 * - Generate database tables filled with the meta data of the collection.
 * - Correlation with freedb.
 * - Submittion to music brainz for identification.
 *
 */
public class Checker
{
	public static void main(String[] args)
	{
		final String filename="/home/mark/dev/topics_archive/music/library/by_name/";
		IteratorFileRecursive iter=new IteratorFileRecursive(filename);
		FilterAnd<File> filter=new FilterAnd<File>();
		filter.addFilter(new FilterFile());
		filter.addFilter(new FilterNameRegexp("^.*.mp3"));
		IteratorWithFilter<File> it=new IteratorWithFilter<File>(iter, filter);
		int count=0;
		for(File x: it) {
			System.out.println(x.getName());
			MediaFile oMediaFile = new MP3File(x);
			try {
				ID3Tag[] tags=oMediaFile.getTags();
				if(tags.length!=1) {
					String msg="File ["+x.getName()+"] has "+tags.length+" tags";
					count++;
					//throw new RuntimeException(msg);
				}
				/*
				for(ID3Tag tag: tags) {
					System.out.println(tag);
				}
				*/
			} catch (ID3Exception ex) {
				Logger.getLogger(Checker.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		System.out.println("number of more than 1 tag "+count);
	}
}