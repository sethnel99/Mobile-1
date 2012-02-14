package com.nomad.nomadclient;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NomadClientApplication extends Application{
	private  ArrayList<FoodTruck> trucks; //all of the food trucks 
	public int loadingInBackground = 0;

	public NomadClientApplication(){
		trucks = new ArrayList<FoodTruck>();
	}

	//accesssor methods for activities
	public ArrayList<FoodTruck> getTrucks(){
		return trucks;
	}

	//pulls down all truck data from Parse
	public void loadTrucksFromParse(){
		trucks = new ArrayList<FoodTruck>();
		//Query the "Trucks" class
		ParseQuery query = new ParseQuery("Trucks");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		Log.v("PARSE IS STARTING","parse!");

		//execute the query
		ArrayList<ParseObject> parseData = new ArrayList<ParseObject>();
		try {
			parseData = query.find();


			//Look at each ParseObject returned, turn it into a FoodTruck object, and put it into trucks.
			for(int i = 0; i < parseData.size(); i++){
				ParseObject temp = parseData.get(i);

				String parseID = (String)temp.getObjectId();
				String name = temp.getString("Name");
				Log.v("new truck from parse",name);
				String description = temp.getString("Sescription");
				String locationString = temp.getString("LocationString");
				ParseGeoPoint pgeo = temp.getParseGeoPoint("Location");
				GeoPoint location = new GeoPoint((int)(pgeo.getLatitude()*Math.pow(10,6)),(int)(pgeo.getLongitude()*Math.pow(10,6)));
				
				//get logo file, convert logo file into a drawable
				ParseFile pf = (ParseFile)temp.get("Logo");
				byte[] logoFile = pf.getData();
				ByteArrayInputStream is = new ByteArrayInputStream(logoFile);
				Bitmap bitmapLogo = BitmapFactory.decodeStream(is);

				//grab the first message for each truck as well, so save load time later
				ParseQuery queryFirstMessage = new ParseQuery("Messages");
				queryFirstMessage.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
				queryFirstMessage.whereEqualTo("TruckID",parseID);
				queryFirstMessage.orderByDescending("createdAt");

				ParseObject firstMessage = queryFirstMessage.getFirst();
				trucks.add(new FoodTruck(parseID, name,locationString,description,name,firstMessage.getString("Message"),new BitmapDrawable(bitmapLogo)));

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v("PARSE IS ENDING","parse!");


	}

	public void loadTruckLists(int truckIndex){
		//Gets the truck in question, and resets its menu and messages
		FoodTruck ft = trucks.get(truckIndex);
		ft.menu = new ArrayList<MenuFoodItem>();
		ft.messages = new ArrayList<String>();


		//sets up and executes the query to find all of the messages in the background
		ParseQuery queryMessages = new ParseQuery("Messages");
		queryMessages.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		queryMessages.setLimit(10);
		loadingInBackground++;
		queryMessages.findInBackground(new MyFindCallback(truckIndex) {
			public void done(List<ParseObject> parseData, ParseException e) {
				if (e == null) {
					for(int i = 0; i < parseData.size(); i++){
						ParseObject temp = parseData.get(i);
						trucks.get(truckIndex).messages.add(temp.getString("Message"));
						Log.v("got message item",temp.getString("Message"));
					}
				} else {
					Log.d("Parse", "Error: " + e.getMessage());
				}
				loadingInBackground--;
			}
		});

		//Sets up and executes the query to find all menu items which belong to that truck
		ParseQuery queryMenu = new ParseQuery("MenuItems");
		queryMenu.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		queryMenu.whereEqualTo("TruckID",ft.parseID);

		loadingInBackground++;
		queryMenu.findInBackground(new MyFindCallback(truckIndex) {
			public void done(List<ParseObject> parseData, ParseException e) {
				if (e == null) {

					//For each menu item, create a MenuFoodItem object and add it to that trucks menu
					for(int i = 0; i < parseData.size(); i++){
						ParseObject temp = parseData.get(i);

						String id = temp.getObjectId();
						String name = temp.getString("Name");	
						double price = temp.getInt("Price");
						
						//get logo file, convert logo file into a drawable
						ParseFile pf;
						byte[] logoFile = null;
						try {
							pf = (ParseFile)temp.get("ItemPic");
							logoFile = pf.getData();
						} catch (ParseException e1) {
							Log.v("Parse Error",e.getMessage());
						}
						ByteArrayInputStream is = new ByteArrayInputStream(logoFile);
						Bitmap bitmapPic = BitmapFactory.decodeStream(is);

						Log.v("got menu item",name);
						trucks.get(truckIndex).menu.add(new MenuFoodItem(id,name,price,new BitmapDrawable(bitmapPic)));
					}

				} else {
					Log.d("Parse", "Error: " + e.getMessage());
				}

				loadingInBackground--;
			}
		});





	}


	private abstract class MyFindCallback extends FindCallback{
		int truckIndex;

		public MyFindCallback(int i){
			super();
			truckIndex = i;
		}
	}



}
