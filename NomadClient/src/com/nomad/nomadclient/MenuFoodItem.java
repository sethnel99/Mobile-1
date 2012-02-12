package com.nomad.nomadclient;

import android.graphics.drawable.BitmapDrawable;

public class MenuFoodItem {
	public String ParseID;
	public String name;
	public double price;
	public BitmapDrawable itemPicture;
	
	public MenuFoodItem(String id, String n, double p, BitmapDrawable pic){
		ParseID = id; 
		name = n;

		price = p;
		itemPicture = pic;
	}
}
