import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {

	private File json;
	private String path;
	
	public File getJson() {
		return json;
	}
	public void setJson(File json) {
		this.json = json;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public Json(File file){
		this.path = file.getAbsolutePath();
		this.json = file;
	}
	
	public ArrayList<Tweet> ParseJson() throws IOException, 
	org.json.simple.parser.ParseException, JSONException{
	
		BufferedReader br = new BufferedReader(new FileReader(this.path));
		StringBuffer stringBuff = new StringBuffer();
		String string = null;
		while((string = br.readLine()) != null) {
			stringBuff.append(string + System.getProperty("line.separator"));
		}
		String stri = stringBuff.toString(); 
	   
	    JSONArray array = new JSONArray(stri);
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
	    	
	    	System.out.println(createdAt);
	    	System.out.println(text);
	    	System.out.println(name);
	    	System.out.println(screenName);
	    	System.out.println(avatar);
	    	System.out.println("-------------------------");
	    }
		return listTweet;
	}
}