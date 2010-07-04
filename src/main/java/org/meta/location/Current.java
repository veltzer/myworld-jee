package org.meta.location;

/**
 * Current location class. This is a single pattern.
 * @author mark
 */
public class Current {

	protected double longitude;

	/**
	 * Get the value of longitude
	 *
	 * @return the value of longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Set the value of longitude
	 *
	 * @param longitude new value of longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	protected double latitude;

	/**
	 * Get the value of latitude
	 *
	 * @return the value of latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Set the value of latitude
	 *
	 * @param latitude new value of latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	private static Current instance=null;

	public static Current getInstance() {
		if(instance==null) {
			instance=new Current();
		}
		return instance;
	}

	private Current() {
		latitude=32.087772;
		longitude=34.851379;
	}
}
