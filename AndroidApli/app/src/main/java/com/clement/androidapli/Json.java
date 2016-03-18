package com.clement.androidapli;
import java.util.ArrayList;

//import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {

	private JSONArray flux ;
	private String account;
	
	public JSONArray getSearch() {
		return flux;
	}
	public void setSearch(JSONArray jArray) {
		this.flux = jArray;
	}

	public Json(JSONArray apiReturn){
		this.flux = apiReturn;
	}
	public Json(String str){
		this.account = str;
	}
	
	public ArrayList<Tweet> ParseJson() throws JSONException {
		
		
		ArrayList<Tweet> listTweet = new ArrayList<Tweet>();
		try{	
		    
		    
		    //put the value we need in tweets 
		    for (int i=0;i<flux.length();i++){
		    	JSONObject jsonObject = flux.getJSONObject(i);
		    	String createdAt = jsonObject.getString("created_at");
		    	String text = jsonObject.getString("text");
		    	
		    	JSONObject jsonObject2 = jsonObject.getJSONObject("user");
		    	String name = jsonObject2.getString("name");
		    	String screenName = jsonObject2.getString("screen_name");
		    	String avatar = jsonObject2.getString("profile_image_url");
		    	
		    	listTweet.add(new Tweet(createdAt,text,name,screenName,avatar));
		    	
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return listTweet;
	}
	

	public Account ParseJsonAccount() throws JSONException{
		
		JSONObject jsonObject = new JSONObject(account);
		Account account = null;
		try{
			String name = jsonObject.getString("name");
			String screen_name = jsonObject.getString("screen_name");
			String profile_image_url = jsonObject.getString("profile_image_url");
			
			account = new Account(name,screen_name,profile_image_url);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return account;
	}
}