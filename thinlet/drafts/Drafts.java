package thinlet.drafts;

import thinlet.*;

/**
 *
 */
public class Drafts extends Thinlet {

	/**
	 *
	 */
	public Drafts() {
		try {
			add(parse("drafts.xml"));
		} catch (Exception exc) { exc.printStackTrace(); }
	}
	
	/**
	 *
	 */
	public void loadDraft(Object tree, Object preview) {
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
			removeAll(preview);
			if (draft != null) {
				add(preview, draft);
			}
		}
	}
	
	/**
	 *
	 */
	public static void main(String[] args) {
		new FrameLauncher("Thinlet Drafts", new Drafts(), 480, 270);
	}
}