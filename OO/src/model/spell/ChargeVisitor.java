package model.spell;

import controller.mainController.GameControl;
import model.player.A47;
import model.player.Guard;
import model.player.Havoc;
import model.player.Jager;
import model.player.LockSmith;
import model.player.Warden;

public class ChargeVisitor extends VisitorAdapter {
	public void visit(Havoc havoc) {
		havoc.superCharge();
		GameControl.getInstance().selectNextPlayer();
	};

	public void visit(Jager jager) {
		jager.superCharge();
		GameControl.getInstance().selectNextPlayer();
	};

	public void visit(A47 a47) {
		a47.superCharge();
		GameControl.getInstance().selectNextPlayer();
	};

	public void visit(LockSmith locksmith) {
		locksmith.superCharge();
		GameControl.getInstance().selectNextPlayer();
	};

	public void visit(Warden warden) {
		warden.superCharge();
		GameControl.getInstance().selectNextPlayer();
	};

	public void visit(Guard guard) {
		guard.superCharge();
		GameControl.getInstance().selectNextPlayer();
	};
}
