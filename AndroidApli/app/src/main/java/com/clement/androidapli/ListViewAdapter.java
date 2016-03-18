package com.clement.androidapli;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clement on 15/03/2016.
 */
public class ListViewAdapter extends ArrayAdapter<Tweet> {

    //tweets est la liste des models à afficher
    public ListViewAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row,parent, false);
        }

        TweetView viewHolder = (TweetView) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetView();
            viewHolder.setName((TextView) convertView.findViewById(R.id.name));
            viewHolder.setText((TextView) convertView.findViewById(R.id.text));
            viewHolder.setAvatar((ImageView) convertView.findViewById(R.id.avatar));
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Tweet tweet = getItem(position);
        Bitmap bitmap = getBitmapFromURL(tweet.getAvatar());
        //il ne reste plus qu'à remplir notre vue
        viewHolder.getName().setText(tweet.getName()+"  @"+tweet.getScreenName());
        viewHolder.getText().setText(tweet.getText());
        viewHolder.getAvatar().setImageBitmap(bitmap);
        return convertView;
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
