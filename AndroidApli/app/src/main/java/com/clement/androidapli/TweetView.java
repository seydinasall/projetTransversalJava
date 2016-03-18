package com.clement.androidapli;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

/**
 * Created by clement on 16/03/2016.
 */
public class TweetView {
    private TextView name;
    private TextView text;
    private ImageView avatar;

    public TextView getName() {
        return name;
    }
    public void setName(TextView name) {
        this.name = name;
    }
    public TextView getText() {
        return text;
    }
    public void setText(TextView text) {
        this.text = text;
    }
    public ImageView getAvatar() {
        return avatar;
    }
    public void setAvatar(ImageView image) {
        this.avatar = image;
    }
}
