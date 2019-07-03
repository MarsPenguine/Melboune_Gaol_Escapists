package controller.keylayout;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.JOptionPane;

import controller.mainController.GraphicControl;
import controller.mainController.GameControl;
import controller.mainController.History;
import controller.observer.StateObserver;
import controller.mainController.Prompt;
import controller.command.ArrowKeyCommand;
import controller.command.Command;
import controller.command.FacingCommand;
import controller.command.IdleCommand;
import controller.command.SpellKeyCommand;
import utility.DIR;
import model.player.Player;
import model.player.PlayerStat;
import model.spell.ArrestVisitor;
import model.spell.BreakWallVisitor;
import model.spell.ChargeVisitor;
import model.spell.DisguseVisitor;
import model.spell.PickDoorVisitor;
import model.spell.PlaceTrapVisitor;
import model.spell.PlayerVisitor;
import view.SetupJFame;

public class DefaultKeyLayout extends KeyLayout {

	private PlayerVisitor pickDoor = new PickDoorVisitor();
	private PlayerVisitor breakWall = new BreakWallVisitor();
	private PlayerVisitor disguse = new DisguseVisitor();
	private PlayerVisitor placeTrap = new PlaceTrapVisitor();
	private PlayerVisitor arrest = new ArrestVisitor();
	private History history;
	private GraphicControl drawingControl;
	private static DefaultKeyLayout instance;
	private ArrayList<Observer> observers;
	private GameControl gc;
	private Prompt prompt;

	// singleton
	public static DefaultKeyLayout getInstance() {
		if (instance == null) {
			synchronized (DefaultKeyLayout.class) {
				if (instance == null) {
					DefaultKeyLayout akm = new DefaultKeyLayout();
					instance = akm;
				}
			}
		}
		return instance;
	}

	public DefaultKeyLayout() {
		prompt = Prompt.getInstance();
		drawingControl = GraphicControl.getInstance();
		drawingControl.refresh();
		observers = new ArrayList<>();
		history = History.getInstance();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		/*
		 * when a key is pressed get player information from GameControl
		 */
		gc = GameControl.getInstance();
		int count = gc.getPlayerCounter();
		Player currentPlayer = gc.getCurrentPlayer();

		// ====================== ESC =====================
		if (e.getKeyCode() == 0x1B) {
			if (observers.size() == 0) {
				StateObserver kc = StateObserver.getInstance();
				observers.add(kc);
			}
			notifyObservers("pause");
			return;
		}

		// ====================== T =====================
		if (e.getKeyCode() == 0x54) {
			Object[] possibilities = { "1", "2", "3", "load another universe" };
			String s = (String) JOptionPane.showInputDialog(SetupJFame.getInstance(),
					"How many turns would you like to undo", "Undo", JOptionPane.PLAIN_MESSAGE, null, possibilities,
					"1");
			try {
				if (s.contains("load")) {
					history.reproduceHistory(); // load another game
				} else {
					history.undo(Integer.parseInt(s)); // undo
				}
			} catch (InterruptedException | NumberFormatException | NullPointerException e1) {
				prompt.setMessage("canceled");
			}

		}

		// ====================== Y =====================
		if (e.getKeyCode() == 0x59) {
			prompt.setMessage(history.saveToFile()); // save game
		}

		if (currentPlayer.getStatus() != PlayerStat.trapped && currentPlayer.getStatus() != PlayerStat.arrested) {
			// ==================cast spell==================
			int keyCode = e.getKeyCode();
			if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
				castSkill(count, keyCode);
				drawingControl.drawMoving();
				return;
			}
			// ==============================================

			// ==================move==================
			movement(count, e);
			// ========================================
		} else {
			// if player is immobilized instantiate an idle command for undo/load game purposes.
			System.err.println("wait");
			Command command = new IdleCommand(count);
			command.execute();
			history.addTurn(command);
			drawingControl.refresh();
		}

	}

	// converting a key event to a direction.
	private DIR getDir(KeyEvent e) {
		int key = e.getKeyCode();
		DIR dir = null;
		switch (key) {
		case KeyEvent.VK_UP:
			dir = DIR.up;
			break;
		case KeyEvent.VK_DOWN:
			dir = DIR.down;
			break;
		case KeyEvent.VK_LEFT:
			dir = DIR.left;
			break;
		case KeyEvent.VK_RIGHT:
			dir = DIR.right;
			break;
		default:
			break;
		}

		return dir;
	}

	private void castSkill(int count, int keyCode) {
		Command command;
		switch (keyCode) {
		case KeyEvent.VK_A:
			PlayerVisitor pv = new ChargeVisitor();
			command = new SpellKeyCommand(count, pv);
			break;
		case KeyEvent.VK_Q:
			command = new SpellKeyCommand(count, breakWall);
			break;
		case KeyEvent.VK_W:
			command = new SpellKeyCommand(count, placeTrap);
			break;
		case KeyEvent.VK_E:
			command = new SpellKeyCommand(count, pickDoor);
			break;
		case KeyEvent.VK_R:
			command = new SpellKeyCommand(count, disguse);
			break;
		case KeyEvent.VK_F:
			command = new SpellKeyCommand(count, arrest);
			break;
		default:
			return;
		}
		command.execute();
		history.addTurn(command);
	}

	private void movement(int count, KeyEvent e) {
		Command command;
		DIR dir = getDir(e);
		/*
		 *  if user's input matches character's facing, then move the character, else change the facing of the character
		 *  so that user can choose a direction to cast a spell
		 */
		if (dir == gc.getCurrentPlayer().getFacing()) {
			command = new ArrowKeyCommand(count, dir);
			command.execute();
			history.addTurn(command);
		} else {
			Command fCommand = new FacingCommand(count, dir);
			fCommand.execute();
			history.addTurn(fCommand);
		}
	}

	public void notifyObservers(String args) {
		for (Observer observer : observers) {
			((StateObserver) observer).update(args);
		}
	}
}
