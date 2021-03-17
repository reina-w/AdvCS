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


public class USHMap {
	
	//dimensional variables
	private final int WIDTH = 1000, HEIGHT = 800;
	private int bHeight = 50, mWidth = WIDTH, mHeight = HEIGHT-bHeight;
	private Image ush;
	private int cWidth = 30, lineWeight = 5;
	private Color cColor = Color.RED;
	private LocationGraph<Integer> myGraph = new LocationGraph<Integer>();
	private int num=0;
	private boolean canDraw = true;
	private ArrayList<Integer> pair = new ArrayList<>();
	
	public USHMap() throws IOException {
		
		graphics();
				
	}
	
	public void graphics() throws IOException {
		
		//frame set up
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
		
		//panel set up
		JPanel myPanel = new JPanel();
		JPanel mapPanel = new JPanel(){
							
			//paint components
			public void paint(Graphics g) {
								
				//draw background
				g.drawImage(ush, 0, 0, mWidth, mHeight, this);
				for(Integer v: myGraph.vertices.keySet()) {
					int x = myGraph.vertices.get(v).xLoc;
					int y = myGraph.vertices.get(v).yLoc;
		
					g.setColor(cColor);
					g.fillOval(x-cWidth/2, y-cWidth/2, cWidth, cWidth);
					
					for(Edge e: myGraph.vertices.get(v).edges) {
						
						int x2 = e.getNeighbor(myGraph.vertices.get(v)).xLoc;
						int y2 = e.getNeighbor(myGraph.vertices.get(v)).yLoc;
						
						Graphics2D g2 = (Graphics2D) g;
						g2.setStroke(new BasicStroke(lineWeight));
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
				
				if(canDraw) {
					int x = e.getX();
					int y = e.getY();
					
					for(Integer v: myGraph.vertices.keySet()) {
						int xLoc = myGraph.vertices.get(v).xLoc;
						int yLoc = myGraph.vertices.get(v).yLoc;
						

						if(isOn(x, y, xLoc, yLoc)) {

							pair.add(v);
							if(pair.size() == 2) {
								myGraph.connect(pair.get(0), pair.get(1));
								frame.getContentPane().repaint();
								pw.println(pair.get(0) + " " + pair.get(1));
								pair.clear();
								
							}
							return;
						} 
					}
					myGraph.addVertex(++num, x, y);
					pw.println(num + " " + x + " " + y);
					frame.getContentPane().repaint();
				}
				
			}
					
			public void mouseReleased(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		JButton saveP = new JButton("Save Points");
		saveP.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				pw.close();
				canDraw = false;
			}
		});
		
		
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
	
	public boolean isOn(int x, int y, int xLoc, int yLoc) {
		// TODO Auto-generated method stub
		int dis = (int)(Math.sqrt(Math.pow(xLoc-x, 2)+ Math.pow(yLoc-y, 2)));
		if(dis >= 0 && dis <= cWidth) return true;
		return false;
	}
	
	//call the game in the main method
	public static void main(String[] args) throws IOException {
		
		new USHMap();
	}

 }
