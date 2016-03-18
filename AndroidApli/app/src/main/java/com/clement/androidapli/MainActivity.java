package com.clement.androidapli;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().
                permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        ArrayList<Tweet> timeLine;
        final EditText editText;

        Api api = new Api();
        JSONArray apiReturn = api.GetTimeline();
        Json json = new Json(apiReturn);
        try {
            timeLine = json.ParseJson();
/*
            for(int i=0;i<array.size();i++){
                System.out.println(array.get(i).getName());
                System.out.println(array.get(i).getScreenName());
                System.out.println(array.get(i).getText());
                System.out.println("-----------------------------------------");
            }
*/

            ListView lv = (ListView) findViewById(R.id.listView);
            ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, timeLine);
            lv.setAdapter(adapter);


            Button button = (Button) findViewById(R.id.refresh);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click

                    Api api = new Api();
                    JSONArray apiReturn = api.GetTimeline();
                    Json json = new Json(apiReturn);

                    try {
                        ArrayList<Tweet> refresh = json.ParseJson();
                        ListView lv = (ListView) findViewById(R.id.listView);

                        ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, refresh);
                        lv.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            editText = (EditText) findViewById(R.id.search);
            Button searchButton = (Button) findViewById(R.id.searchButton);
            //search button action
            searchButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click

                    Api api = new Api();
                    String text = editText.getText().toString();
                    JSONArray apiReturn = api.searchTweets(text);
                    Json json = new Json(apiReturn);

                    try {
                        ArrayList<Tweet> search = json.ParseJson();
                        ListView lv = (ListView) findViewById(R.id.listView);

                        ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, search);
                        lv.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // send button action of your virtual keybord
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_SEND) {

                        Api api = new Api();
                        String text = editText.getText().toString();
                        JSONArray apiReturn = api.searchTweets(text);
                        Json json = new Json(apiReturn);

                        try {
                            ArrayList<Tweet> search = json.ParseJson();
                            ListView lv = (ListView) findViewById(R.id.listView);

                            ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, search);
                            lv.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        handled = true;
                    }
                    return handled;
                }
            });

            String apiAccount = api.GetAccount();
            Json jsonAccount = new Json(apiAccount);
            Account account = jsonAccount.ParseJsonAccount();
            Bitmap bitmap = getBitmapFromURL(account.getAvatar());

            ImageView userAvatar = (ImageView) findViewById(R.id.userAvatar);
            userAvatar.setImageBitmap(bitmap);

            TextView userName = (TextView) findViewById(R.id.userName);
            userName.setText(account.getName()+"  @"+account.getScreenName());




        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
