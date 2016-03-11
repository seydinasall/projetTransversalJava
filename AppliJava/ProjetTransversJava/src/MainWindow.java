import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class MainWindow extends JFrame {
	
	private JFrame frame;
    private JPanel panelHead;
    private ArrayList<Tweet> twitter;
    private JTable tabTwitter;
	private JButton refresh;

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

	public MainWindow() throws IOException, ParseException, JSONException
    {
    	frame = new JFrame();
    	panelHead = new JPanel();
    	refresh = new JButton("Refresh");
	}
    
    public void Init(Json json) throws IOException, ParseException, JSONException{
    	
    	twitter = json.ParseJson();
    	
    	frame.setPreferredSize(new Dimension(600, 500));
        frame.setLayout(new BorderLayout());
       
        panelHead.setBackground(Color.blue);
        frame.add(panelHead, BorderLayout.NORTH);
        panelHead.setSize(new Dimension(600, 50));
        panelHead.setOpaque(true);
        panelHead.setLayout(new BorderLayout());        
       

  
        tabTwitter = new JTable(new TweetModel(twitter));
        tabTwitter.setRowHeight(100);
        tabTwitter.getColumnModel().getColumn(0).setHeaderValue("Timeline");       
        JScrollPane scroll = new JScrollPane(tabTwitter);
        frame.add(scroll);
        
        
        
        refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
					
				Api api = new Api();
				
				Json jsonRefresh;
				try {
					jsonRefresh = new Json(api);
					
					ArrayList<Tweet> twitterUpdate = jsonRefresh.ParseJson();
					//tabTwitter = new JTable(new TweetModel(tweets));
					tabTwitter.setModel(new TweetModel(twitterUpdate));
					//panelFond.repaint();
				} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        panelHead.add(refresh,BorderLayout.WEST);

        frame.pack();
        frame.setVisible(true);
    }
}
