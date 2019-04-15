package command;

import util.PlayerMover;
import world.Direction;
import world.Player;
import world.World;

public class SaveCommand extends Command {
	private World world;
	
	public SaveCommand() {
		super("save", ": saves player state in the game.");
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();
		
		if (world.savePlayer(player)) {
			player.sendToPlayer("Player saved.");
		}
	}

}
