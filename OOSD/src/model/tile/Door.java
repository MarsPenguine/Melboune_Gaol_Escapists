package model.tile;

import java.awt.image.BufferedImage;

import model.player.Player;

public class Door extends Decorator {
	private boolean doorOpened;

	public Door(BufferedImage img, Tile base) {
		super(img, base);
		this.doorOpened = false;
	}

	public boolean getDoorStatus() {
		return doorOpened;
	}

	public void openDoor() {
		doorOpened = true;
	}

	public void lockDoor() {
		doorOpened = false;
	}

	@Override
	public void interact(Player player) throws Exception {
		if (this.getDoorStatus()) {
			player.walk();
		} else {
			es.setMessage("door locked");
			throw new Exception("door locked");
		}

	}

}
