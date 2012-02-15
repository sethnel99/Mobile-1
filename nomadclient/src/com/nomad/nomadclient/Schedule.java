package com.nomad.nomadclient;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Schedule extends ListActivity{
	ListView mListView;	//the listview for the schedule entries
	ScheduleListAdapter mListAdapter; //the adapter for the listview
	FoodTruck foodTruck; //the truck who's schedule is represented
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        
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
        
 
        ((TextView)findViewById(R.id.schedulePrompt)).setText(foodTruck.name + "'s Schedule");

        //set up the adapter
        mListAdapter = new ScheduleListAdapter(foodTruck,this.getApplicationContext());
        setListAdapter(mListAdapter);  
    
        
    }
}
