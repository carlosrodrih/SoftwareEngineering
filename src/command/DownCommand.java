package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class DownCommand extends CommandWithShortcut {

	public DownCommand() {
		super("down", "d", ": moves the player down.");
	}

	@Override
	public synchronized void execute(Player player) {
		PlayerMover.move(player, Direction.DOWN);
	}

}
