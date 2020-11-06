package GEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Text extends Shape {
	
	private int pixel = 15;

	public Text(int x, int y, int w, int h, Color c) {
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
		g.setColor(c);
		g.drawString(input, x, y);
		g.setFont(new Font("TimesRoman", Font.PLAIN, textSize));
		
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		int len = input.length();
		int textBox = len*pixel;
		if(x >= this.x && x <= (this.x + textBox)
				&& y <= this.y && y >= this.y - textSize)
			return true;
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

}
