package model.spell;

import java.io.Serializable;

import model.player.A47;
import model.player.Guard;
import model.player.Havoc;
import model.player.Jager;
import model.player.LockSmith;
import model.player.Warden;

public interface PlayerVisitor extends Serializable{

	public void visit(Havoc havoc);

	public void visit(Jager jager);

	public void visit(A47 a47);

	public void visit(LockSmith locksmith);

	public void visit(Warden warden);

	public void visit(Guard guard);
}
