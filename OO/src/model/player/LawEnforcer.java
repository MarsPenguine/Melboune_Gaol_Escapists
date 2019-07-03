package model.player;

import utility.Position;

public abstract class LawEnforcer extends Player {
	private int trapTimer;
	public LawEnforcer(Position pos, String name) {
		super(pos, name);
		this.trapTimer = 3;
		super.imageChoosenNum = 3;
	}

	@Override
	// set the player's status to trapped when stepped on a trap
	public boolean getTrapped() {
		this.setStatus(PlayerStat.trapped);
		return true;
	}

	@Override
	// if trapped, the player can not move until trapTimer hits 0
	public void recover() {
		trapTimer -= 1;
		if (trapTimer == 0) {
			this.setStatus(PlayerStat.normal);
			trapTimer = 3;
		}

	}


}
