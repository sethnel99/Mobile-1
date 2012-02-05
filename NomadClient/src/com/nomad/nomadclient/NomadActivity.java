package com.nomad.nomadclient;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class NomadActivity extends ListActivity{
	ListView mListView;
	EditText searchBar;
	CustomListAdapter mListAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trucklist);
       
        mListView = this.getListView();
        searchBar = (EditText)findViewById(R.id.searchEditText);
        
      
        int rID = R.layout.truckrow;
        int[] viewIDs = {R.id.distanceText, R.id.nameText, R.id.locationText};
        int[] viewTypes = {CustomListAdapter.TEXTVIEW,CustomListAdapter.TEXTVIEW,CustomListAdapter.TEXTVIEW};
      
        ArrayList<String[]> items = fillFakeTrucks();
        String[] searchStrings = new String[items.size()];
        for(int i = 0; i < searchStrings.length; i++){
        	searchStrings[i] = items.get(i)[1];
        }
        
        
        mListAdapter = new CustomListAdapter(getApplicationContext(),rID,viewIDs, viewTypes, items, searchStrings, 0);
        setListAdapter(mListAdapter);
        mListView.setTextFilterEnabled(true);
        searchBar.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
            	mListAdapter.getFilter().filter(s);
            }
        }); 

        
       

        
        
        
    }
    
    public  ArrayList<String[]> fillFakeTrucks(){
    	ArrayList<String[]> toRet = new ArrayList<String[]>();
    	
       	String[] item = new String[3];
       	
    	item[0] = "0.1";
    	item[1] = "5411 Empanadas";
    	item[2] = "W. Jackson & Wells";
    	toRet.add(item);
    	
    	item = new String[3];
    	item[0] = "0.2";
    	item[1] = "Hummingbird Kitchen";
    	item[2] = "W. Jackson & Wells";
    	toRet.add(item);
    	
    	item = new String[3];
    	item[0] = "0.3";
    	item[1] = "Flirty Cupcakes";
    	item[2] = "W. Jackson & Wells";
    	toRet.add(item);
    	
    	item = new String[3];
    	item[0] = "0.4";
    	item[1] = "More Mobile";
    	item[2] = "W. Jackson & Wells";  
    	toRet.add(item);
    	
    	item = new String[3];
    	item[0] = "0.5";
    	item[1] = "Gaztro-Wagon";
    	item[2] = "W. Jackson & Wells";
    	toRet.add(item);
    	
    	item = new String[3];
    	item[0] = "0.6";
    	item[1] = "Meatyballs Mobile";
    	item[2] = "W. Jackson & Wells"; 
    	toRet.add(item);
    	
    	item = new String[3];
      	item[0] = "1.0";
    	item[1] = "Simple Sandwich";
    	item[2] = "W. Jackson & Wells";   
    	toRet.add(item);
    	
    	item = new String[3];
      	item[0] = "0.9";
    	item[1] = "Happy Bodgea";
    	item[2] = "W. Jackson & Wells";
    	toRet.add(item);
    	
    	item = new String[3];
      	item[0] = "0.8";
    	item[1] = "Beavers Donuts";
    	item[2] = "W. Jackson & Wells";
    	toRet.add(item);
    	
    	item = new String[3];
      	item[0] = "0.7";
    	item[1] = "Brown Bag";
    	item[2] = "W. Jackson & Wells";
    	toRet.add(item);
    	
    	return toRet;
    	
    	
    }
 

  
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, TruckPage.class);           
		startActivity(i); 
	}
}