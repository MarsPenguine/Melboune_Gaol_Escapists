package model.spell;

import com.google.java.contract.Requires;

import controller.mapReader.TileManager;
import utility.Position;
import model.player.LockSmith;
import model.player.Player;
import model.tile.Door;
import model.tile.Tile;

public class PickDoorVisitor extends VisitorAdapter {

	public PickDoorVisitor() {
		super.spellRange = 1;
	}

	@Override
	public void visit(LockSmith locksmith) {
		pickLock(locksmith);
	}

	@Requires("!player.checkStamina(requiredStamina)")
	private void pickLock(Player locksmith) {
		boolean done = false;
		if (!locksmith.checkStamina(requiredStamina)) {
			return;
		}
		int targetSeq = 0;
		Position targetPosition = locksmith.getNextXPosition(spellRange);
		targetSeq = targetPosition.getSeq();
		System.out.println(spellRange);
		System.out.println(targetSeq);
		Tile nextTile = TileManager.getTileType(targetSeq);
		System.out.println(nextTile.getClass().getSimpleName());
		if (nextTile instanceof Door) {
			System.out.println("unlocked: " + targetSeq);
			((Door) nextTile).openDoor();
			locksmith.deductStamina(requiredStamina);
			done = true;
		} else
			System.out.println("no door ahead");

		if (!done) {
			System.out.println("no locked door ahead");
		}

	}

}
