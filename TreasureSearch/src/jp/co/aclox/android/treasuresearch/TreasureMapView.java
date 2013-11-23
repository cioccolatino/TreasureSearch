package jp.co.aclox.android.treasuresearch;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;

public class TreasureMapView extends MapView {

	private static final String TAG = "Treasure";

	private MapController mController = null;
	private CheckPointOverlay mCheckPointOverlay = null;
	private TreasureOverlay mTreasureOverlay = null;
	private TreasureMapViewListener mListener = null;

	public TreasureMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
    	mController = getController();

    	Resources res = getResources();
		mCheckPointOverlay = new CheckPointOverlay(res.getDrawable(R.drawable.flag_yellow), this);
		getOverlays().add(mCheckPointOverlay);
		mTreasureOverlay = new TreasureOverlay(res.getDrawable(R.drawable.takarabako), this);
    	getOverlays().add(mTreasureOverlay);
    	setBuiltInZoomControls(true);
	}

	public TreasureMapViewListener getListener() {
		return mListener;
	}

	public void setListener(TreasureMapViewListener listener) {
		mListener = listener;
	}

	public void moveTo(GeoPoint point) {
		mController.animateTo(point);
	}

	public int addCheckPoint(int number, CheckPoint checkPoint) {
		double distance = checkPoint.getDistance();

		Log.v(TAG, String.format("CheckPoint(%d) 距離:%.3fkm", number, distance / 1000));
		return mCheckPointOverlay.addCheckerItem(number, checkPoint.getGeoPoint(), distance);
	}

	public void setTreasure(Area treasureArea) {
		mTreasureOverlay.setTreasureItem(treasureArea.getGeoPoint(), "宝箱", treasureArea.getAddress());
	}

	public void setTreasureDiscovered(boolean discovered) {
		mTreasureOverlay.setTreasureDiscovered(discovered);
	}

	public void clear() {
		mCheckPointOverlay.clearCheckerItem();
		mTreasureOverlay.setTreasureDiscovered(false);
		mTreasureOverlay.clearTreasureItem();
	}

	/*
	 *
	 */
	public interface TreasureMapViewListener {
		public void onMapViewLongPress(GeoPoint point);
		public void onCheckPointTap(int number);
		public void onTreasureItemTap();
	}

}