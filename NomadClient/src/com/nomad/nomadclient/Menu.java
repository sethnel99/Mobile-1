package com.nomad.nomadclient;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Menu extends ListActivity{
	ListView mListView;
	CustomListAdapter mListAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
    	Button backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				finish();
			}
		});
		
       
        mListView = this.getListView();
      
        ArrayList<MenuFoodItem> menu = new ArrayList<MenuFoodItem>();
        for(int i = 0; i < 15; i++){
        	menu.add(new MenuFoodItem("Chicken Empanadas",R.drawable.empanadapic,9.99));
        }
        
        FoodTruck temp = new FoodTruck("5411 Empanadas","W. Jackson and Wells","5411 Empanadas");
        temp.addMenu(menu);
        
        
        mListAdapter = new FoodTruckListAdapter(getApplicationContext(),temp,FoodTruckListAdapter.MENU);
        setListAdapter(mListAdapter);
        mListView.setTextFilterEnabled(true);
       

        
       

        
        
        
    }
}
