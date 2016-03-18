package com.clement.gui;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import com.clement.api.Api;
import com.clement.obj.Json;
import com.clement.obj.Tweet;


public class MainWindow extends JFrame {
	
	private JFrame frame;
    private JPanel panelHead;
    private ArrayList<Tweet> twitter;
    private JTable tabTwitter;
	private JButton refresh;
	private JTextField jtf;
	private JLabel jl;


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

	public void AddAccountInfo() throws JSONException{
	    	Api api = new Api();
	    	Json json = new Json();
	    	jl = json.ParseJsonAccount(api.getAccount());
	    	panelHead.add(jl,BorderLayout.CENTER);
	}
	
	public MainWindow() throws IOException, ParseException, JSONException
    {
    	frame = new JFrame();
    	panelHead = new JPanel();
    	refresh = new JButton("Refresh \n Timeline");
    	jtf = new JTextField("search");
    	Init();
	}
    
    public void Init() throws IOException, ParseException, JSONException{
    	
    	Api api = new Api();
		Json json = new Json();
    	twitter = json.ParseJson(api.getTimeline());
    	
    	//init frame
    	frame.setPreferredSize(new Dimension(600, 500));
        frame.setLayout(new BorderLayout());
       
        //init panelHead
        panelHead.setBackground(Color.GRAY);
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
					jsonRefresh = new Json();
					
					ArrayList<Tweet> twitterUpdate = jsonRefresh.ParseJson(api.getTimeline());
					tabTwitter.setModel(new TweetModel(twitterUpdate));
			        tabTwitter.getColumnModel().getColumn(0).setHeaderValue("Timeline");       

					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
        
        panelHead.add(refresh,BorderLayout.WEST);

        //Button search action
        Font police = new Font("Arial", Font.BOLD, 12);
        jtf.setFont(police);
        jtf.setPreferredSize(new Dimension(150, 30));
        
        jtf.addActionListener(new ActionListener(){
        	
        	//refresh JTable        	
			public void actionPerformed(ActionEvent event) {
					
				Api api = new Api();
				Json jsonSearch;
				try {
					
					jsonSearch = new Json();
					
					ArrayList<Tweet> twitterSearch = jsonSearch.ParseJson(api.searchTweets(jtf.getText()));
					tabTwitter.setModel(new TweetModel(twitterSearch));
			        tabTwitter.getColumnModel().getColumn(0).setHeaderValue("Search");       

					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
        
        panelHead.add(jtf,BorderLayout.EAST);

        AddAccountInfo();
        
        frame.pack();
        frame.setVisible(true);
    }
    
   
}
