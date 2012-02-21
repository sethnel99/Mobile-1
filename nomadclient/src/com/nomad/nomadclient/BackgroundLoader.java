package com.nomad.nomadclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class BackgroundLoader extends AsyncTask<Void, Void, Boolean>{
	private ProgressDialog pd; //the progress dialog
	private String title; //the title of the progress dialog
	private String  message; //the body of the progress dialog
	private Runnable task; //contains the code we want to run in the background
	private Runnable postTask; //execute when the task ends
	private Context c;
	private boolean withProgressDialog; //if the loader shoud pop up a progress dialog on the UI thread while it acts


	public BackgroundLoader(Context context,Runnable r, Runnable postR,String t, String m){
		super();
		c = context;
		task = r;
		postTask = postR;
		title = t;
		message = m;
		withProgressDialog = true;
	}

	public BackgroundLoader(Runnable r, Runnable postR){
		super();
		task = r;
		postTask = postR;
		withProgressDialog = false;
	}

	@Override
	protected void onPreExecute(){
		if(withProgressDialog){
			pd = new ProgressDialog(c);
			pd.setTitle(title);
			pd.setMessage(message);
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

	}

	@Override
	protected Boolean doInBackground(Void... params) {
		task.run();
		return true;
	}


	@Override
	protected void onPostExecute(Boolean result) {	
		if(withProgressDialog && pd != null)
			pd.dismiss();
		
		if(postTask != null)
			postTask.run();
	}





}