package com.nomad.nomadclient;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Menu extends ListActivity{
	ListView mListView;	//the listview for the menu items
	MenuListAdapter mListAdapter; //the adapter for the listview
	FoodTruck foodTruck; //the truck who's menu is represented
	int truckIndex; 
	Activity thisClass = this;
	int menuItemFocused;
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
		truckIndex = getIntent().getExtras().getInt("truckIndex");
		foodTruck = ((NomadClientApplication)this.getApplication()).getTrucks().get(truckIndex); 

		((TextView)findViewById(R.id.menuPrompt)).setText(foodTruck.name + "'s Menu");

		//set up the adapter
		mListAdapter = new MenuListAdapter(foodTruck,getApplicationContext());
		setListAdapter(mListAdapter);
		mListView.setTextFilterEnabled(true);      




	}

	//If any item in the list is clicked
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		menuItemFocused = position;
		if(foodTruck.menu.get(menuItemFocused).itemPicture != null){
			//start the truck page activity for that truck
			Intent i = new Intent(thisClass, MenuItemPage.class);
			i.putExtra("TruckIndex",truckIndex);
			i.putExtra("MenuItemIndex",menuItemFocused);
			startActivity(i); 
		}
		else{
			BackgroundLoader lwpd = new BackgroundLoader(thisClass,sleepWhileMenuPicLoads,openMenuItemPage,"Loading","Finishing Loading Item");
			lwpd.execute();
		}


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
					final TextView menuItemText = (TextView)v.findViewById(R.id.menuItemText);
					final TextView menuItemPrice = (TextView)v.findViewById(R.id.menuItemPrice);

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

	Runnable sleepWhileMenuPicLoads = new Runnable() {
		public void run(){
			foodTruck.menu.get(menuItemFocused).decodeImageFile();
		}
	};

	Runnable openMenuItemPage = new Runnable() {
		public void run(){
			//start the truck page activity for that truck
			Intent i = new Intent(thisClass, MenuItemPage.class);
			i.putExtra("TruckIndex",truckIndex);
			i.putExtra("MenuItemIndex",menuItemFocused);
			startActivity(i); 
		}
	};

}
