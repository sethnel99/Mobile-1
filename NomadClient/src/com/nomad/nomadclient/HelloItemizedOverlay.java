package com.nomad.nomadclient;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class HelloItemizedOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public HelloItemizedOverlay(Drawable defaultMarker, Context mc) {
		super(boundCenterBottom(defaultMarker));
		mContext = mc;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay){
		mOverlays.add(overlay);
		populate();
	}
	
	protected boolean onTap(int index){
		OverlayItem item = mOverlays.get(index);
		 /* AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet());
		  dialog.show();*/
		  return true;
	}
	
	public static Drawable myBoundCenterBottom(Drawable dr){
		return boundCenterBottom(dr);
	}
	
	
	


}
