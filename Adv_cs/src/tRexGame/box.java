package tRexGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class box extends Rectangle{
	
	private int xMax = 1200;
	private double v = 10;
	private double dv = 0.005;
	private int x_i = x;
	private Image rock = Toolkit.getDefaultToolkit().getImage("box.png");
	
	public box(int xloc, int yloc, int width, int height) {
		
		super(xloc, yloc, width, height);
	}
	
	public void draw(Graphics g) {
		g.drawImage(rock, x, y, width, height, null);
	}
	
	public void move() {
		x -= v;
		v += dv;
		if(x <= -20) x = xMax;
	}
	
	public void reset() {
		x = x_i;
	}
}
