package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class NorthCommand extends CommandWithShortcut {

	public NorthCommand() {
		super("north", "n", ": moves the player north.");
	}

	@Override
	public synchronized void execute(Player player) {
		PlayerMover.move(player, Direction.NORTH);
	}

}
