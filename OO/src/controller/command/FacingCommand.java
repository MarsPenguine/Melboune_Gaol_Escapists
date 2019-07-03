package controller.command;

import controller.mainController.GraphicControl;
import controller.mainController.GameControl;
import utility.DIR;
import model.player.Player;

public class FacingCommand implements Command {
	private final int currentPlayer;
	private final DIR dir;

	public FacingCommand(int currentPlayer, DIR dir) {
		this.currentPlayer = currentPlayer;
		this.dir = dir;
	}

	@Override
	public void execute() {
		GameControl gc=GameControl.getInstance();
		Player player = gc.getPlayers().get(currentPlayer);
		player.setFacing(dir);
		GraphicControl.getInstance().refresh();
	}

}
