package com.nomad.nomadclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

//test comment 2222
public class CustomListAdapter extends BaseAdapter{
	public static int TEXTVIEW = 0;
	public static int IMAGEVIEW = 1;

	int rowLayoutID; //the layout id of the row to be inflated
	int[] viewIDs;	 //contains the ids of each view to be filled in within the row
	public ArrayList<String[]> items; //all of the data - each element of the arraylist is a string[] containing either string messages or image resource ids
	public ArrayList<String[]> originalItems; //the original items in listview. Used for filtering.
	int[] viewTypes; //for each view id, what type of view is it?
	String[] searchableStrings;
	int sortField; //the field within each String[] which should be used to filter the list. i.e. sortField = 2, so sort the ArrayList based on the
	//third entry in its String[]
	Context c;  //given context
	Boolean filteredList;
	MyCustomFilter mFilter; //filter for listview


	//Object[position][view]. First index refers to which row, second index refers to the variables that go in each view of viewIDs[]
	public CustomListAdapter(Context context, int rID, int[] vIDs, int[] vTypes, ArrayList<String[]> i, String[] sFields, int sf){
		viewIDs = vIDs;
		viewTypes = vTypes;
		items = i;
		rowLayoutID = rID;
		searchableStrings = sFields;
		c = context;
		sortField = sf;
		filteredList = true;
		sortItemsByField();
	}
	
	public CustomListAdapter(Context context, int rID, int[] vIDs, int[] vTypes, ArrayList<String[]> i, int sf){
		viewIDs = vIDs;
		viewTypes = vTypes;
		items = i;
		rowLayoutID = rID;
		c = context;
		filteredList = false;
		sortField = sf;
		sortItemsByField();
	}
	
	public CustomListAdapter(Context context, int rID, int[] vIDs, int[] vTypes, ArrayList<String[]> i, String[] sFields){
		viewIDs = vIDs;
		viewTypes = vTypes;
		items = i;
		rowLayoutID = rID;
		c = context;
		filteredList = true;
	}
	
	public CustomListAdapter(Context context, int rID, int[] vIDs, int[] vTypes, ArrayList<String[]> i){
		viewIDs = vIDs;
		viewTypes = vTypes;
		items = i;
		rowLayoutID = rID;
		c = context;
		filteredList = false;
	}

	//sorts the ArrayList of items based on the given sortField
	public void sortItemsByField(){
		Collections.sort(items,new Comparator<String[]>() {
			//custom compare of String[]'s
			public int compare(String[] strings, String[] otherStrings) {
				return strings[sortField].compareTo(otherStrings[sortField]);
			}
		});
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) c.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

		View row = inflater.inflate(rowLayoutID, parent, false);


		View[] views = new View[viewIDs.length];


		for(int i = 0; i < views.length; i++){



			views[i] = row.findViewById(viewIDs[i]);

			if(viewTypes[i] == this.TEXTVIEW){
				TextView tv = (TextView)views[i];
				tv.setText((String)items.get(position)[i]);

			}
			else if(viewTypes[i] == this.IMAGEVIEW){
				ImageView iv = (ImageView)views[i];
				iv.setImageResource(Integer.valueOf((String)items.get(position)[i]));
			}	
		}

		return row;
	}

	public Filter getFilter(){
		if (mFilter == null)
			mFilter = new MyCustomFilter();
		return mFilter;
	}

	@Override 
	public int getItemViewType(int position){
		return 1;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		return position-1;
	}

	private class MyCustomFilter extends Filter{
		private final Object mLock = new Object();


		@Override 
		protected FilterResults performFiltering(CharSequence constraint) {
				
			//	Log.v("Filtering",constraint);

			// Initiate our results object
			FilterResults results = new FilterResults();

			// If the list has not been filtered yet, initialize the original list
			//lock because it says so
			if (originalItems == null) {
				synchronized (mLock) { 
					originalItems = new ArrayList<String[]>(items);
				}
			}
			// No prefix is sent to filter by so we're going to send back the original array
			if (constraint == null || constraint.length() == 0) {
				synchronized (mLock) {
					results.values = originalItems;
					results.count = originalItems.size();
				}
			} else {
				// Compare lower case strings
				String constraintString = constraint.toString().toLowerCase();

				//will store the new list of filtered items
				final ArrayList<String[]> newItems = new ArrayList<String[]>();

				//check each original item
				for (int i = 0; i < originalItems.size(); i++) {
				
					//if the list isn't meant to be filtered, or it is and this entry matches the filter, add it to the new list
					if (!filteredList || searchableStrings[i].contains(constraintString)) {
						newItems.add(originalItems.get(i));
					}  

				}
				// Set and return
				results.values = newItems;
				results.count = newItems.size();
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence prefix, FilterResults results) {
			//set items to the results of the filtering
			items = (ArrayList<String[]>) results.values;

			// Let the adapter know about the updated list
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}

}


