/**
 * This is the meta configuration package.
 * 
 * The idea is to allow any part of meta to get configuration data from here.
 * 
 * Design goals:
 * 
 * - type safetiness: If I want a file I need to get a java.io.File not a string.
 * 
 * - configuration names are constants and therefore checked at compile time.
 * 
 * - configuration loads ALL configuration options on load time (boot time) and checks
 *	that all configurations are there (this will probably be from the database.
 * - configuration makes type specific checks at load time (If a directory is supposed
 * to exist then it checks that this is the case.
 */

package org.meta.conf;

