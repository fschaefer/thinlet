package thinlet.examples.demo;
import thinlet.*;

public class Calculator extends Thinlet {

	public Calculator() {
		try {
			add(parse("calculator.xml"));
		} catch (Exception exc) { exc.printStackTrace(); }
	}
	
	public void calculate(String number1, String number2, Object result) { // Widget result
		try {
			int i1 = Integer.parseInt(number1);
			int i2 = Integer.parseInt(number2);
			setString(result, "text", String.valueOf(i1 + i2));
			// result.set("text", String.valueOf(i1 + i2));
		} catch (NumberFormatException nfe) {
			getToolkit().beep();
		}
	}
	
	public static void main(String[] args) {
		new FrameLauncher("Calculator", new Calculator(), 320, 240);
	}
}
