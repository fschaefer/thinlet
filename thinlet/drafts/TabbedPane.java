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
		String[] placements = { "top", "left", "bottom", "right" };
		thinlet.setChoice(tabbedpane, "placement", placements[index]);
	}
	
	/**
	 *
	 */
	public void changeEnabled(Thinlet thinlet, boolean selected, Object tabbedpane, int tabindex) {
		Object tab = thinlet.getItem(tabbedpane, "tab", tabindex);
		thinlet.setBoolean(tab, "enabled", selected);
	}
}