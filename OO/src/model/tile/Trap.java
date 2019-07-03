package model.tile;

import controller.mainController.Prompt;
import controller.mapReader.TileManager;
import model.player.Player;

public class Trap extends Decorator {

	public Trap(Tile base) {
		super(base);
		es = Prompt.getInstance();
	}

	// trap the player who stepped on trap, the undecorate itself, will no trap prisoners
	public void trapPlayer(Player player) {
		if (player.getTrapped()) {
			es.setMessage("TRAPPED!: " + player.getName());
			Tile floor = getPlainTile();
			TileManager.undecorate(floor, super.getPos().getSeq());
		}
	}


	@Override
	public void interact(Player player) throws Exception {
		player.walk();
		this.trapPlayer(player);

	}

}