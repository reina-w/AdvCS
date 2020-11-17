package tRexGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class dinosaur extends Rectangle{

	private int dy = 5;
	private int yMin = 400, yMax = 510;
	private double v = 0, a = 0.5;
	private Image dino = Toolkit.getDefaultToolkit().getImage("greenDino.png");
	
	public dinosaur(int xloc, int yloc, int width, int height) {
		
		super(xloc, yloc, width, height);
	}
	
	public void draw(Graphics g) {
		
		g.drawImage(dino, x, y, width, height, null);
		
	}
	
	public void jump() {
		v = -3;
	}
	
	public void move() {
		y += v;
		v += a;
		if(y >= yMax) v = 0;
	}
	
}
