package thinlet.drafts;

import thinlet.*;

/**
 *
 */
public class TabbedPane {
	
	/**
	 *
	 */
	public void changePlacement(Thinlet thinlet, Object combobox, Object tabbedpane) {
		int index = thinlet.getInteger(combobox, "selected");
		String[] placements = { "top", "left", "bottom", "right", "stacked" };
		thinlet.setChoice(tabbedpane, "placement", placements[index]);
	}
	
	/**
	 *
	 */
	public void changeEnabled(Thinlet thinlet, boolean selected, Object tabbedpane, int tabindex) {
		Object tab = thinlet.getItem(tabbedpane, tabindex);
		thinlet.setBoolean(tab, "enabled", selected);
	}
	
	/**
	 *
	 */
	public void changeText(Thinlet thinlet, Object tabbedpane) {
		thinlet.setString(thinlet.getItem(tabbedpane, 0), "text", "First!");
	}
	
	public void focus(Thinlet thinlet, Object component) {
		thinlet.setBoolean(component, "visible", true);
		System.out.println(thinlet.requestFocus(component));
	}
	
	public void setBorder(Thinlet thinlet, boolean bordered, Object textarea) {
		thinlet.setBoolean(textarea, "border", bordered);
	}
}