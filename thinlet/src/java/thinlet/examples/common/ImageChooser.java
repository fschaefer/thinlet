package thinlet.examples.common;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

import thinlet.*;

/**
 *
 */
public class ImageChooser {
	
	/**
	 *
	 */
	public void load(Thinlet thinlet, Object tree) throws IOException {
		String classpath = System.getProperty("java.class.path");
		StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);
		while (st.hasMoreTokens()) {
			File file = new File(st.nextToken());
			if (!file.exists()) { continue; }
			String path = file.getCanonicalPath();
			if (findNode(thinlet, tree, path) != null) { continue; } // don't add twice
			if (file.isDirectory()) {
				Object node = addNode(thinlet, tree, null, path);
			}
			else {
				Object node = addNode(thinlet, tree, null, path);
				addNode(thinlet, node, null, "loading...");
				thinlet.putProperty(node, "loading...", Boolean.TRUE);
			}
		}
	}
	
	/**
	 *
	 */
	public void expand(Thinlet thinlet, Object node) throws ZipException, IOException{
		if (thinlet.getProperty(node, "loading...") == Boolean.TRUE) {
			System.out.println("> " + thinlet.getString(node, "text"));
			thinlet.removeAll(node);
			thinlet.putProperty(node, "loading...", null);
			
			ZipFile zipfile = new ZipFile(new File(thinlet.getString(node, "text")));
			for (Enumeration entries = zipfile.entries(); entries.hasMoreElements();) {
				ZipEntry zipentry = (ZipEntry) entries.nextElement();
				//if (!zipentry.isDirectory()) { continue; }
				//System.out.println("> " + zipentry.getName());
				StringTokenizer st = new StringTokenizer(zipentry.getName(), "/");
				Object parent = node;
				while (st.hasMoreTokens()) {
					String text = st.nextToken();
					Object current = findNode(thinlet, parent, text);
					if (current == null) {
						current = addNode(thinlet, parent, null, text);
					}
					parent = current;
				}
				/*System.out.println("Name:" + zipentry.getName() + " Directory:" + zipentry.isDirectory() +
					" Comment:" + zipentry.getComment() + " Extra:" + zipentry.getExtra() +
					" Method:" + zipentry.getMethod() + " CompressedSize:" + zipentry.getCompressedSize() +
					" Size:" + zipentry.getSize() + " Crc:" + zipentry.getCrc() + " Time:" + zipentry.getTime());*/   
			}
			zipfile.close();
		}
	}
	
	/**
	 *
	 */
	private Object addNode(Thinlet thinlet, Object parent, Image icon, String text) {
		Object node = thinlet.create("node");
		thinlet.setString(node, "text", text);
		thinlet.setBoolean(node, "expanded", false);
		thinlet.add(parent, node);
		return node;
	}
	
	/**
	 *
	 */
	private Object findNode(Thinlet thinlet, Object parent, String text) {
		Object[] nodes = thinlet.getItems(parent);
		for (int i = 0; i < nodes.length; i++) {
			if (text.equals(thinlet.getString(nodes[i], "text"))) { return nodes[i]; }
		}
		return null;
	}
	
	/**
	 *
	 */
	public static void main(String[] args) throws Exception {
		Thinlet thinlet = new Thinlet();
		thinlet.setFont(new Font("Tahoma", Font.PLAIN, 11));
		thinlet.setColors(0xf2f1e4, 0x000000, 0xffffff,
			0x909090, 0xb0b0b0, 0xededed, 0xd7d5c2, 0x89899a, 0xc5c5dd);
		thinlet.add(thinlet.parse("/thinlet/common/imagechooser.xml", new ImageChooser()));
		new FrameLauncher("Image chooser", thinlet, 240, 320);
	}
}
