import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JTable;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class ManagerApi {

	
	public String GetTweet(Json json, Api api, MainWindow window) 
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, JSONException{
		
		
		json.setJson(api.GetTimeline(api.getTwitter_acces_token(),
				api.getTwitter_acces_secret()).toString());
		
		if(window.getTabTwitter() == null){
			window.Init(json);
		}
		else{
			//window.setTabTwitter(null);
			window.setTabTwitter(new JTable(new TweetModel(window.getTwitter())));
		}
		return null;
	}
}
