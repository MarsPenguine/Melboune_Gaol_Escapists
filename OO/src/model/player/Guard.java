package model.player;

import utility.Position;
import model.spell.PlayerVisitor;

public class Guard extends LawEnforcer {

	public Guard(Position pos, String name) {
		super(pos, name);
		super.FOV=3;
		super.status = PlayerStat.normal;
		super.imageChoosenNum = 2;
	}

	@Override
	public void accpet(PlayerVisitor v) {
		v.visit(this);
		
	}

}
