package com.nomad.nomadclient;

public class MenuFoodItem {
	public String ParseID;
	public String name;
	public int drawableID;
	public double price;
	
	public MenuFoodItem(String id, String n, int i, double p){
		ParseID = id; 
		name = n;
		drawableID = i;
		price = p;
	}
}
