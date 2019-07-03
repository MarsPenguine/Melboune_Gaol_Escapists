package controller.observer;

import java.util.Observable;
import java.util.Observer;

import controller.mainController.KeyLayoutController;
import controller.keylayout.DefaultKeyLayout;
import controller.keylayout.EmptyKeyLayout;
import controller.keylayout.PausedKeyLayout;

public class StateObserver implements Observer {
	private DefaultKeyLayout defaultKeyLayout;
	private EmptyKeyLayout standbyKeyLayout;
	private PausedKeyLayout pauseKeyLayout;
	private static StateObserver instance = null;

	public StateObserver() {
		defaultKeyLayout = (DefaultKeyLayout.getInstance());
		standbyKeyLayout = (EmptyKeyLayout.getInstance());
		pauseKeyLayout = (PausedKeyLayout.getInstance());
	}

	public static StateObserver getInstance() {
		if (instance == null) {
			synchronized (StateObserver.class) {
				if (instance == null) {
					StateObserver gc = new StateObserver();
					instance = gc;
				}
			}
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	// call StateController to change key board layout when game state changed
	public void update(String args) {
		KeyLayoutController stateContext = KeyLayoutController.getInstance();

		switch (args) {
		case "drawing":
			stateContext.setKeyLaylout(standbyKeyLayout);
			break;
		case "not drawing":
			stateContext.setKeyLaylout(defaultKeyLayout);
			break;
		case "pause":
			stateContext.setKeyLaylout(pauseKeyLayout);
			break;
		default:
			break;
		}
	}

}
