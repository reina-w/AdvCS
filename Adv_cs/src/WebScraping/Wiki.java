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
import java.util.ArrayList;

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


public class Wiki {
	
	//class variables 
	private final int WIDTH = 800, HEIGHT = 700, T_HEIGHT = 70, BORDER = 30;
	private Color buttonBg = new Color(190, 207, 194), displayBg = new Color(225, 235, 227);
	private int blankW = 170, blankH = 20;
	private String myTab = "          "; //a helper variable for better formatting
	
	
	public Wiki() {
		
		//frame set up
		JFrame frame = new JFrame();
				
		//panel set up
		JPanel panel = new JPanel(); //main panel
		JPanel bottomPanel = new JPanel(); //display panel
		JPanel topPanel = new JPanel(); //enter information panel
				
		//panel layout
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		panel.setBorder(BorderFactory.createTitledBorder("Wikipedia"));
				
				
		//initializes a display area, which cannot be typed into
	 	JTextArea displayArea = new JTextArea();
	 	displayArea.setBackground(displayBg);
	 	displayArea.setEditable(false);
	 	displayArea.setLineWrap(true);
	 	displayArea.setWrapStyleWord(true);
	 	
	 	
	 	// all information can be displayed throughout the program
	 	JScrollPane scroll = new JScrollPane (displayArea);
	 	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	 	scroll.setPreferredSize(new Dimension(WIDTH-BORDER,HEIGHT-T_HEIGHT));
	 	bottomPanel.add(scroll);
	    
	 	
		//instruction
		JTextArea topic = new JTextArea();
	    topic.setEditable(false);
	    topic.setText("    Enter a topic: ");
	    topic.setBackground(buttonBg);
	    
		//blank
		JTextArea name = new JTextArea();
	    name.setEditable(true);
	    name.setPreferredSize(new Dimension(blankW, blankH));
	    
	    //search button
	  	JButton searchB = new JButton("Search");
	  	searchB.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	
	  			//get all text from search method
	  			ArrayList<String> ans = search(name.getText());
	  			displayArea.setText("");
	  			for(int i=0; i<ans.size(); i++) {
	  				
	  				//no need for two spaced lines if it is the first element
	  				if(i==0) displayArea.setText(displayArea.getText()
	  						+ myTab + ans.get(i));
	  				//line spacing & tab for better formatting
	  				else displayArea.setText(displayArea.getText()
		  						+ "\n\n" + myTab + ans.get(i));
	  				
	  			}
	  		
	  		}
	  	});
	    
	    //adding JTextArea & button to the innerpanel
	    topPanel.add(topic);
	    topPanel.add(name);
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
	
	//search method for getting information
	public ArrayList<String> search(String n) {
		
		ArrayList<String> ans = new ArrayList<>();
		
		//url format
		n = n.replaceAll(" ", "_");
		
		//the common words that appear when there are multiple possible definitions
		String w1 = "most commonly refers to:";
		String w2 = "may refer to:";
		
		try {
	
			Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + n).get();
			Element body = doc.select("div#bodyContent").first();
			Elements paragraphs = doc.getElementsByTag("p");
			
			boolean cando = true;
			
			for(int i=0; i<=2; i++) {
				
				//when there are multiple definitions
				if(paragraphs.get(i).text().contains(w1) 
						|| paragraphs.get(i).text().contains(w2)) {
					String s1 = "Your topic may refer to the following definitions:";
					ans.add(s1);
					
					int cnt = 1;
					
					//get the div which contains the useful urls
					body = doc.getElementsByClass("mw-parser-output").first();
					for(Element ch : body.children()) {
						if(ch.is("ul")) {
							//get all the links
							for(Element link : ch.select("a")) {
								ans.add("(" + cnt + ") " + link.text());
								cnt++;
							}
							
						}
						//print out around first 10 links 
						//but it depends on how many links in each "ul"
						if(cnt >= 10) break;
					}
					
					//instructions
					String s2 = "If one of the above is what you are looking for, "
							+ "please copy and paste the correct format into the blank. ";
					String s3 = "If not, please try to phrase the topic in a more detailed format. "
							+ "Thank you!";
					ans.add(s2);
					ans.add(s3);
					cando = false;
				}
				if(!cando) break;
			}
			
			//when the link directs to the correct page
			if(cando) {
				
				for(Element para : paragraphs) {
					ans.add(para.text());
				}
			}
 			
		} catch (IOException e) {
			System.out.println("Couldn't connect");
		}
		
		return ans;
	}
	
	
	public static void main(String[] args) {
		
		new Wiki();
		
	}

}
