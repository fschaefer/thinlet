package thinlet.drafts;

import java.awt.*;
import thinlet.*;

/**
 *
 */
public class Utilities {
	
	private static Utilities utilities;
	
	/**
	 *
	 */
	public static Utilities getUtilities() {
		if (utilities == null) {
			try {
				Class.forName("java.awt.GraphicsEnvironment");
				utilities = (Utilities) Class.forName("thinlet.drafts.Utilities2").newInstance();
			} catch (Exception exc) { /* not 1.4 */
				utilities = new Utilities();
			}
		}
		return utilities;
	}
	
	/**
	 *
	 */
	public String[] getFontList() {
		return Toolkit.getDefaultToolkit().getFontList();
	}
	
	/**
	 *
	 */
	public Object getDesktopProperty(String key) {
		return null;
	}
	
	/**
	 *
	 */
	public void initGraphics(Graphics g) {
	}
}