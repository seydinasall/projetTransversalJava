import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.omg.CORBA.Request;

public class main {

	public static void main(String[] args) throws IOException, ParseException, JSONException {
		// TODO Auto-generated method stub
	
		Api api = new Api();
		Json json = new Json(api);
		
		MainWindow window = new MainWindow(json);
		
	}

}
