package com.nomad.nomadclient;

import com.parse.Parse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

		//If the process was killed and then restarted here, get the trucks from the cache
		if(((NomadClientApplication)this.getApplication()).getTrucks().size() == 0){
			Parse.initialize(this, "FoX2hKFWtiUIWgt2mioFIJvwdwgy461XAS7n367S", "EU6d1ccuc3rUiW09IXqnLGF8XNngazVCWZDvSfC1"); 
			((NomadClientApplication)this.getApplication()).loadTrucksFromParse(NomadClientApplication.CACHE_FIRST);
		}


		//Grab the truck index (given as an extra) and get the truck
		Bundle b = this.getIntent().getExtras();
		truckIndex = b.getInt("truckIndex");
		foodTruck = ((NomadClientApplication)this.getApplication()).getTrucks().get(truckIndex);

		//Set up the views in this activity with the correct data
		((TextView)findViewById(R.id.titleTextView)).setText(foodTruck.name);
		((TextView)findViewById(R.id.descriptTextView)).setText(foodTruck.descriptor);
		((ImageView)findViewById(R.id.truckLogoImageView)).setImageDrawable(foodTruck.logo);
		((TextView)findViewById(R.id.latestMessageText)).setText(foodTruck.messages.get(0));

		//begin loading the truck's data in the background, if you need to 
		if(foodTruck.menu.size() == 0)
			((NomadClientApplication)thisClass.getApplication()).loadTruckLists(truckIndex, NomadClientApplication.NETWORK_FIRST);





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
				Log.v("menu button pressed","yes");

				//if things are still loading, show a progress dialog in the meantime
				if(((NomadClientApplication)thisClass.getApplication()).loadingMenu){
					Log.v("loading menu not done","yet");
					BackgroundLoader lwpd = new BackgroundLoader(thisClass,sleepWhileMenuLoads,openMenu,"Loading","Loading Menu");
					lwpd.execute();
				}else{
					//start the menu class
					Intent i = new Intent(thisClass, Menu.class);  
					i.putExtra("truckIndex",truckIndex);
					startActivity(i); 
				}

			}
		});
	

		//What to do if they click the schedule "button"////////////////////////
		RelativeLayout scheduleViewButton = (RelativeLayout)findViewById(R.id.scheduleButtonView);
		scheduleViewButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Log.v("clicked schedule button","clicked schedule button");

				//if things are still loading, show a progress dialog in the meantime
				if(((NomadClientApplication)thisClass.getApplication()).loadingSchedule){
					Log.v("loading schedule not done","yet");
					BackgroundLoader lwpd = new BackgroundLoader(thisClass,sleepWhileScheduleLoads,openSchedule,"Loading","Loading Schedule");
					lwpd.execute();
				}else{
					//start the menu class
					Intent i = new Intent(thisClass, Schedule.class);  
					i.putExtra("truckIndex",truckIndex);
					startActivity(i); 
				}





			}
		});
		///////////////////////////////////////////////////


	}

	///////////////////////define a bunch of runnables to keep code uncluttered////////////////
	Runnable sleepWhileMenuLoads = new Runnable() {
		public void run(){
			while(((NomadClientApplication)thisClass.getApplication()).loadingMenu){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	};

	Runnable sleepWhileScheduleLoads = new Runnable() {
		public void run(){
			while(((NomadClientApplication)thisClass.getApplication()).loadingSchedule){
				try {
					Thread.sleep(1000);				
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	};

	Runnable openMenu = new Runnable() {
		public void run(){
			//start the menu class
			Intent i= new Intent(thisClass, Menu.class);
			i.putExtra("truckIndex",truckIndex);
			startActivity(i); 
		}
	};

	Runnable openSchedule = new Runnable() {
		public void run(){
			//start the menu class
			Intent i= new Intent(thisClass, Schedule.class);
			i.putExtra("truckIndex",truckIndex);
			startActivity(i); 
		}
	};





}
