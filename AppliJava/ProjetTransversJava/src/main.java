import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class main {

	public static void main(String[] args) throws KeyManagementException, 
		NoSuchAlgorithmException, IOException, ParseException, JSONException  {
		// TODO Auto-generated method stub
	
		Api api = new Api();
		Json json = new Json(api);
		MainWindow window = new MainWindow();  

		ManagerApi managerTimeline = new ManagerApi();
		managerTimeline.GetTweet(json,api,window);
	}

}
