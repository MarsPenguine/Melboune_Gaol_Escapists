package controller.mainController;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.keylayout.DefaultKeyLayout;
import controller.keylayout.EmptyKeyLayout;
import controller.keylayout.KeyLayout;

public class KeyLayoutController extends KeyAdapter {
	private KeyLayout state;
	private KeyLayout defaultState;
	private static KeyLayoutController instance;

	public KeyLayoutController() {
		defaultState = DefaultKeyLayout.getInstance();
		state = defaultState;
	}

	public static KeyLayoutController getInstance() {
		if (instance == null) {
			synchronized (KeyLayoutController.class) {
				if (instance == null) {
					KeyLayoutController gc = new KeyLayoutController();
					instance = gc;
				}
			}
		}
		return instance;
	}

	public KeyLayout getState() {
		return this.state;
	}

	public void setKeyLaylout(KeyLayout newState) {
		state = newState;
	}

	public void setKeyLaylout() {
		if (state instanceof EmptyKeyLayout) {
			state = defaultState;
		}
	}

	@Override
	// forward key event to keylayout classes
	public void keyPressed(KeyEvent e) {
		state.keyPressed(e);
	}
}
