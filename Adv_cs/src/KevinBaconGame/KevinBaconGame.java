package KevinBaconGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class KevinBaconGame {
	
	//the graph has actors as its vertices, and movies as its edges <actor, movie>
	LabeledGraph<String, String> myGraph = new LabeledGraph<String, String>();

	//initiate maps for actors and movies
	HashMap<Integer, String> actor = new HashMap<Integer, String>();
	HashMap<Integer, String> movie = new HashMap<Integer, String>();
	
	//graphics variables
	private final int WIDTH = 800, HEIGHT = 700, T_HEIGHT = 70, B_HEIGHT = 100, BORDER = 40;
	private int blankW = 170, blankH = 20;
	private Color buttonBg = new Color(190, 207, 194), displayBg = new Color(225, 235, 227);
	private JPanel panel, topPanel, drawPanel, bottomPanel;
	private JPanel[] bArr = new JPanel[2];
	private JTextArea displayArea;
	
	public KevinBaconGame() throws IOException{
		
		GraphSetUp();
		Graphics();
		
	}
	
	public void GraphSetUp() throws IOException {
		
		//read in the actor file
		BufferedReader br = new BufferedReader(new FileReader("actors.txt"));
				
		String s = "";
				
		//build the map for actor names and their numbers
		while((s = br.readLine()) != null) {
					
			String[] words = s.split("~");
			int num = Integer.parseInt(words[0]);
			actor.put(num, words[1]);
			myGraph.addVertex(words[1]);
//			testing
//			System.out.println(num);
//			System.out.println(words[1]);
		}
				
		//read in the movie file
		br = new BufferedReader(new FileReader("movies.txt"));
				
		s = "";
				
		//build the map for actor names and their numbers
		while((s = br.readLine()) != null) {
							
			String[] words = s.split("~");
			int num = Integer.parseInt(words[0]);
			movie.put(num, words[1]);
//			testing
//			System.out.println(num);
//			System.out.println(words[1]);
		}
				
		//read in the movie-actor file
		br = new BufferedReader(new FileReader("movie-actors.txt"));
						
		s = "";
				
		int curMov = 0;
		int mov = 0;
		int act = 0;
		
		//actors from the same movie
		ArrayList<Integer> sameMov = new ArrayList<>();
				
		//counter for the number of edges
		int cnt = 0;
				
		//connect the edges
		while((s = br.readLine()) != null) {
					
			String[] nums = s.split("~");
			mov = Integer.parseInt(nums[0]);
			act = Integer.parseInt(nums[1]);
					
			//the first movie
			if(curMov == 0) {
				curMov = mov;
				sameMov.add(act);
						
			//if the current movie is the same as the previous one
			} else if(curMov == mov) {
				sameMov.add(act);
						
			//if the current movie is not the same as the previous one
			} else if(curMov != mov) {
						
				//run through the all list of actors in the same movie
				//and connect them with edges
				for(int i=0; i<sameMov.size(); i++) {
					for(int j=i+1; j<sameMov.size(); j++) {
						myGraph.connect(actor.get(sameMov.get(i)), 
							actor.get(sameMov.get(j)), movie.get(mov));
						cnt++;
					}
				}
				//set the current movie to the common movie variable
				curMov = mov;
				//clear the arraylist
				sameMov.clear();
				//add the actor 
				sameMov.add(act);
			}
					
					
		}
				
		//add the last movie and its actors that was ignored in the while loop
		for(int i=0; i<sameMov.size(); i++) {
			for(int j=i+1; j<sameMov.size(); j++) {
				myGraph.connect(actor.get(sameMov.get(i)), 
						actor.get(sameMov.get(j)), movie.get(mov));
				cnt++;
			}
		}
				
//		test the number of edges in the graph
//		System.out.println(cnt);

//		test BFS
//		System.out.println(myGraph.BFS("Sam Worthington", "Jason Whyte"));
		
	}
	
	
	public void Graphics() {
		
		//frame set up
		JFrame frame = new JFrame();
				
		//panel set up
		JPanel panel = new JPanel(); //main panel
		JPanel topPanel = new JPanel(); //actor name input panel
		JPanel drawPanel = new JPanel(); //the display panel
		JPanel bottomPanel = new JPanel(); //buttons panel
				
		//panel layout
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		panel.setBorder(BorderFactory.createTitledBorder("Kevin-Bacon Game"));
		
		//actor 1 title
		JTextArea title1 = new JTextArea();
	    title1.setEditable(false);
	    title1.setText("    Actor #1: ");
	    title1.setBackground(buttonBg);
	    
		//actor 1 blank
		JTextArea actor1 = new JTextArea();
	    actor1.setEditable(true);
	    actor1.setPreferredSize(new Dimension(blankW, blankH));
	    
	    //actor 2 title
	  	JTextArea title2 = new JTextArea();
	  	title2.setEditable(false);
	  	title2.setText("    Actor #2: ");
	  	title2.setBackground(buttonBg);
	  	
	  	//actor 2 blank
	    JTextArea actor2 = new JTextArea();
	    actor2.setEditable(true);
	    actor2.setPreferredSize(new Dimension(blankW, blankH));
	    
	    //adding JTextArea to the innerpanel
	    topPanel.add(title1);
	    topPanel.add(actor1);
	    topPanel.add(title2);
	    topPanel.add(actor2);
	    
	    //initializes a display area, which cannot be typed into
	 	displayArea = new JTextArea();
	 	displayArea.setBackground(displayBg);
	 	displayArea.setEditable(false);
	 	displayArea.setPreferredSize(new Dimension(WIDTH-BORDER, HEIGHT-T_HEIGHT-B_HEIGHT));
	 	
	 	//add the displayArea to the drawPanel
	 	drawPanel.add(displayArea);
	    
	    //connection button
	  	JButton connectionB = new JButton("Actors' Connection");
	  	connectionB.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	  			findConnection(actor1.getText(), actor2.getText());
	  		}
	  	});
	  	
	  	//common movie button
	  	JButton commonMovieB = new JButton("Actors' Common Movies");
	  	commonMovieB.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	  			findCommonMovies(actor1.getText(), actor2.getText());
	  		}
	  	});
	  	
	  	//common actor button
	  	JButton commonActorB = new JButton("Actors' Common Colleagues");
	  	commonActorB.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	  			findCommonActors(actor1.getText(), actor2.getText());
	  		}
	  	});
	  	
	  	//furthest actor button
	  	JButton furthestActorB = new JButton("Furthest Related Actor");
	  	furthestActorB.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	  			findFurthest(actor1.getText(), actor2.getText());
	  		}
	  	});
	  	
	  	
	  	//adding buttons to the bottompanel with two rows
	  	bArr[0] = new JPanel();
	  	bArr[0].setBackground(buttonBg);
	  	bArr[0].add(connectionB);
	  	bArr[0].add(commonMovieB);
	  	
	  	bArr[1] = new JPanel();
	  	bArr[1].setBackground(buttonBg);
	  	bArr[1].add(commonActorB);
	  	bArr[1].add(furthestActorB);
	  	
	  	bottomPanel.add(bArr[0]);
	  	bottomPanel.add(bArr[1]);
	  
	    //settings of the topPanel
	    topPanel.setBackground(buttonBg);
	    topPanel.setPreferredSize(new Dimension(WIDTH, T_HEIGHT));
	    
	    //settings of the drawPanel
	    drawPanel.setBackground(displayBg);
	    drawPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT-T_HEIGHT-B_HEIGHT));
	    
	    //settings of the topPanel
	    bottomPanel.setBackground(buttonBg);
	    bottomPanel.setPreferredSize(new Dimension(WIDTH, B_HEIGHT));
		
	    //add three panels to the main panel
	    panel.add(topPanel);
	    panel.add(drawPanel);
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
	
	//change to proper format
	public String toPF(String a) {
		
		//get the first and last name
		String[] parts = a.split(" ");
		
		//capitalize the first letter of each name
		for(int i=0; i<parts.length; i++) {
			parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1).toLowerCase();
		}
		
		//put the names together
		String newA = "";
		for(int i=0; i<parts.length; i++) {
			newA += parts[i] + " ";
		}
		return newA.substring(0, newA.length()-1);
	}
	
	//find the path to connect the two actors
	public void findConnection(String a, String b) {
		
		try {
			
			//when the two actors are different
			if(!toPF(a).equals(toPF(b))) {
				
				//use BFS algorithm
				ArrayList<Object> path = myGraph.BFS(toPF(a), toPF(b));
				
				//the number of edges in the path
				int num = (path.size()-1)/2;
				
				//connectivity number
				displayArea.setText("The connectivity number between " 
				+ path.get(0) + " and " + path.get(path.size()-1) + " is " + num + ".\n\n");
				
				//print out the path
				for(int i=0; i<=path.size()-3; i+=2) {
					displayArea.setText(displayArea.getText() + "\n" + path.get(i) + 
							" was in " + path.get(i+1) + " with " + path.get(i+2) + ".\n\n");
				}
			
			//when the two actors are the same person
			} else {
				displayArea.setText("The two actors are the same person.");
			}
			
		} catch (Exception e){
			
			//when there is a blank entry
			if(a.length() == 0|| b.length() == 0) {
				displayArea.setText("Please enter both actors' names.");
				
			//wrong spelling / the actor does not exist...
			} else {
				displayArea.setText("Sorry, the connection is not found. "
						+ "\n" + "Please check whether your entries are valid "
						+ "or the actors may not exist in the game data.");
			}
		}
		
	}
	
	//find the common movies the two actors share
	public void findCommonMovies(String a, String b) {
		
		try {
			
			//when the actors do not have movies in common
			if(myGraph.getEdges(toPF(a), toPF(b)).isEmpty()) {
				displayArea.setText(toPF(a) + " and " + toPF(b) + " have not collaborated in any movies yet.");
				
			//when the two actors are different 
			} else if(!toPF(a).equals(toPF(b))){
				displayArea.setText(toPF(a) + " and " + toPF(b) + " are both in the following movie(s): ");
				
				//get each movie
				for(String movie : myGraph.getEdges(toPF(a), toPF(b))) {
					displayArea.setText(displayArea.getText() + "\n\n" + movie);
				}
				
			//when the two actors are the same person
			} else {
				displayArea.setText("The two actors are the same person.");
			}
			
		} catch (Exception e) {
			
			//when there is a blank entry
			if(a.length() == 0|| b.length() == 0) {
				displayArea.setText("Please enter both actors' names.");
				
			//wrong spelling / the actor does not exist...
			} else {
				displayArea.setText("Sorry, the collaboration is not found. "
						+ "\n" + "Please check whether your entries are valid."
						+ "or the actors may not exist in the game data.");
			}
		}
		
	}
	
	//find the common actors the two actors have collaborated with
	public void findCommonActors(String a, String b) {
		
		try {
			
			//when the actors do not have common colleagues
			if(myGraph.getCommonPair(toPF(a), toPF(b)).isEmpty()) {
				displayArea.setText(toPF(a) + " and " + toPF(b) + " do not have common colleagues.");
			
			//when the two actors are different 
			} else if(!toPF(a).equals(toPF(b))){
				displayArea.setText(toPF(a) + " and " + toPF(b) + " have the following common colleagues: ");
				
				//if the number of common colleagues <= 10 then display all of them
				if(myGraph.getCommonPair(toPF(a), toPF(b)).size() <= 10) {
					
					for(String actor : myGraph.getCommonPair(toPF(a), toPF(b))) {
						displayArea.setText(displayArea.getText() + "\n\n" + actor);
					}
					
				//if the number > 10 then display the first ten actors in the set
				} else {
					int cnt = 0;
					for(String actor : myGraph.getCommonPair(toPF(a), toPF(b))) {
						displayArea.setText(displayArea.getText() + "\n\n" + actor);
						cnt++;
						if(cnt >= 10) break;
					}
					displayArea.setText(displayArea.getText() + " ...... ");
				}
				
			//when the two actors are the same person
			} else {
				displayArea.setText("The two actors are the same person.");
			}
			
		} catch (Exception e) {
			
			//when there is a blank entry
			if(a.length() == 0|| b.length() == 0) {
				displayArea.setText("Please enter both actors' names.");
				
			//wrong spelling / the actor does not exist...
			} else {
				displayArea.setText("Sorry, the common colleagues are not found. "
						+ "\n" + "Please check whether your entries are valid."
						+ "or the actors may not exist in the game data.");
			}
		}
		
	}
	
	//find the furthest related actor of the given actor(s)
	public void findFurthest(String a, String b) {
		
		
		try {
			
			//when entry 1 is not blank
			if(a.length() > 0) {
				
				//first actor entry
				displayArea.setText("The furthest related actor(s) of " 
						+ toPF(a) + " is/are: ");
				
				//if the number of furthest actors <= 5 then display all of them
				if(myGraph.getFurthest(toPF(a)).size() <= 5) {
					
					for(String actor : myGraph.getFurthest(toPF(a))) {
						displayArea.setText(displayArea.getText() + "\n\n" + actor);
					}
					
				//if the number > 5 then display the first ten actors in the set
				} else {
					int cnt = 0;
					for(String actor : myGraph.getFurthest(toPF(a))) {
						displayArea.setText(displayArea.getText() + "\n\n" + actor);
						cnt++;
						if(cnt >= 5) break;
					}
					displayArea.setText(displayArea.getText() + " ...... " + "\n\n\n");
				}
				
			}
			
			//when two actors are different AND entry 2 is not blank
			if(!toPF(a).equals(toPF(b)) && b.length() > 0) {
				//second actor entry
				displayArea.setText(displayArea.getText() + "The furthest related actor(s) of " 
						+ toPF(b) + " is/are: ");
				
				//if the number of furthest actors <= 5 then display all of them
				if(myGraph.getFurthest(toPF(b)).size() <= 5) {
					
					for(String actor : myGraph.getFurthest(toPF(b))) {
						displayArea.setText(displayArea.getText() + "\n\n" + actor);
					}
					
				//if the number > 5 then display the first ten actors in the set
				} else {
					int cnt = 0;
					for(String actor : myGraph.getFurthest(toPF(b))) {
						displayArea.setText(displayArea.getText() + "\n\n" + actor);
						cnt++;
						if(cnt >= 5) break;
					}
					displayArea.setText(displayArea.getText() + " ...... ");
				}
			}
			
		} catch (Exception e) {
			
			//when both entries are blank
			if(a.length() == 0 && b.length() == 0) {
				displayArea.setText("Please enter both actors' names.");
				
			//wrong spelling / the actor does not exist...
			} else {
				displayArea.setText("Sorry, the furthest related actor(s) of Actor 1 are not found. "
					+ "\n" + "Please check whether your entry are valid."
					+ "or the actors may not exist in the game data.");
			}
			
		}
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		KevinBaconGame myGame = new KevinBaconGame();
		
	}

}
