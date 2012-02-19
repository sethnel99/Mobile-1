package com.nomad.nomadclient;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Menu extends ListActivity{
	ListView mListView;	//the listview for the menu items
	MenuListAdapter mListAdapter; //the adapter for the listview
	FoodTruck foodTruck; //the truck who's menu is represented

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		//if the activity resumes and the global data has been garbage collected, finish()
		if(((NomadClientApplication)this.getApplication()).getTrucks().size() == 0)
			finish();



		//if they click the back button, finish the activity
		Button backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				finish();
			}
		});

		//initialize the listview variable
		mListView = this.getListView();

		//fetch the foodtruck who's menu will be shown
		int truckIndex = getIntent().getExtras().getInt("truckIndex");
		foodTruck = ((NomadClientApplication)this.getApplication()).getTrucks().get(truckIndex); 

		((TextView)findViewById(R.id.menuPrompt)).setText(foodTruck.name + "'s Menu");

		//set up the adapter
		mListAdapter = new MenuListAdapter(foodTruck,getApplicationContext());
		setListAdapter(mListAdapter);
		mListView.setTextFilterEnabled(true);      




	}
	
	  private class MenuListAdapter extends ArrayAdapter<MenuFoodItem>{
	    	ArrayList<MenuFoodItem> menu;
	    	Context c;
	    	
	    	public MenuListAdapter(FoodTruck t, Context context){
	    		super(context,t.menu.size());
	    		menu = t.menu;
	    		c = context;
	    	}
	    	
	    	@Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	    		
	    			LayoutInflater inflater = (LayoutInflater) c.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	    			View v = convertView;
	    		   
	    	        final MenuFoodItem i = menu.get(position);
	    	        if (i != null) {
	    	            if(i.isASectionDivider){
	    	                
	    	                v = inflater.inflate(R.layout.section_divider, null);
	    	                v.setOnClickListener(null);
	    	                v.setOnLongClickListener(null);
	    	                v.setLongClickable(false);
	    	 
	    	                final TextView sectionView = (TextView) v.findViewById(R.id.sectionString);
	    	                sectionView.setText(i.name);
	    	            }else{
	    	            	

	    	                v = inflater.inflate(R.layout.menurow, null);
	    	                final ImageView foodPicView = (ImageView)v.findViewById(R.id.foodPic);
	    	                final TextView menuItemText = (TextView)v.findViewById(R.id.menuItemText);
	    	                final TextView menuItemPrice = (TextView)v.findViewById(R.id.menuItemPrice);
	    	                	
	    	                	foodPicView.setBackgroundDrawable(i.itemPicture);
	    	                	menuItemText.setText(i.name);
	    	                	menuItemPrice.setText("$"+String.format("%.2f",i.price));
	    	            }
	    	        }
	    	        return v;
	    	}
	    	
	    	@Override 
	    	public int getItemViewType(int position){
	    		if(menu.get(position).isASectionDivider)
	    			return 1;
	    		else 
	    			return 2;
	    	}

	    	@Override
	    	public int getCount() {
	    		return menu.size();
	    	}

	    	@Override
	    	public MenuFoodItem getItem(int position) {
	    		return menu.get(position);
	    	}

	    	@Override
	    	public long getItemId(int position) {
	    		return 0;
	    	}
	    	

	    	
	    }
/*
	private class FoodTruckMenuListAdapter extends CustomListAdapter{
		//passing in a single truck, will create an adapter for information in that single truck based on type 
		public FoodTruckMenuListAdapter(Context c, FoodTruck ft){
			super(c);

			//data to pass to super class
			ArrayList<Object[]> items = new ArrayList<Object[]>();

			int rowID = R.layout.menurow; //layout id of the menu row layout file
			int[] viewIDs = {R.id.foodPic, R.id.menuItemText, R.id.menuItemPrice}; //the views that will be populated in the row
			int[] viewTypes = {IMAGEVIEW_BITMAPDRAWABLE, TEXTVIEW, TEXTVIEW}; //the types of those views

			Object[] temp;
			MenuFoodItem tempmfi; 
			ArrayList<MenuFoodItem> menu = ft.menu; //fetch the menu

			for(int i = 0; i < menu.size(); i++){ //populate items using the data from menu
				temp = new Object[3];
				tempmfi = menu.get(i);
				temp[0] = tempmfi.itemPicture;
				temp[1] = tempmfi.name;
				temp[2] = "$"+String.format("%.2f",tempmfi.price);
				items.add(temp);
			}

			super.initAdapter(rowID, viewIDs, viewTypes, items);


		}
	}*/
}
