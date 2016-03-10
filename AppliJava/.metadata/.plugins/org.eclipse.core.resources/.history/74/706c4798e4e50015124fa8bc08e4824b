import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

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
		// TODO Auto-generated method stub
		return this.listTweet.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Tweet tweet = this.listTweet.get(row);
		JLabel tweetLab = null;
		switch(col) {
			case 0 :
				 tweetLab = new JLabel("<html><b>"+ 
					tweet.getName()+
						 "</b> @" + tweet.getScreenName() + "<br>" +
						 tweet.getText() + "<br>" +  
						 tweet.getCreatedAt());
		}
		return tweetLab.getText();
	}
}