package com.clement.androidapli;

public class Tweet {

	private String createdAt;
	private String text;
	private String name;
	private String screenName;
	private String avatar;
	
	
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public Tweet(String createdAt,String text,String name,
			String screenName,String avatar){
		this.createdAt = createdAt;
		this.text = text;
		this.name = name;
		this.screenName = screenName;
		this.avatar = avatar;
	}
}
