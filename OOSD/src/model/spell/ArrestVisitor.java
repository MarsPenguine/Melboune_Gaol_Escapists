package model.spell;

import com.google.java.contract.Requires;

import controller.mainController.GameControl;
import controller.mainController.Prompt;
import model.player.Guard;
import model.player.LawEnforcer;
import model.player.Player;
import model.player.PlayerStat;
import model.player.Warden;

public class ArrestVisitor extends VisitorAdapter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3438264664254752954L;

	public ArrestVisitor() {
		
		spellRange=1;
		requiredStamina=0;
	}
	@Override
	public void visit(Warden warden) {
		arrest(warden);
	}

	@Override
	public void visit(Guard guard) {
		arrest(guard);
	}
	@Requires("!player.checkStamina(requiredStamina)")
	public void arrest(LawEnforcer player) {
		GameControl gc=GameControl.getInstance();
		if (!player.checkStamina(requiredStamina)) {
			return;
		}

		boolean done=false;
		for (int i = 0; i < gc.getPlayers().size(); i++) {
			Player targetPlayer =  gc.getPlayers().get(i);
			if (targetPlayer instanceof LawEnforcer) {
				return;
			}
			if (player.withinRange(targetPlayer,1)&&!player.getPos().equals(targetPlayer.getPos())) {
				targetPlayer.setStatus(PlayerStat.arrested);
				Prompt.getInstance().setMessage(player.getName() + " arrested " + targetPlayer.getName());
				done=true;
				gc.setLawScore();
			}
		}
		if (!done) {
			System.out.println("no player within range;");
		}
	}

}
