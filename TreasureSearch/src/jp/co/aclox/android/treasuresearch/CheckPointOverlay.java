package jp.co.aclox.android.treasuresearch;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CheckPointOverlay extends ItemizedOverlay<OverlayItem> implements GestureDetector.OnGestureListener {

	private TreasureMapView mMapView = null;
	private ArrayList<OverlayItem> mOverlayItems = new ArrayList<OverlayItem>();
	private GestureDetector mGestureDetector = null;

	CheckPointOverlay(Drawable defaultMarker, TreasureMapView mapView) {
		super(defaultMarker);
		mMapView = mapView;
		ItemizedOverlay.boundCenterBottom(defaultMarker);
		Rect rect = defaultMarker.getBounds();
		defaultMarker.setBounds(rect.left + 9, rect.top, rect.right + 9, rect.bottom);

		mGestureDetector = new GestureDetector(this);
		populate();	// 必ず実行
	}

	/*
	 * (非 Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#onTap(int)
	 */
    @Override
    protected boolean onTap(int index) {
        super.onTap(index);
        OverlayItem item = getItem(index);
        if (item != null) {
        	int number = Integer.parseInt(item.getTitle());
	        if ( mMapView.getListener() != null) {
	        	mMapView.getListener().onCheckPointTap(number);
	        }
        }

        return true;
    }

    /*
     * (非 Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#createItem(int)
     */
    @Override
    protected OverlayItem createItem(int i) {
        return this.mOverlayItems.get(i);
    }

    /*
     * (非 Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#size()
     */
    @Override
    public int size() {
        return this.mOverlayItems.size();
    }

    /*
     * (非 Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#onTouchEvent(android.view.MotionEvent, com.google.android.maps.MapView)
     */
    @Override
    public boolean onTouchEvent(android.view.MotionEvent event, MapView mapView) {
        mGestureDetector.onTouchEvent(event);
    	return super.onTouchEvent(event, mapView);
    }

    /*
     * (非 Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#draw(android.graphics.Canvas, com.google.android.maps.MapView, boolean)
     */
	@Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
        if (!shadow) {
/*
        	Point p1 = new Point();
	    	Point p2 = new Point();
       		Paint paint = new Paint();
	    	int length = Land.RECT.length;
	    	for (int i = 0; i < length; i++) {
	    		LandRect rect = Land.RECT[i];
		    	mapView.getProjection().toPixels(
		    			new GeoPoint((int)(rect.getLatitude1() * 1E6), (int)(rect.getLongitude1() * 1E6)), p1);
	            mapView.getProjection().toPixels(
		            	new GeoPoint((int)(rect.getLatitude2() * 1E6), (int)(rect.getLongitude2() * 1E6)), p2);
     		    paint.setColor(Color.LTGRAY);
     		    paint.setStyle(Paint.Style.STROKE);
   		    	canvas.drawRect(p1.x, p1.y, p2.x, p2.y, paint);
//			    	Log.d(TAG, "draw " + p1.x+ ","+p1.y + "-"+p2.x+","+p2.y);
        	}
*/
        	int size = mOverlayItems.size();
        	Point p = new Point();
       		Paint paint = new Paint();
 		    paint.setColor(Color.RED);
 		    paint.setTextSize(12);
        	for (int i = 0; i < size; i++) {
        		OverlayItem item = mOverlayItems.get(i);
		    	mapView.getProjection().toPixels(item.getPoint(), p);
		    	int number = Integer.parseInt(item.getTitle());
//		    	double distance = Double.parseDouble(item.getSnippet());
//    			String text = (distance >= 1000 ?
//    					String.format("[%d]%.1fkm", number, distance / 1000) :
//    					String.format("[%d]%dm", number, (int)distance));
    			String text = String.format("%d", number);
	        	canvas.drawText(text, 0, text.length(), p.x + 3, p.y - 10, paint);
        	}

        }
	}

	/*
	 * (非 Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
	 */
	public boolean onDown(MotionEvent event) {
		return true;
	}

	/*
	 * (非 Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		return true;
	}

	/*
	 * (非 Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onLongPress(android.view.MotionEvent)
	 */
	public void onLongPress(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        GeoPoint point = mMapView.getProjection().fromPixels((int)x, (int)y);

        if ( mMapView.getListener() != null) {
        	mMapView.getListener().onMapViewLongPress(point);
        }

	}

	/*
	 * (非 Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		return true;
	}

	/*
	 * (非 Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onShowPress(android.view.MotionEvent)
	 */
	public void onShowPress(MotionEvent event) {

	}

	/*
	 * (非 Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onSingleTapUp(android.view.MotionEvent)
	 */
	public boolean onSingleTapUp(MotionEvent event) {
		return true;
	}

	/*
	 *
	 */
	public int addCheckerItem(int number, GeoPoint point, double distance) {
		OverlayItem item = new OverlayItem(point, Integer.toString(number), Double.toString(distance));
		mOverlayItems.add(item);
		setLastFocusedIndex(-1);	// これが無いとArrayIndexOutOfBoundsException発生
		populate();
		return mOverlayItems.size() - 1;
	}

	public void clearCheckerItem() {
		mOverlayItems.clear();
		setLastFocusedIndex(-1);	// これが無いとArrayIndexOutOfBoundsException発生
		populate();
	}

}
