package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class WestCommand extends CommandWithShortcut {

	public WestCommand() {
		super("west", "w", ": moves the player west.");
	}

	@Override
	public synchronized void execute(Player player) {
		PlayerMover.move(player, Direction.WEST);
	}

}
