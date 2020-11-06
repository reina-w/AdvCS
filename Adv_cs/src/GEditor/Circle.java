package GEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Circle extends Shape{

	public Circle(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
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
			g.drawOval(x-(int)(width/2), y-(int)(width/2), width, width);
			
		} else if(shadow) {
			g.setColor(Color.gray);
			g.fillOval(x-(int)(width/2)+trans, y-(int)(width/2)-trans, width, width);
			g.setColor(c);
			g.fillOval(x-(int)(width/2), y-(int)(width/2), width, width);
			
		} else {
			g.setColor(c);
			g.fillOval(x-(int)(width/2), y-(int)(width/2), width, width);
		}
		
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		int dis = (int)(Math.sqrt(Math.pow(this.x-x, 2)+ Math.pow(this.y-y, 2)));
		if(dis >= 0 && dis <= width/2) return true;
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		int xlen = x2-x1;
		int ylen = y2-y1;
		
		width = 2*(int)(Math.sqrt(Math.pow(xlen, 2)+ Math.pow(ylen, 2)));
	}

}
