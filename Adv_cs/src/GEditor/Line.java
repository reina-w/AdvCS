package GEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Line extends Shape{

	public Line(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c);
        g2.setStroke(new BasicStroke(lineWeight));
        g2.draw(new Line2D.Float(x, y, x+width, y+height));
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub

		double slope = (double) height / width;
		
		if((double)(x-this.x)/(y-this.y) - slope <= 3 &&
				(double)(x-this.x)/(y-this.y) - slope >= -3
				&& x>= this.x && x <= this.x+width
				&& y >= this.y && y <= this.y+height) return true;
		
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		width = x2-x1;
		height = y2-y1;
	}

}
