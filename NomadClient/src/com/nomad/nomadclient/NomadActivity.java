package com.nomad.nomadclient;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;

public class NomadActivity extends ListActivity{
	ListView mListView; //The listview displaying the trucks
	EditText searchBar; //the editbox used to filter the list
	FoodTruckListAdapter mListAdapter; //the adapter used to populate the listview
	ListActivity thisClass = this;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		//initialize view variables



		//start up parse
		Parse.initialize(this, "FoX2hKFWtiUIWgt2mioFIJvwdwgy461XAS7n367S", "EU6d1ccuc3rUiW09IXqnLGF8XNngazVCWZDvSfC1"); 

		//load all of the trucks from parse
		BackgroundLoader lwpd = new BackgroundLoader(this, new Runnable() {
			public void run(){
				((NomadClientApplication)thisClass.getApplication()).loadTrucksFromParse(NomadClientApplication.NETWORK_FIRST);
			}
		}, new Runnable(){
			public void run(){
				//get the trucks from the global array
				ArrayList<FoodTruck> trucks =((NomadClientApplication)thisClass.getApplication()).getTrucks();

				setContentView(R.layout.trucklist);
				mListView = thisClass.getListView();
				searchBar = (EditText)findViewById(R.id.searchEditText);


				
				//set up the adapter
				mListAdapter = new FoodTruckListAdapter(getApplication(), trucks);
				setListAdapter(mListAdapter);
				mListView.setTextFilterEnabled(true);

				//set up the search bar to detect when its text has changed and filter the results
				searchBar.addTextChangedListener(new TextWatcher(){
					public void afterTextChanged(Editable s) {}
					public void beforeTextChanged(CharSequence s, int start, int count, int after){}
					public void onTextChanged(CharSequence s, int start, int before, int count){
						mListAdapter.getFilter().filter(s);
					}
				}); 
				
				//What to do if they click the map button
				Button mapButton = (Button)findViewById(R.id.mapButton);
				mapButton.setOnClickListener(new View.OnClickListener(){
					public void onClick(View v) {
						//start the map activity
						Intent i = new Intent(thisClass, TruckMap.class);   
						i.putExtra("fromPage", TruckMap.FROM_LIST);
						startActivity(i); 
					}
				});

			}
		},"Loading","Loading Truck Data");
		lwpd.execute();




		





	}

	//If any item in the list is clicked
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		//determine the actual index of the truck clicked in the global ArrayList
		TextView globalPositionText = (TextView)v.findViewById(R.id.globalPosition);
		int truckIndex = Integer.valueOf((String)globalPositionText.getText());

		//start the truck page activity for that truck
		Intent i = new Intent(this, TruckPage.class);   
		i.putExtra("truckIndex",truckIndex);
		startActivity(i); 
	}

	public class FoodTruckListAdapter extends CustomListAdapter{

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
				double dist = ((NomadClientApplication)c).getDistanceFrom(tempft.location);
				if(dist < 100) //to make sure the distance isn't too long to display in its alloted space
					temp[0] = String.format("%.1f",dist);
				else
					temp[0] = ">100";
				temp[1] = tempft.name;
				temp[2] = tempft.locationString;
				temp[3] = ""+i;
				items.add(temp);
				searchFields.add(tempft.searchString);
				//Log.v("creating",""+items.get(i)[1] + ":"+searchFields[i]);
			}

			super.initAdapter(rowID, viewIDs, viewTypes, items, searchFields);	
		}
	}
}