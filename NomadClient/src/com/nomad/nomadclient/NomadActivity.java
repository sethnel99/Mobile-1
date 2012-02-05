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
	FoodTruckListAdapter mListAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trucklist);
       
        mListView = this.getListView();
        searchBar = (EditText)findViewById(R.id.searchEditText);
        
        ArrayList<FoodTruck> trucks = fillFakeTrucks();
        
        mListAdapter = new FoodTruckListAdapter(getApplicationContext(), trucks);
        
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
    
    public  ArrayList<FoodTruck> fillFakeTrucks(){
    	ArrayList<FoodTruck> toRet = new ArrayList<FoodTruck>();
    	
    	toRet.add(new FoodTruck("5411 Empanadas","W. Jackson & Wells", "5411 Empanadas"));
    	toRet.add(new FoodTruck("Flirty Cupcakes","W. Jackson & Wells", "Flirty Cupcakes"));
    	toRet.add(new FoodTruck("More Mobile","W. Jackson & Wells", "More Mobile"));
    	toRet.add(new FoodTruck("Gaztro-Wagon","W. Jackson & Wells", "Gaztro-Wagon"));
    	toRet.add(new FoodTruck("Meatyballs Mobile","W. Jackson & Wells", "Meatyballs Mobile"));
    	toRet.add(new FoodTruck("Simple Sandwich","W. Jackson & Wells", "Simple Sandwich"));
    	toRet.add(new FoodTruck("Happy Bodega","W. Jackson & Wells", "Happy Bodega"));
    	toRet.add(new FoodTruck("Beavers Donuts","W. Jackson & Wells", "Beavers Donuts"));
    	toRet.add(new FoodTruck("Brown Bag","W. Jackson & Wells", "Brown Bag"));
    	toRet.add(new FoodTruck("Hummingbird Kitchen","W. Jackson & Wells", "Hummingbird Kitchen"));

    	
    	return toRet;
    	
    	
    }
 

  
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, TruckPage.class);           
		startActivity(i); 
	}
}