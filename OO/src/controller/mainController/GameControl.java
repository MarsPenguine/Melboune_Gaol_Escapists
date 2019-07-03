package controller.mainController;

import java.util.ArrayList;

import com.google.java.contract.Ensures;

import controller.mapReader.XML;
import utility.Position;
import model.factories.Factory;
import model.factories.PlayerFactory;
import model.player.Player;

public class GameControl  {
	private final int gameSize = 29;
	private int playerCounter = 0;
	private static GameControl instance = null;
	private ArrayList<Player> players;
	private int turns;
	private int prisonerScore;
	private int lawScore;

	public GameControl() {
		initilize();
		prisonerScore = 0;
		lawScore = 0;
	}

	public int getPrisonerScore() {
		return prisonerScore;
	}

	public void setPrisonerScore() {
		this.prisonerScore++;
	}

	public int getLawScore() {
		return lawScore;
	}

	public void setLawScore() {
		this.lawScore++;
	}

	public static GameControl getInstance() {
		if (instance == null) {
			synchronized (GameControl.class) {
				if (instance == null) {
					GameControl gc = new GameControl();
					instance = gc;
				}
			}
		}
		return instance;
	}

	public int getGameSize() {
		return gameSize;
	}

	public Player getCurrentPlayer() {
		return this.players.get(playerCounter);
	}

	public Player getPreviousPlayer() {
		if (playerCounter == 0) {
			return this.players.get(players.size() - 1);
		} else {
			return this.players.get(playerCounter - 1);
		}
	}
	
	@Ensures("instance == null")
	public void reset() {
		instance = null;
		try {
			XML.initilizeMap();
		} catch (Exception e) {
		}
	}

	private void initilize() {
		players = new ArrayList<Player>();

		Factory factory = new PlayerFactory();
		Player h = factory.createPlayer(new Position(7, 5), "havoc", "Test Havoc");
		Player j = factory.createPlayer(new Position(7, 6), "jager", "Test Jager");
		Player a = factory.createPlayer(new Position(6, 7), "A47", "Test A47");
		Player l = factory.createPlayer(new Position(7, 8), "locksmith", "Test LockSmith");
		Player g = factory.createPlayer(new Position(12, 5), "guard", "Test Guard");
		Player w = factory.createPlayer(new Position(12, 6), "warden", "Test Warden");

		players.add(h);
		players.add(j);
		players.add(a);
		players.add(l);
		players.add(g);
		players.add(w);

		playerCounter = 0;

	}

	public void selectNextPlayer() {
		if (playerCounter < players.size() - 1) {
			setPlayerCounter();
			;
		} else {
			setPlayerCounter(0);
			setTurns(getTurns() + 1);
		}

	}



	public void setPlayerCounter() {
		this.playerCounter++;
	}

	public void setPlayerCounter(int x) {
		this.playerCounter = x;
	}

	public int getPlayerCounter() {
		return playerCounter;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

}