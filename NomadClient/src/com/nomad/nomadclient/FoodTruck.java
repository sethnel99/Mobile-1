package com.nomad.nomadclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.graphics.Bitmap;
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
	public ArrayList<MessageEntry> messages; //the messages/tweets from the truck
	public ArrayList<ScheduleEntry> schedule; //the schedule for the truck
	public Bitmap logo; //the truck's logo
	public boolean loadingMessages;
	public boolean loadingMenu;
	public boolean loadingSchedule;
	public ArrayList<String> menuCategories;
	public boolean hasLoaded;
	
	public FoodTruck(String id, String n, String ls, GeoPoint l, String d, String s,MessageEntry firstMessage, Bitmap pic,ArrayList<String> mc){
		parseID = id;
		name = n;
		locationString = ls;
		location = l;
		descriptor = d;
		searchString = s;
		menu = new ArrayList<MenuFoodItem>();
		messages = new ArrayList<MessageEntry>();
		schedule = new ArrayList<ScheduleEntry>();
		menuCategories = mc;
		
		
		//insert schedule's day dividers
		schedule.add(new ScheduleEntry(0));
		schedule.add(new ScheduleEntry(1));
		schedule.add(new ScheduleEntry(2));
		schedule.add(new ScheduleEntry(3));
		schedule.add(new ScheduleEntry(4));
		schedule.add(new ScheduleEntry(5));
		schedule.add(new ScheduleEntry(6));
		
		//insert menu's category dividers
		for(int i = 0; i < menuCategories.size();i++){
			menu.add(new MenuFoodItem(menuCategories.get(i),i));
		}
		
		logo = pic;
		if(firstMessage != null)
			messages.add(firstMessage);
		
		
		loadingMessages = false;
		loadingMenu = false;
		loadingSchedule = false;
		hasLoaded = false;
		
	}
	
	public void sortMenu(){
		Collections.sort(menu,new Comparator<MenuFoodItem>() {
			//custom compare of String[]'s
			public int compare(MenuFoodItem item1, MenuFoodItem item2) {
				int toRet = item1.category - item2.category;
				if(toRet == 0){
					if(item1.isASectionDivider)
						return -1;
					if(item2.isASectionDivider)
						return 1;
					toRet = item1.name.compareTo(item2.name);
				}
				return toRet;
			}
		});
	}
	
	


}
