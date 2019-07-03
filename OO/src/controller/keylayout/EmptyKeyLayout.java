package controller.keylayout;

import java.awt.event.KeyEvent;

public class EmptyKeyLayout extends KeyLayout {
	private static EmptyKeyLayout instance;

	public EmptyKeyLayout() {

	}
	public static EmptyKeyLayout getInstance() {
		if (instance == null) {
			synchronized (EmptyKeyLayout.class) {
				if (instance == null) {
					EmptyKeyLayout gc = new EmptyKeyLayout();
					instance = gc;
				}
			}
		}
		return instance;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("freeze");

	}

}
