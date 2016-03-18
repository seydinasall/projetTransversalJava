package com.clement.androidapli;
import java.util.ArrayList;

//import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {


	public ArrayList<Tweet> ParseJson(JSONArray flux) throws JSONException {
		
		
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
}