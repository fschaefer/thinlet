package thinlet.drafts;

import thinlet.*;

/**
 *
 */
public class BeanTest {
	
	/**
	 *
	 */
	public void setBorder(Thinlet thinlet, Object panel, boolean value) {
		thinlet.setBoolean(panel, "border", value);
	}
	
	/**
	 *
	 */
	public void setScrollable(Thinlet thinlet, Object panel, boolean value) {
		thinlet.setBoolean(panel, "scrollable", value);
	}
	
	/**
	 *
	 */
	public void setIcon(Thinlet thinlet, Object panel, boolean value) {
		thinlet.setIcon(panel, "icon", value ? thinlet.getIcon("/icons/composemail.gif") : null);
	}
	
	/**
	 *
	 */
	public void setTitle(Thinlet thinlet, Object panel, String text) {
		thinlet.setString(panel, "text", (text.length() > 0) ? text : null);
	}
}