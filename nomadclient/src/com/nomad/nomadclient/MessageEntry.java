package com.nomad.nomadclient;

import java.util.Date;

public class MessageEntry {
	String message;
	String timeCreated;
	
	public MessageEntry(String m, Date t){
		message = m;
		timeCreated = String.format("%02d/%02d/%02d %02d:%02d",t.getMonth()+1,t.getDay()+1,t.getYear()+1900,t.getHours(),t.getMinutes());
		//timeCreated = t.getMonth()+"/"+t.getDay()+"/"+t.getYear()+" " + String.format("%02d",t.getHours()) + ":" + t.getMinutes();
	}
}
