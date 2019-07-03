package model.player;

import java.awt.image.BufferedImage;

import utility.DIR;
import utility.Position;
import model.spell.PlayerVisitor;

public class ProxyPlayer extends LawEnforcer {
	A47 realPlayer; // reference to actual player

	public ProxyPlayer(Position pos, String name, A47 a47) {
		super(pos, name);
		this.realPlayer = a47;
	}

	@Override
	public void accpet(PlayerVisitor sVisitor) {
		sVisitor.visit(realPlayer);
	}

	@Override
	public BufferedImage getStandingImage() {
		if (this.status == PlayerStat.arrested) {
			return sprite.getSprite(7, 0, wh, wh);
		} else
			return sprite.getSprite(0, 4, wh, wh);
	}

	@Override
	// forward method to actual player
	public void move(DIR dir) throws Exception {
		if (this.getStatus() == PlayerStat.arrested) {
			setInvisiable();
			return;
		}
		realPlayer.move(dir);
	}

	@Override
	// forward method to actual player
	public void setFacing(DIR dir) {
		if (dir != null)
			realPlayer.setFacing(dir);
	}

	@Override
	// forward method to actual player
	public DIR getFacing() {
		return realPlayer.getFacing();
	}

	@Override
	// forward every setStatus request to actual player except arrest
	public void setStatus(Enum<PlayerStat> status) {
		if (status == PlayerStat.arrested) {
			this.setStatus(status);
		} else {
			realPlayer.setStatus(status);
		}
	}

	public A47 getRealPlayer() {
		return this.realPlayer;
	}

	private void setInvisiable() {
		this.setPos(new Position(-1, -1));
	}

}
