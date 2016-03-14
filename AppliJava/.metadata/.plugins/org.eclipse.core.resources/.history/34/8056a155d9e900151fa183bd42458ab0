import java.io.IOException;
import java.util.ArrayList;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {

	private JSONArray search ;
	
	public JSONArray getSearch() {
		return search;
	}
	public void setSearch(JSONArray flux) {
		this.search = flux;
	}

	public Json(JSONArray apiReturn){
		this.search = apiReturn;
	}
	
	public ArrayList<Tweet> ParseJson() throws JSONException {
		
		
		ArrayList<Tweet> listTweet = new ArrayList<Tweet>();
		try{	
		    
		    
		    //put the value we need in tweets 
		    for (int i=0;i<search.length();i++){
		    	JSONObject jsonObject = search.getJSONObject(i);
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
	
	public ArrayList<Acount> ParseJsonAcount(){
		return null;
			
	}
	
	public ArrayList<Tweet> ParseJsonSearch() throws JSONException {
		
		ArrayList<Tweet> listTweet = new ArrayList<Tweet>();
		try{	
		    
		    
		    //put the value we need in tweets 
		    for (int i=0;i<search.length();i++){
		    	JSONObject jsonObject = search.getJSONObject(i);
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