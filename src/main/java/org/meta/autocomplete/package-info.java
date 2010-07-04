/**
 * This package deals with autocomplete structures which may be of 3 types:
 * - in RAM
 * - in some backing store (embedded database, file, extenal database,...)
 * - A combination of both.
 *
 * Currntly only two implementations exist:
 * - A tree trie which is very efficient
 * - A Set which is less efficient but much easier to handle.
 *
 * TODO: Need to add another method like "get first 10 completions" so that the
 * inteface will be more efficient and will enable further optimizations (like
 * when going to the database).
 */

package org.meta.autocomplete;

