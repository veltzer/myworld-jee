package org.meta.timing;

/**
 *
 * @author mark
 */
public class StopWatch {
	private enum StopWatchState {
		NOT_STARTED,
		STARTED,
		ENDED
	}
	private long startTime;
	private long stopTime;
	private long result;
	private StopWatchState state=StopWatchState.NOT_STARTED;
	/**
	 * Constructor for this stopwatch
	 */
	public StopWatch() {
		reset();
	}

	/**
	 * Start the stopwatch
	 */
	public void start() {
		if(state!=StopWatchState.NOT_STARTED) {
			throw new RuntimeException("bad state");
		}
		state=StopWatchState.STARTED;
		startTime=System.currentTimeMillis();
	}
	/**
	 * Stop the stopwatch
	 */
	public void stop() {
		if(state!=StopWatchState.STARTED) {
			throw new RuntimeException("bad state");
		}
		stopTime=System.currentTimeMillis();
		state=StopWatchState.ENDED;
		result=stopTime-startTime;
	}
	/**
	 * returns elapsed time in milliseconds
	 *
	 * @return
	 */
	public long getElapsedTime() {
		if(state!=StopWatchState.ENDED) {
			throw new RuntimeException("bad state");
		}
		return result;
	}
	/**
	 * Reset this instance so that it can be used again
	 */
	public void reset() {
		startTime=java.lang.Long.MIN_VALUE;
		stopTime=java.lang.Long.MIN_VALUE;
		state=StopWatchState.NOT_STARTED;
	}
}