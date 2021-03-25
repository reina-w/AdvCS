package GPS;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GPS.LocationGraph.Edge;


public class USHMapCreate {
	
	//1. variables for the GUI
	
	//frame, panel, button dimensions
	private JFrame frame;
	private final int WIDTH = 1000, HEIGHT = 800;
	private int bHeight = 50, mWidth = WIDTH, mHeight = HEIGHT-bHeight;
	
	//background image
	private Image ush;
	
	//circle, line dimension, color
	private int cWidth = 30, lineWeight = 5; 
	private Color cColor = Color.RED; //initial color
	
	//user can draw vertexes/edges before clicking the save button
	private boolean canDraw = true;
	
	//2. variables for the Graph
	private LocationGraph<Integer> myGraph = new LocationGraph<Integer>();
	
	//the info number of each vertex
	private int num=0;
	
	//holds the info of two vertices being connected
	private ArrayList<Integer> pair = new ArrayList<>();
	
	
	//program runner
	public USHMapCreate() throws IOException {
		
		graphics();
	}
	
	//design the GUI and draw the map
	public void graphics() throws IOException {
		
		//frame set up
		frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
		//panel set up
		JPanel myPanel = new JPanel();
		JPanel mapPanel = new JPanel(){
							
			//paint components
			public void paint(Graphics g) {
								
				//draw background
				g.drawImage(ush, 0, 0, mWidth, mHeight, this);
				
				//setup color and line dimension
				g.setColor(cColor);
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(lineWeight));
				
				//get all vertices
				for(Integer v: myGraph.vertices.keySet()) {
					int x = myGraph.vertices.get(v).xLoc;
					int y = myGraph.vertices.get(v).yLoc;
		
					//draw the vertices
					g.fillOval(x-cWidth/2, y-cWidth/2, cWidth, cWidth);
					
					//get all edges and neighbors
					for(Edge e: myGraph.vertices.get(v).edges) {
						
						int x2 = e.getNeighbor(myGraph.vertices.get(v)).xLoc;
						int y2 = e.getNeighbor(myGraph.vertices.get(v)).yLoc;
						
						//draw the edges
						g.drawLine(x, y, x2, y2);
					}
					
				}
				
			}
		};
		JPanel bottomPanel = new JPanel();
		
		//panel layout
		BoxLayout boxlayout = new BoxLayout(myPanel, BoxLayout.Y_AXIS);
		myPanel.setLayout(boxlayout);
		myPanel.setBorder(BorderFactory.createTitledBorder("Welcome to Universal Studios Hollywood!"));
			
		//open images
		try {
			ush = Toolkit.getDefaultToolkit().getImage("universalStudio.png");
		} catch (Exception e) {
			System.out.println("problem opening files...");
		}
				
		PrintWriter pw = new PrintWriter(new FileWriter("universalStudioMap"));
		mapPanel.addMouseListener(new MouseListener() {
					
			public void mousePressed(MouseEvent e) {
				
				//when the map is not saved
				if(canDraw) {
					int x = e.getX();
					int y = e.getY();
					
					for(Integer v: myGraph.vertices.keySet()) {
						int xLoc = myGraph.vertices.get(v).xLoc;
						int yLoc = myGraph.vertices.get(v).yLoc;
						
						//if current click is on an existing vertex
						if(isOn(x, y, xLoc, yLoc)) {

							//add the vertex to pair
							pair.add(v);
							//when there are two vertices in the pair
							if(pair.size() == 2) {
								
								//connect the two vertices
								myGraph.connect(pair.get(0), pair.get(1));
								frame.getContentPane().repaint();
								
								//write the connection in the file
								pw.println(pair.get(0) + " " + pair.get(1));
								//empty the pair arraylist
								pair.clear();
								
							}
							return;
						} 
					}
					
					//if the current click isnnot on an existing vertex
					//then add a new vertex to the graph
					myGraph.addVertex(++num, x, y);
					
					//write the new vertex in the file
					pw.println(num + " " + x + " " + y);
					frame.getContentPane().repaint();
				}
				
			}
					
			public void mouseReleased(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		//save button
		JButton saveP = new JButton("Save");
		saveP.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//when clicked close the PrintWriter
				pw.close();
				//the user cannot draw new vertices or edges anymore
				canDraw = false;
			}
		});
		
		//panel connections and preferrences
		bottomPanel.add(saveP);
		mapPanel.setPreferredSize(new Dimension(mWidth, mHeight));
		bottomPanel.setPreferredSize(new Dimension(WIDTH, bHeight));
		myPanel.add(mapPanel);
		myPanel.add(bottomPanel);
		
		//the rest of the frame set up 
		frame.add(myPanel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setFocusable(true);
	
	}
	
	//method checking whether current position is on an exisitng vertex
	public boolean isOn(int x, int y, int xLoc, int yLoc) {
		int dis = (int)(Math.sqrt(Math.pow(xLoc-x, 2)+ Math.pow(yLoc-y, 2)));
		if(dis >= 0 && dis <= cWidth) return true;
		return false;
	}
	
	//call the game in the main method
	public static void main(String[] args) throws IOException {
		
		new USHMapCreate();
	}

 }
