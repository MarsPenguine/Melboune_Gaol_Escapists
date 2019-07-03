package view;

import static java.lang.Thread.sleep;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.mainController.GameControl;
import controller.observer.StateObserver;
import controller.mainController.KeyLayoutController;
import controller.keylayout.DefaultKeyLayout;
import controller.keylayout.EmptyKeyLayout;

public class SetupJFame extends JFrame implements Runnable, Observer {
	private BufferStrategy bs;
	private final JFileChooser fc = new JFileChooser();
	private Boolean isDrawing = true;
	public static final long serialVersionUID = 1L;
	private GamePanel gp;
	private Thread thread;
	private KeyLayoutController KeyMonitorProxy;
	private StateObserver keyController;
	private static SetupJFame instance;
	private ArrayList<Observer> observers;
	private int scale;
	// The menu should show a squared board and the pieces placed on the board

	private SetupJFame() {
		setSize();
		observers = new ArrayList<>();
		KeyMonitorProxy = KeyLayoutController.getInstance();
		keyController = StateObserver.getInstance();
		observers.add(keyController);
		setTitle("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIgnoreRepaint(true);
		setVisible(true);
		pack();
		gp.addKeyListener(KeyMonitorProxy);
	}

	public static SetupJFame getInstance() {
		if (instance == null) {
			synchronized (SetupJFame.class) {
				if (instance == null) {
					SetupJFame gameFrame = new SetupJFame();
					instance = gameFrame;
				}
			}
		}
		return instance;
	}

	public JFileChooser getFc() {
		return fc;
	}

	private void setSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double height = screenSize.getHeight();
		double temp =height / 720.0;
		scale = (int) Math.ceil(temp) -1;
		System.out.println(scale);
	}

	public void addNotify() {
		super.addNotify();

		createBufferStrategy(2);
		bs = getBufferStrategy();
		gp = new GamePanel(bs, 1280, 720, scale);
		add(gp);
		setSize(gp.getSize());
		setLocationRelativeTo(null);
		if (thread == null) {
			thread = new Thread(this, "GameThread");
			thread.start();
		}
	}

	@Override
	public void run() {
		while (true) {
			GameControl gc = GameControl.getInstance();
			if (gc.getPrisonerScore()==4) {
				JOptionPane.showMessageDialog(null, "info", "Prisoner win!", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			if (gc.getLawScore()==4) {
				JOptionPane.showMessageDialog(null, "info", "Law Enforcement win!", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			try {
				sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (isDrawing && KeyMonitorProxy.getState() instanceof DefaultKeyLayout) {
				notifyObservers("drawing");
			}

			while (isDrawing) {
				gp.renderMap();
				gp.render();
				gp.draw();
			}
			if (KeyMonitorProxy.getState() instanceof EmptyKeyLayout) {
				notifyObservers("not drawing");
			}

		}

	}

	public void addListener(Observer listener) {
		observers.add(listener);

	}

	public void removeListener(Observer listener) {
		observers.remove(listener);

	}

	public void notifyObservers(String args) {
		for (Observer observer : observers) {
			((StateObserver) observer).update(args);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		switch (String.valueOf(arg)) {
		case "draw":
			isDrawing = true;
			System.out.println("drawing");
			break;

		case "stop":
			isDrawing = false;
			System.out.println("stop drawing");
			break;
		}
	}
}
