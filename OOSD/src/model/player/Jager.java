package model.player;

import utility.Position;
import model.spell.PlayerVisitor;

public class Jager extends Prisoner {

	public Jager(Position pos, String name) {
		super(pos, name);
		super.imageChoosenNum = 1;
	}

	@Override
	public void accpet(PlayerVisitor v) {
		v.visit(this);
		
	}

}
