package com.nomad.nomadclient;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
	Activity thisClass = this;
	


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trucklist);

		//initialize view variables
		mListView = this.getListView();
		searchBar = (EditText)findViewById(R.id.searchEditText);
		
		
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


		//start up parse
		Parse.initialize(this, "FoX2hKFWtiUIWgt2mioFIJvwdwgy461XAS7n367S", "EU6d1ccuc3rUiW09IXqnLGF8XNngazVCWZDvSfC1"); 
		
		//load all of the trucks from parse
		LoadWithProgressDialog lwpd = new LoadWithProgressDialog(this,"Loading","Loading Truck Data", new Runnable() {
			public void run(){
				((NomadClientApplication)thisClass.getApplication()).loadTrucksFromParse();
			}
		}, new Runnable(){
			public void run(){
				//get the trucks from the global array
				ArrayList<FoodTruck> trucks =((NomadClientApplication)thisClass.getApplication()).getTrucks();
				Log.v("I just got the trucks","trucks size: " + trucks.size());
				

				//set up the adapter
				mListAdapter = new FoodTruckListAdapter(getApplicationContext(), trucks);
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
				
			}
		});
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
}