package controller.command;

import controller.mainController.GraphicControl;
import controller.mainController.GameControl;
import utility.DIR;
import model.player.Player;
import model.player.PlayerStat;

public class ArrowKeyCommand implements Command {
	private final int currentPlayer;
	private final DIR dir;

	public ArrowKeyCommand(int currentPlayer, DIR dir) {
		this.currentPlayer = currentPlayer;
		this.dir = dir;
	}

	@Override
	public void execute() {
		GameControl gc = GameControl.getInstance();
		Player player = gc.getPlayers().get(currentPlayer);
		try {

			player.move(dir);
			if (player.getStatus() != PlayerStat.charged) {
				player.recoverStamina();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			GraphicControl.getInstance().refresh();
			return;
		}
		printInfo(player);
		GraphicControl.getInstance().drawMoving();
	};

	protected void printInfo(Player currentPlayer) {
		GameControl gc = GameControl.getInstance();
		int count = gc.getPlayerCounter();
		System.out.println(currentPlayer.getClass().getSimpleName() + " ");
		System.out.println(" @ " + currentPlayer.getPos().getSeq());
		System.out.println(currentPlayer.getPos());
		System.out.println();

		if (currentPlayer.getStatus() == PlayerStat.charged) {
			return;
		} else {
			gc.selectNextPlayer();
		}
	}

}
