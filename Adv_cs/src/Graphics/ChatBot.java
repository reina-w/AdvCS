package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ChatBot {
	
	private final int WIDTH = 700, HEIGHT = 720, TEXTHEIGHT = 500;
	
	private JTextArea displayarea, typearea;
	
	private boolean entered = false;
	private boolean isFirst = true;
	
	private final String[] answers = {"This is an interesting thought.", "What do you think about that?",
			"So, how do you like our talk so far?", "That's so cool!", "I'm afraid I cannot help much with that.", 
			"I'm glad to hear that.", "Love to know more about it.", "Wow you are amazing! Let's keep talking.",
			"It's totally fine.", "That's a great question."};
	
	
	public ChatBot() {
		JPanel panel = new JPanel();
		
		// default x-axis
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		// gives our panel a nice looking border and title
		panel.setBorder(BorderFactory.createTitledBorder("IQ_750"));
		
		// initializes a display area, which cannot be typed into
		displayarea = new JTextArea();
		displayarea.setBackground(new Color(235, 218, 247));
		displayarea.setEditable(false);
		
		// initializes an input area, where the user will type into
		typearea = new JTextArea();
		typearea.setBackground(new Color(245, 242, 208));
		typearea.setEditable(true);
		
		// we'll listen for when the user presses enter - when it's pressed, we'll
		// do the same thing as when the send button is pressed.
		typearea.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n')
					sendReceive();
				}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
		});
		
		// put the text fields into scroll panes so that we can see everything that's 
		// been displayed throughout the program
		JScrollPane scroll = new JScrollPane (displayarea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JScrollPane scroll2 = new JScrollPane (typearea);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		// set a "preferred" size for our scroll panels - this will not always be used, but 
		// the window will try
		scroll.setPreferredSize(new Dimension(WIDTH,TEXTHEIGHT));
		scroll2.setPreferredSize(new Dimension(WIDTH,HEIGHT-TEXTHEIGHT));
		panel.add(scroll);
		panel.add(scroll2);
		
		// create and add a listener to the button
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			
			// what we want to happen when the user clicks this button
			public void actionPerformed(ActionEvent e) {
				sendReceive();
			}
		});
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			
			// what we want to happen when the user clicks this button
			public void actionPerformed(ActionEvent e) {
				clearText();
			}
		});
		
		// create an inner container for the button and add it to our main panel
		JPanel innerPanel = new JPanel();
		innerPanel.add(sendButton);
		innerPanel.add(clearButton);
		panel.add(innerPanel);
		
		// the main container, with the usual setup
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		panel.setFocusable(true);
		
		// beginning text display
		displayarea.setText("\n Hey! I'm IQ_750. Say anything to start the conversation.");
		
		run();
	}
	
	public void sendReceive() {
		
		// if the input field has legitimate text, add it to the display field
		if (!typearea.getText().trim().equals("")) {
			displayarea.setText(displayarea.getText()+"\n\n   User0328: "+typearea.getText().trim());
			entered = true;
		}
		
		// clear out the input field
		typearea.setText("");
		
	}
	
	public void clearText() {
		
		displayarea.setText("\n\n   ChatBot: "+ "Hi again!");
	}
	
	public void run() {
			
		while (true) {
			// if there was user input, pause for a second then give a random answer
			
			if (entered) {
				
				if(isFirst) {
					try {Thread.sleep(500);}
					catch (InterruptedException e) {}
					displayarea.setText(displayarea.getText()+"\n\n   ChatBot: "+ "Hi! Nice to meet you. Let's talk!");
					isFirst = false;
					entered = false;
					
				} else {
					try {Thread.sleep(500);}
					catch (InterruptedException e) {}
					int rand = (int)(Math.random()*answers.length);
					displayarea.setText(displayarea.getText()+"\n\n   ChatBot: "+answers[rand]);
					entered = false;
				}
				
			}
			try {Thread.sleep(50);} 
			catch (InterruptedException e) {}
		}
		
	}
	
	// the usual basic main method to get our window running
	public static void main(String[] args) {
		new ChatBot();
	}
}
