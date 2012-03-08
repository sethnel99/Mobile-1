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

import java.util.ArrayList;
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
import com.parse.Parse;

public class TruckMap extends MapActivity {
	public static int FROM_LIST = 0;
	public static int FROM_TRUCK_PAGE = 1;

	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	ArrayList<FoodTruck> trucks;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		//If the process was killed and then restarted here, get the trucks from the cache
		if(((NomadClientApplication)this.getApplication()).getTrucks().size() == 0){
			Parse.initialize(this, "FoX2hKFWtiUIWgt2mioFIJvwdwgy461XAS7n367S", "EU6d1ccuc3rUiW09IXqnLGF8XNngazVCWZDvSfC1"); 
			((NomadClientApplication)this.getApplication()).loadTrucksFromParse(NomadClientApplication.CACHE_FIRST);
		}

		trucks = ((NomadClientApplication)this.getApplication()).getTrucks();

		if(trucks.size() == 0)
			return;

		//Grab the truck index (given as an extra) and get the truck
		Bundle b = this.getIntent().getExtras();
		int startIndex = -1;
		if(b.containsKey("TruckIndex"))
			startIndex = b.getInt("TruckIndex");



		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		mapOverlays = mapView.getOverlays();

		// first overlay
		drawable = getResources().getDrawable(R.drawable.truckpin);
		itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);



		CustomOverlayItem tempOverlayItem;
		FoodTruck tempTruck = trucks.get(0);

		int minLat = tempTruck.location.getLatitudeE6();
		int maxLat = minLat;
		int minLong = tempTruck.location.getLongitudeE6();
		int maxLong = minLong;
		int tempLat;
		int tempLong;
		for(int i = 0; i < trucks.size(); i++){
			tempTruck = trucks.get(i);

			if(tempTruck.location != null){
				tempOverlayItem = new CustomOverlayItem(tempTruck.location,tempTruck.name,tempTruck.descriptor,i);
				
				itemizedOverlay.addOverlay(tempOverlayItem);

				tempLat = tempTruck.location.getLatitudeE6();
				tempLong = tempTruck.location.getLongitudeE6();
				if(tempLat < minLat)
					minLat = tempLat;
				if(tempLat > maxLat)
					maxLat = tempLat;
				if(tempLong < minLong)
					minLong = tempLong;
				if(tempLong > maxLong)
					maxLong = tempLong;
			}

		}

		mapOverlays.add(itemizedOverlay);


		final MapController mc = mapView.getController();

		mc.zoomToSpan(maxLat-minLat, maxLong-minLong);

		if(startIndex == -1)
			mc.animateTo(new GeoPoint((minLat+maxLat)/2,(minLong+maxLong)/2));
		else
			mc.animateTo(trucks.get(startIndex).location);







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


	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
