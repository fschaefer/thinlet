package thinlet.drafts;

import java.awt.*;
import thinlet.*;

/**
 *
 */
public class Drafts extends Thinlet {

	/**
	 *
	 */
	public Drafts() {
		//setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		try {
			add(parse("drafts.xml"));
		} catch (Exception exc) { exc.printStackTrace(); }
	}
	
	/**
	 *
	 */
	public void loadDraft(Object splitpane, Object tree) {
		if (getCount(splitpane) > 2) {
			remove(getItem(splitpane, 1));
		}
		Object node = getSelectedItem(tree);
		if (node != null) {
			Object draft = getProperty(node, "draft");
			if (draft == null) {
				String ui = (String) getProperty(node, "ui");
				String logic = (String) getProperty(node, "logic");
				if ((ui != null) && (logic != null)) {
					try {
						Object handler = Class.forName(logic).getConstructor(null).newInstance(null);
						draft = parse(ui, handler);
						putProperty(node, "draft", draft);
					} catch (Exception exc) { exc.printStackTrace(); }
				}
			}
			if (draft != null) {
				add(splitpane, draft, 1);
			}
		}
	}
	
	/**
	 *
	 */
	public void paint(Graphics g) {
		Utilities.getUtilities().initGraphics(g);
		super.paint(g);
	}
	
	/**
	 *
	 */
	public static void main(String[] args) {
		new FrameLauncher("Thinlet Drafts", new Drafts(), 480, 270);
	}
}