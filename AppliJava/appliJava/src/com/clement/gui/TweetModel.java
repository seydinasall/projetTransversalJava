package com.clement.gui;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import com.clement.obj.Tweet;

public class TweetModel extends AbstractTableModel {

	private ArrayList<Tweet> listTweet;
	
	
	public TweetModel(ArrayList<Tweet> list) {
		listTweet = list;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method 
		return this.listTweet.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Tweet tweet = this.listTweet.get(row);
		JLabel tweetLab = null;
		switch(col) {
			case 0 :
				 tweetLab = new JLabel("<html>"+"<img src="+tweet.getAvatar()+">"
						 +"<b>"+tweet.getName()
						 +"</b> @" + tweet.getScreenName() + "<br>" +
						 tweet.getText() + "<br></div>" +  
						 tweet.getCreatedAt());
		}
		return tweetLab.getText();
	}
}