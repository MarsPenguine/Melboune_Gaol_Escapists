package controller.mainController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import controller.command.Command;
import view.SetupJFame;

public class History implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7016604426828708071L;
	private ArrayList<Command> stepList;
	private static History instance;
	private JFileChooser fileChooser;

	public static History getInstance() {
		if (instance == null) {
			synchronized (GameControl.class) {
				if (instance == null) {
					History gc = new History();
					instance = gc;
				}
			}
		}
		return instance;
	}

	public History() {
		stepList = new ArrayList<Command>();
	}

	/**
	 * add a command to stepList
	 * @param turn
	 */
	public void addTurn(Command turn) {
		stepList.add(turn);
	}

	private void setDefaultFile() {
		fileChooser = SetupJFame.getInstance().getFc();
		File file = new File("MyTest.sav");
		fileChooser.setCurrentDirectory(file);
		fileChooser.setSelectedFile(file);
	}

	/**
	 * save current stepList to a file
	 * @return
	 */
	public String saveToFile() {
		setDefaultFile();
		fileChooser.showSaveDialog(null);
		File file = fileChooser.getSelectedFile();
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(stepList);
			oos.close();
			return "Save Success!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Save Failed!";
		}
	}

	/**
	 * load a list of steps from a save file
	 * 
	 * @return
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public ArrayList<Command> LoadFromFile() throws IOException, NullPointerException {
		setDefaultFile();
		fileChooser.showOpenDialog(null);
		File file = fileChooser.getSelectedFile();
		FileInputStream fis;
		ObjectInputStream ois;
		fis = new FileInputStream(file);
		ois = new ObjectInputStream(fis);
		try {
			return (ArrayList<Command>) ois.readObject();
		} catch (ClassNotFoundException e) {
			return null;
		}

	}

	/**
	 * load a list of steps from a save file then reproduce the game from that list
	 * 
	 * @throws InterruptedException
	 */
	public void reproduceHistory() throws InterruptedException {
		try {
			this.stepList = LoadFromFile();
		} catch (IOException e) {
			Prompt.getInstance().setMessage("File not found");
			return;
		} catch (NullPointerException e) {
			Prompt.getInstance().setMessage("No file choosen");
		}
		GameControl.getInstance().reset();
		GameControl.getInstance();
		for (int i = 0; i < stepList.size(); i++) {
			Prompt.getInstance().setMessage("redoing step " + (i + 1));
			stepList.get(i).execute();
		}
	}

	/**
	 * undo 
	 * @param targetTurn
	 * @throws InterruptedException
	 */
	public void undo(int targetTurn) throws InterruptedException {
		int totalTurns = GameControl.getInstance().getTurns();
		if (targetTurn > totalTurns) {
			targetTurn = totalTurns;
		}
		targetTurn = GameControl.getInstance().getTurns() - targetTurn;

		// create a temporary steplist, reset the original list then store new steps(Commands) in it
		ArrayList<Command> tempTurnList = new ArrayList<Command>();
		tempTurnList = stepList;
		stepList = new ArrayList<Command>();

		// reset GameControl
		GameControl.getInstance().reset();
		GameControl.getInstance();

		for (int i = 0; i < tempTurnList.size(); i++) {
			Prompt.getInstance().setMessage("undoing");
			tempTurnList.get(i).execute();
			stepList.add(tempTurnList.get(i));
			// keep executing command form steplist till turn counter in GameControl hits target turn
			if (GameControl.getInstance().getTurns() == targetTurn) {
				return;
			}
		}
	}
}
