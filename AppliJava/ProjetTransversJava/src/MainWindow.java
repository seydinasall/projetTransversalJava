import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class MainWindow extends JFrame {
	
	private JFrame frame;
    private JLayeredPane lpane;
    private JPanel panelFond;
    private JPanel panelHead;
    private ArrayList<Tweet> twitter;
    private JTable tabTwitter;
    
    public MainWindow(Json json) throws IOException, ParseException, JSONException
    {
    	frame = new JFrame();
    	lpane = new JLayeredPane();
    	panelFond = new JPanel();
    	panelHead = new JPanel();
    	twitter = json.ParseJson();
    	Init();
	}
    
    public void Init() throws IOException, ParseException, JSONException{
    	frame.setPreferredSize(new Dimension(600, 500));
        frame.setLayout(new BorderLayout());
        frame.add(lpane, BorderLayout.CENTER);
        
        lpane.setBounds(0, 0, 600, 500);
        panelFond.setBackground(Color.lightGray);
        panelFond.setBounds(0, 30, 600, 450);
        panelFond.setOpaque(true);
        panelHead.setBackground(Color.blue);
        panelHead.setBounds(0, 0, 600, 50);
        panelHead.setOpaque(true);
        
        tabTwitter = new JTable(new TweetModel(twitter));
        tabTwitter.setRowHeight(100);
        JScrollPane scroll = new JScrollPane(tabTwitter);
        panelFond.add(scroll);

        
        lpane.add(panelFond, new Integer(0), 0);
        lpane.add(panelHead, new Integer(1), 0);        
        frame.pack();
        frame.setVisible(true);
    }
}
