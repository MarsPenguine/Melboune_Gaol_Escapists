package controller.keylayout;

import java.awt.event.KeyEvent;

import controller.mainController.GraphicControl;
import controller.observer.StateObserver;
import controller.mainController.Prompt;

public class PausedKeyLayout extends KeyLayout {
	private static PausedKeyLayout instance;
	private Prompt es = Prompt.getInstance();

	public PausedKeyLayout() {
	}

	public static PausedKeyLayout getInstance() {
		if (instance == null) {
			synchronized (StateObserver.class) {
				if (instance == null) {
					PausedKeyLayout gc = new PausedKeyLayout();
					instance = gc;
				}
			}
		}
		return instance;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			es.setMessage("Game Unpaused");
			StateObserver.getInstance().update("not drawing");
			GraphicControl.getInstance().refresh();
		} else {
			es.setMessage("Game Paused");
			GraphicControl.getInstance().refresh();
		}
	}
}
