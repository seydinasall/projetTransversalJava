package com.clement.androidapli;

/**
 * Created by clement on 16/03/2016.
 */
public class Account {

    private String name;
    private String screenName;
    private String avatar;

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getScreenName() {
        return screenName;
    }
    public void setScreenName(String screen_name){
        this.screenName = screen_name;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

    public Account(String name, String screenName, String avatar){
        this.name = name;
        this.screenName = screenName;
        this.avatar = avatar;
    }
}
