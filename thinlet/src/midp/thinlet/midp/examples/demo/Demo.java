package thinlet.midp.examples.demo;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

import thinlet.midp.*;

public class Demo extends MIDlet {
	
	Display display;

	protected void startApp() throws MIDletStateChangeException {
		display = Display.getDisplay(this);
		DemoCanvas canvas = new DemoCanvas(this); 
		display.setCurrent(canvas);
	}

	protected void pauseApp() {
	}
	
	void destroyImpl() {
		try {
			destroyApp(false);
			notifyDestroyed();
		} catch (MIDletStateChangeException  mste) {}
	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
			display.setCurrent(null);
	}
}

class DemoCanvas extends Thinlet {
	
	private static final Command exitcommand = new Command("Exit", Command.EXIT, 0);
	private Demo midlet;

	public DemoCanvas(Demo midlet) {
		this.midlet = midlet;
		try {
			add(parse("demo.xml"));
		} catch (Exception exc) { exc.printStackTrace(); }
		addCommand(exitcommand);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		if (command == exitcommand) {
			midlet.destroyImpl();
		}
		else super.commandAction(command, displayable);
	}
	
	protected void handle(Object source, Object part, String action) {
		if ("selectlist".equals(action)) {
			int index = getInteger(find("selectmode"), "selected");
			setBoolean(find("demolist"), "visible", index == 0);
			setBoolean(find("demotree"), "visible", index == 1);
			setBoolean(find("demotable"), "visible", index == 2);
		}
	}
}
