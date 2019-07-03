package model.tile;

import java.awt.image.BufferedImage;

import controller.mainController.Prompt;
import model.player.Player;

public class Wall extends Decorator {
	public Wall(BufferedImage img,Tile base) {
		super(img, base);
		es = Prompt.getInstance();
	}
	@Override
	public void interact(Player player) throws Exception {
		es.setMessage("wall ahead");
		throw new Exception("wall ahead");
	}

}
