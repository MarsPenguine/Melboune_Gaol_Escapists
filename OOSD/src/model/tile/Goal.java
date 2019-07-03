package model.tile;

import java.awt.image.BufferedImage;

import controller.mainController.GameControl;
import model.player.Player;
import model.player.PlayerStat;

public class Goal extends Decorator {

	public Goal(BufferedImage img, Tile base) {
		super(img, base);
	}

	@Override
	public void interact(Player player) throws Exception {
		player.setStatus(PlayerStat.invincible);
		GameControl.getInstance().setPrisonerScore();
		
		es.setMessage("You Win!");
	}

}
