package model.tile;

import java.awt.image.BufferedImage;

import controller.mainController.Prompt;
import utility.Position;
import model.player.Player;
/*
 * Decorator:
 * attach responsibility at runtime
 * eg. when placing a trap, decorate a Floor(a most basic tile) with Trap(a decorator)
 */
public abstract class Decorator implements Tile {
	protected final Tile plainTile;
	private BufferedImage img;
	protected Prompt es;

	public Decorator(BufferedImage img, Tile base) {
		this.plainTile = base;
		this.img = img;
		es = Prompt.getInstance();
	}

	public Decorator(Tile base) {
		this.plainTile = base;
		this.img = plainTile.getImg();
	}

	public Tile getPlainTile() {
		return this.plainTile;
	}

	@Override
	public void setImg(BufferedImage img) {
		this.img = img;

	}

	@Override
	public BufferedImage getImg() {
		return this.img;
	}

	@Override
	public int getWidth() {
		return plainTile.getWidth();
	}

	@Override
	public int getHeight() {
		return plainTile.getHeight();
	}

	@Override
	public Position getPos() {
		return plainTile.getPos();
	}

	@Override
	public void setPos(Position pos) {
		plainTile.setPos(pos);
	}

	@Override
	public abstract void interact(Player player) throws Exception;

	@Override
	public boolean withinrange(Player player) {
		int tilex = this.getPos().getX();
		int tiley = this.getPos().getY();
		int px = player.getPos().getX();
		int py = player.getPos().getY();
		int fov = player.getFOV();
		boolean x = Math.abs(px - tilex) <= fov;
		boolean y = Math.abs(py - tiley) <= fov;
		if (x && y) {
			return true;
		} else {
			return false;
		}
	}
}
