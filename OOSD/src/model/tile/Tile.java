package model.tile;

import java.awt.image.BufferedImage;

import utility.Position;
import model.player.Player;

public interface Tile {
	public void setImg(BufferedImage img);

	public int getWidth();

	public int getHeight();

	public BufferedImage getImg();

	public Position getPos();

	public void setPos(Position pos);
	
	/**
	 * check if this tile is within player's FOV
	 * @param player
	 * @return true if this tile is within player's FOV
	 */
	public boolean withinrange(Player player);
	
	/**
	 * different type of tile have different behavior when interacting with a player
	 */
	public void interact(Player player) throws Exception;
}
