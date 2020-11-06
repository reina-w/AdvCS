package Graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

// we will extend JPanel (a built-in Java class). A panel will 
// have the graphics capabilities we want.
public class ListenerExample {
	
	// constants that are predefined and won't change 
	// don't use 'magic numbers' in your code!!!

	
	// the width/height of our window - note I set this 
	// final bc I didn't allow the window size to change
	private final int width = 800, height = 600;
	
	// some constants and one variable to keep track of my rectangle's location/dimensions
	private final int rectHeight = 50, rectWidth = 50, speed = 3;
	private int rectX = width/2;
	private int rectY = height/2;
	
	// variable to keep track of our speed.
	private int xSpeed, ySpeed = 0;
	
	// coordinates and radius for our circle
	private int circleX, circleY;
	private final int diam = 30;
	
	// boolean to keep track of whether we should draw our circle
	private boolean drawCircle = false;
	
	// our JFrame will be an instance variable so that we can use it to repaint
	private JFrame frame ;
 
	// this is where we do the graphics initializations
	public ListenerExample() {
		
		// the frame holds the panel. A frame is simply a container,
		// it does nothing but hold panels and other graphics tools
		frame = new JFrame();
		
		// set the window size - notice, no magic numbers!
		frame.setSize(width, height);
		
		// this ends the program when the close button is pressed
		// probably always a good idea to use this
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add our customized panel to the container
		frame.add(new JPanel() {
			// defines how to paint our panel - this is called 
			// note that I never call this directly.
			// If I want to update my original graphics display, I call repaint()
			public void paint(Graphics g) {
			
				// notice - no magic numbers!
				g.setColor(Color.RED);
				g.fillRect(rectX, rectY, rectWidth, rectHeight);
				
				if (drawCircle) {
					g.setColor(Color.blue);
					g.fillOval(circleX, circleY, diam, diam);
				}
				
				// there are tons of graphics drawing methods - check them out!
			}
		});
		
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
		frame.setFocusable(true);
		
		
		// add the listeners onto our container
		frame.addKeyListener(new KeyListener() {

			// what we want to happen when a key is pressed - note that this only 
			// activates once when the key is pressed.
			// also - you'll find that Java likes certain keys more than others -
			// play around with which keys to use
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'l')
					xSpeed = speed;
				else if (e.getKeyChar() == 'j')
					xSpeed = -speed;
				else if (e.getKeyChar() == 'i')
					ySpeed = -speed;
				else if (e.getKeyChar() == 'm')
					ySpeed = speed;
			}

			// what we want to happen when the user stops pressing a key.
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == 'l' || e.getKeyChar() == 'j')
					xSpeed = 0;
				else if (e.getKeyChar() == 'i' || e.getKeyChar() == 'm')
					ySpeed = 0;
			}


			public void keyTyped(KeyEvent e) {}
		});
		frame.addMouseListener(new MouseListener() {
			// what we want to happen when the user presses with the mouse
			public void mousePressed(MouseEvent e) {
				circleX = e.getX() - diam/2;
				circleY = e.getY() - diam/2;
				drawCircle = true;
			}
			
			public void mouseReleased(MouseEvent e) {
				drawCircle = false;
			}

			// we need to have these methods since we implement the listeners, but 
			// we don't need them to have functionality
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		// get our functionality going (if we have any)
		run();
	}

	// This is what we want the code to do as the panel is open.
	public void run() {
		
		// since I have the default close operation, a while(true) condition is ok
		while (true) {
			
			// if we're changing coordinates, etc. of our graphics objects, 
			// always need to repaint!
			frame.getContentPane().repaint();
			
			// do your logic here
			rectX += xSpeed;
			rectY += ySpeed;
			
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
	// very simple main method - create our graphics object
	public static void main(String[] args) {
		ListenerExample runner = new ListenerExample(); 
	}
}


