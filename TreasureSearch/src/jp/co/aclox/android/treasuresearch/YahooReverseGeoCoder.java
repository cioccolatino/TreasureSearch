package jp.co.aclox.android.treasuresearch;



import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.util.Log;
import android.net.Uri;
import android.content.Context;
import android.content.res.Resources;

public class YahooReverseGeoCoder {

	private static final String YAHOO_REVERSE_GEOCODER_URL = "http://reverse.search.olp.yahooapis.jp/OpenLocalPlatform/V1/reverseGeoCoder";
	private static final String TAG = "Treasure";

	private Context mContext = null;
	private DefaultHttpClient mHttpClient = null;
	private String mYahooAppId = null;

	public YahooReverseGeoCoder(Context context) {
		mContext = context;
		mHttpClient = new DefaultHttpClient();
		Resources res = mContext.getResources();
		mYahooAppId = res.getString(R.string.yahoo_appid);
	}

	public Area getArea(double latitude, double longtitude) throws YahooMapApiException {
		String json = null;
		Area area = null;

		Uri.Builder uribuilder = new Uri.Builder();
		uribuilder.encodedPath(YAHOO_REVERSE_GEOCODER_URL);
		uribuilder.appendQueryParameter("lat", latitude + "");
		uribuilder.appendQueryParameter("lon", longtitude + "");
		uribuilder.appendQueryParameter("datum", "tky");
		uribuilder.appendQueryParameter("output", "json");
		uribuilder.appendQueryParameter("appid", mYahooAppId);
		String uri = uribuilder.toString();

		try {
			HttpGet httpGet = new HttpGet();
			HttpResponse response = null;
			httpGet.setURI(new URI(uri));
			response = mHttpClient.execute(httpGet);
			if (response == null) {
				Log.w(TAG, "YahooReverseGeoCoder.getArea:Response is null");
				throw new YahooMapApiException("通信エラー");
			}
			int status = response.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK) {
				Log.w(TAG, "YahooReverseGeoCoder.getArea:HttpStatus=" + status);
				throw new YahooMapApiException("通信エラー status=" + status);
			}

			json = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			Log.e(TAG, "getArea:" + e.toString());
			e.printStackTrace();
			return null;
		} catch (URISyntaxException e) {
			Log.e(TAG, "getArea:" + e.toString());
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			Log.e(TAG, "getArea:" + e.toString());
			e.printStackTrace();
			return null;
		}

		try {
            JSONObject jObject = new JSONObject(json);
            JSONObject jResultInfo = jObject.getJSONObject("ResultInfo");
            int resultcount = jResultInfo.getInt("Count");
            if ( resultcount == 0 ) {
    			return null;
            }

            JSONObject jFeature = jObject.getJSONArray("Feature").getJSONObject(0);
            JSONObject jProperty = jFeature.getJSONObject("Property");

        	area = new Area();
        	area.setLatitude(latitude);
        	area.setLongtitude(longtitude);
        	area.setAddress(jProperty.getString("Address"));

        	if (jProperty.has("AddressElement")) {
	        	JSONArray jElements = jProperty.getJSONArray("AddressElement");
	            int length = jElements.length();
	            for ( int i = 0; i < length; i++ ) {
	            	JSONObject jElement = jElements.getJSONObject(i);
	            	String name = jElement.getString("Name");
	            	String level = jElement.getString("Level");
	            	if ( level.equals("prefecture")) area.setPrefecture(name);
	            	else if ( level.equals("city")) area.setCity(name);
	            	else if ( level.equals("oaza")) area.setTown(name);
	            	else if ( level.equals("aza")) area.setChoban(name);
	            }
        	}


		} catch (JSONException e) {
			Log.e(TAG, "YahooReverseGeoCoder.getArea:" + e.toString());
			e.printStackTrace();
			return null;
		}

		return area;
	}





}

