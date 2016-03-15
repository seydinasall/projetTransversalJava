package com.clement.seydina.Programme;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import com.clement.seydina.Api.Api;
import com.clement.seydina.GUI.MainWindow;
import com.clement.seydina.Object.Json;

public class main {

	public static void main(String[] args) 
			throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ParseException, JSONException {
		// TODO Auto-generated method stub

		Api api = new Api();
		JSONArray apiReturn = api.GetTimeline();
		Json json = new Json(apiReturn);
		MainWindow window = new MainWindow(json);  
	}
}
