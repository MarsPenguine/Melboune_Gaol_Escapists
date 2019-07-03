package model.spell;

import com.google.java.contract.Requires;

import controller.mainController.Prompt;
import controller.mapReader.TileManager;
import model.player.Jager;
import model.tile.Trap;

public class PlaceTrapVisitor extends VisitorAdapter {
	public PlaceTrapVisitor() {
		requiredStamina = 3;
	}

	@Override
	public void visit(Jager jager) {
		placeTrap(jager);

	}

	@Requires("!jager.checkStamina(requiredStamina)")
	private void placeTrap(Jager jager) {
		boolean done = false;
		if (!jager.checkStamina(requiredStamina)) {
			return;
		} else {
			int pos = jager.getPos().getSeq();
			Trap trap = new Trap(TileManager.getTileType(pos));
			TileManager.getTileMaps().set(pos, trap);
			jager.deductStamina(requiredStamina);
			Prompt.getInstance().setMessage("TRAP PLACED @ " + pos);
			done = true;
		}

		if (!done) {
			System.out.println("cant place trap here");
		}
	}

}
