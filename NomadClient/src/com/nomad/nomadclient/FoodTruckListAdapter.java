package com.nomad.nomadclient;

import java.util.ArrayList;

import android.content.Context;

public class FoodTruckListAdapter extends CustomListAdapter{
	public static int MENU = 0;
	public static int MESSAGES = 1;
	
	//passing in an entire list of trucks, will create an adapter for the truck list
	public FoodTruckListAdapter(Context c, ArrayList<FoodTruck> a){
		super(c);
		int rowID = R.layout.truckrow;
		int[] viewIDs = {R.id.distanceText, R.id.nameText, R.id.locationText, R.id.globalPosition};
		int[] viewTypes = {TEXTVIEW, TEXTVIEW, TEXTVIEW, TEXTVIEW };
		
		ArrayList<Object[]> items = new ArrayList<Object[]>();
		ArrayList<String> searchFields =  new ArrayList<String>();
		
		Object[] temp;
		FoodTruck tempft;
		for(int i = 0; i < a.size(); i++){
			temp = new Object[4];
			tempft = a.get(i);
			temp[0] = String.format("%.1f",((NomadClientApplication)c).getDistanceFrom(tempft.location));
			temp[1] = tempft.name;
			temp[2] = tempft.locationString;
			temp[3] = ""+i;
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
			//data to pass to super class
			ArrayList<Object[]> items = new ArrayList<Object[]>();
			
			int rowID = R.layout.menurow; //layout id of the menu row layout file
			int[] viewIDs = {R.id.foodPic, R.id.menuItemText}; //the views that will be populated in the row
			int[] viewTypes = {IMAGEVIEW_BITMAPDRAWABLE, TEXTVIEW}; //the types of those views
			
			Object[] temp;
			MenuFoodItem tempmfi; 
			ArrayList<MenuFoodItem> menu = ft.menu; //fetch the menu
			
			for(int i = 0; i < menu.size(); i++){ //populate items using the data from menu
				temp = new Object[3];
				tempmfi = menu.get(i);
				temp[0] = tempmfi.itemPicture;
				temp[1] = tempmfi.name;
				items.add(temp);
			}
			
			super.initAdapter(rowID, viewIDs, viewTypes, items);
			
		}
	}
	
	

}
