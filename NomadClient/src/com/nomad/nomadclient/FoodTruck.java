package com.nomad.nomadclient;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.drawable.BitmapDrawable;

public class FoodTruck {
	public String parseID; //the object ID of the truck on parse
	public String name; //the name of the truck
	public String location; //the location of the truck
	public String descriptor; //the description of the truck
	public String searchString; //the search string of the truck
	public ArrayList<MenuFoodItem> menu; //the trucks menu
	public ArrayList<String> messages; //the messages/tweets from the truck
	public ArrayList<ScheduleEntry> schedule; //the schedule for the truck
	public BitmapDrawable logo; //the truc's logo

	public FoodTruck(String id, String n, String l, String d, String s,String firstMessage, BitmapDrawable pic){
		id = parseID;
		name = n;
		location = l;
		descriptor = d;
		searchString = s;
		menu = new ArrayList<MenuFoodItem>();
		messages = new ArrayList<String>();
		schedule = new ArrayList<ScheduleEntry>();
		
		schedule.add(new ScheduleEntry(0));
		schedule.add(new ScheduleEntry(0,720,"West Jackson and Wells St."));
		schedule.add(new ScheduleEntry(0,760,"720 E. Monroe St."));
		schedule.add(new ScheduleEntry(1));
		schedule.add(new ScheduleEntry(1,720,"West Jackson and Wells St."));
		schedule.add(new ScheduleEntry(1,760,"720 E. Monroe St."));
		schedule.add(new ScheduleEntry(2));
		schedule.add(new ScheduleEntry(2,720,"West Jackson and Wells St."));
		schedule.add(new ScheduleEntry(2,760,"720 E. Monroe St."));
		schedule.add(new ScheduleEntry(3));
		schedule.add(new ScheduleEntry(3,720,"West Jackson and Wells St."));
		schedule.add(new ScheduleEntry(3,760,"720 E. Monroe St."));
		schedule.add(new ScheduleEntry(4));
		schedule.add(new ScheduleEntry(4,720,"West Jackson and Wells St."));
		schedule.add(new ScheduleEntry(4,760,"720 E. Monroe St."));
		schedule.add(new ScheduleEntry(5));
		schedule.add(new ScheduleEntry(5,720,"West Jackson and Wells St."));
		schedule.add(new ScheduleEntry(5,760,"720 E. Monroe St."));
		schedule.add(new ScheduleEntry(6));
		schedule.add(new ScheduleEntry(6,720,"West Jackson and Wells St."));
		schedule.add(new ScheduleEntry(6,760,"720 E. Monroe St."));
		
		logo = pic;
		if(firstMessage != null)
			messages.add(firstMessage);
	}


	public double calculateDistance(){
		int r = (int)(20*Math.random());
		return .1 * r;
	}



}
