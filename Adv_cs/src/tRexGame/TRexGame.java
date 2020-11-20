package tRexGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TRexGame {
	
	private final int WIDTH = 1200, HEIGHT = 800;
	private int dXloc = 100, dYloc = 500, dWidth = 100, dHeight = 130;
	private int gXloc = 0, gYloc = 630, gWidth = WIDTH, gHeight = 160;
	private int bXloc_1 = 400, bYloc_1 = 575, bWidth_1 =100, bHeight_1 = 80; 
	private int bXloc_2 = 850, bYloc_2 = 560, bWidth_2 = 110, bHeight_2 = 95; 
	private int bXloc_3 = 1250, bYloc_3 = 575, bWidth_3 = 100, bHeight_3 = 80; 
	private Image ground;
	private int curScore = 0, highScore = 0;
	private String cScore = "SCORE: " + curScore,
			hScore = "BEST: " + highScore,
			start = "TAP TO START";
	private int startSize = 50, startX = 420, startY= 400;
	private int scoreSize = 25, scoreY = 40, cScoreX = 500, hScoreX = 1000;

	private boolean startGame = false;
	
	private dinosaur d = new dinosaur(dXloc, dYloc, dWidth, dHeight);
	private box b_1 = new box(bXloc_1, bYloc_1, bWidth_1, bHeight_1);
	private box b_2 = new box(bXloc_2, bYloc_2, bWidth_2, bHeight_2);
	private box b_3 = new box(bXloc_3, bYloc_3, bWidth_3, bHeight_3);

	public TRexGame() {
		
		//frame set up
		JFrame frame = new JFrame("Welcome to T-Rex Runner");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panel set up
		JPanel panel = new JPanel(){
			
			public void paint(Graphics g) {
				
				g.drawImage(ground, gXloc, gYloc, gWidth, gHeight, this);
				
				d.draw(g);
				b_1.draw(g);
				b_2.draw(g);
				b_3.draw(g);
				
				g.setFont(new Font("TimesRoman", Font.BOLD, startSize));
				if(!startGame) g.drawString(start, startX, startY);
				g.setFont(new Font("TimesRoman", Font.BOLD, scoreSize)); 
				g.drawString(cScore, cScoreX, scoreY);
				g.drawString(hScore, hScoreX, scoreY);
			}
		};
		
		panel.setBackground(new Color(170, 227, 221));
	
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setFocusable(true);
		
		try {
			ground = Toolkit.getDefaultToolkit().getImage("ground.png");
			
		} catch (Exception e) {
			System.out.println("problem opening files...");
		}
		
		//add key listener
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyChar() == ' ' && d.canJump()) {
					if(startGame) {
						d.jump();
					}
					startGame = true;
					
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		while(true) {
			
			if(startGame) {
				d.move();
				b_1.move();
				b_2.move();
				b_3.move();
			}
			
			if(d.intersects(b_1) || d.intersects(b_2) || d.intersects(b_3)) { 
				startGame = false;
				int reply = JOptionPane.showConfirmDialog(null, "Game Over. \n" + cScore + " \nWant to restart?", 
						"T-Rex Game", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, null);
				
				if (reply == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "See you next time!!",
							"T-Rex Game", JOptionPane.INFORMATION_MESSAGE, null);
				} else {
					highScore = curScore;
				}
				
				curScore = 0;
				d.reset();
				b_1.reset();
				b_2.reset();
				b_3.reset();
					
			}
			
			frame.getContentPane().repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
				
	}
	
	
	public static void main(String[] args) {
		new TRexGame();
	}

}
