package jp.co.aclox.android.treasuresearch;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ClientProtocolException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;
import android.util.Log;
import android.net.Uri;

import com.google.android.maps.GeoPoint;

public class LatLng2DistanceEngine {

	private static final String TAG = "Treasure";
	private static final String API_URL="http://lab.uribou.net/ll2dist/";

	private DefaultHttpClient mHttpClient = null;

	public LatLng2DistanceEngine() {
		mHttpClient = new DefaultHttpClient();
	}

	public double getDistance(GeoPoint point1, GeoPoint point2) {
		double distance = -1;
		InputStream is = null;

		Uri.Builder uribuilder = new Uri.Builder();
		uribuilder.encodedPath(API_URL);
		uribuilder.appendQueryParameter("ll1", (point1.getLatitudeE6() / 1E6) + "," + (point1.getLongitudeE6() / 1E6));
		uribuilder.appendQueryParameter("ll2", (point2.getLatitudeE6() / 1E6) + "," + (point2.getLongitudeE6() / 1E6));
		String uri = uribuilder.toString();

		try {
			HttpGet httpGet = new HttpGet();
			HttpResponse response = null;
			httpGet.setURI(new URI(uri));
			response = mHttpClient.execute(httpGet);
			if (response == null) {
				Log.w(TAG, "getDistance:Response is null");
				return -1;
			}
			int status = response.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK) {
				Log.w(TAG, "getDistance:HttpStatus=" + status);
				return -1;
			}
			is = response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			Log.e(TAG, "getDistance:" + e.toString());
			e.printStackTrace();
			return -1;
		} catch (URISyntaxException e) {
			Log.e(TAG, "getDistance:" + e.toString());
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			Log.e(TAG, "getDistance:" + e.toString());
			e.printStackTrace();
			return -1;
		}

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "UTF-8");

			int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch(eventType) {
                case XmlPullParser.START_TAG:
                    String tag = parser.getName();
                    if(tag.equals("distance")) {
                    	distance = Double.parseDouble(parser.nextText());
                    	break;
                    }
                    break;
                }
                eventType = parser.next();
            }
            if ( distance < 0 ) {
    			Log.w(TAG, "getDistance: Tag 'distance' not found");
    			return -1;
            }

		} catch (XmlPullParserException e) {
			Log.e(TAG, "getDistance:" + e.toString());
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			Log.e(TAG, "getDistance:" + e.toString());
			e.printStackTrace();
			return -1;
		}

		return distance;
	}

}

