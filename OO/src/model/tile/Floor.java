package model.tile;

import java.awt.image.BufferedImage;

import utility.Position;
import model.player.Player;

public class Floor implements Tile {
	private Position pos;
	private BufferedImage img;
	private int width;
	private int height;

	public Floor(BufferedImage img, Position pos) {
		this.img = img;
		this.width = 32;
		this.height = 32;
		this.pos = pos;
	}

	@Override
	public void setImg(BufferedImage img) {
		this.img = img;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public BufferedImage getImg() {
		return img;
	}

	@Override
	public Position getPos() {
		return pos;
	}

	@Override
	public void setPos(Position pos) {
		this.pos = pos;
	}

	@Override
	public boolean withinrange(Player player) {
		int tilex = this.getPos().getX();
		int tiley = this.getPos().getY();
		int px = player.getPos().getX();
		int py = player.getPos().getY();
		int fov = player.getFOV();
		boolean x = Math.abs(px-tilex)<=fov;
		boolean y = Math.abs(py-tiley)<=fov;
		if (x && y) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void interact(Player player) {
		player.walk();
		
	}

}
