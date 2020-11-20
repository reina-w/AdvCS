package tRexGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class rock extends Rectangle{
	
	private int xMax = 1200;
	private int v = 12;
	private int x_i = x;
	private Image rock = Toolkit.getDefaultToolkit().getImage("rock_1.png");
	
	public rock(int xloc, int yloc, int width, int height) {
		
		super(xloc, yloc, width, height);
	}
	
	public void draw(Graphics g) {
		g.drawImage(rock, x, y, width, height, null);
	}
	
	public void move() {
		x -= v;
		if(x <= -20) x = xMax;
	}
	
	public void reset() {
		x = x_i;
	}
}
