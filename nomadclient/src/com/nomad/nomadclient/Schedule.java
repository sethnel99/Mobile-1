package com.nomad.nomadclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    
    private class ScheduleListAdapter extends ArrayAdapter<ScheduleEntry>{
    	ArrayList<ScheduleEntry> schedule;
    	Context c;
    	
    	public ScheduleListAdapter(FoodTruck t, Context context){
    		super(context,t.schedule.size());
    		schedule = t.schedule;
    		c = context;
    		
    		sortSchedule();
    	}
    	
    	
    	private void sortSchedule(){
    		Collections.sort(schedule,new Comparator<ScheduleEntry>() {
    			//custom compare of String[]'s
    			public int compare(ScheduleEntry se1, ScheduleEntry se2) {
    				return ((1440 * se1.dayOfWeek + se1.time) - (1440 * se2.dayOfWeek + se2.time));
    			}
    		});
    	}
    	
    	
    	@Override
        public View getView(int position, View convertView, ViewGroup parent) {
    		
    			LayoutInflater inflater = (LayoutInflater) c.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    			View v = convertView;
    		   
    	        final ScheduleEntry i = schedule.get(position);
    	        if (i != null) {
    	            if(i.isASectionDivider){
    	                
    	                v = inflater.inflate(R.layout.section_divider, null);
    	                v.setOnClickListener(null);
    	                v.setOnLongClickListener(null);
    	                v.setLongClickable(false);
    	 
    	                final TextView sectionView = (TextView) v.findViewById(R.id.sectionString);
    	                sectionView.setText(ScheduleEntry.stringDayOfWeek(i.dayOfWeek));
    	            }else{
    	            	

    	                v = inflater.inflate(R.layout.schedulerow, null);
    	                final TextView timeTextView = (TextView)v.findViewById(R.id.sectionRowTime);
    	                final TextView locationTextView = (TextView)v.findViewById(R.id.sectionRowLocation);
    	                	
    	                	
    	                    timeTextView.setText(ScheduleEntry.getTimeString(i.time));
    	                    locationTextView.setText(i.location);
    	            }
    	        }
    	        return v;
    	}
    	
    	@Override 
    	public int getItemViewType(int position){
    		if(schedule.get(position).isASectionDivider)
    			return 1;
    		else 
    			return 2;
    	}

    	@Override
    	public int getCount() {
    		return schedule.size();
    	}

    	@Override
    	public ScheduleEntry getItem(int position) {
    		return schedule.get(position);
    	}

    	@Override
    	public long getItemId(int position) {
    		return 0;
    	}
    	

    	
    }
}
