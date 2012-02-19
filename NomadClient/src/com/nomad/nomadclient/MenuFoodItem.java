package com.nomad.nomadclient;

import android.graphics.drawable.BitmapDrawable;

public class MenuFoodItem {
	public String ParseID;
	public String name;
	public double price;
	public BitmapDrawable itemPicture;
	public int category;
	public boolean isASectionDivider;
	
	public MenuFoodItem(String id, String n, double p, BitmapDrawable pic, int c){
		ParseID = id; 
		name = n;

		price = p;
		itemPicture = pic;
		category = c;
		isASectionDivider = false;
	}
	
	public MenuFoodItem(String n, int c){
		name = n;
		category = c;
		isASectionDivider = true;
	}
}
