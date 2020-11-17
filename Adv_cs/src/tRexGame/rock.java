package tRexGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class rock extends Rectangle {
	
	private int xloc, yloc, width, height;
	private int xMax = 1200;
	private int v = 5;
	private Image rock = Toolkit.getDefaultToolkit().getImage("rock.png");
	
	public rock(int xloc, int yloc, int width, int height) {
		
		super(xloc, yloc, width, height);
	}
	
	public void draw(Graphics g) {
		g.drawImage(rock, x, y, width, height, null);
	}
	
	public void move() {
		x -= v;
		if(x <= 0) x = xMax;
	}
}
