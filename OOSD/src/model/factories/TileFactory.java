package model.factories;

import java.awt.image.BufferedImage;

import utility.Position;
import model.tile.Door;
import model.tile.Goal;
import model.tile.Tile;
import model.tile.Wall;

public class TileFactory extends FactoryAdaptor {

	@Override
	public Tile createTile(BufferedImage img, Position pos, int w, int h) {
		Tile floor = null;
		return floor;

	}

	public Tile createTile(BufferedImage img, Tile floor, int t) {
		Tile tile = null;
		switch (t) {
		case 3:
			tile = new Door(img, floor);
			break;
		case 1:
			tile = new Wall(img, floor);
			break;
		case 2:
			tile = new Goal(img, floor);
			break;
		}
		return tile;

	}
}
