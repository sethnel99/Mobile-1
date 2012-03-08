package com.nomad.nomadclient;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CustomOverlayItem extends OverlayItem{
	public int index;
	public CustomOverlayItem(GeoPoint point, String title, String snippet, int globalIndex) {
		super(point, title, snippet);
		index = globalIndex;
	}
	

}
