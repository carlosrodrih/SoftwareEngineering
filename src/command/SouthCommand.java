package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class SouthCommand extends CommandWithShortcut {

	public SouthCommand() {
		super("south", "s", ": moves the player south.");
	}

	@Override
	public synchronized void execute(Player player) {
		PlayerMover.move(player, Direction.SOUTH);
	}

}
