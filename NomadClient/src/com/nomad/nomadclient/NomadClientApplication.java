package com.nomad.nomadclient;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.parse.FindCallback;
import com.parse.ParseException;
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
				String description = temp.getString("Sescription");
				String locationString = temp.getString("LocationString");
				ParseGeoPoint pgeo = temp.getParseGeoPoint("Location");
				GeoPoint location = new GeoPoint((int)(pgeo.getLatitude()*Math.pow(10,6)),(int)(pgeo.getLongitude()*Math.pow(10,6)));
				Log.v("new truck from parse",name);


				//grab the first message for each truck as well, so save load time later
				ParseQuery queryFirstMessage = new ParseQuery("Messages");
				queryFirstMessage.whereEqualTo("TruckID",parseID);
				queryFirstMessage.orderByDescending("createdAt");

				ParseObject firstMessage = queryFirstMessage.getFirst();
				trucks.add(new FoodTruck(parseID, name,locationString,description,name,firstMessage.getString("Message")));

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
		queryMessages.setLimit(10);
		loadingInBackground++;
		queryMessages.findInBackground(new MyFindCallback(truckIndex) {
			public void done(List<ParseObject> parseData, ParseException e) {
				if (e == null) {
					for(int i = 0; i < parseData.size(); i++){
						ParseObject temp = parseData.get(i);
						trucks.get(truckIndex).messages.add(temp.getString("Message"));
					}
				} else {
					Log.d("Parse", "Error: " + e.getMessage());
				}
				loadingInBackground--;
			}
		});

		//Sets up and executes the query to find all menu items which belong to that truck
		ParseQuery queryMenu = new ParseQuery("MenuItems");
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

						trucks.get(truckIndex).menu.add(new MenuFoodItem(id,name,R.drawable.empanadapic,price));
					}

					//update that truck within trucks
					//trucks.set(truckIndex, ft);


				} else {
					Log.d("Parse", "Error: " + e.getMessage());
				}

				loadingInBackground--;
			}
		});





	}






	//Loads the menu for the truck at the given index
	public void loadMenuForTruck(int truckIndex){
		//Gets the truck in question, and resets its menu
		FoodTruck ft = trucks.get(truckIndex);
		ft.menu = new ArrayList<MenuFoodItem>();



		//Sets up the query to find all menu items which belong to that truck
		ParseQuery query = new ParseQuery("MenuItems");
		query.whereEqualTo("TruckID",ft.parseID);


		//executes the query
		ArrayList<ParseObject> parseData = new ArrayList<ParseObject>();
		try {
			parseData = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//For each menu item, create a MenuFoodItem object and add it to that trucks menu
		for(int i = 0; i < parseData.size(); i++){
			ParseObject temp = parseData.get(i);

			String id = temp.getObjectId();
			String name = temp.getString("Name");	
			double price = temp.getInt("Price");
			ft.menu.add(new MenuFoodItem(id,name,R.drawable.empanadapic,price));
		}


	}

	/*
	public  ArrayList<FoodTruck> fillFakeTrucks(){
		ArrayList<FoodTruck> toRet = new ArrayList<FoodTruck>();

		toRet.add(new FoodTruck("xy9RVozfL1","5411 Empanadas","W. Jackson & Wells","Empanadas", "5411 Empanadas"));
		toRet.add(new FoodTruck("xy9RVozfL1","Flirty Cupcakes","W. Jackson & Wells","Empanadas", "Flirty Cupcakes"));
		toRet.add(new FoodTruck("xy9RVozfL1","More Mobile","W. Jackson & Wells","Empanadas", "More Mobile"));
		toRet.add(new FoodTruck("xy9RVozfL1","Gaztro-Wagon","W. Jackson & Wells","Empanadas", "Gaztro-Wagon"));
		toRet.add(new FoodTruck("xy9RVozfL1","Meatyballs Mobile","W. Jackson & Wells","Empanadas", "Meatyballs Mobile"));
		toRet.add(new FoodTruck("xy9RVozfL1","Simple Sandwich","W. Jackson & Wells","Empanadas", "Simple Sandwich"));
		toRet.add(new FoodTruck("xy9RVozfL1","Happy Bodega","W. Jackson & Wells","Empanadas", "Happy Bodega"));
		toRet.add(new FoodTruck("xy9RVozfL1","Beavers Donuts","W. Jackson & Wells","Empanadas", "Beavers Donuts"));
		toRet.add(new FoodTruck("xy9RVozfL1","Brown Bag","W. Jackson & Wells","Empanadas", "Brown Bag"));
		toRet.add(new FoodTruck("xy9RVozfL1","Hummingbird Kitchen","W. Jackson & Wells","Empanadas", "Hummingbird Kitchen"));


		return toRet;


	}*/

	private abstract class MyFindCallback extends FindCallback{
		int truckIndex;

		public MyFindCallback(int i){
			super();
			truckIndex = i;
		}
	}



}
