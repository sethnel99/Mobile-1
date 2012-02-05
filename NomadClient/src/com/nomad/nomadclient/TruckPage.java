package com.nomad.nomadclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TruckPage extends Activity{
	Activity thisClass = this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truckpage);
        
        /*
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/CandelaBold.ttf");
		((TextView)findViewById(R.id.scheduleButtonText)).setTypeface(tf);
		((TextView)findViewById(R.id.menuButtonText)).setTypeface(tf);
		((TextView)findViewById(R.id.mapButtonText)).setTypeface(tf);
		((TextView)findViewById(R.id.latestMessageText)).setTypeface(tf);
		((TextView)findViewById(R.id.phoneTextView)).setTypeface(tf);
		((TextView)findViewById(R.id.latestMessagePromptText)).setTypeface(tf);
		((TextView)findViewById(R.id.descriptTextView)).setTypeface(tf);
		((TextView)findViewById(R.id.titleTextView)).setTypeface(tf)
		*/
        
		Button backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				finish();
			}
		});
		
		TextView callPhone = (TextView)findViewById(R.id.phoneTextView);
		callPhone.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + "2409946399"));
				startActivity(intent);
				
			}
		});
		
		
		RelativeLayout mapViewButton = (RelativeLayout)findViewById(R.id.mapButtonView);
		mapViewButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				//Log.v("clicked map button","clicked map button");
			}
		});
		
		RelativeLayout menuViewButton = (RelativeLayout)findViewById(R.id.menuButtonView);
		menuViewButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//Log.v("clicked menu","clicked menu");
				Intent i = new Intent(thisClass, Menu.class);           
				startActivity(i); 
			}
		});
		
		RelativeLayout scheduleViewButton = (RelativeLayout)findViewById(R.id.scheduleButtonView);
		scheduleViewButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				//Log.v("clicked schedule button","clicked schedule button");
			}
		});
		
		
    }
       
}
