package thinlet.drafts;

import thinlet.*;

public class ProgressMonitor implements Runnable {
	
	public void start(Thinlet thinlet) throws Exception {
		thinlet.add(thinlet.parse("progressdialog.xml", this));
	}
	
	private Thinlet thinlet;
	private Object dialog, status, progressbar;
	
	public void init(Thinlet thinlet, Object dialog, Object status, Object progressbar) {
		this.thinlet = thinlet;
		this.dialog = dialog; this.status = status; this.progressbar = progressbar;
		new Thread(this).start();
	}

	public void run() {
		for (int i = 1; i <= 10; i++) {
			thinlet.setString(status, "text", "Completed " + i + " out of 10.");
			thinlet.setInteger(progressbar, "value", i * 10);
			try {
				Thread.sleep(450);
			} catch (InterruptedException ie) {}
		}
		thinlet.remove(dialog);
	}
	
	public void close(Thinlet thinlet, Object dialog) {
		thinlet.remove(dialog);
	}
	
	public void cancel(Thinlet thinlet, Object dialog) {
		thinlet.remove(dialog);
	}
}