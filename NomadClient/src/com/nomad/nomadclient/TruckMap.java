/***
 * Copyright (c) 2011 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.nomad.nomadclient;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TruckMap extends MapActivity {
	public static int FROM_LIST = 0;
	public static int FROM_TRUCK_PAGE = 1;

	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	CustomItemizedOverlay<OverlayItem> itemizedOverlay;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        Button backButton = (Button)findViewById(R.id.backButton);
        Button listButton = (Button)findViewById(R.id.listButton);
        
        int fromPage = this.getIntent().getExtras().getInt("fromPage");
        if(fromPage == FROM_TRUCK_PAGE){
        	((Button)findViewById(R.id.backButton)).setVisibility(View.VISIBLE); 
        	((Button)findViewById(R.id.listButton)).setVisibility(View.GONE);
        }
        
        backButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				finish();
			}
		});
        listButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				finish();
			}
		});
		   
        
        mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		mapOverlays = mapView.getOverlays();
		
		// first overlay
		drawable = getResources().getDrawable(R.drawable.truckpin);
		itemizedOverlay = new CustomItemizedOverlay<OverlayItem>(drawable, mapView);
		
		GeoPoint point = new GeoPoint((int)(51.5174723*1E6),(int)(-0.0899537*1E6));
		OverlayItem overlayItem = new OverlayItem(point, "5411 Empadas", 
				"Mexican, Empanadas, Cheap");
		itemizedOverlay.addOverlay(overlayItem);
		
		GeoPoint point2 = new GeoPoint((int)(51.515259*1E6),(int)(-0.086623*1E6));
		OverlayItem overlayItem2 = new OverlayItem(point2, "Flirty Cupcakes","Cupcakes, Sweet, Desert");		
		itemizedOverlay.addOverlay(overlayItem2);
		
		mapOverlays.add(itemizedOverlay);
	
		
		final MapController mc = mapView.getController();
		mc.animateTo(point2);
		mc.setZoom(16);
		
    }
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
