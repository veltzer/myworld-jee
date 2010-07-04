package org.meta.wrappers.org.apache.commons.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.meta.progress.Progress;

/**
 * This is a wrapper for the DirectoryWalker class
 * - does away with it's checked exceptions.
 * - does away with the collection in it's methods.
 * - reports on its progress
 *
 * @author mark
 */
public class DirectoryWalker extends org.apache.commons.io.DirectoryWalker {

	/**
	 * Progress report to be used
	 * @param p
	 */
	Progress p;

	/**
	 *
	 * @param p
	 */
	public DirectoryWalker(Progress p) {
		super();
		this.p=p;
	}

	/**
	 *
	 * @param directory
	 * @param depth
	 * @param results
	 * @return
	 * @throws java.io.IOException
	 */
	@Override
	final protected boolean handleDirectory(File directory, int depth, Collection results) throws IOException {
		//super.handleDirectory(directory, depth, results);
		return handleDirectory(directory, depth);
	}

	/**
	 *
	 * @param directory
	 * @param depth
	 * @param results
	 * @throws java.io.IOException
	 */
	@Override
	final protected void handleDirectoryEnd(File directory, int depth, Collection results) throws IOException {
		super.handleDirectoryEnd(directory, depth, results);
		handleDirectoryEnd(directory,depth);
	}


	/**
	 *
	 * @param file
	 * @param depth
	 * @param results
	 * @throws java.io.IOException
	 */
	@Override
	final protected void handleFile(File file, int depth, Collection results) throws IOException {
		super.handleFile(file, depth, results);
		handleFile(file,depth);
		p.workl(file.getName());
	}

	/**
	 * This is part of the singleton pattern(make sure that no one
	 * creates instances of this class).
	 */
	protected void handleFile(File file,int depth) {
		// do nothing, please override
	}

	/**
	 *
	 * @param startDirectory
	 * @param results
	 * @throws java.io.IOException
	 */
	@Override
	final protected void handleStart(File startDirectory, Collection results) throws IOException {
		super.handleStart(startDirectory, results);
		handleStart(startDirectory);
		p.startl("walking");
	}

	/**
	 *
	 * @param startDirectory
	 */
	protected void handleStart(File startDirectory) {
		// do nothing, please override
	}

	/**
	 *
	 * @param results
	 * @throws java.io.IOException
	 */
	@Override
	final protected void handleEnd(Collection results) throws IOException {
		super.handleEnd(results);
		handleEnd();
		p.endl("walking");
		p.fini();
	}

	/**
	 *
	 */
	protected void handleEnd() {
		// do nothing, please override
	}
	/**
	 * This is my own simplified version of the walk method
	 * - got ridd of the stupid collection to carry around
	 * - catch all the exceptions coming out of this layer and rethrow them as runtime exceptions.
	 */
	public void walk(File file) {
		List results=new ArrayList<Object>();
		try {
			walk(file,results);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 *
	 * @param directory
	 * @param depth
	 */
	protected void handleDirectoryEnd(File directory, int depth) {
		// do nothing, please override
	}

	/**
	 *
	 * @param directory
	 * @param depth
	 * @return
	 */
	protected boolean handleDirectory(File directory, int depth) {
		// do nothing, please override
		return true;
	}
}