package thinlet.demo;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import thinlet.*;

/**
 *
 */
public class Demo extends Thinlet {

	/**
	 *
	 */
	public Demo() {
		try {
			add(parse("demo.xml"));
		} catch (Exception exc) { exc.printStackTrace(); }
	}
	
	/**
	 *
	 */
	public static void main(String[] args) {
		new FrameLauncher("Demo", new Demo(), 320, 320);
	}

	boolean textinit;
	boolean valueinit;
	
	/**
	 *
	 */
	public void loadText(Object textarea) {
		try {
			InputStream inputstream = null;
			try {
				inputstream = getClass().getResourceAsStream("demodialog.xml");
			} catch (Throwable e) {}
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
			StringBuffer text = new StringBuffer();
			for (int c = reader.read(); c != -1; c = reader.read()) {
				if (((c > 0x1f) && (c < 0x7f)) ||
						((c > 0x9f) && (c < 0xffff)) || (c == '\n')) {
					text.append((char) c);
				}
				else if (c == '\t') {
					text.append("  ");
				}
			}
			reader.close();
			setString(textarea, "text", text.toString());
			textinit = true;
		} catch (Exception exc) { getToolkit().beep(); }
	}

	/**
	 *
	 */
	public void changeEditable(boolean editable, Object textarea) {
		setBoolean(textarea, "editable", editable);
	}

	/**
	 *
	 */
	public void changeEnabled(boolean enabled, Object textarea) {
		setBoolean(textarea, "enabled", enabled);
	}

	Object dialog;

	/**
	 *
	 */
	public void showDialog() {
		try {
			if (dialog == null) {
				dialog = parse("demodialog.xml");
			}
			add(dialog);
		} catch (Exception exc) { exc.printStackTrace(); }
	}

	/**
	 *
	 */
	public void findText(Object combobox, String what,
			boolean match, boolean down) {
		closeDialog();
		if (what.length() == 0) { return; }

		boolean cacheditem = false;
		for (int i = getCount(combobox) - 1; i >= 0; i--) {
			String choicetext = getString(getItem(combobox, i), "text");
			if (what.equals(choicetext)) { cacheditem = true; break; }
		}
		if (!cacheditem) {
			Object choice = create("choice");
			setString(choice, "text", what);
			add(combobox, choice);
		}

		Object textarea = find("textarea");
		int end = getInteger(textarea, "end");
		String text = getString(textarea, "text");
		
		if (!match) {
			what = what.toLowerCase();
			text = text.toLowerCase();
		}

		int index = text.indexOf(what, down ? end : 0);
		if (!down && (index != -1) && (index >= end)) { index = -1; }
		if (index != -1) {
			setInteger(textarea, "start", index);
			setInteger(textarea, "end", index + what.length());
			requestFocus(textarea);
		}
		else {
			getToolkit().beep();
		}
	}

	/**
	 *
	 */
	public void closeDialog() {
		remove(dialog);
	}
	
	/**
	 *
	 */
	public void insertList(Object list) {
		Object item = create("item");
		setString(item, "text", "New item");
		setIcon(item, "icon", getIcon("/icons/bookmarks.gif"));
		add(list, item, 0);
	}

	/**
	 *
	 */
	public void deleteList(Object delete, Object list) {
		for (int i = getCount(list) - 1; i >= 0; i--) {
			Object item = getItem(list, i);
			if (getBoolean(item, "selected")) {
				remove(item);
			}
		}
		setBoolean(delete, "enabled", false);
	}
	
	/**
	 *
	 */
	public void changeSelection(Object list, Object delete) {
		setBoolean(delete, "enabled", getSelectedIndex(list) != -1);
	}

	/**
	 *
	 */
	public void setSelection(Object list, String selection, Object delete) {
		for (int i = getCount(list) - 1; i >= 0; i--) {
			setBoolean(getItem(list, i), "selected", false);
		}
		setChoice(list, "selection", selection);
		setBoolean(delete, "enabled", false);
	}
	
	/**
	 *
	 */
	public void sliderChanged(int value, Object spinbox) {
		setString(spinbox, "text", String.valueOf(value));
		hsbChanged();
	}
	
	/**
	 *
	 */
	public void spinboxChanged(String text, Object slider) {
		try {
			int value = Integer.parseInt(text);
			if ((value >= 0) && (value <= 255)) {
				setInteger(slider, "value", value);
				hsbChanged();
			}
		} catch (NumberFormatException nfe) { getToolkit().beep(); }
	}
	
	private Object sl_red, sl_green, sl_blue;
	private Object tf_hue, tf_saturation, tf_brightness;
	private Object pb_hue, pb_saturation, pb_brightness;
	
	/**
	 *
	 */
	private void hsbChanged() {
		if (sl_red == null) {
			sl_red = find("sl_red"); sl_green = find("sl_green"); sl_blue = find("sl_blue");
			tf_hue = find("tf_hue"); tf_saturation = find("tf_saturation"); tf_brightness = find("tf_brightness");
			pb_hue = find("pb_hue"); pb_saturation = find("pb_saturation"); pb_brightness = find("pb_brightness");
		}
		
		int red = getInteger(sl_red, "value");
		int green = getInteger(sl_green, "value");
		int blue = getInteger(sl_blue, "value");
		
		float[] hsb = Color.RGBtoHSB(red, green, blue, null);

		setString(tf_hue, "text", String.valueOf(hsb[0]));
		setString(tf_saturation, "text", String.valueOf(hsb[1]));
		setString(tf_brightness, "text", String.valueOf(hsb[2]));

		setInteger(pb_hue, "value", (int) (100f * hsb[0]));
		setInteger(pb_saturation, "value", (int) (100f * hsb[1]));
		setInteger(pb_brightness, "value", (int) (100f * hsb[2]));
	}
}