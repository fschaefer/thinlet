package thinlet.drafts;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import thinlet.*;

/**
 *
 */
public class DesktopProperties {
	
	/**
	 *
	 */
	public void loadProperties(Thinlet thinlet,
			Object colortable, Object geometrytable, Object fonttable, Object audiolist) {
		String[] keys = (String[]) Utilities.getUtilities().getDesktopProperty("win.propNames");
		if (keys != null) {
			Hashtable iconcache = new Hashtable();
			for (int i = 0; i < keys.length; i++) {
				Object value = Utilities.getUtilities().getDesktopProperty(keys[i]);
				if (value instanceof Color) { // Color Properties
					Image icon = (Image) iconcache.get(value);
					int rgb = ((Color) value).getRGB();
					if (icon == null) {
						int[] pix = new int[12 * 12];
						for (int x = 0; x < 12; x++) {
							for (int y = 0; y < 12; y++) {
								pix[x + y * 12] = ((x > 0) && (x < 11) &&
									(y > 0) && (y < 11)) ? rgb : 0xff666666;
							}
						}
						icon = thinlet.createImage(
							new MemoryImageSource(12, 12, pix, 0, 12));
						iconcache.put(value, icon);
					}
					addRow(thinlet, colortable, keys[i],
						icon, "0x" + Integer.toHexString(rgb).substring(2));
				}
				else if (value instanceof Integer) { // Geometry or Input Properties
					addRow(thinlet, geometrytable, keys[i], null, value.toString());
				}
				else if (value instanceof Font) { // Font Properties
					Font font = (Font) value;
					StringBuffer text = new StringBuffer(font.getName());
					if (font.isBold()) { text.append(" bold"); }
					if (font.isItalic()) { text.append(" italic"); }
					text.append(' '); text.append(font.getSize());
					addRow(thinlet, fonttable, keys[i], null, text.toString());
				}
				else if (value instanceof Boolean) { // Visual Effect Properties
					addRow(thinlet, geometrytable, keys[i], null, value.toString());
				}
				else if (value instanceof Runnable) { // Audio-feedback Properties
					Object item = thinlet.create("item");
					thinlet.setString(item, "text", keys[i]);
					thinlet.putProperty(item, "runnable", value);
					thinlet.add(audiolist, item);
				}
				else System.out.println(value.getClass().getName());
			}
		}
	}
	
	/**
	 *
	 */
	private void addRow(Thinlet thinlet, Object table, String key, Image icon, String value) {
		Object row = thinlet.create("row");
		Object keycell = thinlet.create("cell");
		thinlet.setString(keycell, "text", key);
		thinlet.add(row, keycell);
		Object valuecell = thinlet.create("cell");
		if (icon != null) { thinlet.setIcon(valuecell, "icon", icon); }
		thinlet.setString(valuecell, "text", value);
		thinlet.add(row, valuecell);
		thinlet.add(table, row);
	}
	
	/**
	 *
	 */
	public void rowSelected(Thinlet thinlet, Object audiolist, Object playbutton) {
		thinlet.setBoolean(playbutton, "enabled", thinlet.getSelectedIndex(audiolist) != -1);
	}
	
	/**
	 *
	 */
	public void play(Thinlet thinlet, Object audiolist) {
		Object item = thinlet.getSelectedItem(audiolist);
		Runnable runnable = (Runnable) thinlet.getProperty(item, "runnable");
		runnable.run();
	}
}