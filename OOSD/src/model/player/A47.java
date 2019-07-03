package model.player;

import utility.Position;
import model.spell.PlayerVisitor;

public class A47 extends Prisoner {

	public A47(Position pos, String name) {
		super(pos, name);
		super.imageChoosenNum = 4;
	}

	@Override
	public void accpet(PlayerVisitor sVisitor) {
		sVisitor.visit(this);
		
	}

}
