package thinlet.drafts;

import java.awt.*;
import thinlet.*;

/**
 *
 */
public class Utilities2 extends Utilities {
	
	/**
	 *
	 */
	public String[] getFontList() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	}
	
	/**
	 *
	 */
	public Object getDesktopProperty(String key) {
		return Toolkit.getDefaultToolkit().getDesktopProperty(key);
	}
	
	/**
	 *
	 */
	public void initGraphics(Graphics g) {
		((Graphics2D) g).setRenderingHint(
			RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
}