
// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This program draws a "Spiral of Lines". When the mouse is pressed
 * then dragged, a series of lines are drawn from the press point to
 * the current location.
 * 
 * This will be enhanced as part of a lab exercise with various other
 * functionality.
 * 
 * @author Jim Teresco (initial Spiral drawing)
 * @author Ira Goldstein (revised swing components)
 * @author Put Lab Partners' Names Here
 * @version Spring 2023
 */

public class SpiralsAndScribbles extends MouseAdapter implements Runnable {

	// a list of pairs of points to keep track of the start and end coordinates
	// where we need to draw lines
	private ArrayList<Point[]> lines = new ArrayList<>();

	// press point for the current spiral
	private Point pressPoint;

	private JPanel panel;

      // should we clear on exit?
      private JCheckBox clearOnExit;

      // what should we draw?
      private JComboBox drawWhat;

      // color mode
      private JComboBox colorMode;
	

	// this method is called by the paintComponent method of
	// the anonymous extension of JPanel, to keep that method
	// from getting too long
	protected void redraw(Graphics g) {

		// draw all of the lines in the list
		for (Point[] p : lines) {
			g.drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
		}
	}

	/**
	 * The run method to set up the graphical user interface
	 */
	@Override
	public void run() {

		// set up the GUI "look and feel" which should match
		// the OS on which we are running
		JFrame.setDefaultLookAndFeelDecorated(false);

		// create a JFrame in which we will build our very
		// tiny GUI, and give the window a name
		JFrame frame = new JFrame("SpiralsAndScribbles");
		frame.setPreferredSize(new Dimension(500, 500));

		// tell the JFrame that when someone closes the
		// window, the application should terminate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

           JPanel outerPanel = new JPanel(new BorderLayout());
           frame.add(outerPanel);

		// JPanel with a paintComponent method
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {

				// first, we should call the paintComponent method 
				// we areoverriding in JPanel
				super.paintComponent(g);

				// redraw our graphics items
				redraw(g);
			}
		};

           outerPanel.add(panel, BorderLayout.CENTER);
           panel.addMouseListener(this);
           panel.addMouseMotionListener(this);

           JPanel controlPanel = new JPanel(new GridLayout(1,3));
           clearOnExit = new JCheckBox("Clear on exit?", false);
           controlPanel.add(clearOnExit);

           JComboBox<String> drawWhat = new JComboBox<String>();
           drawWhat.addItem("Spirals");
           drawWhat.addItem("Scribbles");
           drawWhat.setSelectedItem("Spirals"); 
		   
           controlPanel.add(drawWhat);

           // drawWhat.addActionListener(this);

           JComboBox<String> colorMode = new JComboBox<String>();
           colorMode.addItem("Black");
           colorMode.addItem("Colorful");
           colorMode.addItem("More Colorful");
           colorMode.addItem("Crazy Colorful");
           controlPanel.add(colorMode);

           outerPanel.add(controlPanel, BorderLayout.SOUTH);

		// display the window we've created
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {

		pressPoint = e.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		Point p[] = new Point[2];
		p[0] = pressPoint;
		p[1] = e.getPoint();
		lines.add(p);
		panel.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {

		lines.clear();
		panel.repaint();
	}

	public static void main(String args[]) {

		// The main method is responsible for creating a thread (more
		// about those later) that will construct and show the graphical
		// user interface.
		javax.swing.SwingUtilities.invokeLater(new SpiralsAndScribbles());
	}
}
