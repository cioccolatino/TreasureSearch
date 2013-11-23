package jp.co.aclox.android.treasuresearch;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class TreasureOverlay extends ItemizedOverlay<OverlayItem> {
	private static final String TAG = "Treasure";
	private static final int TREASURE_ZOOM_LEVEL = 18;

	private TreasureMapView mMapView = null;
	private GeoPoint mTreasurePoint = null;
	private OverlayItem mTreasureItem = null;
	private boolean mTreasureVisible = false;
	private boolean mTreasureDiscovered = false;

	TreasureOverlay(Drawable defaultMarker, TreasureMapView mapView) {
		super(defaultMarker);
		Log.v(TAG, "TreasureOverlay created.");
		mMapView = mapView;
		ItemizedOverlay.boundCenter(defaultMarker);
		setLastFocusedIndex(-1);	// これが無いとArrayIndexOutOfBoundsException発生
		populate();	// 必ず実行
	}

	@Override
	protected OverlayItem createItem(int i) {
    	return mTreasureItem;
	}

	@Override
	public int size() {
        return (mTreasureVisible ? 1 : 0);
	}

	@Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
    	boolean treasureVisibleLast = mTreasureVisible;
    	if (mTreasurePoint == null) mTreasureVisible = false;
    	else if (mTreasureDiscovered) mTreasureVisible = true;
    	else if (mMapView.getZoomLevel() < TREASURE_ZOOM_LEVEL) mTreasureVisible = false;
    	else mTreasureVisible = true;

    	if ( mTreasureVisible != treasureVisibleLast ) {
			setLastFocusedIndex(-1);
    		populate();
    	}
		super.draw(canvas, mapView, shadow);
	}

    @Override
    protected boolean onTap(int index) {
        super.onTap(index);
        if (mMapView.getListener() != null) {
        	mMapView.getListener().onTreasureItemTap();
        }

        return true;
    }

	void setTreasureItem(GeoPoint point, String title, String desc) {
		mTreasurePoint = point;
		mTreasureItem = new OverlayItem(point, title, desc);
		setLastFocusedIndex(-1);	// これが無いとArrayIndexOutOfBoundsException発生
		populate();
	}

	void clearTreasureItem() {
		mTreasurePoint = null;
		setLastFocusedIndex(-1);	// これが無いとArrayIndexOutOfBoundsException発生
		populate();
	}

	void setTreasureDiscovered(boolean discovered) {
		mTreasureDiscovered = discovered;
		setLastFocusedIndex(-1);	// これが無いとArrayIndexOutOfBoundsException発生
		populate();
	}

}

