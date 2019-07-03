package model.player;

import utility.Position;
import model.spell.PlayerVisitor;

public class Havoc extends Prisoner {

	public Havoc(Position pos, String name) {
		super(pos, name);
		super.imageChoosenNum = 0;
	}

	@Override
	public void accpet(PlayerVisitor v) {
		v.visit(this);
		
	}
	

}
