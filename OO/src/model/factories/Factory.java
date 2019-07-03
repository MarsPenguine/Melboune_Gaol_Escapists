package model.factories;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import utility.Position;
import model.player.Player;
import model.tile.Tile;

public interface Factory extends Serializable{
	Player createPlayer(Position pos,String type,String name);
	Tile createTile(BufferedImage img, Position pos, int w,int h);
}
