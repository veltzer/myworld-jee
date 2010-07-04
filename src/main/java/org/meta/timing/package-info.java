package org.meta.timing;

/**
 * This package is about timing code and peforming benchmarks.
 *
 * StopWatch: enabled you to measure the time for a piece of code.
 *
 * Plans:
 * Write a class on above stopwatch that enabled to do statistics on many
 * activations of the same stopwatch.
 *
 * Here is a use:
 * 
 * Token k=Timing.getToken("my piece of codes name");
 *
 * Timing.start(k);
 *
 * // some code
 * 
 * Timing.stop(k);
 *
 * Why this token thing? So that we could:
 * - nest timings of different pieces of code in the same run.
 * - use the same token to get statistics about many timings of the same code
 * 
 * At the end we could:
 *
 * TimingData td=Timing.getTimingData(k);
 *
 * And now we can extract number of runs, expectency and standard deviation from
 * the timing data...
 *
 * or maybe just:
 * 
 * Timing.produceReport();
 *
 * Things to think about:
 * - thread safetiness of this interface. We would like this to be thread not
 * safe in order to be quick, but this means that the static interface is out.
 */

