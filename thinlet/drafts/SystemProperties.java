package thinlet.drafts;

import java.util.*;
import thinlet.*;

/**
 *
 */
public class SystemProperties {
	
	private static final String[] KEYS = { "java.version", "java.vendor",
		"java.vendor.url", "java.home", "java.vm.specification.version",
		"java.vm.specification.vendor", "java.vm.specification.name",
		"java.vm.version", "java.vm.vendor", "java.vm.name", "java.specification.version",
		"java.specification.vendor", "java.specification.name", "java.class.version",
		"java.class.path", "java.library.path", "java.io.tmpdir", "java.compiler",
		"java.ext.dirs", "os.name", "os.arch", "os.version", "file.separator",
		"path.separator", "line.separator", "user.name", "user.home", "user.dir" }; 
	
	/**
	 *
	 */
	public void loadProperties(Thinlet thinlet, Object table) {
		try {
			Properties properties = System.getProperties();
			for (Enumeration keys = properties.propertyNames(); keys.hasMoreElements();) {
				String key = (String) keys.nextElement();
				addRow(thinlet, table, key, properties.getProperty(key));
			}
		} catch (SecurityException exc) { // inside applet
			for (int i = 0; i < KEYS.length; i++) {
				try {
					addRow(thinlet, table, KEYS[i], System.getProperty(KEYS[i]));
				} catch (SecurityException sexc) {}
			}
		}
	}
	
	/**
	 *
	 */
	private void addRow(Thinlet thinlet, Object table, String key, String value) {
		Object row = thinlet.create("row");
		Object keycell = thinlet.create("cell");
		thinlet.setString(keycell, "text", key);
		thinlet.add(row, keycell);
		Object valuecell = thinlet.create("cell");
		thinlet.setString(valuecell, "text", value);
		thinlet.add(row, valuecell);
		thinlet.add(table, row);
	}
}