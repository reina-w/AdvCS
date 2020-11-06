package GEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.ColorChooserUI;

public class GraphicsEditor {

	private final int WIDTH = 800, HEIGHT = 700, BHEIGHT = 150;
	private int xLoc =0, yLoc =0;
	private int moveShape = -1;
	private Color shapeColor = Color.black;
	private int mode = 0;
	private ArrayList<Shape> shapeList = new ArrayList<>();
	private int blankW = 60, blankH = 20;
	private JPanel[] bArr = new JPanel[3];
	
	public GraphicsEditor() {
		
		//frame set up
		JFrame frame = new JFrame();
		
		//panel set up
		JPanel panel = new JPanel();
		
		//panel layout
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		panel.setBorder(BorderFactory.createTitledBorder("Graphics Editor"));

		//buttons
		JButton circleButton = new JButton("Circle ●");
		circleButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 1;
			}
		});
		
		JButton rectButton = new JButton("Rectangle ◼");
		rectButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 2;
			}
		});
		
		JButton lineButton = new JButton("Line");
		lineButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 3;
			}
		});
		
		JButton textButton = new JButton("Text");
		textButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 4;
			}
		});
		
		JButton emptyCirButton = new JButton("Circle ◦");
		emptyCirButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 5;
			}
		});
		
		JButton emptyRecButton = new JButton("Rectangle ︎☐");
		emptyRecButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 6;
			}
		});
		
		JButton deleButton = new JButton("Delete");
		deleButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = -1;
			}
		});
		
		JButton moveButton = new JButton("Move");
		moveButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = -2;
			}
		});
		
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int index = shapeList.size()-1;
				shapeList.remove(index);
				frame.getContentPane().repaint();
				
			}
		});
		
		JButton shadowButton = new JButton("Shadow");
	    shadowButton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            mode = -3;
	         }
	      });
	    
		JButton colorButton = new JButton("Change Color");
	    colorButton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            mode = -4;
	         }
	      });
	    
	    JButton frontButton = new JButton("Front");
	    frontButton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            mode = -5;
	         }
	      });
	    
	    JTextArea lineWid = new JTextArea();
	    lineWid.setEditable(false);
	    lineWid.setText("Line Width:");
	    
	    JTextArea lineContent = new JTextArea();
	    lineContent.setEditable(true);
	    lineContent.setPreferredSize(new Dimension(blankW, blankH));
	    
	    JTextArea textTitle = new JTextArea();
	    textTitle.setEditable(false);
	    textTitle.setText("Text Content:");
	    
	    JTextArea textContent = new JTextArea();
	    textContent.setEditable(true);
	    textContent.setPreferredSize(new Dimension(blankW, blankH));
	    
	    JTextArea sizeTitle = new JTextArea();
	    sizeTitle.setEditable(false);
	    sizeTitle.setText("Text Size:");
	    
	    JTextArea textSize = new JTextArea();
	    textSize.setEditable(true);
	    textSize.setPreferredSize(new Dimension(blankW, blankH));
	 
		//innerPanel holds all the buttons on the top of the frame
	    //there are separated into three lines
		JPanel innerPanel = new JPanel();
		bArr[0] = new JPanel();
		bArr[0].setBackground(Color.gray);
		bArr[0].add(circleButton);
		bArr[0].add(emptyCirButton);
		bArr[0].add(rectButton);
		bArr[0].add(emptyRecButton);
		bArr[0].add(lineButton);
		bArr[0].add(lineWid);
		bArr[0].add(lineContent);
		
		bArr[1] = new JPanel();
		bArr[1].setBackground(Color.gray);
		bArr[1].add(textButton);
		bArr[1].add(textTitle);
		bArr[1].add(textContent);
		bArr[1].add(sizeTitle);
		bArr[1].add(textSize);
		bArr[1].add(colorButton);
		
		bArr[2] = new JPanel();
		bArr[2].setBackground(Color.gray);
		bArr[2].add(shadowButton);
		bArr[2].add(moveButton);
		bArr[2].add(frontButton);
		bArr[2].add(undoButton);
		bArr[2].add(deleButton);
		
		innerPanel.setBackground(Color.gray);
		innerPanel.setPreferredSize(new Dimension(WIDTH, BHEIGHT));
		
		innerPanel.add(bArr[0]);
		innerPanel.add(bArr[1]);
		innerPanel.add(bArr[2]);
		panel.add(innerPanel);
		
		//graphics g paint 
		JPanel drawPanel = new JPanel(){
			
			public void paint(Graphics g) {
				
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, WIDTH, HEIGHT);
				for(int i=0; i< shapeList.size(); i++) {
					shapeList.get(i).draw(g);
				}
				
			}
		};
		
		drawPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT-BHEIGHT));
		panel.add(drawPanel);
		
//		JFrame frame = new JFrame();
		
		//mouselistener
		drawPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				xLoc = e.getX();
				yLoc = e.getY();
				
				if(mode == 1) {
					Circle cir = new Circle(xLoc, yLoc, 0, 0, shapeColor);
					shapeList.add(cir);
					
				} else if(mode == 2){
					Rectangle rect = new Rectangle(xLoc, yLoc, 0, 0, shapeColor);
					shapeList.add(rect);
					
				} else if(mode == 5) {
					Circle cir = new Circle(xLoc, yLoc, 0, 0, shapeColor);
					cir.empty = true;
					shapeList.add(cir);
					
				} else if(mode == 6){
					Rectangle rect = new Rectangle(xLoc, yLoc, 0, 0, shapeColor);
					rect.empty = true;
					shapeList.add(rect);
					
				} else if(mode == 3){
					Line line = new Line(xLoc, yLoc, 0, 0, shapeColor);
					line.lineWeight = Integer.parseInt(lineContent.getText());
					shapeList.add(line);
					
				} else if(mode == 4){
					Text text = new Text(xLoc, yLoc, 0, 0, shapeColor);
					text.input = textContent.getText();
					text.textSize = Integer.parseInt(textSize.getText());
					shapeList.add(text);
					
				} else if(mode == -1) {
					for(int i=0; i< shapeList.size(); i++) {
						if(shapeList.get(i).isOn(xLoc, yLoc)) {
							shapeList.remove(i);
						}
					}
					
				} else if (mode == -3) {
					for(int i=0; i< shapeList.size(); i++) {
						if(shapeList.get(i).isOn(xLoc, yLoc)) {
							shapeList.get(i).shadow = true;
						}
					}
				} else if (mode == -5) {
					for(int i=0; i< shapeList.size(); i++) {
						if(shapeList.get(i).isOn(xLoc, yLoc)) {
							Shape temp = shapeList.get(i);
							shapeList.remove(i);
							shapeList.add(temp);
						}
					}
				}
				
				frame.getContentPane().repaint();
				
				if(mode == -2) {
					for(int i=0; i< shapeList.size(); i++) {
						if(shapeList.get(i).isOn(xLoc, yLoc)) {
							moveShape = i;
						}
					}
					
				} else if(mode == -4) {
					Color color = JColorChooser.showDialog(panel, "Choose a color", shapeColor);
		            if (color != null) { // new color selected
		               shapeColor = color;
		            }
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(mode == -2) moveShape = -1;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//mouse motion listener
		drawPanel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
				int size = shapeList.size();
				int x2 = e.getX();
				int y2 = e.getY();
				
				if(mode == 1 || mode == 2 || mode == 3 || mode == 5 || mode == 6) {
					
					shapeList.get(size-1).resize(xLoc, yLoc, x2, y2);
					
				} else if (mode == -2) {

					int x = shapeList.get(moveShape).x;
					int y = shapeList.get(moveShape).y;
					shapeList.get(moveShape).move(x, y, x2, y2);
					
				}
				
				frame.getContentPane().repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//frame set up
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		panel.setFocusable(true);
		
		
		
	}
	
	

	public static void main(String[] args) {
		new GraphicsEditor();
	}

}
