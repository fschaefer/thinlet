package thinlet;

import java.applet.*;
import java.awt.*;

/**
 * Useful utility class to start Thinlet application in an applet window.
 */
public class AppletLauncher extends Applet implements Runnable {
	
	private transient Thinlet content;
	private transient Image doublebuffer;
	
	/**
	 *
	 */
	public void init() {
		setBackground(Color.white); setForeground(Color.darkGray);
		setLayout(new BorderLayout());
		add(new Label("Loading...", Label.CENTER), BorderLayout.CENTER);
		new Thread(this).start();
	}
	
	/**
	 *
	 */
	public void run() {
		try {
			content = (Thinlet) Class.forName(getParameter("class")).newInstance();
			removeAll();
			add(content, BorderLayout.CENTER);
		} catch (Throwable exc) {
			removeAll();
			add(new Label(exc.getMessage()), BorderLayout.CENTER);
		}
		doLayout(); repaint();
	}
	
	/**
	 *
	 */
	public void doLayout() {
		super.doLayout();
		if (doublebuffer != null) {
			doublebuffer.flush();
			doublebuffer = null;
		}
	}
	
	/**
	 *
	 */
	public void stop() {
		if (doublebuffer != null) {
			doublebuffer.flush();
			doublebuffer = null;
		}
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
	public void destroy() {
		content.destroy();
	}
}
