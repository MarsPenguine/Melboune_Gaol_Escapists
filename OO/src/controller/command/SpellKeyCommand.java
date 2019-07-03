package controller.command;

import model.player.Player;
import model.spell.PlayerVisitor;
import controller.mainController.GameControl;

public class SpellKeyCommand implements Command {
	final int currentPlayer;
	final PlayerVisitor pv;

	public SpellKeyCommand(int currentPlayer, PlayerVisitor pv) {
		this.currentPlayer=currentPlayer;
		this.pv = pv;
	}

	@Override
	public void execute() {
		GameControl gc=GameControl.getInstance();
		Player player = gc.getCurrentPlayer();
		player.accpet(pv);

	}

}
