package com.tek.apps.android.stadiumguide.football.map;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class FoodAndDrinkOverlay extends ItemizedOverlay {

//  Store these as global instances so we don't keep reloading every time
   
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	
	private Context mContext;

	public FoodAndDrinkOverlay(Drawable defaultMarker, Context context) {
		super(defaultMarker);
		this.mContext = context;
	}


	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

//	@Override
//	public boolean onTap(GeoPoint point, MapView mapView) {
//		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//		dialog.setTitle("Tapped");
//		dialog.setMessage("you touched it well done");
//		dialog.show();
//		return true;
//
//	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

}
