import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class MainWindow extends JFrame {
	
	private JFrame frame;
    private JPanel panelHead;
    private ArrayList<Tweet> twitter;
    private JTable tabTwitter;
	private JButton refresh;
	private JTextField jtf; 


	public JTextField getJtf() {
		return jtf;
	}

	public void setJtf(JTextField jtf) {
		this.jtf = jtf;
	}

	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public JPanel getPanelHead() {
		return panelHead;
	}

	public void setPanelHead(JPanel panelHead) {
		this.panelHead = panelHead;
	}

	public ArrayList<Tweet> getTwitter() {
		return twitter;
	}

	public void setTwitter(ArrayList<Tweet> twitter) {
		this.twitter = twitter;
	}

	public JTable getTabTwitter() {
		return tabTwitter;
	}

	public void setTabTwitter(JTable tabTwitter) {
		this.tabTwitter = tabTwitter;
	}

	public JButton getRefresh() {
		return refresh;
	}

	public void setRefresh(JButton bouton) {
		this.refresh = bouton;
	}

	public MainWindow(Json json) throws IOException, ParseException, JSONException
    {
    	frame = new JFrame();
    	panelHead = new JPanel();
    	refresh = new JButton("Refresh");
    	jtf = new JTextField("search");
    	Init(json);
	}
    
    public void Init(Json json) throws IOException, ParseException, JSONException{
    	
    	twitter = json.ParseJson();
    	
    	//init frame
    	frame.setPreferredSize(new Dimension(600, 500));
        frame.setLayout(new BorderLayout());
       
        //init panelHead
        panelHead.setBackground(Color.blue);
        frame.add(panelHead, BorderLayout.NORTH);
        panelHead.setOpaque(true);
        panelHead.setLayout(new BorderLayout());        
       
        //init tab
        tabTwitter = new JTable(new TweetModel(twitter));
        tabTwitter.setRowHeight(150);
        tabTwitter.getColumnModel().getColumn(0).setHeaderValue("Timeline");       
        JScrollPane scroll = new JScrollPane(tabTwitter);
        frame.add(scroll);
        
        
        //button refresh action 
        refresh.addActionListener(new ActionListener(){
        	
        	//refresh JTable        	
			public void actionPerformed(ActionEvent event) {
					
				Api api = new Api();
				
				Json jsonRefresh;
				try {
					String apiReturn = api.GetTimeline(api.getTwitter_acces_token(), 
							api.getTwitter_acces_secret());
					jsonRefresh = new Json(apiReturn);
					
					ArrayList<Tweet> twitterUpdate = jsonRefresh.ParseJson();
					tabTwitter.setModel(new TweetModel(twitterUpdate));
			        tabTwitter.getColumnModel().getColumn(0).setHeaderValue("Timeline");       

					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
        panelHead.add(refresh,BorderLayout.WEST);

        Font police = new Font("Arial", Font.BOLD, 12);
        jtf.setFont(police);
        jtf.setPreferredSize(new Dimension(150, 30));
        
        jtf.addActionListener(new ActionListener(){
        	
        	//refresh JTable        	
			public void actionPerformed(ActionEvent event) {
					
				Api api = new Api();
				JSONArray apiReturn = null;
				Json jsonSearch;
				try {
					apiReturn = api.searchTweets(jtf.getText(), 
							api.getTwitter_acces_token(), api.getTwitter_acces_secret());
					jsonSearch = new Json(apiReturn);
					
					ArrayList<Tweet> twitterSearch = jsonSearch.ParseJsonSearch();
					tabTwitter.setModel(new TweetModel(twitterSearch));
			        tabTwitter.getColumnModel().getColumn(0).setHeaderValue("Search");       

					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
        
        panelHead.add(jtf,BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }
}
