package controller.command;

import controller.mainController.GameControl;
import model.player.Player;

public class IdleCommand implements Command {
	private final int currentPlayer;
	public IdleCommand(int count) {
		currentPlayer=count;
	}

	@Override
	public void execute() {
		GameControl gc=GameControl.getInstance();
		Player player=gc.getPlayers().get(currentPlayer);
		player.recover();
		
		gc.selectNextPlayer();

	}

}
