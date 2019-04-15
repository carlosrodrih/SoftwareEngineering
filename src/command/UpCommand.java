package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class UpCommand extends CommandWithShortcut {

	public UpCommand() {
		super("up", "u", ": moves the player up.");
	}

	@Override
	public synchronized void execute(Player player) {
		PlayerMover.move(player, Direction.UP);
	}

}
