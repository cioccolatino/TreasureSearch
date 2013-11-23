package jp.co.aclox.android.treasuresearch;

import android.util.Log;
import android.content.Context;

public class AreaEngine {
	private static final String TAG = "Treasure";

	private Context mContext = null;
	private YahooReverseGeoCoder mCoder = null;
	private int mMaxRetries = 30;

	public AreaEngine(Context context) {
		mContext = context;
		mCoder = new YahooReverseGeoCoder(mContext);
	}

	public AreaEngine(Context context, YahooReverseGeoCoder coder) {
		mContext = context;
		mCoder = coder;
	}

	public Area getRandomArea() {
		Area area = null;
		int rectNo = 0;
		LandRect rect = null;
		double lat = 0.0, lon = 0.0;
		int count = 0;

		while (area == null && count < mMaxRetries) {
	    	count++;
	    	rectNo = (int)(Math.random() * Land.RECT.length);
	    	rect = Land.RECT[rectNo];
	    	lat = rect.getLatitude2() + Math.random() * 1.0;
	    	lon = rect.getLongitude1() + Math.random() * 1.0;
	    	try {
		    	area = mCoder.getArea(lat, lon);
		    	if ( area == null ) {
		    		Log.v(TAG, "(" + count + ")" + lat + "," + lon + " NG:住所無し");
		    	} else if ( area.getPrefecture() == null ) {
		    		Log.v(TAG, "(" + count + ")" + lat + "," + lon + " NG:" + area.getAddress());
		    		area = null;
		    	} else {
		    		Log.v(TAG, "(" + count + ")" + lat + "," + lon + " OK:" + area.getAddress());
		    	}
	    	} catch (YahooMapApiException e) {
	    		e.printStackTrace();
				Log.e(TAG, "getRandomArea:" + e.toString());
				return null;
	    	}
		}
		if (area == null) {
			Log.w(TAG, "getRandomArea: No area selected.(count=" + count + ")");
	    	return null;
		}
		return area;
	}
}