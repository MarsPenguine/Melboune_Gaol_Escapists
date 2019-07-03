package model.factories;

import java.awt.image.BufferedImage;

import utility.Position;
import model.player.Player;
import model.tile.Tile;

public class FactoryAdaptor implements Factory {

	@Override
	public Player createPlayer(Position pos, String type, String name) {
		return null;
	}

	@Override
	public Tile createTile(BufferedImage img, Position pos, int w, int h) {
		return null;
	}

	
}
