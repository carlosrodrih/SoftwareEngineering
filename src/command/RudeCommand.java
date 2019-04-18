package command;

import util.PlayerMover;
import world.Direction;
import world.Gear;
import world.GearContainer;
import world.Movable;
import world.Player;
import world.Room;
import world.World;

public class RudeCommand extends Command {
	private String _target;
	private World world;

	public RudeCommand() {
		super("rude", ": make a rude gesture. Also: rude <target>: make a rude gesture at a player/MOB.");
		_target = null;
	}
	
	public RudeCommand(String target) {
		super("rude", ": make a rude gesture. Also: rude <target>: make a rude gesture at a player/MOB.");
		_target = target;
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();
		if (_target == null) {
			((Room) player.getLocation()).sendToRoom(player.getName()
					+ " makes a rude gesture!");
		}
		else {
			for (Movable mov : ((Room) player.getLocation()).listMovables()) {
				if (mov.getName().equalsIgnoreCase(_target)) {
					((Room) player.getLocation()).sendToRoom(player
							.getName()
							+ " makes a rude gesture at " + _target + "!");
				}
			}
		}
	}

}
