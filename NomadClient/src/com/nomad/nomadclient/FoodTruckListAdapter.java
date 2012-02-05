package com.nomad.nomadclient;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class FoodTruckListAdapter extends CustomListAdapter{
	public static int MENU = 0;
	public static int MESSAGES = 1;
	
	//passing in an entire list of trucks, will create an adapter for the truck list
	public FoodTruckListAdapter(Context c, ArrayList<FoodTruck> a){
		super(c);
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
	
	//passing in a single truck, will create an adapter for information in that single truck based on type 
	public FoodTruckListAdapter(Context c, FoodTruck ft, int type){
		super(c);
		
		if(type == MENU){
			ArrayList<String[]> items = new ArrayList<String[]>();
			
			int rowID = R.layout.menurow;
			int[] viewIDs = {R.id.foodPic, R.id.menuItemText};
			int[] viewTypes = {IMAGEVIEW, TEXTVIEW};
			
			String[] temp;
			MenuFoodItem tempmfi;
			ArrayList<MenuFoodItem> menu = ft.menu;
			
			for(int i = 0; i < menu.size(); i++){
				temp = new String[3];
				tempmfi = menu.get(i);
				temp[0] = String.valueOf(tempmfi.drawableID);
				temp[1] = tempmfi.name;
				items.add(temp);
			}
			
			super.initAdapter(rowID, viewIDs, viewTypes, items);
			
		}
	}
	
	

}
