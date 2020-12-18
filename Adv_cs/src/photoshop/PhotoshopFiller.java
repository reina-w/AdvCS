package photoshop;
// Photoshop program that can run several manipulations on 
// an image
// filler code by Mr. David

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class PhotoshopFiller extends Component {

	// the name of the output file. will be determined by which methods are called
    private String outputName;
    
    // the 2d array of colors representing the image
    private Color[][] pixels;
    
    // the width and height of the image 
    private int w,h;
    
    //the maximum pixels of the rgb value
    private int max = 255;
    

    // this method increases each color's rgb value by a given amount.
    // don't forget that rgb values are limited to the range [0,255]
    public void brighten(int amount) {
        outputName = "brightened_" + outputName;

        for(int i = 0; i < pixels.length; i++) {
			for(int j = 0; j < pixels[i].length; j++) {
				Color c = pixels[i][j];
				pixels[i][j] = new Color(
						Math.min(max, c.getRed()+amount), 
						Math.min(max, c.getGreen()+amount),
						Math.min(max, c.getBlue()+amount));
			}
		}
    }
    
    // flip an image either horizontally or vertically.
    public void flip(boolean horizontally) {
        outputName = "flipped_" + outputName;
        
        if(horizontally) {
        	for(int i = 0; i < pixels[i].length; i++) {
            	for(int j = 0; j < pixels[i].length/2; j++) {
            		Color c = pixels[i][j];
            		pixels[i][j] = pixels[i][pixels[i].length-j-1];
            		pixels[i][pixels[i].length-j-1] = c;
            	}
            }
        	
        } else {
        	for(int i = 0; i < pixels.length/2; i++) {
            	for(int j = 0; j < pixels[i].length; j++) {
            		Color c = pixels[i][j];
            		pixels[i][j] = pixels[pixels.length-i-1][j]; 
            		pixels[pixels.length-i-1][j] = c;
            	}
            }
        }
        
        
    }
    
    // negates an image
    // to do this: subtract each pixel's rgb value from 255 
    // and use this as the new value
    public void negate() {
        outputName = "negated_" + outputName;
        
        for(int i = 0; i < pixels[i].length; i++) {
        	for(int j = 0; j < pixels[i].length; j++) {
        		Color c = pixels[i][j];
        		pixels[i][j] = new Color(
        				max-c.getRed(),
        				max-c.getGreen(),
        				max-c.getBlue());
        	}
        }
    }
    
    // this makes the image 'simpler' by redrawing it using only a few colors
    // to do this: for each pixel, find the color in the list that is closest to
    // the pixel's rgb value. 
    // use this predefined color as the rgb value for the changed image.
    public void simplify() {
    
    		// the list of colors to compare to. Feel free to change/add colors
    		Color[] colorList = {Color.BLUE, Color.RED,Color.ORANGE, Color.MAGENTA,
                Color.BLACK, Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN};
        outputName = "simplified_" + outputName;
        
        // your code here
    }
    
    // optional helper method (recommended) that finds the 'distance' 
    // between two colors.
    // use the 3d distance formula to calculate
    public double distance(Color c1, Color c2) {
    	
    		// your code here
    		return 0.0;		// delete this
    }
    
    // this blurs the image
    // to do this: at each pixel, sum the 8 surrounding pixels' rgb values 
    // with the current pixel's own rgb value. 
    // divide this sum by 9, and set it as the rgb value for the blurred image
    public void blur() {
		outputName = "blurred_" + outputName;
		
		for(int i = 1; i < pixels[i].length-1; i++) {
        	for(int j = 1; j < pixels[i].length-1; j++) {
        		
        		int sumR = 0;
        		int sumG = 0;
        		int sumB = 0;
        		
        		if(i == 0 && j == 0) {
        			for(int k = 0; k <= 1; k++) {
            			for(int l = 0; l <= 1; l++) {
            				Color c = pixels[i+k][j+l];
            				sumR += c.getRed();
            				sumG += c.getGreen();
            				sumB += c.getBlue();
            			}
            		}
        		}
        		
        		for(int k = -1; k <= 1; k++) {
        			for(int l = -1; l <= 1; l++) {
        				Color c = pixels[i+k][j+l];
        				sumR += c.getRed();
        				sumG += c.getGreen();
        				sumB += c.getBlue();
        			}
        		}
        		pixels[i][j] = new Color(sumR/9, sumG/9, sumB/9);
        	}
        }
	}
    
    // this highlights the edges in the image, turning everything else black. 
    // to do this: at each pixel, sum the 8 surrounding pixels' rgb values. 
    // now, multiply the current pixel's rgb value by 8, then subtract the sum.
    // this value is the rgb value for the 'edged' image
    public void edge() {
        outputName = "edged_" + outputName;

        // your code here
    }
    
    
    // *************** DON'T MESS WITH THE BELOW CODE **************** //
    
    // feel free to check it out, but don't change it unless you've consulted 
    // with Mr. David and understand what the code's doing
    
    

    public void run() throws IOException {
    	JFileChooser fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+ "Images");
		fc.setCurrentDirectory(workingDirectory);
		fc.showOpenDialog(null);
		File my_file = fc.getSelectedFile();
		if (my_file == null)
			System.exit(-1);
		
		// reads the image file and creates our 2d array
        BufferedImage image = ImageIO.read(my_file);
        BufferedImage new_image = new BufferedImage(image.getWidth(),
                        image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        create_pixel_array(image);
		outputName = my_file.getName();
		
		// runs the manipulations determined by the user
		System.out.println("Enter the manipulations you would like to run on the image.\nYour "
				+ "choices are: brighten, flip, negate, blur, edge, or simplify.\nEnter each "
				+ "manipulation you'd like to run, then type in 'done'.");
		Scanner in = new Scanner(System.in);
		String action = in.next().toLowerCase();
		while (!action.equals("done")) {
    			try {
	    			if (action.equals("brighten")) {
	    				System.out.println("enter an amount to increase the brightness by");
	    				int brightness = in.nextInt();
	        			Method m = getClass().getDeclaredMethod(action, int.class);
	        			m.invoke(this, brightness);
	    			}
	    			else if (action.equals("flip")) {
	    				System.out.println("enter \"h\" to flip horizontally, anything else to flip vertically.");
	        			Method m = getClass().getDeclaredMethod(action, boolean.class);
	        			m.invoke(this, in.next().equals("h"));
	    			}
	    			else {
	        			Method m = getClass().getDeclaredMethod(action);
	        			m.invoke(this, new Object[0]);
	    			}
	    			System.out.println("done. enter another action, or type 'done'");
    			}
    			catch (NoSuchMethodException e) {
    				System.out.println("not a valid action, try again");
    			} catch (IllegalAccessException e) {} 
    			catch (IllegalArgumentException e) {}
    			catch (InvocationTargetException e) {}
    			
    			action = in.next().toLowerCase();
    		} 
        in.close();
        
        // turns our 2d array of colors into a new png file
        create_new_image(new_image);
        File output_file = new File("Images/" + outputName);
        ImageIO.write(new_image, "png", output_file);
    }
    
    public void create_pixel_array(BufferedImage image) {
        w = image.getWidth();
        h = image.getHeight();
        pixels = new Color[h][];
        for (int i = 0; i < h; i++) {
            pixels[i] = new Color[w];
            for (int j = 0; j < w; j++) {
                pixels[i][j] = new Color(image.getRGB(j,i));
            }
        }
    }

    public void create_new_image(BufferedImage new_image) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
            		new_image.setRGB(j, i, pixels[i][j].getRGB());
            }
        }
    }

    public static void main(String[] args) {
		new PhotoshopFiller();
	}

    public PhotoshopFiller() {
        try {
			run();
		} catch (IOException e) {
			System.out.println("Image does not exist :(");}
    }
}