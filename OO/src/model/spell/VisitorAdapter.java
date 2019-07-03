package model.spell;

import controller.mainController.Prompt;
import model.player.A47;
import model.player.Guard;
import model.player.Havoc;
import model.player.Jager;
import model.player.LockSmith;
import model.player.Warden;

public abstract class VisitorAdapter implements PlayerVisitor {
	protected int requiredStamina;
	protected int spellRange;
	protected int spellNo;
	protected Prompt es;

	public VisitorAdapter() {
		requiredStamina = 3;
		es=Prompt.getInstance();
	}

	public void visit(Havoc havoc) {
		System.out.println("you cant use this ability");
		es.setMessage("you cant use this ability");
	};

	public void visit(Jager jager) {
		System.out.println("you cant use this ability");es.setMessage("you cant use this ability");
	};

	public void visit(A47 a47) {
		System.out.println("you cant use this ability");es.setMessage("you cant use this ability");
	};

	public void visit(LockSmith locksmith) {
		System.out.println("you cant use this ability");es.setMessage("you cant use this ability");
	};

	public void visit(Warden warden) {
		System.out.println("you cant use this ability");es.setMessage("you cant use this ability");
	};

	public void visit(Guard guard) {
		System.out.println("you cant use this ability");es.setMessage("you cant use this ability");
	};

}
