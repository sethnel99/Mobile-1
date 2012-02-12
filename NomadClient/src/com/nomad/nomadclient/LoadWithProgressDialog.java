package com.nomad.nomadclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadWithProgressDialog extends AsyncTask<Void, Void, Boolean>{
	private ProgressDialog pd; //the progress dialog
	private String title; //the title of the progress dialog
	private String  message; //the body of the progress dialog
	private Runnable task; //contains the code we want to run in the background
	private Runnable postTask; //execute when the task ends
	private Context c;


	public LoadWithProgressDialog(Context context,String t, String m,Runnable r, Runnable postR){
		super();
		c = context;
		task = r;
		postTask = postR;
		title = t;
		message = m;
	}

	@Override
	protected void onPreExecute(){
		pd = ProgressDialog.show(c,title, message, false, false);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		task.run();
		return true;
	}


	@Override
	protected void onPostExecute(Boolean result) {
		pd.dismiss();
		if(postTask != null)
			postTask.run();
	}





}