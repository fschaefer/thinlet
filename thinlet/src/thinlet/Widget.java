package thinlet;

import java.awt.*;

/**
 *
 */
public class Widget {
	
	private transient Thinlet thinlet;
	private transient Object widget;
	
	/**
	 *
	 */
	Widget(Thinlet thinlet, Object widget) {
		this.thinlet = thinlet;
		this.widget = widget;
	}
	
	public void set(String key, String value) {
		thinlet.setString(widget, key, value);
	}
}

//jikes -classpath \java\j2sdk1.4.1\jre\lib\rt.jar;%CLASSPATH% thinlet\drafts\*.java thinlet\designer\*.java thinlet\common\*.java
//java -nojit thinlet.drafts.Drafts
