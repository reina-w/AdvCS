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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import GPS.LocationGraph.Edge;


public class USHMapRun {
	
	//1. variables for the GUI
	
	//frame, panel, button dimensions
	private JFrame frame;
	private final int WIDTH = 1000, HEIGHT = 800;
	private int bHeight = 50, mWidth = WIDTH, mHeight = HEIGHT-bHeight;
	
	//background image
	private Image ush;
	
	//circle, line dimension, color
	private int cWidth = 30, lineWeight = 5;
	private Color iColor = Color.RED, pColor = Color.BLUE; //initial color and path color
	
	//2. variables for the Graph
	private LocationGraph<Integer> myGraph = new LocationGraph<Integer>();
	
	//start/end circle info
	private int startC = -1, endC = -1;
	
	//path by dijkstras
	private ArrayList<Integer> path = new ArrayList<>();
	
	
	//program runner
	public USHMapRun() throws IOException {
		
		creatGraph();
		graphics();
				
	}
	
	//build the graph based on the file created in USHMapCreate
	public void creatGraph() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("universalStudioMap"));
		
		//read the file
		String s = "";
		while((s = br.readLine()) != null) {
			String[] s0 = s.split(" ");
			
			//length = 3 means it is a vertex with (info, x, y)
			if(s0.length == 3) {
				myGraph.addVertex(Integer.parseInt(s0[0]), 
								  Integer.parseInt(s0[1]), 
								  Integer.parseInt(s0[2]));
				
			//length = 2 means it is an edge with (info1, info2)
			} else if(s0.length == 2) {
				myGraph.connect(Integer.parseInt(s0[0]), Integer.parseInt(s0[1]));
			}
		}
	}
	
	//design the GUI and draw the map/path
	public void graphics() {
		
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
				
				//setup the line dimension
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(lineWeight));
				
				//1. paint the whole map with initial color
				g.setColor(iColor);
				
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
				
				//2. paint the path with path color
				g.setColor(pColor);
				
				//when the user clicks on a vertex as the starting point
				if(startC != -1) {
					int x = myGraph.vertices.get(startC).xLoc;
					int y = myGraph.vertices.get(startC).yLoc;
					//paint start vertex
					g.fillOval(x-cWidth/2, y-cWidth/2, cWidth, cWidth);
				}
				
				//get all vertices in the path
				for(int i=0; i<path.size(); i++) {
					int x1 = myGraph.vertices.get(path.get(i)).xLoc;
					int y1 = myGraph.vertices.get(path.get(i)).yLoc;
					//paint the vertice
					g.fillOval(x1-cWidth/2, y1-cWidth/2, cWidth, cWidth);
					
					//when the vertex is not the last one
					if(i<path.size()-1) {
						
						//get its next vertex
						int x2 = myGraph.vertices.get(path.get(i+1)).xLoc;
						int y2 = myGraph.vertices.get(path.get(i+1)).yLoc;
						//paint the edge
						g.drawLine(x1, y1, x2, y2);
					}
				}
				
			}
		};
		JPanel bottomPanel = new JPanel();
		
		//panel layout
		BoxLayout boxlayout = new BoxLayout(myPanel, BoxLayout.Y_AXIS);
		myPanel.setLayout(boxlayout);
		myPanel.setBorder(BorderFactory.createTitledBorder("Welcome to Universal Studios Hollywood!"));

		mapPanel.setPreferredSize(new Dimension(mWidth, mHeight));
		bottomPanel.setPreferredSize(new Dimension(WIDTH, bHeight));
		myPanel.add(mapPanel);
		myPanel.add(bottomPanel);
		
		//open images
		try {
			ush = Toolkit.getDefaultToolkit().getImage("universalStudio.png");
		} catch (Exception e) {
			System.out.println("problem opening files...");
		}
				
		//get the path by Dijkstras
		getPath(mapPanel);
		
		//the rest of the frame set up 
		frame.add(myPanel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setFocusable(true);
	
	}
	
	
	//get the path by Dijkstras
	public void getPath(JPanel mapPanel) {
		
		mapPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			
			//click on a vertex to set it as the starting point
			public void mousePressed(MouseEvent e) {
				
				int x = e.getX();
				int y = e.getY();
				
				for(Integer v: myGraph.vertices.keySet()) {
					int xLoc = myGraph.vertices.get(v).xLoc;
					int yLoc = myGraph.vertices.get(v).yLoc;
					
					//check whether the click is on a vertex or not
					if(isOn(x, y, xLoc, yLoc)) {
						
						//set it as the start
						startC = v;
						frame.getContentPane().repaint();
						return;
					} 
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		
		mapPanel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {}
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				for(Integer v: myGraph.vertices.keySet()) {
					int xLoc = myGraph.vertices.get(v).xLoc;
					int yLoc = myGraph.vertices.get(v).yLoc;
					
					//check whether the mouse is hovering over a vertex
					if(isOn(x, y, xLoc, yLoc)) {
						
						//when there is a start
						if(startC != -1) {
							//set current as end
							endC = v;
							//find the path
							path = myGraph.Dijkstras(startC, endC);
							frame.getContentPane().repaint();
						}
						
						return;
					} 
				}
				
			}
			
		});
	}
	
	//method checking whether current position is on an exisitng vertex
	public boolean isOn(int x, int y, int xLoc, int yLoc) {
		int dis = (int)(Math.sqrt(Math.pow(xLoc-x, 2)+ Math.pow(yLoc-y, 2)));
		if(dis >= 0 && dis <= cWidth) return true;
		return false;
	}
	
	
	//call the game in the main method
	public static void main(String[] args) throws IOException {
		
		new USHMapRun();
	}

 }
