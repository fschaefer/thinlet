package thinlet.examples.demo;

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
    
    // current theme index
    private int ct = 0;
    // theme definitions
    private int[][] defT = {    
        {
            0xece9d8, 0x000000, 0xf5f4f0,
            0x919b9a, 0xb0b0b0, 0xededed,
            0xb9b9b9, 0xff899a, 0xc5c5dd
        },
        {
            0xe6e6e6, 0x000000, 0xffffff,
            0x909090, 0xb0b0b0, 0xededed,
            0xb9b9b9, 0x89899a, 0xc5c5dd
        },
        {
            0xeeeecc, 0x000000, 0xffffff,
            0x999966, 0xb0b096, 0xededcb,
            0xcccc99, 0xcc6600, 0xffcc66
        },
        {
            0x6375d6, 0xffffff, 0x7f8fdd,
            0xd6dff5, 0x9caae5, 0x666666,
            0x003399, 0xff3333, 0x666666
        }
    };
    
    private int[][] t = new int[4][9];
    
    /**
     *
     */
    public Demo() {
        try {
            add(parse("demo.xml"));
        } catch (Exception exc) { exc.printStackTrace(); }
        // copy values from defaults to current
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 9; j++)
                t[i][j] = defT[i][j];
        actionTheme("t0");
    }
    
    /**
     *
     */
    public static void main(String[] args) {
        new FrameLauncher("Demo", new Demo(), 340, 340);
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
    
    public void actionTheme(String idx) {
        ct = idx.charAt(1) - '0';
        setColors(
            t[ct][0], t[ct][1], t[ct][2],
            t[ct][3], t[ct][4], t[ct][5],
            t[ct][6], t[ct][7], t[ct][8]);
            
        adjustColors(null);
            
    }

    public void resetColors() {
        for (int i = 0; i < 9; i++)
            t[ct][i] = defT[ct][i];
        actionTheme("t" + ct);
    }
    
    public void adjustColors(Object spin) {
        if (spin == null) {
            // set the spinboxes after theme change
            for (int i = 0; i < 9; i++) {
                Object sp = find(i + "_r");
                setString(sp, "text", String.valueOf(new Color(t[ct][i]).getRed()));
                sp = find(i + "_g");
                setString(sp, "text", String.valueOf(new Color(t[ct][i]).getGreen()));
                sp = find(i + "_b");
                setString(sp, "text", String.valueOf(new Color(t[ct][i]).getBlue()));
                sp = find(i + "_h");
                setString(sp, "text", "#" + toHexString(t[ct][i]));
            }
            return;
        }
        String name = getString(spin, "name");
        int idx = 0;
        try {
            idx = Integer.parseInt(name.substring(0, 1));
        } catch (Exception e) {};
        Color c = new Color(t[ct][idx]);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int val = 0;
	try {
		val = Integer.parseInt(getString(spin, "text"));
	} catch (Exception e) {};
        if (name.endsWith("r")) {
            r = val;
        } else if (name.endsWith("g")) {
            g = val;
        } else b = val;
        c = new Color(r, g, b);
        t[ct][idx] = c.getRGB();
        Object sp = find(idx + "_h");
        setString(sp, "text", "#" + toHexString(t[ct][idx]));
        setColors(
            t[ct][0], t[ct][1], t[ct][2],
            t[ct][3], t[ct][4], t[ct][5],
            t[ct][6], t[ct][7], t[ct][8]);
    }

    private String toHexString(int num) {
        String res = Integer.toHexString(0x00ffffff & num);
        int len = res.length();
        if (len < 6) for (int i = 0; i < 6 - len; i++) res = "0" + res;
        return res;
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
    
    public void changeBorder(boolean enabled, Object textarea) {
        setBoolean(textarea, "border", enabled);
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
    
    public void sortAction(Object header) {
	    int idx = getSelectedIndex(header);
	    Object column = getSelectedItem(header);
	    String sort = getChoice(column, "sort");
	    if (sort == "none") return;
	    Object table = getParent(header);
	    Object[] rows = getItems(table);
	    removeAll(table);
	    sortArray(rows, new RowComparator(this, idx, sort == "ascent"));
	    for (int i = 0; i < rows.length; i++) add(table, rows[i]);
	    repaint(table);
    }

    /**
     *   Basic sort for small arrays.
     */
    private void sortArray(Object[] arr, Comparator comp) {

        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (comp.compare(arr[i], arr[i + 1]) > 0) {
                    Object temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                    swapped = true;
                }
            }
        } while (swapped);
    }


    static class RowComparator implements Comparator {
	    private boolean ascending = false;
	    private int column = 0;
	    private Thinlet thinlet = null;

	    public RowComparator(Thinlet thinlet, int column, boolean ascending) {
		    this.thinlet = thinlet;
		    this.column = column;
		    this.ascending = ascending;
	    }

	    public int compare(Object row1, Object row2) {
		    Object cell1 = thinlet.getItem(row1, column);
		    Object cell2 = thinlet.getItem(row2, column);
		    String s1 = thinlet.getString(cell1, "text");
		    String s2 = thinlet.getString(cell2, "text");
		    if (s1 == null) return ascending? -1 : 1;
		    if (s2 == null) return ascending? 1 : -1;
		    return (ascending ? 1 : -1 ) * s1.compareTo(s2);
	    }
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
    private Object rgb_label;
    
    /**
     *
     */
    public void storeWidgets(Object sl_red, Object sl_green, Object sl_blue,
    Object tf_hue, Object tf_saturation, Object tf_brightness,
    Object pb_hue, Object pb_saturation, Object pb_brightness,
    Object rgb_label) {
        this.sl_red = sl_red;
        this.sl_green = sl_green;
        this.sl_blue = sl_blue;
        this.tf_hue = tf_hue;
        this.tf_saturation = tf_saturation;
        this.tf_brightness = tf_brightness;
        this.pb_hue = pb_hue;
        this.pb_saturation = pb_saturation;
        this.pb_brightness = pb_brightness;
        this.rgb_label = rgb_label;
    }
    
    /**
     *
     */
    private void hsbChanged() {
        int red = getInteger(sl_red, "value");
        int green = getInteger(sl_green, "value");
        int blue = getInteger(sl_blue, "value");
        
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        
        setColor(rgb_label, "background", new Color(red, green, blue));
        setString(tf_hue, "text", String.valueOf(hsb[0]));
        setString(tf_saturation, "text", String.valueOf(hsb[1]));
        setString(tf_brightness, "text", String.valueOf(hsb[2]));
        
        setInteger(pb_hue, "value", (int) (100f * hsb[0]));
        setInteger(pb_saturation, "value", (int) (100f * hsb[1]));
        setInteger(pb_brightness, "value", (int) (100f * hsb[2]));
    }
}
