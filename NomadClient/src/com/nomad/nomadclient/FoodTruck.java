package com.nomad.nomadclient;

import java.util.ArrayList;

import android.graphics.drawable.BitmapDrawable;

import com.google.android.maps.GeoPoint;

public class FoodTruck {
	public String parseID; //the object ID of the truck on parse
	public String name; //the name of the truck
	public String locationString; //a string describing the location of the truck
	public String descriptor; //the description of the truck
	public String searchString; //the search string of the truck
	public GeoPoint location; //the actual location of the truck
	public ArrayList<MenuFoodItem> menu; //the trucks menu
	public ArrayList<String> messages; //the messages/tweets from the truck
	public ArrayList<ScheduleEntry> schedule; //the schedule for the truck
	public BitmapDrawable logo; //the truc's logo

	public FoodTruck(String id, String n, String ls, GeoPoint l, String d, String s,String firstMessage, BitmapDrawable pic){
		parseID = id;
		name = n;
		locationString = ls;
		location = l;
		descriptor = d;
		searchString = s;
		menu = new ArrayList<MenuFoodItem>();
		messages = new ArrayList<String>();
		schedule = new ArrayList<ScheduleEntry>();
		
		//insert schedule's day dividers
		schedule.add(new ScheduleEntry(0));
		schedule.add(new ScheduleEntry(1));
		schedule.add(new ScheduleEntry(2));
		schedule.add(new ScheduleEntry(3));
		schedule.add(new ScheduleEntry(4));
		schedule.add(new ScheduleEntry(5));
		schedule.add(new ScheduleEntry(6));
		
		logo = pic;
		if(firstMessage != null)
			messages.add(firstMessage);
	}


}
