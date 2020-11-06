package tRexGame;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TRexGame {
	
	private final int WIDTH = 1000, HEIGHT = 800;
	
	public TRexGame() {
		//frame set up
		JFrame frame = new JFrame();
				
		//panel set up
		JPanel panel = new JPanel();
				
		//panel layout
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),"T-Rex Runner"));
		
		//frame set up
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		panel.setFocusable(true);
				
	}
	
	public static void main(String[] args) {
		new TRexGame();
	}

}
