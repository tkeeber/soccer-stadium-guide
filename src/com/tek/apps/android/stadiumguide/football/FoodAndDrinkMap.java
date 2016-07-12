package com.tek.apps.android.stadiumguide.football;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;
import com.tek.apps.android.stadiumguide.football.map.MapLocation;
import com.tek.apps.android.stadiumguide.football.map.MapLocationOverlay;
import com.tek.apps.android.stadiumguide.football.map.MapLocationViewer;
import com.tek.apps.android.stadiumguide.football.map.StadiumOverlay;

public class FoodAndDrinkMap extends MapActivity implements MapLocationViewer {

	private AwayTeamDbAdapter mDbAdapter = null;
		
    //  Known latitude/longitude coordinates that we'll be using.
    private List<MapLocation> mapLocations;
    
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.food_and_drink_map);

		mDbAdapter = new AwayTeamDbAdapter(this);
		mDbAdapter.open();
		
		
		mapLocations = new ArrayList<MapLocation>();
		
		MapView mapView = (MapView)findViewById(R.id.mapView);
	    MapController mapController = mapView.getController();
		mapView.setBuiltInZoomControls(true);
		
		
		mapView.displayZoomControls(true);
		mapView.setBuiltInZoomControls(true);
		
		List<Overlay> mapOverlays = mapView.getOverlays();
//		Drawable drawable = this.getResources().getDrawable(R.drawable.food_32);
		MapLocationOverlay venueOverlay = new MapLocationOverlay(this,this);

		int stadiumId = getIntent().getIntExtra("stadiumId", -1);
		int[] venuesToShow = getIntent().getIntArrayExtra("venuesToShow");
		
		setMapLocations(venuesToShow);
		
		//show all the venues on the map.
//		if (venuesToShow != null) {
//			Cursor cursor = mDbAdapter.fetchFoodAndDrinkVenuesForMap(venuesToShow);
//			startManagingCursor(cursor);
//
//			cursor.moveToFirst();
//			for (int i=0; i<cursor.getCount(); i++) { 
//
//				//			GeoPoint point = new GeoPoint(19240000,-99120000);
//				Log.d("", "Latitude: " + cursor.getInt(3));
//				Log.d("", "Longitude: " + cursor.getInt(4));
//				
//				String name = (cursor.getString(1));
//				int latitude = (int) (cursor.getDouble(3) * 1E6);
//				int longitude = (int) (cursor.getDouble(4) * 1E6);
//				GeoPoint point = new GeoPoint(latitude, longitude);
//
//				mapLocations.add(new MapLocation(name,latitude,longitude));
//				
//				OverlayItem overlayitem = new OverlayItem(point, "blah", "blah");
//				venueOverlay.addOverlay(overlayitem);	  
//
//				cursor.moveToNext();
//			}
//			cursor.close();
//		}
		
		Drawable stadiumDrawable = this.getResources().getDrawable(R.drawable.soccer_stadium_32);

		StadiumOverlay stadiumOverlay = new StadiumOverlay(stadiumDrawable, this);
		
		//show the stadium on the map.
		Cursor stadiumCursor = mDbAdapter.fetchStadiumDetails(stadiumId);
		startManagingCursor(stadiumCursor);
		stadiumCursor.moveToFirst();
		
		GeoPoint stadiumPoint = null;
		
		for (int i=0; i<stadiumCursor.getCount(); i++) {
			int latitude = (int) (stadiumCursor.getDouble(4) * 1E6);
			int longitude = (int) (stadiumCursor.getDouble(5) * 1E6);
			stadiumPoint = new GeoPoint(latitude, longitude);
			
			OverlayItem overlayitem = new OverlayItem(stadiumPoint, "stadium", "stadium");
			stadiumOverlay.addOverlay(overlayitem);	  

			mapController.animateTo(stadiumPoint);
		}
		
		mapOverlays.add(venueOverlay);
		mapOverlays.add(stadiumOverlay);

		mapController.setZoom(14); 
		mapController.setCenter(stadiumPoint);
		
		mDbAdapter.close();
	}
	
	public void setMapLocations(int[] venuesToShow) {

		//show all the venues on the map.
		if (venuesToShow != null) {
			Cursor cursor = mDbAdapter.fetchFoodAndDrinkVenuesForMap(venuesToShow);
			startManagingCursor(cursor);

			cursor.moveToFirst();
			for (int i=0; i<cursor.getCount(); i++) { 

				String name = (cursor.getString(2));
				int latitude = (int) (cursor.getDouble(3) * 1E6);
				int longitude = (int) (cursor.getDouble(4) * 1E6);

				mapLocations.add(new MapLocation(name,latitude,longitude));

				cursor.moveToNext();
			}
			cursor.close();
		}

	}
	
	public List<MapLocation> getMapLocations() {
		return mapLocations;
	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}


}
