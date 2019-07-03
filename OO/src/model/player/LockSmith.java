package model.player;

import utility.Position;
import model.spell.PlayerVisitor;

public class LockSmith extends Prisoner {

	public LockSmith(Position pos, String name) {
		super(pos, name);
		super.imageChoosenNum = 5;
	}

	

	@Override
	public void accpet(PlayerVisitor sVisitor) {
		sVisitor.visit(this);
		
	}

}
