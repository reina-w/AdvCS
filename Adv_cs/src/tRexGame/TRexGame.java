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
	private int dXloc = 100, dYloc = 500, dWidth = 140, dHeight = 150;
	private int gXloc = 0, gYloc = 630, gWidth = WIDTH, gHeight = 160;
	private int rXloc_1 = 400, rYloc_1 = 575,rWidth_1 =100, rHeight_1 = 80; 
	private int rXloc_2 = 850, rYloc_2 = 553,rWidth_2 = 120, rHeight_2 = 110; 
	private int rXloc_3 = 1250, rYloc_3 = 575,rWidth_3 = 100, rHeight_3 = 80; 
	private Image ground;
	private int curScore = 0, highScore = 0;
	private String cScore = "SCORE: " + curScore,
			hScore = "BEST: " + highScore,
			start = "TAP TO START";
	private int startSize = 50, startX = 420, startY= 400;
	private int scoreSize = 25, scoreY = 40, cScoreX = 500, hScoreX = 1000;

	private boolean startGame = false;
	
	private dinosaur d = new dinosaur(dXloc, dYloc, dWidth, dHeight);
	private rock r_1 = new rock(rXloc_1, rYloc_1, rWidth_1, rHeight_1);
	private rock r_2 = new rock(rXloc_2, rYloc_2, rWidth_2, rHeight_2);
	private rock r_3 = new rock(rXloc_3, rYloc_3, rWidth_3, rHeight_3);

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
				r_1.draw(g);
				r_2.draw(g);
				r_3.draw(g);
				
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
				r_1.move();
				r_2.move();
				r_3.move();
			}
			
			if(d.intersects(r_1) || d.intersects(r_2) || d.intersects(r_3)) { 
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
				r_1.reset();
				r_2.reset();
				r_3.reset();
					
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
