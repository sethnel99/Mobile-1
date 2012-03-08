package com.nomad.nomadclient;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NomadClientApplication extends Application{
	public static int NETWORK_FIRST = 0;
	public static int CACHE_FIRST = 1;

	private  ArrayList<FoodTruck> trucks; //all of the food trucks 
	private LocationManager locationManager;
	private MyLocationListener locationListener;

	public NomadClientApplication(){
		trucks = new ArrayList<FoodTruck>();
	}

	@Override
	public void onCreate(){
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new MyLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}

	//accesssor methods for activities
	public ArrayList<FoodTruck> getTrucks(){
		return trucks;
	}
	
	public void reloadTruck(int truckIndex){
		String parseID = trucks.get(truckIndex).parseID;
		ParseQuery query = new ParseQuery("Trucks");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query.whereEqualTo("objectId", parseID);
		
		try {
			ParseObject temp = query.getFirst();
			String name = temp.getString("Name");
			Log.v("new truck from parse",name + " " + parseID);
			String description = temp.getString("Description");
			String locationString = temp.getString("LocationString");
			ParseGeoPoint pgeo = temp.getParseGeoPoint("Location");
			GeoPoint location;
			if(pgeo != null)
				 location = new GeoPoint((int)(pgeo.getLatitude()*Math.pow(10,6)),(int)(pgeo.getLongitude()*Math.pow(10,6)));
			else
				 location = null;

			//get logo file, convert logo file into a drawable
			ParseFile pf = (ParseFile)temp.get("Logo");
			byte[] logoFile = pf.getData();
			ByteArrayInputStream is = new ByteArrayInputStream(logoFile);
			Bitmap bitmapLogo = BitmapFactory.decodeStream(is);

			JSONArray categoriesJSON = temp.getJSONArray("MenuCategories");
			//turn the categories JSONArray into an ArrayList
			ArrayList<String> categories = new ArrayList<String>();     
			int len = categoriesJSON.length();
			for (int j=0;j<len;j++){ 
				try {
					categories.add(categoriesJSON.get(j).toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


			//grab the first message for each truck as well, so save load time later
			ParseQuery queryFirstMessage = new ParseQuery("Messages");
			queryFirstMessage.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
			queryFirstMessage.whereEqualTo("TruckID",parseID);
			queryFirstMessage.orderByDescending("createdAt");

			ParseObject firstMessage = queryFirstMessage.getFirst();
			MessageEntry mse = new MessageEntry(firstMessage.getString("Message"),firstMessage.getCreatedAt());
			trucks.set(truckIndex,new FoodTruck(parseID, name,locationString,location,description,name,mse,bitmapLogo,categories));


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//pulls down all truck data from Parse
	public void loadTrucksFromParse(int loadType){
		trucks = new ArrayList<FoodTruck>();


		ParseQuery.CachePolicy cachePolicy;
		if(loadType == NETWORK_FIRST)
			cachePolicy= ParseQuery.CachePolicy.NETWORK_ELSE_CACHE;
		else 
			cachePolicy = ParseQuery.CachePolicy.CACHE_ELSE_NETWORK;

		//Query the "Trucks" class
		ParseQuery query = new ParseQuery("Trucks");
		query.setCachePolicy(cachePolicy);

		Log.v("PARSE IS STARTING","parse!");

		//execute the query
		ArrayList<ParseObject> parseData = new ArrayList<ParseObject>();
		try {
			parseData = query.find();


			//Look at each ParseObject returned, turn it into a FoodTruck object, and put it into trucks.
			for(int i = 0; i < parseData.size(); i++){
				ParseObject temp = parseData.get(i);

				String parseID = temp.getObjectId();
				String name = temp.getString("Name");
				Log.v("new truck from parse",name + " " + parseID);
				String description = temp.getString("Description");
				String locationString = temp.getString("LocationString");
				ParseGeoPoint pgeo = temp.getParseGeoPoint("Location");
				GeoPoint location;
				if(pgeo != null)
					 location = new GeoPoint((int)(pgeo.getLatitude()*Math.pow(10,6)),(int)(pgeo.getLongitude()*Math.pow(10,6)));
				else
					 location = null;
				
				//get logo file, convert logo file into a drawable
				ParseFile pf = (ParseFile)temp.get("Logo");
				byte[] logoFile = pf.getData();
				ByteArrayInputStream is = new ByteArrayInputStream(logoFile);
				Bitmap bitmapLogo = BitmapFactory.decodeStream(is);

				JSONArray categoriesJSON = temp.getJSONArray("MenuCategories");
				//turn the categories JSONArray into an ArrayList
				ArrayList<String> categories = new ArrayList<String>();     
				int len = categoriesJSON.length();
				for (int j=0;j<len;j++){ 
					try {
						categories.add(categoriesJSON.get(j).toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}


				//grab the first message for each truck as well, so save load time later
				ParseQuery queryFirstMessage = new ParseQuery("Messages");
				queryFirstMessage.setCachePolicy(cachePolicy);
				queryFirstMessage.whereEqualTo("TruckID",parseID);
				queryFirstMessage.orderByDescending("createdAt");

				ParseObject firstMessage = queryFirstMessage.getFirst();
				MessageEntry mse;
				if(firstMessage == null)
					mse = new MessageEntry("No recent messages", new Date());
				else
					mse = new MessageEntry(firstMessage.getString("Message"),firstMessage.getCreatedAt());
				trucks.add(new FoodTruck(parseID, name,locationString,location,description,name,mse,bitmapLogo,categories));

			}


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e){
			e.printStackTrace();
		}
		Log.v("PARSE IS ENDING","parse!");

		//sorts items based on the sort field
		Collections.sort(trucks,new Comparator<FoodTruck>() {
			//custom compare of Object[]s
			public int compare(FoodTruck ft1, FoodTruck ft2) {
				return (int) (getDistanceFrom(ft1.location)*10 - getDistanceFrom(ft2.location)*10);
			}
		});



	}

	public void loadTruckLists(int truckIndex, int loadType){
		//Gets the truck in question, and resets its menu and messages

		ParseQuery.CachePolicy cachePolicy;
		if(loadType == NETWORK_FIRST)
			cachePolicy= ParseQuery.CachePolicy.NETWORK_ELSE_CACHE;
		else 
			cachePolicy = ParseQuery.CachePolicy.CACHE_ELSE_NETWORK;


		FoodTruck ft = trucks.get(truckIndex);
		ft.hasLoaded = true;

		//sets up and executes the query to find all of the messages in the background
		ParseQuery queryMessages = new ParseQuery("Messages");
		queryMessages.whereEqualTo("TruckID",ft.parseID);
		queryMessages.setCachePolicy(cachePolicy);
		queryMessages.orderByDescending("createdAt");
		queryMessages.setLimit(10);
		queryMessages.setSkip(1);
		ft.loadingMessages = true;
		queryMessages.findInBackground(new MyFindCallback(truckIndex) {
			public void done(List<ParseObject> parseData, ParseException e) {
				if (e == null) {
					final List<ParseObject> pd = parseData;


					//Parse the menu data in the background, so that we don't lock up the UI thread
					BackgroundLoader lwpd = new BackgroundLoader(new Runnable(){
						public void run(){
						//	Log.v("size of messages returned",""+pd.size());
							for(int i = 0; i < pd.size(); i++){
								ParseObject temp = pd.get(i);
								MessageEntry mse = new MessageEntry(temp.getString("Message"),temp.getCreatedAt());
								trucks.get(truckIndex).messages.add(mse);
								Log.v("got message item",temp.getString("Message") + " " + temp.getString("TruckID"));
							}

							trucks.get(truckIndex).loadingMessages = false;
							

						}

					}, null);
					lwpd.execute();


				} else {
					Log.d("Parse", "Error: " + e.getMessage());
				}

			}
		});

		//Sets up and executes the query to find all menu items which belong to that truck
		ParseQuery queryMenu = new ParseQuery("MenuItems");
		queryMenu.setCachePolicy(cachePolicy);
		queryMenu.whereEqualTo("TruckID",ft.parseID);

		ft.loadingMenu = true;

		queryMenu.findInBackground(new MyFindCallback(truckIndex) {
			public void done(List<ParseObject> parseData, ParseException e) {
				if (e == null) {

					final List<ParseObject> pd = parseData;

					//Parse the menu data in the background, so that we don't lock up the UI thread
					BackgroundLoader lwpd = new BackgroundLoader(new Runnable(){
						public void run(){
							//For each menu item, create a MenuFoodItem object and add it to that trucks menu
							for(int i = 0; i < pd.size(); i++){
								ParseObject temp = pd.get(i);

								String id = temp.getObjectId();
								String name = temp.getString("Name");	
								double price = temp.getInt("Price");
								int category = temp.getInt("Category");

								//get logo file, convert logo file into a drawable
								ParseFile pf = null;
								try {
									pf = (ParseFile)temp.get("ItemPic");
								} catch (ParseException e1) {
									Log.v("Parse Error",e1.getMessage());
								}
								
								Log.v("got menu item",name+ " " + temp.getString("TruckID"));
								trucks.get(truckIndex).menu.add(new MenuFoodItem(id,name,price,pf,category));
							}
							
							FoodTruck thisTruck = trucks.get(truckIndex);

							thisTruck.sortMenu();
							thisTruck.loadingMenu = false;
							
							for(int i = 0; i < thisTruck.menu.size(); i++){
								if(!thisTruck.menu.get(i).isASectionDivider && thisTruck.menu.get(i).itemPicture == null)
									thisTruck.menu.get(i).decodeImageFile();
							}
						}

					}, null);
					lwpd.execute();


				} else {
					Log.d("Parse", "Error: " + e.getMessage());
				}

			}
		});


		//Sets up and executes the query to find all schedule entries which belong to that truck
		ParseQuery querySchedule = new ParseQuery("ScheduleEntry");
		querySchedule.setCachePolicy(cachePolicy);
		querySchedule.whereEqualTo("TruckID",ft.parseID);

		ft.loadingSchedule = true;
		querySchedule.findInBackground(new MyFindCallback(truckIndex) {
			public void done(List<ParseObject> parseData, ParseException e) {
				final List<ParseObject> pd = parseData;
				if (e == null) {

					//parse the schedule data in the background, so that the UI thread does not lock up
					BackgroundLoader lwpd = new BackgroundLoader(new Runnable(){
						public void run(){

							//For each item, create a ScheduleEntry object and add it to that trucks schedule
							for(int i = 0; i < pd.size(); i++){
								ParseObject temp = pd.get(i);

								String id = temp.getObjectId();
								String location = temp.getString("Location");	
								int dow = temp.getInt("DayOfWeek");
								int time = temp.getInt("Time");


								trucks.get(truckIndex).schedule.add(new ScheduleEntry(id,dow,time,location));
								//Log.v("got schedule item",""+time);
							}
							trucks.get(truckIndex).loadingSchedule = false;
						}

					}, null);
					lwpd.execute();



				} else {
					Log.d("Parse", "Error: " + e.getMessage());
				}
			}
		});





	}

	public double getDistanceFrom(GeoPoint gp){
		if(gp == null)
			return Integer.MAX_VALUE;
		Location currentLoc = locationListener.getLocation();

		double lat = ((double)gp.getLatitudeE6()) / 1e6;
		double lng = ((double)gp.getLongitudeE6()) / 1e6;
		Location gpLoc = new Location(currentLoc);
		gpLoc.setLatitude(lat);
		gpLoc.setLongitude(lng);
		return currentLoc.distanceTo(gpLoc)/1000 * 0.621371192; //convert from meters to km to miles
	}


	private abstract class MyFindCallback extends FindCallback{
		int truckIndex;

		public MyFindCallback(int i){
			super();
			truckIndex = i;
		}
	}


	private class MyLocationListener implements LocationListener{
		Location currentBestLocation;

		public MyLocationListener(){
			currentBestLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
			Location l2 = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
			if(isBetterLocation(l2))
				currentBestLocation = l2;
		}

		public Location getLocation(){
			return  currentBestLocation;
		}

		@Override 
		public void onLocationChanged(Location location){

			if(isBetterLocation(location))
				currentBestLocation = location;
		}

		@Override
		public void onProviderDisabled(String arg0) {


		}

		@Override
		public void onProviderEnabled(String provider) {


		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {


		}

		//taken from example android docs
		private boolean isBetterLocation(Location location){
			if (currentBestLocation == null) {
				// A new location is always better than no location
				return true;
			}

			// Check whether the new location fix is newer or older
			long timeDelta = location.getTime() - currentBestLocation.getTime();
			boolean isSignificantlyNewer = timeDelta > 1000*4*60;
			boolean isSignificantlyOlder = timeDelta < -1000*4*60;
			boolean isNewer = timeDelta > 0;

			// If it's been more than two minutes since the current location, use the new location
			// because the user has likely moved
			if (isSignificantlyNewer) {
				return true;
				// If the new location is more than two minutes older, it must be worse
			} else if (isSignificantlyOlder) {
				return false;
			}

			// Check whether the new location fix is more or less accurate
			int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
			boolean isLessAccurate = accuracyDelta > 0;
			boolean isMoreAccurate = accuracyDelta < 0;
			boolean isSignificantlyLessAccurate = accuracyDelta > 200;

			// Check if the old and new location are from the same provider
			boolean isFromSameProvider = isSameProvider(location.getProvider(),
					currentBestLocation.getProvider());

			// Determine location quality using a combination of timeliness and accuracy
			if (isMoreAccurate) {
				return true;
			} else if (isNewer && !isLessAccurate) {
				return true;
			} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
				return true;
			}
			return false;






		}

		/** Checks whether two providers are the same */
		private boolean isSameProvider(String provider1, String provider2) {
			if (provider1 == null) {
				return provider2 == null;
			}
			return provider1.equals(provider2);
		}


	}


}
