import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {

	private String timeLine;
	
	public String getJson() {
		return timeLine;
	}
	public void setJson(String timeLine) {
		this.timeLine = timeLine;
	}


	public Json(Api api){
		System.out.println(api.getTwitter_acces_token());
		System.out.println(api.getTwitter_acces_secret());
		this.timeLine = api.verifyCredentials(api.getTwitter_acces_token(), api.getTwitter_acces_secret()).toString();
	
	}
	
	public ArrayList<Tweet> ParseJson() throws IOException, 
	org.json.simple.parser.ParseException, JSONException{
		
		
		
		/*BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(path), "UTF8"));
		StringBuffer stringBuff = new StringBuffer();
		String string = null;
		while((string = br.readLine()) != null) {
			stringBuff.append(string + System.getProperty("line.separator"));
		}
		String stri = stringBuff.toString(); 
	   */
		System.out.println(timeLine);
	    JSONArray array = new JSONArray(timeLine);
	    ArrayList<Tweet> listTweet = new ArrayList<Tweet>();
	    for (int i=0;i<array.length();i++){
	    	JSONObject jsonObject = array.getJSONObject(i);
	    	String createdAt = jsonObject.getString("created_at");
	    	String text = jsonObject.getString("text");
	    	
	    	JSONObject jsonObject2 = jsonObject.getJSONObject("user");
	    	String name = jsonObject2.getString("name");
	    	String screenName = jsonObject2.getString("screen_name");
	    	String avatar = jsonObject2.getString("profile_image_url");
	    	
	    	listTweet.add(new Tweet(createdAt,text,name,screenName,avatar));
	    	/*
	    	System.out.println(createdAt);
	    	System.out.println(text);
	    	System.out.println(name);
	    	System.out.println(screenName);
	    	System.out.println(avatar);
	    	System.out.println("-------------------------");*/
	    }
		return listTweet;
	}
}