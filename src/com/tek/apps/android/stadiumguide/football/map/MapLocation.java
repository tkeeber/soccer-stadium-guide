package com.tek.apps.android.stadiumguide.football.map;

import com.google.android.maps.GeoPoint;


public class MapLocation {

	private GeoPoint	point;
	private String		name;

	public MapLocation(String name,double latitude, double longitude) {
		this.name = name;
		point = new GeoPoint((int)(latitude),(int)(longitude));
	}

	public GeoPoint getPoint() {
		return point;
	}

	public String getName() {
		return name;
	}
}