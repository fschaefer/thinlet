package thinlet;

import java.awt.*;
import java.awt.event.*;

/**
 *
 */
public class FrameLauncher extends Frame implements WindowListener {
	
	private transient Thinlet content;
	private transient Image doublebuffer;
	
	/**
	 * @param title
	 * @param content
	 * @param width
	 * @param height
	 */
	public FrameLauncher(String title, Thinlet content, int width, int height) {
		super(title);
		this.content = content;
		add(content, BorderLayout.CENTER);
		addWindowListener(this);
		pack();
		Insets is = getInsets();
		width += is.left + is.right;
		height += is.top + is.bottom;
		Dimension ss = getToolkit().getScreenSize();
		width = Math.min(width, ss.width);
		height = Math.min(height, ss.height);
		setBounds((ss.width - width) / 2, (ss.height - height) / 2, width, height); 
		setVisible(true);
		//maximize: setBounds(-is.left, -is.top, ss.width + is.left + is.right, ss.height + is.top + is.bottom);
	}
	
	/**
	 *
	 */
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 *
	 */
	public void paint(Graphics g) { 
		if (doublebuffer == null) {
			Dimension d = getSize();
			doublebuffer = createImage(d.width, d.height);
		}
		Graphics dg = doublebuffer.getGraphics();
		dg.setClip(g.getClipBounds());
		super.paint(dg);
		dg.dispose();
		g.drawImage(doublebuffer, 0, 0, this);
	}
	
	/**
	 *
	 */
	public void doLayout() {
		if (doublebuffer != null) {
			doublebuffer.flush();
			doublebuffer = null;
		}
		super.doLayout();
	}

	/**
	 *
	 */
	public void windowClosing(WindowEvent e) {
		if (content.destroy()) {
			System.exit(0);
		}
		setVisible(true);
	}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
