package jp.co.aclox.android.treasuresearch;

public class LandRect {
	private double mLatitude1 = 0;
	private double mLongitude1 = 0;
	private double mLatitude2 = 0;
	private double mLongitude2 = 0;

	public LandRect(double latitude1, double longitude1, double latitude2, double longitude2) {
		mLatitude1 = latitude1;
		mLongitude1 = longitude1;
		mLatitude2 = latitude2;
		mLongitude2 = longitude2;
	}

	public double getLatitude1() { return mLatitude1; }
	public double getLongitude1() { return mLongitude1; }
	public double getLatitude2() { return mLatitude2; }
	public double getLongitude2() { return mLongitude2; }

}
