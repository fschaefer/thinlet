package thinlet;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Useful utility class to start a Thinlet application in AWT Frame.
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
		
		int[] pix = new int[16 * 16];
		for (int x = 0; x < 16; x++) {
			int sx = ((x >= 1) && (x <= 9)) ? 1 : (((x >= 11) && (x <= 14)) ? 2 : 0);
			for (int y = 0; y < 16; y++) {
				int sy = ((y >= 1) && (y <= 9)) ? 1 : (((y >= 11) && (y <= 14)) ? 2 : 0);
				pix[y * 16 + x] = ((sx == 0) || (sy == 0)) ? 0xffffffff :
					((sx == 1) ? ((sy == 1) ? (((y == 2) && (x >= 2) && (x <= 8)) ? 0xffffffff :
						(((y >= 3) && (y <= 8)) ? ((x == 5) ? 0xffffffff : (((x == 4) || (x == 6)) ?
							0xffe8bcbd : 0xffb01416)) : 0xffb01416)) : 0xff377ca4) :
						((sy == 1) ? 0xff3a831d : 0xfff2cc9c)); 
			}
		}
		setIconImage(createImage(new MemoryImageSource(16, 16, pix, 0, 16)));
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
	 * This method calls Thinlet.destroy(), and exits only if that method
	 * returns true. This provides a way for programmers to perform
	 * cleanup tasks on exit, or disallow exiting altogether.
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
