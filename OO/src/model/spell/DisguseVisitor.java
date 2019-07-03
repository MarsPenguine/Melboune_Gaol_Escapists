package model.spell;

import java.util.ArrayList;

import com.google.java.contract.Requires;

import controller.mainController.GameControl;
import model.player.A47;
import model.player.Player;
import model.player.ProxyPlayer;

public class DisguseVisitor extends VisitorAdapter {
	GameControl gc;

	@Override
	public void visit(A47 player) {
		disguse(player);
		gc = GameControl.getInstance();
	}

	@Requires("!player.checkStamina(requiredStamina)")
	public void disguse(A47 player) {
		boolean done = false;
		if (!player.checkStamina(requiredStamina)) {
			return;
		}

		ArrayList<Player> playerlist = gc.getPlayers();
		for (int i = 0; i < playerlist.size(); i++) {
			if (playerlist.get(i) instanceof A47) {
				playerlist.remove(i);
				ProxyPlayer px = new ProxyPlayer(player.getPos(), player.getName() + " proxy", player);
				playerlist.add(i, px);
				player.deductStamina(requiredStamina);
				done = true;
			}

		}

		if (!done) {
			System.out.println("cant disguse");
		}

	}

}
