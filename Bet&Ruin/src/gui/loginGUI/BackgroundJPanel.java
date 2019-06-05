package gui.loginGUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class BackgroundJPanel extends JPanel {
	BufferedImage img ;
	/*
	 * 
	 */
	public BackgroundJPanel(BufferedImage img) {
		super();
		this.img=img;
	}
	private static final long serialVersionUID = 1L;

	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(img, 0, 0, this);
	}
}
