package com.nomad.nomadclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScheduleListAdapter extends ArrayAdapter<ScheduleEntry>{
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
	                
	                v = inflater.inflate(R.layout.schedule_section_divider, null);
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
