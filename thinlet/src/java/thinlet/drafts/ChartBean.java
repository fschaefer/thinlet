package thinlet.drafts;

import java.awt.*;
import java.util.*;

public class ChartBean extends Component {
	
	private static int[] colors = { 0x2f505f, 0x207f00, 0x9fa0bf, 0x203f4f,
		0xaf6030, 0x8f3f6f, 0x2f505f, 0x2f505f, 0xa03f4f, 0x207f00, 0x907f40,
		0x9fa0bf, 0x203f4f, 0xaf6030, 0x8f3f6f, 0x603fb0, 0x2f505f, 0xa03f4f,
		0x207f00, 0x2f505f, 0xa03f4f, 0xff8000 };
	private static Random random = new Random();
	
	public void paint(Graphics g) {
		Dimension d = getSize();
		g.setColor(Color.white);
		g.fillRect(0, 0, d.width, d.height);
		for (int i = 0; i < colors.length; i++) {
			int value = 10 + Math.abs(random.nextInt() % 70);
			g.setColor(new Color(colors[i]));
			g.fillRect(10 + i * 10, 100 - value, 8, value);
		}
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(240, 120);
	}
}