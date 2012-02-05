package com.nomad.nomadclient;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class FoodTruckListAdapter extends CustomListAdapter{
	public static int TRUCKLIST = 0;
	
	
	private int type;
	
	public FoodTruckListAdapter(Context c, ArrayList<FoodTruck> a, int t){
		super(c);
		type = t;
		int rowID = R.layout.truckrow;
		int[] viewIDs = {R.id.distanceText, R.id.nameText, R.id.locationText};
		int[] viewTypes = {TEXTVIEW, TEXTVIEW, TEXTVIEW };
		int sortField = 0;
		
		ArrayList<String[]> items = new ArrayList<String[]>();
		ArrayList<String> searchFields =  new ArrayList<String>();
		
		String[] temp;
		FoodTruck tempft;
		for(int i = 0; i < a.size(); i++){
			temp = new String[3];
			tempft = a.get(i);
			temp[0] = String.valueOf(tempft.calculateDistance());
			temp[1] = tempft.name;
			temp[2] = tempft.location;
			items.add(temp);
			searchFields.add(tempft.searchString);
			//Log.v("creating",""+items.get(i)[1] + ":"+searchFields[i]);
		}
		
		super.initAdapter(rowID, viewIDs, viewTypes, items, searchFields, sortField);	
	}
	

}
