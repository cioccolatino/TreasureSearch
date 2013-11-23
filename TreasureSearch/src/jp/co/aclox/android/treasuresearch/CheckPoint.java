package jp.co.aclox.android.treasuresearch;

import java.io.Serializable;

import com.google.android.maps.GeoPoint;

public class CheckPoint implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2166591771026034130L;
	private double mLatitude = 0, mLongitude = 0;
	private double mDistance = 0;

	public CheckPoint(double latitude, double longitude) {
		mLatitude = latitude;
		mLongitude = longitude;
	}

	public double getLatitude() { return mLatitude; }
	public double getLongitude() { return mLongitude; }
	public double getDistance() { return mDistance; }
	public GeoPoint getGeoPoint() {
		return new GeoPoint((int)(mLatitude * 1E6), (int)(mLongitude * 1E6));
	}

	public void setLatitude(double latitude) { mLatitude = latitude; }
	public void setLongtitude(double longitude) { mLongitude = longitude; }
	public void setDistance(double distance) { mDistance = distance; }
}
