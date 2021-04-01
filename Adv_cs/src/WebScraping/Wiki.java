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
	
	private final int WIDTH = 800, HEIGHT = 700, T_HEIGHT = 70, BORDER = 30;
	private Color buttonBg = new Color(190, 207, 194), displayBg = new Color(225, 235, 227);
	private int blankW = 170, blankH = 20;
	private Font myFont = new Font("SansSerif", Font.BOLD, 20);
	
	
	
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
	 	displayArea.setPreferredSize(new Dimension(WIDTH-BORDER, HEIGHT-T_HEIGHT));
	 	displayArea.setLineWrap(true);
	 	displayArea.setWrapStyleWord(true);
	 	
	 	
	 	// been displayed throughout the program
	 	JScrollPane scroll = new JScrollPane (displayArea);
	 	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	 	scroll.setPreferredSize(new Dimension(WIDTH-BORDER,HEIGHT-T_HEIGHT));
	 	bottomPanel.add(scroll);
	 	
	 	
	 	//add the displayArea to the drawPanel
	 	bottomPanel.add(displayArea);
	    
	 	
		//instruction
		JTextArea title = new JTextArea();
	    title.setEditable(false);
	    title.setText("    Enter a name: ");
	    title.setBackground(buttonBg);
	    
		//blank
		JTextArea name = new JTextArea();
	    name.setEditable(true);
	    name.setPreferredSize(new Dimension(blankW, blankH));
	    
	    //search button
	  	JButton searchB = new JButton("Search");
	  	searchB.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
//	  			ArrayList<String> ans = search(name.getText());
//	  			displayArea.setText("");
//	  			for(int i=0; i<ans.size(); i++) {
//	  				displayArea.setText(displayArea.getText()
//		  						+ "\n\t" + ans.get(i));
//	  				
//	  			}
	  		
	  		}
	  	});
	    
	    
	    //adding JTextArea & button to the innerpanel
	    topPanel.add(title);
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
	
	public ArrayList<String> search(String n) {
		
		ArrayList<String> ans = new ArrayList<>();
		n = n.replaceAll(" ", "_");
		
		try {
			Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + n).get();
			Elements paragraphs = doc.getElementsByTag("p");
			for(Element p : paragraphs) {
				ans.add(p.text());
				System.out.println(p.text());
			}
			
 			
		} catch (IOException e) {
			System.out.println("Couldn't connect");
			
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		new Wiki();
		
	}

}
