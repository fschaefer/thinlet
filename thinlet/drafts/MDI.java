package thinlet.drafts;

import java.awt.*;
import thinlet.*;

/**
 *
 */
public class MDI {
	
	/**
	 *
	 */
	public void newDialog(Thinlet thinlet, Object desktop) {
		try {
			Object dialog = thinlet.parse("mdidialog.xml", this);
			thinlet.add(desktop, dialog, 0);
		} catch (Exception exc) {}
	}
	
	/**
	 *
	 */
	public void closeDialog(Thinlet thinlet, Object dialog) {
		thinlet.remove(dialog);
	}
}