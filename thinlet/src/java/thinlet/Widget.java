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
	public Widget(String classname) {
		widget = Thinlet.create(classname);
	}
	
	/**
	 *
	 */
	public String getClassName() { 
		return null;
	}
	
	/**
	 *
	 */
	public void set(String key, String value) {
		thinlet.setString(widget, key, value);
	}
	
	public boolean requestFocus(Object component) {
		return true;
	}
}
