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
    
    public void actionTheme(String idx) {
        int index = idx.charAt(1) - '0';
        switch (index) {
            case 0: //xp
                setColors(0xece9d8, 0x000000, 0xf5f4f0,
                0x919b9a, 0xb0b0b0, 0xededed, 0xb9b9b9, 0xff899a, 0xc5c5dd);
                break;
            case 1: //gray
                setColors(0xe6e6e6, 0x000000, 0xffffff,
                0x909090, 0xb0b0b0, 0xededed, 0xb9b9b9, 0x89899a, 0xc5c5dd);
                break;
            case 2: //yellow
                setColors(0xeeeecc, 0x000000, 0xffffff,
                0x999966, 0xb0b096, 0xededcb, 0xcccc99, 0xcc6600, 0xffcc66);
                break;
            case 3: //blue
                setColors(0x6375d6, 0xffffff, 0x7f8fdd,
                0xd6dff5, 0x9caae5, 0x666666, 0x003399, 0xff3333, 0x666666);
                break;
        }
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
        actionTheme("t0");
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
