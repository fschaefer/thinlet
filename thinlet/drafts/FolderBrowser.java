package thinlet.drafts;

import java.awt.*;
import java.io.*;
import thinlet.*;

/**
 *
 */
public class FolderBrowser {
	
	/**
	 *
	 */
	public void init(Thinlet thinlet, Object tree) {
		//System.out.println(System.getProperty("user.home") + " " + File.separatorChar);
		addNode(thinlet, tree, "C:");
	}
	
	/**
	 *
	 */
	public void expand(Thinlet thinlet, Object tree, Object node) {
		if (thinlet.getProperty(node, "load") == Boolean.TRUE) {
			String path = "";
			for (Object item = node; item != tree; item = thinlet.getParent(item)) {
				path = thinlet.getString(item, "text") + File.separatorChar + path;
			}
			String[] list = new File(path).list();
			for (int i = 0; i < list.length; i++) {
				for (int j = i; (j > 0) && (list[j - 1].compareTo(list[j]) > 0); j--) {
					String swap = list[j]; list[j] = list[j - 1]; list[j - 1] = swap;
				}
			}
			thinlet.removeAll(node);
			for (int i = 0; i < list.length; i++) {
				if (new File(path + list[i]).isDirectory()) {
					addNode(thinlet, node, list[i]);
				}
			}
			thinlet.putProperty(node, "load", null);
		}
	}
	
	private Image foldericon;
	
	/**
	 *
	 */
	private void addNode(Thinlet thinlet, Object node, String text) {
		Object subnode = thinlet.create("node");
		thinlet.setString(subnode, "text", text);
		if (foldericon == null) {
			foldericon = thinlet.getIcon("/icons/open.gif");
		}
		thinlet.setIcon(subnode, "icon", foldericon);
		thinlet.setBoolean(subnode, "expanded", false);
		thinlet.putProperty(subnode, "load", Boolean.TRUE);
		thinlet.add(node, subnode);
		
		Object loading = thinlet.create("node");
		thinlet.setString(loading, "text", "loading...");
		thinlet.add(subnode, loading);
	}
}