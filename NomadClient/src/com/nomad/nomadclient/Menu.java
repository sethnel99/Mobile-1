package com.nomad.nomadclient;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
      
        int rID = R.layout.menurow;
        int[] viewIDs = {R.id.foodPic,R.id.menuItemText};
        int[] viewTypes = {CustomListAdapter.IMAGEVIEW,CustomListAdapter.TEXTVIEW};
      
        ArrayList<String[]> items = new ArrayList<String[]>();
        String[] item;
        for(int i = 0; i < 15; i++){
        	item = new String[2];
        	item[0]= String.valueOf(R.drawable.empanadapic);
        	item[1]= "Chicken Empanada";
        	items.add(item);
        }
        
        
        mListAdapter = new CustomListAdapter(getApplicationContext(),rID,viewIDs, viewTypes, items);
        setListAdapter(mListAdapter);
        mListView.setTextFilterEnabled(true);
       

        
       

        
        
        
    }
}
