package model.spell;

import com.google.java.contract.Requires;

import controller.mapReader.TileManager;
import utility.Position;
import model.player.Havoc;
import model.player.Player;
import model.tile.Tile;
import model.tile.Wall;

public class BreakWallVisitor extends VisitorAdapter {


	public BreakWallVisitor() {
		super.spellRange = 1;
		spellNo = 1;
	}


	@Override
	public void visit(Havoc havoc) {
		breakWall(havoc);
	}

	@Requires("!player.checkStamina(requiredStamina)")
	private void breakWall(Player player) {
		if (!player.checkStamina(requiredStamina)) {
			return;
		}
		int targetSeq = 0;
		Position targetPosition = player.getNextXPosition(spellRange);
		targetSeq = targetPosition.getSeq();
		Tile nextTile =TileManager.getTileType(targetSeq);
		if (nextTile instanceof Wall) {
			System.out.println("break wall: " + targetSeq);
			Tile floor = ((Wall) nextTile).getPlainTile();
			TileManager.undecorate(floor, targetSeq);
			player.deductStamina(requiredStamina);
		} else
			System.out.println("no wall ahead");
	}

}
