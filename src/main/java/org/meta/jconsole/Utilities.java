package org.meta.jconsole;

/**
 *
 * @author mark
 */
public class Utilities {

	// used in the pad() method
	/**
	 *
	 */
	public static final int PAD_LEFT = 1;
	/**
	 *
	 */
	public static final int PAD_RIGHT = 2;

	/*
	 * Pads the given string with spaces
	 */
	/**
	 *
	 * @param aStr
	 * @param aLength
	 * @param padPos
	 * @return
	 */
	public static String Pad(String aStr, int aLength, int padPos) {
		StringBuffer theResult = new StringBuffer();

		if ((aStr != null) && (aStr.length() > aLength)) {
			theResult.append(aStr.substring(0, aLength));
		} else {
			int l;

			if (aStr == null) {
				l = aLength;
			} else {
				aStr = aStr.trim();
				l	= aLength - aStr.length();
				theResult.append(aStr);
			}

			for (int x = 0; x < l; x++) {
				if (padPos == PAD_LEFT) {
					theResult.insert(0, " ");
				} else if (padPos == PAD_RIGHT) {
					theResult.append(" ");
				}
			}
		}

		return theResult.toString();
	}
}