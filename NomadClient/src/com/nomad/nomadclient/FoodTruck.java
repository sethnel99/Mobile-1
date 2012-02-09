package com.nomad.nomadclient;

import java.util.ArrayList;

import android.view.MenuItem;

public class FoodTruck {
	public String parseID; //the object ID of the truck on parse
	public String name; //the name of the truck
	public String location; //the location of the truck
	public String descriptor; //the description of the truck
	public String searchString; //the search string of the truck
	public ArrayList<MenuFoodItem> menu; //the trucks menu
	
	public FoodTruck(String id, String n, String l, String d, String s){
		id = parseID;
		name = n;
		location = l;
		descriptor = d;
		searchString = s;
		menu = new ArrayList<MenuFoodItem>();
	}
	
	
	public double calculateDistance(){
		int r = (int)(20*Math.random());
		return .1 * r;
	}
	
	

}
