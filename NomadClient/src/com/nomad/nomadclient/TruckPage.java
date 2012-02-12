package com.nomad.nomadclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TruckPage extends Activity{
	Activity thisClass = this;
	FoodTruck foodTruck; //the food truck represented by the page
	int truckIndex; //that trucks index in the global arraylist

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.truckpage);

		//Grab the truck index (given as an extra) and get the trck
		Bundle b = this.getIntent().getExtras();
		truckIndex = b.getInt("truckIndex");
		foodTruck = ((NomadClientApplication)this.getApplication()).getTrucks().get(truckIndex);

		//Set up the views in this activity with the correct data
		((TextView)findViewById(R.id.titleTextView)).setText(foodTruck.name);
		((TextView)findViewById(R.id.descriptTextView)).setText(foodTruck.descriptor);
		((ImageView)findViewById(R.id.truckLogoImageView)).setImageDrawable(foodTruck.logo);

		//begin loading the truck's data in the background, if you need to 
		if(foodTruck.menu.size() == 0)
			((NomadClientApplication)thisClass.getApplication()).loadTruckLists(truckIndex);





		//What to do if they click the back button
		Button backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				//finish this activity
				finish();
			}
		});

		//What to do if they click the phone-number button
		TextView callPhone = (TextView)findViewById(R.id.phoneTextView);
		callPhone.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				//Call the number
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + "2409946399"));
				startActivity(intent);

			}
		});


		//What to do if they click the map "button"
		RelativeLayout mapViewButton = (RelativeLayout)findViewById(R.id.mapButtonView);
		mapViewButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				//start the map activity
				Intent i = new Intent(thisClass, TruckMap.class);   
				i.putExtra("fromPage", TruckMap.FROM_TRUCK_PAGE);
				startActivity(i);
			}
		});

		//What to do if they click the menu "button"
		RelativeLayout menuViewButton = (RelativeLayout)findViewById(R.id.menuButtonView);
		menuViewButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {

				//if things are still loading, show a progress dialog in the meantime
				if(((NomadClientApplication)thisClass.getApplication()).loadingInBackground != 0){
					LoadWithProgressDialog lwpd = new LoadWithProgressDialog(thisClass,"Loading","Loading Menu", new Runnable() {
						public void run(){
							while(((NomadClientApplication)thisClass.getApplication()).loadingInBackground != 0){
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
							
							}
						}
					},new Runnable() {
						public void run(){
							//start the menu class
							Intent i = new Intent(thisClass, Menu.class);  
							i.putExtra("truckIndex",truckIndex);
							startActivity(i); 
						}
					});
					lwpd.execute();
				}

			}
		});


		//What to do if they click the schedule "button"
		RelativeLayout scheduleViewButton = (RelativeLayout)findViewById(R.id.scheduleButtonView);
		scheduleViewButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				//Log.v("clicked schedule button","clicked schedule button");
			}
		});


	}

}
