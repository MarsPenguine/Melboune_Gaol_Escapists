package model.player;

import utility.Position;
import model.spell.PlayerVisitor;

public class Warden extends LawEnforcer {

	public Warden(Position pos, String name) {
		super(pos, name);
		super.FOV = 5;
		super.imageChoosenNum = 3;
	}

	@Override
	public void accpet(PlayerVisitor v) {
		v.visit(this);
	}

}
