package tRexGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class box extends Rectangle{
	
	//the rightmost xloc the box starts
	private int xMax = 1200;
	//the velocity of the box moving leftward
	private double v = 10;
	//the box moves faster as time goes on
	private double dv = 0.005;
	//the initial xloc and velocity
	private int x_i = x;
	private double v_i = v;
	//image of box
	private Image rock = Toolkit.getDefaultToolkit().getImage("box.png");
	
	//constructor
	public box(int xloc, int yloc, int width, int height) {
		
		super(xloc, yloc, width, height);
	}
	
	//draw the box
	public void draw(Graphics g) {
		g.drawImage(rock, x, y, width, height, null);
	}
	
	//change the xloc to move leftward
	public void move() {
		x -= v;
		v += dv;
		
		//goes to the rightmost of the panel when reaches the left end
		if(x <= -20) x = xMax;
	}
	
	//reset the variables
	public void reset() {
		x = x_i;
		v = v_i;
	}
}
