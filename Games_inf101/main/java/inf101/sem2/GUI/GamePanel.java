package inf101.sem2.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A class for clickable panels
 * This is basically a Button with some special design that changes.
 * When this panel is clicked a call to the MouseListener will be made
 * which then changes the color.
 *
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class GamePanel extends JPanel {
	private Color color;

	private static final long serialVersionUID = 1L;

	public GamePanel(MouseListener listener) {
		this.color = null;
		setPreferredSize(new Dimension(3, 3));
		//use methods in JPanel to set initial style
		setEnabled(true);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//setBorder(new RoundedBorder(20)); //10 is the radius
		//add mouse listener which calls click method
		addMouseListener(listener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(color != null) {
			g.setColor(color);
			Dimension dim = this.getSize();
			g.fillOval(0, 0, dim.width, dim.height);
		}
	}

	/**
	 * Sets the color of this panel.
	 * When the updateUI function is called the color
	 * will be changed to this color
	 *
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
