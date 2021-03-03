package GPS;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tRexGame.TRexGame;

public class USHMap {
	
	//dimensional variables
	private final int WIDTH = 1100, HEIGHT = 800;
	private int bWidth = WIDTH, bHeight = HEIGHT;
	private Image ush;
	private LocationGraph<Integer> g = new LocationGraph<Integer>();
	private int num;
	
	public USHMap() throws IOException {
		
		graphics();
				
	}
	
	public void graphics() throws IOException {
		
		//frame set up
		JFrame frame = new JFrame("Welcome to Universal Studios Hollywood!");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
		//panel set up
		JPanel myPanel = new JPanel(){
							
			//paint components
			public void paint(Graphics g) {
								
				//draw background
				g.drawImage(ush, 0, 0, bWidth, bHeight, this);					
			}
		};
						
		//the rest of the frame set up 
		frame.add(myPanel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setFocusable(true);
						
		//open images
		try {
			ush = Toolkit.getDefaultToolkit().getImage("universalStudio.png");
		} catch (Exception e) {
			System.out.println("problem opening files...");
		}
		
		PrintWriter pw = new PrintWriter(new FileWriter("universalStudioMap"));
		frame.addMouseListener(new MouseListener() {
			
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				g.addVertex(num++, x, y);
				pw.println(num + " " + x + y);
			}
			
			public void mouseReleased(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		pw.close();
	}
	
	//call the game in the main method
	public static void main(String[] args) throws IOException {
		
		new USHMap();
	}

 }
