package com.nomad.nomadclient;

import java.io.ByteArrayInputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseException;
import com.parse.ParseFile;

public class MenuFoodItem {
	public String ParseID;
	public String name;
	public double price;
	private ParseFile imageFile;
	public Bitmap itemPicture;
	public int category;
	public boolean isASectionDivider;
	
	public MenuFoodItem(String id, String n, double p, ParseFile pic, int c){
		ParseID = id; 
		name = n;
		price = p;
		imageFile = pic;
		category = c;
		isASectionDivider = false;
	}
	
	public MenuFoodItem(String n, int c){
		name = n;
		category = c;
		isASectionDivider = true;
	}
	
	public void decodeImageFile(){
		ByteArrayInputStream is;
		try {
			is = new ByteArrayInputStream(imageFile.getData());
			itemPicture = BitmapFactory.decodeStream(is);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
