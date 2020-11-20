package tRexGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class dinosaur extends Rectangle{

	//the default yloc of the dinosaur 
	private int yMax = 500;
	//velocity and acceleration of the jump
	private double v = 0, a = 2.2; 
	//ratio of the dimension (to fix the problem of white space around image)
	private double ratio = 1.3; 
	//image of dinosaur
	private Image dino = Toolkit.getDefaultToolkit().getImage("greenDino_2.png");
	
	//constructor
	public dinosaur(int xloc, int yloc, int width, int height) {
		
		super(xloc, yloc, width, height);
	}
	
	//draw dinosaur
	public void draw(Graphics g) {
		
		g.drawImage(dino, x, y, (int)(width*ratio), (int)(height*ratio), null);
		
	}
	
	//set up velocity when jump
	public void jump() {
		v = -30;
	}
	
	//jump upward and pull back down by gravity acceleration
	public void move() {
		y += v;
		v += a;
		if(y >= yMax) {
			v = 0;
			y = yMax;
		}
	}
	
	//the dinosaur cannot jump again unless it goes back to the ground
	public boolean canJump() {
		if (y <= yMax+10 && y >= yMax-10) return true;
		return false;
	}
	
	//reset the variables
	public void reset() {
		y = yMax;
	}
	
}
