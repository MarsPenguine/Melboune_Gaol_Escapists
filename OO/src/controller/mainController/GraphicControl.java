package controller.mainController;

import java.util.Observable;

import static java.lang.Thread.sleep;

public class GraphicControl extends Observable {
	private static GraphicControl instance;

	public static GraphicControl getInstance() {
		if (instance == null) {
			synchronized (GraphicControl.class) {
				if (instance == null) {
					GraphicControl gc = new GraphicControl();
					instance = gc;
				}
			}
		}
		return instance;
	}

	public GraphicControl() {
	}

	public void drawMoving() {
		try {
			draw();
			sleep(1300);
			stopDrawing();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void refresh() {
		try {
			draw();
			sleep(200);
			stopDrawing();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		setChanged();
		notifyObservers("draw");
	}

	public void stopDrawing() {
		setChanged();
		notifyObservers("stop");
	}

}
