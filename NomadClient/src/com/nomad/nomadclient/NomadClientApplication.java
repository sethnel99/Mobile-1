package com.nomad.nomadclient;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NomadClientApplication extends Application{
	ArrayList<FoodTruck> trucks; //all of the food trucks 

	public NomadClientApplication(){
		trucks = new ArrayList<FoodTruck>();
	}

	//accesssor methods for activities
	public ArrayList<FoodTruck> getTrucks(){
		return trucks;
	}

	//pulls down all truck data from Parse
	public void loadTrucksFromParse(){
		//Query the "Trucks" class
		ParseQuery query = new ParseQuery("Trucks");
		Log.v("PARSE IS STARTING","parse!");
		
		//execute the query
		ArrayList<ParseObject> parseData = new ArrayList<ParseObject>();
		try {
			parseData = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			trucks.add(new FoodTruck(parseID, name,locationString,description,name));	
		}
		Log.v("PARSE IS ENDING","parse!");
		

	}
	
	//Loads the menu for the truck at the given index
	public void loadMenuForTruck(int truckIndex){
		//Gets the truck in question
		FoodTruck ft = trucks.get(truckIndex);
		
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

			String name = temp.getString("Name");	
			double price = temp.getInt("Price");
			ft.menu.add(new MenuFoodItem(name,R.drawable.empanadapic,price));
		}
		
		//update that truck within trucks
		trucks.set(truckIndex, ft);
	}


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


	}



}
