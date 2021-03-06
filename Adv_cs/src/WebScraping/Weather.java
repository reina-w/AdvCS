package WebScraping;

import org.jsoup.Jsoup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Weather {
	
	private final int WIDTH = 500, HEIGHT = 400, T_HEIGHT = 70, BORDER = 30;
	private Color buttonBg = new Color(190, 207, 194), displayBg = new Color(225, 235, 227);
	private int blankW = 170, blankH = 20;
	private Font myFont = new Font("SansSerif", Font.BOLD, 20);
	
	
	
	public Weather() {
		
		//frame set up
		JFrame frame = new JFrame();
				
		//panel set up
		JPanel panel = new JPanel(); //main panel
		JPanel bottomPanel = new JPanel(); //display panel
		JPanel topPanel = new JPanel(); //enter information panel
				
		//panel layout
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		panel.setBorder(BorderFactory.createTitledBorder("Weather Forecast"));
		
		
		//initializes a display area, which cannot be typed into
	 	JTextArea displayArea = new JTextArea();
	 	displayArea.setBackground(displayBg);
	 	displayArea.setEditable(false);
	 	displayArea.setPreferredSize(new Dimension(WIDTH-BORDER, HEIGHT-T_HEIGHT));
	 	
	 	
	 	//add the displayArea to the drawPanel
	 	bottomPanel.add(displayArea);
	    
	 	
		//instruction
		JTextArea title = new JTextArea();
	    title.setEditable(false);
	    title.setText("    Please enter a city/town: ");
	    title.setBackground(buttonBg);
	    
		//blank
		JTextArea city = new JTextArea();
	    city.setEditable(true);
	    city.setPreferredSize(new Dimension(blankW, blankH));
	    
	    //search button
	  	JButton searchB = new JButton("Search");
	  	searchB.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	  			String[] ans = search(city.getText().toLowerCase());
	  			displayArea.setText("");
	  			for(int i=0; i<ans.length; i++) {
	  				if(ans[i] != null) {
	  					displayArea.setText(displayArea.getText()
		  						+ "\n\t" + ans[i]);
	  				}
	  				
	  			}
	  			
	  		}
	  	});
	    
	    
	    //adding JTextArea & button to the innerpanel
	    topPanel.add(title);
	    topPanel.add(city);
	    topPanel.add(searchB);
	  
	  
	    //settings of the topPanel
	    topPanel.setBackground(buttonBg);
	    topPanel.setPreferredSize(new Dimension(WIDTH, T_HEIGHT));
	    
	    
	    //settings of the bottomPanel
	    bottomPanel.setBackground(buttonBg);
	    bottomPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT-T_HEIGHT));
		
	    //add three panels to the main panel
	    panel.add(topPanel);
	    panel.add(bottomPanel);
		
		//frame set up
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		panel.setFocusable(true);
				
	}
	
	public String[] search(String loc) {
		
		String[] ans = new String[6];
		
		try {
			Document doc = Jsoup.connect("https://www.google.com/search?q=" + loc + "+weather").get();
			
			try {
				Element time = doc.getElementById("wob_dts");
				ans[0] = "Time: \t" + time.text();
				
				Element temp = doc.getElementById("wob_tm");
				ans[1] = "Temperature: \t" + temp.text() + "˚F";
				
				Element pp = doc.getElementById("wob_pp");
				ans[2] = "Precipitation: \t" + pp.text();
				
				Element hu = doc.getElementById("wob_hm");
				ans[3] = "Humidity: \t" + hu.text();
				
				Element wind = doc.getElementById("wob_ws");
				ans[4] = "Wind: \t" + wind.text();
				
				Element w = doc.getElementById("wob_dc");
				ans[5] = "Weather: \t" + w.text();
				
			} catch (NullPointerException e) {
				
				ans[0] = "Sorry, please type a correct city/town.";
			}
			
 			
		} catch (IOException e) {
			System.out.println("Couldn't connect");
			
		}
		
		return ans;
	}
	
	public static void main(String[] args) {
		
		new Weather();
		
	}

}
