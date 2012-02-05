package com.nomad.nomadclient;

import java.util.ArrayList;

import android.view.MenuItem;

public class FoodTruck {
	public String name;
	public String location;
	public String searchString;
	public ArrayList<MenuFoodItem> menu;
	
	public FoodTruck(String n, String l, String s){
		name = n;
		location = l;
		searchString = s;
		menu = new ArrayList<MenuFoodItem>();
	}
	
	public double calculateDistance(){
		int r = (int)(20*Math.random());
		return .1 * r;
	}
	
	public void addMenu(ArrayList<MenuFoodItem> m){
		menu = m;
	}
	
	

}
