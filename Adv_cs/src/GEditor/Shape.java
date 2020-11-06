package GEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;

public abstract class Shape {

	protected int x, y, width, height;
	protected Color c;
	protected String input;
	protected int textSize;
	protected int lineWeight;
	protected boolean shadow;
	protected boolean empty;
	protected int trans = 5;
	
	public Shape(int x,int y, int w, int h, Color c) {
		this.x = x; this.y = y;
		width = w; height = h;
		this.c = c;
	}
	
	public void move(int x1, int y1, int x2, int y2) {
		x += x2-x1; y += y2-y1;
	}
	
	public abstract Shape copy();
	public abstract void draw(Graphics g);
	public abstract boolean isOn(int x, int y);
	public abstract void resize(int x1, int y1, int x2, int y2);
}