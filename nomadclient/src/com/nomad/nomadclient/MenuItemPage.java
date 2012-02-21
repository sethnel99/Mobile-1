package com.nomad.nomadclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuItemPage extends Activity{
	MenuFoodItem item;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuitempage);

		//If the process was killed and then restarted here, get the trucks from the cache
		if(((NomadClientApplication)this.getApplication()).getTrucks().size() == 0){
			finish();
		}
		
		Bundle b = this.getIntent().getExtras();
		int truckIndex = b.getInt("TruckIndex");
		int menuItemIndex = b.getInt("MenuItemIndex");
		
		item = ((NomadClientApplication)this.getApplication()).getTrucks().get(truckIndex).menu.get(menuItemIndex);
		
		((TextView)findViewById(R.id.menuTitleTextView)).setText(item.name);
		((ImageView)findViewById(R.id.menuItemImageView)).setImageBitmap(item.itemPicture);
		((TextView)findViewById(R.id.menuPriceTextView)).setText(String.format("$%.2f",item.price));
		

		//What to do if they click the back button
		Button backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				//finish this activity
				finish();
			}
		});

		
	}
}
