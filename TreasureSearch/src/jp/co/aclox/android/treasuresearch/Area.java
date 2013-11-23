package jp.co.aclox.android.treasuresearch;

import java.io.Serializable;

import com.google.android.maps.GeoPoint;

public class Area implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -2686260838202770204L;
	private boolean mValid = true;
	private String mZipCode = null;
	private String mAddress = null;
	private String mPrefecture = null;
	private String mCity = null;
	private String mTown = null;
	private String mChoban = null;
	private double mLatitude = 0;
	private double mLongitude = 0;

	public Area() {
		mValid = true;
	}
	public Area(boolean valid) {
		mValid = valid;
	}

	public boolean isValid() { return mValid; }
	public String getZipCode() { return mZipCode; }
	public String getAddress() { return mAddress; }
	public String getPrefecture() { return mPrefecture; }
	public String getCity() { return mCity; }
	public String getTown() { return mTown; }
	public String getChoban() { return mChoban; }
	public double getLatitude() { return mLatitude; }
	public double getLongtitude() { return mLongitude; }
	public GeoPoint getGeoPoint() {
		return new GeoPoint((int)(mLatitude * 1E6), (int)(mLongitude * 1E6));
	}

	public void setValid(boolean valid) { mValid = valid; }
	public void setZipCode(String zipcode) { mZipCode = zipcode; }
	public void setAddress(String address) { mAddress = address; }
	public void setPrefecture(String prefecture) { mPrefecture = prefecture; }
	public void setCity(String city) { mCity = city; }
	public void setTown(String town) { mTown = town; }
	public void setChoban(String choban) { mChoban = choban; }
	public void setLatitude(double latitude) { mLatitude = latitude; }
	public void setLongtitude(double longitude) { mLongitude = longitude; }

	@Override
	public String toString() {
		if ( mAddress.equals("") )
			return mZipCode + " " + mPrefecture + " " + mCity + " " + mTown + " " + mChoban;
		else
			return mZipCode + " " + mAddress;
	}

	public static final Area INVALID_AREA = new Area(false);


}
