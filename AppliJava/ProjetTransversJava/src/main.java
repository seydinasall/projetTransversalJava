import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class main {

	public static void main(String[] args) throws IOException, ParseException, JSONException {
		// TODO Auto-generated method stub

		
		
		File file = new File("Json/home.json");
		Json json = new Json(file);
		
		MainWindow window = new MainWindow(json);
	}

}
