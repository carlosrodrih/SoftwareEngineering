package command;

import util.PlayerMover;
import world.Direction;
import world.Player;
import world.World;

public class ShutdownCommand extends Command {
	private World world;
	
	public ShutdownCommand() {
		super("shutdown", ": saves the MUD's data and then shuts the system down. (only game administrators can use this)");
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();
		
		if (player.getName().equals("administrator")) {
			world.saveWorld();
			System.exit(0);
		}
		// add else condition?
	}

}
