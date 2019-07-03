package model.factories;

import utility.Position;
import model.player.A47;
import model.player.Guard;
import model.player.Havoc;
import model.player.Jager;
import model.player.LockSmith;
import model.player.Player;
import model.player.Warden;

public class PlayerFactory extends FactoryAdaptor {

	@Override
	public Player createPlayer(Position pos, String t, String name) {
		
		String type = t.toLowerCase();
		
		switch (type) {
		case "havoc":
			Havoc havoc = new Havoc(pos, name);
			return havoc;
		case "jager":
			Jager jager = new Jager(pos, name);
			return jager;
		case "a47":
			A47 a47 = new A47(pos, name);
			return a47;
		case "locksmith":
			LockSmith locksmith = new LockSmith(pos, name);
			return locksmith;
		case "warden":
			Warden warden = new Warden(pos, name);
			return warden;
		case "guard":
			Guard guard = new Guard(pos, name);
			return guard;

		default:
			return null;
		}

	}

}
