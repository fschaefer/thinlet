package thinlet;

import java.applet.*;
import java.awt.*;

/**
 *
 */
public class AppletLauncher extends Applet {
	
	private transient Thinlet content;
	private transient Image doublebuffer;
	
	/**
	 *
	 */
	public void init() {
		setLayout(new BorderLayout());
		try {
			content = (Thinlet) Class.forName(getParameter("class")).newInstance();
			add(content, BorderLayout.CENTER);
		} catch (Throwable exc) {
			add(new Label(exc.getMessage()), BorderLayout.CENTER);
		}
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
