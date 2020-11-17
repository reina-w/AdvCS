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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TRexGame {
	
	private final int WIDTH = 1200, HEIGHT = 800;
	private int dXloc = 60, dYloc = 500, dWidth = 180, dHeight = 184;
	private int gXloc = 0, gYloc = 630, gWidth = WIDTH, gHeight = 160;
	private Image dino, ground, rock;
	private String cScore = "SCORE: 0000",
			hScore = "BEST: 0000",
			start = "TAP TO START";
	private int startSize = 50, startX = 420, startY= 400;
	private int scoreSize = 25, scoreY = 40, cScoreX = 500, hScoreX = 1000;
	private dinosaur d = new dinosaur(dXloc, dYloc, dWidth, dHeight);

	public TRexGame() {
		
		//frame set up
		JFrame frame = new JFrame("Welcome to T-Rex Runner");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panel set up
		JPanel panel = new JPanel(){
			
			public void paint(Graphics g) {
				
				g.drawImage(ground, gXloc, gYloc, gWidth, gHeight, this);
				g.drawImage(dino, dXloc, dYloc, dWidth, dHeight, this);
				g.drawImage(rock, 400, 530, 250, 200, this);
				g.drawImage(rock, 800, 558, 200, 150, this);
				
				g.setFont(new Font("TimesRoman", Font.BOLD, startSize)); 
				g.drawString(start, startX, startY);
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
			dino = Toolkit.getDefaultToolkit().getImage("greenDino.png");
			ground = Toolkit.getDefaultToolkit().getImage("ground.png");
			rock = Toolkit.getDefaultToolkit().getImage("rock.png");
			
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
				if (e.getKeyChar() == ' ') d.jump();
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		while(true) {
			
			d.move();
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
