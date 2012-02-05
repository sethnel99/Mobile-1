package com.nomad.nomadclient;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TruckMap extends MapActivity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        MapView mv = (MapView)findViewById(R.id.mapview);
        mv.setBuiltInZoomControls(true);
        MapController myMC = mv.getController();
        myMC.setCenter(new GeoPoint(41878611,-87363890));
        myMC.setZoom(10);
        
        List<Overlay> mapOverlays = mv.getOverlays();
        Drawable bluetruck = this.getResources().getDrawable(R.drawable.bluetruck);
        HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(bluetruck, this);
    
        GeoPoint point = new GeoPoint(41878611,-87400000);
        OverlayItem overlayitem = new OverlayItem(point, "Hey","Chicago!");
        itemizedoverlay.addOverlay(overlayitem);
        
        point = new GeoPoint(35410000, 139460000);
        overlayitem = new OverlayItem(point, "Hey","Japan!");
        overlayitem.setMarker(itemizedoverlay.myBoundCenterBottom(bluetruck));
        itemizedoverlay.addOverlay(overlayitem);
        
        
        mapOverlays.add(itemizedoverlay);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
