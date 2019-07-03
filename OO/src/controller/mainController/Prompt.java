package controller.mainController;

import java.io.Serializable;

public class Prompt implements Serializable{
	private String errorMessage;
	private static Prompt instance;

	public Prompt() {
		setDefault();
	}

	public static Prompt getInstance() {
		if (instance == null) {
			synchronized (GraphicControl.class) {
				if (instance == null) {
					Prompt Prompt = new Prompt();
					instance = Prompt;
				}
			}
		}
		return instance;
	}

	public String getMessage() {
		return errorMessage;
	}

	public void setMessage(String msg) {
		errorMessage = msg;
		new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				setDefault();
			}
		}.start();
	}

	private void setDefault() {
		errorMessage = "";
	};
}
