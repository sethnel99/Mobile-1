package com.nomad.nomadclient;

import java.util.Calendar;
import java.util.Date;

public class ScheduleEntry {
	int dayOfWeek;
	int time;
	String location;
	boolean isASectionDivider;

	public ScheduleEntry(int dow, int t, String l){
		dayOfWeek = dow;
		time = t;
		location = l;
		isASectionDivider = false;
	}

	public ScheduleEntry(int d){
		dayOfWeek = d;
		isASectionDivider = true;
	}
	
	public static String stringDayOfWeek(int i){
		switch(i){
		case 0:
			return "Monday";
		case 1:
			return "Tuesday";
		case 2:
			return "Wednesday";
		case 3:
			return "Thursday";
		case 4:
			return "Friday";
		case 5:
			return "Saturday";
		case 6:
			return "Sunday";
		}
		
		return "Caturday";
	}
	
	public static String getTimeString(int i){
		String timeString = new String();
		timeString += String.format("%02d",(int) i/60);
		timeString += ":";
		timeString += String.format("%02d", i%60);
		if(i < 720)
			timeString += " am";
		else
			timeString += " pm";
		return timeString;
	}
}
