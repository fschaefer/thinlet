package thinlet.midp.examples.calculator;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

import thinlet.midp.*;

public class Calculator extends MIDlet {
	
	Display display;

	protected void startApp() throws MIDletStateChangeException {
		display = Display.getDisplay(this);
		CalculatorCanvas canvas = new CalculatorCanvas(this); 
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

class CalculatorCanvas extends Thinlet {
	
	private static final Command exitcommand = new Command("Exit", Command.EXIT, 0);
	private Calculator midlet;

	public CalculatorCanvas(Calculator midlet) {
		this.midlet = midlet;
		try {
			add(parse("calculator.xml"));
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
		if ("calculate".equals(action)) {
			try {
				int number1 = Integer.parseInt(getString(find("number1"), "text"));
				int number2 = Integer.parseInt(getString(find("number2"), "text"));
				setString(find("result"), "text", String.valueOf(number1 + number2));
			} catch (NumberFormatException nfe) {}
		}
	}
}
