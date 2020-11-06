package GEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rectangle extends Shape {

	public Rectangle(int x, int y, int w, int h, Color c) {
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
		// TODO Auto-generated method stub
		if(empty) {
			g.setColor(c);
			g.drawRect(x, y, width, height);
			
		} else if(shadow) {
			g.setColor(Color.gray);
			g.fillRect(x+trans, y-trans, width, height);
			g.setColor(c);
			g.fillRect(x, y, width, height);
			
		} else {
			g.setColor(c);
			g.fillRect(x, y, width, height);
		}
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		if(x >= this.x && x <= (this.x + width)
				&& y >= this.y && y <= this.y + height)
			return true;
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		if(x1 <= x2) {
			x = x1;
			width = x2 - x1;
		} else {
			x = x2;
			width = x1 - x2;
		}
		
		if(y1 <= y2) {
			y = y1;
			height = y2 - y1;
		} else {
			y = y2;
			height = y1 - y2;
		}
		
	}

}
