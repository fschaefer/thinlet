package thinlet;

import java.awt.*;

/**
 *
 */
public class Widget {
	
	private transient Thinlet thinlet;
	private transient Object widget;
	
	/**
	 *
	 */
	Widget(Thinlet thinlet, Object widget) {
		this.thinlet = thinlet;
		this.widget = widget;
	}
	
	public void set(String key, String value) {
		thinlet.setString(widget, key, value);
	}
}
