package thinlet.midp;

import java.io.*;
import java.util.*;

public class Converter {
	public static void main(String[] args) throws Exception {
                String srcDir = args[0];
                String destDir = args[1];
		FileInputStream fis = new FileInputStream(srcDir + "/thinlet/Thinlet.java");
		byte[] data = new byte[fis.available()];
		fis.read(data, 0, data.length);
		fis.close();
		String source = new String(data);
                
                source = change(source,"package thinlet;", "package thinlet.midp;");
                
                source = change(source,"/* import thinlet.*; */", "import thinlet.*;");
                
                source = change(source,"c_ctrl = null;","c_ctrl;");
		
		FileInputStream efis = new FileInputStream(destDir + "/eventcode.txt");
		byte[] eventcode = new byte[efis.available()];
		efis.read(eventcode, 0, eventcode.length);
		efis.close();
		source = change(source, "/* insert midp event code */", new String(eventcode));

		int index = 0;
		while ((index = source.indexOf("//java>")) != -1) {
			int end = source.indexOf("//<java", index);
			source = source.substring(0, index) + source.substring(end + 7);
		}
		source = change(source, "/*midp", "");
		source = change(source, "midp*/", "");
		while ((index = source.indexOf("//midp ")) != -1) {
			int end = index;
			while ("\n".indexOf(source.charAt(end + 1)) == -1) { end++; }
			source = source.substring(0, index) + source.substring(index + 7, end) +
				" //midp" + source.substring(end);
		}
		while ((index = source.indexOf(" //java")) != -1) {
			int start = index;
			while ((start > 0) &&
				("\t\n\r".indexOf(source.charAt(start - 1)) == -1)) { start--; }
			source = source.substring(0, start) + "//java " +
				source.substring(start, index) + source.substring(index + 7);
		}

		source = change(source, "Boolean.TRUE", "TRUE");
		source = change(source, "Boolean.FALSE", "FALSE");

		source = change(source, ".getWidth(this)", ".getWidth()");
		source = change(source, ".getHeight(this)", ".getHeight()");
		source = change(source, "Dimension ", "int[] ");
		source = change(source, ".width", "[0]");
		source = change(source, ".height", "[1]");
		source = change(source, "new Dimension()", "new int[] { 0, 0 }");
		source = change(source, "new Dimension(1, 1)", "new int[] { 1, 1 }");
		source = change(source, "new Dimension(", "new int[] { ", ");", " };");
		source = change(source, "Rectangle ", "int[] ");
		source = change(source, ".x", "[2]");
		source = change(source, ".y", "[3]");
		source = change(source, "Color ", "int ");
		source = change(source, "new Color(", "", ")", "");

		source = change(source, "fm.charWidth(", "font.charWidth(");
		source = change(source, "fm.getAscent() + fm.getDescent()", "font.getHeight()");
		source = change(source, "fm.getHeight()", "font.getHeight()");
		source = change(source, "FontMetrics fm = getFontMetrics(font);", "");
		source = change(source, "fm.stringWidth(", "font.stringWidth(");
		source = change(source, " - fm.getLeading()", "");
		source = change(source, " + fm.getLeading()", "");
		source = change(source, "getFontMetrics(getFont())", "font");
		source = change(source, "fm.charsWidth", "font.charsWidth");
		//source = change(source, "reader.read()", "read()");
		//source = change(source, "reader.close();", "data = null; inputreader = null;");
		source = change(source, ".intern()", ""); //bug!!!

		source = change(source, "MouseEvent.MOUSE_ENTERED", "MOUSE_ENTERED");
		source = change(source, "MouseEvent.MOUSE_MOVED", "0");
		source = change(source, "MouseEvent.MOUSE_EXITED", "MOUSE_EXITED");
		source = change(source, "MouseEvent.MOUSE_PRESSED", "MOUSE_PRESSED");
		source = change(source, "MouseEvent.MOUSE_DRAGGED", "MOUSE_DRAGGED");
		source = change(source, "MouseEvent.MOUSE_RELEASED", "MOUSE_RELEASED");

		String[] keysfrom = { "TAB", "F6", "F8", "SPACE", "ENTER",
			"UP", "DOWN", "PAGE_UP", "PAGE_DOWN", "HOME", "END", "ESCAPE", "RIGHT", "LEFT", "BACK_SPACE",
			"DELETE", "A", "X", "C", "V" };
		String[] keysto = { "'\\t'", "0x75", "0x77", "0x20", "'\\n'",
			"0x26", "0x28", "0x21", "0x22", "0x24", "0x23", "0x1B", "0x27", "0x25", "'\\b'",
			"0x7F", "0x41", "0x58", "0x43", "0x56" };
		for (int i = 0; i < keysfrom.length; i++) {
			source = change(source, "KeyEvent.VK_" + keysfrom[i], keysto[i]);
		}

		FileOutputStream fos = new FileOutputStream(destDir + "/thinlet/midp/Thinlet.java");
		fos.write(source.getBytes());
		fos.close();
	}

	private static String change(String source, String oldvalue, String newvalue) {
		return change(source, oldvalue, newvalue, null, null);
	}

	static StringBuffer sb = new StringBuffer();

	private static String change(String source, String oldvalue, String newvalue,
			String oldend, String newend) {
		System.out.println("change(" + oldvalue + ", " + newvalue + ", " + oldend + ", " + newend + ")");
		int index = 0;
		while ((index = source.indexOf(oldvalue, index)) != -1) {
			if (oldend == null) {
				sb.setLength(0);
				sb.append(source.substring(0, index));
				sb.append(newvalue);
				sb.append(source.substring(index + oldvalue.length()));
				source = sb.toString();
			} else {
				int ie = source.indexOf(oldend, index + oldvalue.length());
				sb.setLength(0);
				sb.append(source.substring(0, index));
				sb.append(newvalue);
				sb.append(source.substring(index + oldvalue.length(), ie));
				sb.append(newend);
				sb.append(source.substring(ie + oldend.length()));
				source = sb.toString();
			}
		}
		return source;
	}
}
