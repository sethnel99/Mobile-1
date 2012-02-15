package com.nomad.nomadclient;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Menu extends ListActivity{
	ListView mListView;	//the listview for the menu items
	CustomListAdapter mListAdapter; //the adapter for the listview
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
        mListAdapter = new FoodTruckListAdapter(getApplicationContext(),foodTruck,FoodTruckListAdapter.MENU);
        setListAdapter(mListAdapter);
        mListView.setTextFilterEnabled(true);      
       

    
        
    }
}
