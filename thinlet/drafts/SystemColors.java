package thinlet.drafts;

import java.awt.*;
import java.awt.image.*;
import thinlet.*;

/**
 *
 */
public class SystemColors {

	private static final Color[] colors = { Color.white,
		Color.lightGray, Color.gray, Color.darkGray, Color.black,
		Color.red, Color.pink, Color.orange, Color.yellow,
		Color.green, Color.magenta, Color.cyan, Color.blue };
	private static final String[] colornames = { "white",
		"light gray", "gray", "dark gray", "black",
		"red", "pink", "orange", "yellow",
		"green", "magenta", "cyan", "blue" };
	
	private static final SystemColor[] systemcolors = { SystemColor.desktop,
		SystemColor.activeCaption, SystemColor.activeCaptionText, SystemColor.activeCaptionBorder,
		SystemColor.inactiveCaption, SystemColor.inactiveCaptionText, SystemColor.inactiveCaptionBorder,
		SystemColor.window, SystemColor.windowBorder, SystemColor.windowText,
		SystemColor.menu, SystemColor.menuText,
		SystemColor.text, SystemColor.textText,
		SystemColor.textHighlight, SystemColor.textHighlightText, SystemColor.textInactiveText,
		SystemColor.control, SystemColor.controlText, SystemColor.controlHighlight,
		SystemColor.controlLtHighlight, SystemColor.controlShadow, SystemColor.controlDkShadow,
		SystemColor.scrollbar, SystemColor.info, SystemColor.infoText };
	private static final String[] systemcolornames = { "desktop",
		"active caption", "active caption text", "active caption border",
		"inactive caption", "inactive caption text", "inactive caption border",
		"window", "window border", "window text",
		"menu", "menu text",
		"text", "text text",
		"text highlight", "text highlight text", "text inactive text",
		"control", "control text", "control highlight",
		"control light highlight", "control shadow", "control dark shadow",
		"scrollbar", "info", "info text" };
	
	/**
	 *
	 */
	public void loadColors(Thinlet thinlet, Object list) {
		for (int i = 0; i < colors.length; i++) {
			Object item = thinlet.create("item");
			thinlet.setString(item, "text", colornames[i]);
			Image icon = createIcon(thinlet, colors[i]);
			thinlet.setIcon(item, "icon", icon);
			thinlet.add(list, item);
		}
	}
	
	/**
	 *
	 */
	public void loadSystemColors(Thinlet thinlet, Object list) {
		for (int i = 0; i < systemcolors.length; i++) {
			Object item = thinlet.create("item");
			thinlet.setString(item, "text", systemcolornames[i]);
			Image icon = createIcon(thinlet, systemcolors[i]);
			thinlet.setIcon(item, "icon", icon);
			thinlet.add(list, item);
		}
	}
	
	/**
	 *
	 */
	private static Image createIcon(Component component, Color color) {
		int rgb = color.getRGB();
		int[] pix = new int[12 * 12];
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 12; y++) {
				pix[x + y * 12] = ((x > 0) && (x < 11) &&
					(y > 0) && (y < 11)) ? rgb : 0xff666666;
			}
		}
		return component.createImage(
			new MemoryImageSource(12, 12, pix, 0, 12));
	}
}