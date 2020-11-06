package Graphics;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

// we will extend JPanel (a built-in Java class). A panel will 
// have the graphics capabilities we want (namely paint).
public class SimpleGraphics extends JPanel  {
	
	// constants that are predefined and won't change 
	// don't use 'magic numbers' in your code!!!

	
	// the width/height of our window - note I set this 
	// final bc I didn't allow the window size to change
	private final int width = 600, height = 800;
	private final Color bgColor = new Color(245, 237, 215);
 
	// this is where we do the graphics initializations
	public SimpleGraphics() {
		
		// the frame holds the panel. A frame is simply a container,
		// it does nothing but hold panels and other graphics tools
		JFrame frame = new JFrame();
		
		// set the window size - notice, no magic numbers!
		frame.setSize(width, height);
		frame.setBackground(bgColor);
		
		// this ends the program when the close button is pressed
		// probably always a good idea to use this
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add our customized panel to the container
		frame.add(this);
		
		// this line centers the window upon startup
		frame.setLocationRelativeTo(null);
		
		// decide whether the user can resize the window - 
		// sometimes this is good, sometimes bad.
		// if you choose to set this true, make sure to be 
		// careful with your height/width variables!!
		frame.setResizable(false);
		
		// we need to tell the computer to make your frame and 
		// its contents visible (I don't know why this is automatically
		// set to false...)
		frame.setVisible(true);
		
		// decide whether you will need focus in your program.
		// focus is the ability for the program to pay attention 
		// to just one component - for example, if you have multiple 
		// text input boxes, we need to know which box to focus on
		// at all times
		this.setFocusable(true);
		
		// get our functionality going (if we have any)
		run();
	}

	// This is what we want the code to do as the panel is open.
	public void run() {

		// note - I don't have anything besides graphics setup in
		// this code, so my program won't actually 'do' anything.
		// If I wanted to 'do something', this is where I would do that
	}
	
	// defines how to paint our panel - this is called 
	// note that I never call this directly.
	// If I want to update my original graphics display, I call repaint()
	public void paint(Graphics g) {
	
		// draw a red 50x50 rectangle - note I use 
		// fillRect(), not drawRect()
		// also note that I use magic numbers here, just 
		// because its an example - do not do this in a real project!
		
		//big orange rect
		g.setColor(new Color(227, 185, 100));
		g.fillRoundRect(220, 250, 350, 470, 90, 90);
		
		//top orange circle
		g.setColor(new Color(219, 158, 26));
		g.fillOval(380, 80, 60, 60);
		
		//top green semi circle
		g.setColor(new Color(88, 110, 58));
		g.fillArc(300, 100, 230, 150, 0, -180);
		
		//second semi circle
		g.setColor(new Color(58, 84, 50));
		g.fillArc(350, 180, 130, 120, 0, -180);
		
		//irregular piece beneathe
		g.setColor(new Color(242, 212, 177));
		g.fillRoundRect(450, 350, 60, 270, 90, 90);
		g.fillRoundRect(300, 550, 220, 80, 90, 90);
		
		//third blue semi circle
		g.setColor(new Color(202, 232, 222));
		g.fillArc(350, 300, 140, 130, 0, 180);
		
		//little grey thing
		g.setColor(new Color(143, 166, 165));
		g.fillRoundRect(400, 400, 70, 30, 30, 30);
		
		//middle red circle
		g.setColor(new Color(214, 140, 101));
		g.fillOval(360, 450, 130, 130);
		
		//black quarter circle
		g.setColor(Color.BLACK);
		g.fillArc(0, 520, 640, 520, 90, 90);
		
		//orange circle on top layer
		g.setColor(new Color(227, 185, 100));
		g.fillOval(280, 660, 80, 80);
		
		//green arc
		g.setColor(new Color(56, 66, 54));
		g.fillArc(60, 130, 180, 200, 0, 180);
		g.setColor(bgColor);
		g.fillArc(80, 150, 140, 170, 0, 180);
		
		//left orange circle
		g.setColor(new Color(219, 147, 59));
		g.fillOval(100, 280, 90, 90);
		
		//red semi circle
		g.setColor(new Color(214, 140, 101));
		g.fillArc(80, 345, 150, 100, 0, -180);
		
		//grey semi circle
		g.setColor(new Color(209, 203, 194));
		g.fillArc(85, 345, 150, 200, 0, -180);
		
		//big red triangle
		g.setColor(new Color(196, 87, 77));
		g.fillArc(350, 600, 200, 150, 225, 90);
		g.fillArc(350, 600, 200, 150, -45, 90);
		
		// there are tons of graphics drawing methods - check them out!
	}
	
	// very simple main method - create our graphics object
	public static void main(String[] args) {
		new SimpleGraphics(); 
	}
}


