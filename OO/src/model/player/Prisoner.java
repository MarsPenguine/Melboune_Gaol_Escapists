package model.player;

import utility.Position;

public abstract class Prisoner extends Player {

	public Prisoner(Position pos, String name) {
		super(pos, name);
	}



	@Override
	// a prisoner will not get trapped
	public boolean getTrapped() {
		return false;
	}
	
	@Override
	// a prisoner will not get trapped
	public void recover() {
	}
	

}
