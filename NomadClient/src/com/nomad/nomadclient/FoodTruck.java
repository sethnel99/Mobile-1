package com.nomad.nomadclient;

public class FoodTruck {
	public String name;
	public String location;
	public String searchString;
	
	public FoodTruck(String n, String l, String s){
		name = n;
		location = l;
		searchString = s;
	}
	
	public double calculateDistance(){
		int r = (int)(20*Math.random());
		return .1 * r;
	}
	
	

}
